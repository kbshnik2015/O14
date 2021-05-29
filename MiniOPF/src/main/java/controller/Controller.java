package controller;


import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.managers.WorkWaitersManager;
import lombok.Data;
import lombok.Getter;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.AbstractUserDTO;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;
import model.enums.EmployeeStatus;
import model.enums.OrderAim;
import model.enums.OrderStatus;
import model.enums.ServiceStatus;

@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
@Data
public class Controller
{

    @Getter
    private Model model;

    public Controller()
    {
        model = ModelFactory.getModel();
    }

    public AbstractUserDTO getUserByLogin(String login)
    {
        for (CustomerDTO customer : model.getCustomers().values())
        {
            if (login.equals(customer.getLogin()))
            {
                return customer;
            }
        }
        synchronized (model.getEmployees())
        {
            for (EmployeeDTO employee : model.getEmployees().values())
            {
                if (login.equals(employee.getLogin()))
                {
                    return employee;
                }
            }
        }

        throw new IllegalLoginOrPasswordException("User (login: " + login + ") doesn't exist!");
    }

    public AbstractUserDTO login(String login, String password)
    {
        AbstractUserDTO user = getUserByLogin(login);

        synchronized (user)
        {
            if (password.equals(user.getPassword()))
            {
                return user;
            }
        }

        throw new IllegalLoginOrPasswordException("Wrong login or password!");
    }

    public void deleteCustomerCascade(BigInteger id) throws DataNotFoundWarning
    {
        model.checkCustomerExists(id);

        for (ServiceDTO service : getCustomerServices(id))
        {
            model.deleteService(service.getId());
        }
        for (OrderDTO order : getCustomerOrders(id))
        {
            model.deleteOrder(order.getId());
        }

        model.deleteCustomer(id);
    }

    public void deleteEmployeeCascade(BigInteger id) throws DataNotFoundWarning
    {
        model.checkEmployeeExists(id);

        for (OrderDTO order : getEmployeeOrders(id))
        {
            model.deleteOrder(order.getId());
        }

        model.deleteEmployee(id);
    }

    public void deleteEmployee(BigInteger id) throws DataNotFoundWarning
    {
        model.checkEmployeeExists(id);

        for (OrderDTO order : getEmployeeOrders(id))
        {
            order.setEmployeeId(null);
        }

        model.deleteEmployee(id);
    }

    public void deleteOrderCascade(BigInteger id) throws DataNotFoundWarning
    {
        model.checkOrderExists(id);

        if (model.getOrder(id).getServiceId() != null)
        {
            model.deleteService(model.getOrder(id).getServiceId());
        }

        model.deleteOrder(id);
    }

    public void deleteSpecificationCascade(BigInteger id) throws DataNotFoundWarning
    {
        model.checkSpecificationExists(id);

        for (ServiceDTO service : getSpecificationServices(id))
        {
            model.deleteService(service.getId());
        }

        model.deleteSpecification(id);
    }

    public void deleteDistrictCascade(BigInteger id) throws DataNotFoundWarning
    {
        model.checkDistrictExists(id);

        if (getDistrictChildren(id) != null)
        {
            for (DistrictDTO district : getDistrictChildren(id))
            {
                model.deleteDistrict(district.getId());
            }
        }

        model.deleteDistrict(id);
    }

    public void deleteDistrict(BigInteger id, boolean isLeaveChildrenInHierarchy) throws DataNotFoundWarning
    {
        model.checkDistrictExists(id);

        if (getDistrictChildren(id) != null)
        {
            for (DistrictDTO district : getDistrictChildren(id))
            {
                if (isLeaveChildrenInHierarchy)
                {
                    district.setParentId(model.getDistrict(id).getParentId());
                }
                else
                {
                    district.setParentId(null);
                }
            }
        }

        model.deleteDistrict(id);
    }

    public OrderDTO createNewOrder(BigInteger customerId, BigInteger specId)
            throws DataNotCreatedWarning, DataNotUpdatedWarning
    {
        model.checkCustomerExists(customerId);
        model.checkSpecificationExists(specId);
        if (model.getCustomer(customerId).getBalance() < model.getSpecification(specId).getPrice())
        {
            throw new IllegalTransitionException("Customer (id: " + customerId + ") doesn't have enough money");
        }

        OrderDTO order = new OrderDTO(customerId, null, specId, null, OrderAim.NEW, null);
        changeBalanceOn(customerId, model.getSpecification(specId).getPrice() * (-1));
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();

        return order;
    }

    public OrderDTO createSuspendOrder(BigInteger customerId, BigInteger serviceId) throws DataNotCreatedWarning
    {
        model.checkCustomerExists(customerId);
        model.checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerId, serviceId);
        BigInteger specId = model.getService(serviceId).getSpecificationId();
        model.checkSpecificationExists(specId);

        OrderDTO order = new OrderDTO(customerId, null, specId, serviceId, OrderAim.SUSPEND, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();

        return order;
    }

    public OrderDTO createRestoreOrder(BigInteger customerId, BigInteger serviceId) throws DataNotCreatedWarning
    {
        model.checkCustomerExists(customerId);
        model.checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerId, serviceId);
        BigInteger specId = model.getService(serviceId).getSpecificationId();
        model.checkSpecificationExists(specId);

        OrderDTO order = new OrderDTO(customerId, null, specId, serviceId, OrderAim.RESTORE, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();

        return order;
    }

    public OrderDTO createDisconnectOrder(BigInteger customerId, BigInteger serviceId) throws DataNotCreatedWarning
    {
        model.checkCustomerExists(customerId);
        model.checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerId, serviceId);
        BigInteger specId = model.getService(serviceId).getSpecificationId();
        model.checkSpecificationExists(specId);

        OrderDTO order = new OrderDTO(customerId, null, specId, serviceId, OrderAim.DISCONNECT, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();

        return order;
    }

    public void startOrder(BigInteger orderId) throws DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.ENTERING, OrderStatus.IN_PROGRESS);
    }

    public void suspendOrder(BigInteger orderId) throws DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.SUSPENDED);
    }

    public void restoreOrder(BigInteger orderId) throws DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.SUSPENDED, OrderStatus.IN_PROGRESS);
    }

    public void cancelOrder(BigInteger orderId) throws DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);

        OrderDTO order = model.getOrder(orderId);
        if (order.getOrderStatus() == OrderStatus.COMPLETED)
        {
            throw new IllegalTransitionException("Completed order (id: " + orderId + ") can't be cancelled!");
        }
        else if (order.getOrderAim() == OrderAim.NEW)
        {
            changeBalanceOn(order.getCustomerId(), model.getSpecification(order.getSpecId()).getPrice());
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        model.updateOrder(order);
    }

    public void completeOrder(BigInteger orderId) throws DataNotCreatedWarning, DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);
        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.COMPLETED);

        Date payDayPlusMonth = new Date(System.currentTimeMillis() + 2592000000L);
        OrderDTO order = model.getOrder(orderId);

        if (OrderAim.NEW.equals(order.getOrderAim()))
        {
            model.checkSpecificationExists(order.getSpecId());

            ServiceDTO service =
                    new ServiceDTO(payDayPlusMonth, order.getSpecId(), ServiceStatus.ACTIVE, order.getCustomerId());
            BigInteger serviceId = model.createService(service).getId();
            order.setServiceId(serviceId);

            model.updateOrder(order);
        }
        else
        {
            model.checkServiceExists(order.getServiceId());
            ServiceDTO service = model.getService(order.getServiceId());
            if (OrderAim.DISCONNECT.equals(order.getOrderAim()))
            {
                service.setPayDay(null);
                service.setServiceStatus(ServiceStatus.DISCONNECTED);
            }
            else if (OrderAim.SUSPEND.equals(order.getOrderAim()))
            {
                service.setPayDay(null);
                service.setServiceStatus(ServiceStatus.SUSPENDED);
            }
            else if (OrderAim.RESTORE.equals(order.getOrderAim()))
            {
                service.setPayDay(new Date());
                service.setServiceStatus(ServiceStatus.ACTIVE);
            }

            model.updateService(service);
        }
    }

    private void moveOrderFromTo(BigInteger orderId, OrderStatus entering, OrderStatus inProgress) throws
            DataNotUpdatedWarning
    {
        OrderDTO order = model.getOrder(orderId);

        checkOrderInitialStatus(order, entering);

        order.setOrderStatus(inProgress);
        model.updateOrder(order);
    }

    private void checkOrderInitialStatus(OrderDTO order, OrderStatus initialStatus)
    {
        if (order != null)
        {
            if (order.getOrderStatus() != initialStatus)
            {
                throw new IllegalTransitionException("Illegal transition for order (id: " + order.getId() + ")!");
            }
        }
    }

    public void changeBalanceOn(BigInteger customerId, float amountOfMoney) throws DataNotUpdatedWarning
    {
        model.checkCustomerExists(customerId);

        CustomerDTO customer = model.getCustomer(customerId);
        customer.setBalance(customer.getBalance() + amountOfMoney);
        model.updateCustomer(customer);
    }

    public List<OrderDTO> getFreeOrders()
    {
        List<OrderDTO> list = new ArrayList<>();
        for (OrderDTO ord : model.getOrders().values())
        {
            if (ord.getEmployeeId() == null && !OrderStatus.COMPLETED.equals(ord.getOrderStatus()))
            {
                list.add(ord);
            }
        }

        return list;
    }

    public OrderDTO getFreeOrder()
    {
        for (OrderDTO ord : model.getOrders().values())
        {
            if (ord.getEmployeeId() == null && !OrderStatus.COMPLETED.equals(ord.getOrderStatus()))
            {
                return ord;
            }
        }

        return null;
    }

    public List<DistrictDTO> getDistrictChildren(BigInteger id)
    {
        List<DistrictDTO> list = new ArrayList<>();
        for (DistrictDTO district : model.getDistricts().values())
        {
            if (id.equals(district.getParentId()))
            {
                list.add(district);
            }
        }

        return list;
    }

    public Collection<OrderDTO> getCustomerOrders(BigInteger id)
    {
        model.checkCustomerExists(id);

        return model.getOrders().values().stream()
                .filter(order -> id.equals(order.getCustomerId()))
                .collect(Collectors.toList());
    }

    public Collection<OrderDTO> getCustomerActiveOrders(BigInteger customerId)
    {
        return getCustomerOrders(customerId).stream()
                .filter(order -> isOrderActive(order.getId()))
                .collect(Collectors.toList());
    }

    public boolean isOrderActive(final BigInteger orderId)
    {
        OrderDTO order = model.getOrder(orderId);

        return OrderStatus.ENTERING.equals(order.getOrderStatus()) ||
                OrderStatus.IN_PROGRESS.equals(order.getOrderStatus());
    }

    public Collection<ServiceDTO> getCustomerServices(BigInteger id)
    {
        model.checkCustomerExists(id);

        return model.getServices().values().stream()
                .filter(service -> id.equals(service.getCustomerId()))
                .collect(Collectors.toList());
    }

    public List<SpecificationDTO> getCustomerSpecifications(final CustomerDTO customer)
    {
        List<SpecificationDTO> customerSpecifications = getCustomerServices(customer.getId())
                .stream()
                .filter(service-> !ServiceStatus.DISCONNECTED.equals(service.getServiceStatus()))
                .map(service -> model.getSpecification(service.getSpecificationId()))
                .collect(Collectors.toList());

        customerSpecifications.addAll(getCustomerOrders(customer.getId())
                .stream()
                .filter(order -> OrderAim.NEW.equals(order.getOrderAim()) && (OrderStatus.ENTERING.equals(order.getOrderStatus()) || OrderStatus.IN_PROGRESS.equals(order.getOrderStatus()) ))
                .map(order -> model.getSpecification(order.getSpecId()))
                .collect(Collectors.toList()));

        return customerSpecifications;
    }

    public Collection<OrderDTO> getEmployeeOrders(BigInteger id)
    {
        model.checkEmployeeExists(id);

        return model.getOrders().values().stream()
                .filter(order -> id.equals(order.getEmployeeId()))
                .collect(Collectors.toList());
    }

    public Collection<ServiceDTO> getCustomerConnectedServices(BigInteger id)
    {
        model.checkCustomerExists(id);

        return getCustomerServices(id)
                .stream()
                .filter(service -> ServiceStatus.DISCONNECTED != service.getServiceStatus())
                .collect(Collectors.toList());
    }

    public Collection<OrderDTO> getCustomerNotFinishedOrders(BigInteger id)
    {
        model.checkCustomerExists(id);

        return getCustomerOrders(id)
                .stream()
                .filter(order -> OrderStatus.COMPLETED != order.getOrderStatus())
                .collect(Collectors.toList());
    }

    public Collection<ServiceDTO> getSpecificationServices(BigInteger id)
            throws ObjectNotFoundException
    {
        model.checkSpecificationExists(id);

        return model.getServices().values().stream()
                .filter(service -> id.equals(service.getSpecificationId()))
                .collect(Collectors.toList());
    }

    public ArrayList<OrderDTO> getOrdersOfEmployeesOnVacation()
    {
        ArrayList<OrderDTO> orders = new ArrayList<>();
        synchronized (model.getEmployees())
        {
            for (EmployeeDTO employee : model.getEmployees().values())
            {
                if (EmployeeStatus.ON_VACATION.equals(employee.getEmployeeStatus()))
                {
                    orders.addAll(getEmployeeOrders(employee.getId()));
                }
            }
        }

        return orders;
    }

    public void setEmployeeWaitingStatus(BigInteger id, boolean isWaiting) throws DataNotUpdatedWarning
    {
        model.checkEmployeeExists(id);

        EmployeeDTO employeeDTO = model.getEmployee(id);
        employeeDTO.setWaitingForOrders(isWaiting);
        model.updateEmployee(employeeDTO);

        if (isWaiting)
        {
            WorkWaitersManager.distributeOrders();
        }
    }

    public void goOnVacation(BigInteger id) throws DataNotUpdatedWarning
    {
        model.checkEmployeeExists(id);

        EmployeeDTO employeeDTO = model.getEmployee(id);
        employeeDTO.setEmployeeStatus(EmployeeStatus.ON_VACATION);
        model.updateEmployee(employeeDTO);
    }

    public void returnFromVacation(BigInteger id) throws DataNotUpdatedWarning
    {
        model.checkEmployeeExists(id);

        EmployeeDTO employeeDTO = model.getEmployee(id);
        employeeDTO.setEmployeeStatus(EmployeeStatus.WORKING);
        model.updateEmployee(employeeDTO);
    }

    public void retireEmployee(BigInteger id) throws DataNotUpdatedWarning
    {
        model.checkEmployeeExists(id);

        EmployeeDTO employee = model.getEmployee(id);
        employee.setEmployeeStatus(EmployeeStatus.RETIRED);
        ArrayList<OrderDTO> orders = (ArrayList<OrderDTO>) getEmployeeOrders(id);
        if (orders != null)
        {
            for (OrderDTO order : orders)
            {
                order.setEmployeeId(null);
                model.updateOrder(order);
            }
        }

        model.updateEmployee(employee);
    }

    public void assignOrder(BigInteger employeeId, BigInteger orderId) throws DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);
        model.checkEmployeeExists(employeeId);

        OrderDTO order = model.getOrder(orderId);
        order.setEmployeeId(employeeId);
        model.updateOrder(order);
    }

    public void processOrder(BigInteger employeeId, BigInteger orderId) throws DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);
        model.checkEmployeeExists(employeeId);

        assignOrder(employeeId, orderId);
        startOrder(orderId);
    }

    public void unassignOrder(BigInteger orderId) throws DataNotUpdatedWarning
    {
        model.checkOrderExists(orderId);

        OrderDTO order = model.getOrder(orderId);
        order.setEmployeeId(null);
        model.updateOrder(order);

        WorkWaitersManager.distributeOrders();
    }

    private void checkServiceBelongsToCustomer(BigInteger customerId, BigInteger serviceId)
    {
        if (!customerId.equals(model.getService(serviceId).getCustomerId()))
        {
            throw new RuntimeException("Service doesn't belong to customer!");
        }
    }

    public String getNameOfSpecByOrderId(BigInteger orderId)
    {
        OrderDTO order = model.getOrder(orderId);
        SpecificationDTO specification = model.getSpecification(order.getSpecId());
        return specification.getName();
    }

    public String getNextPayDay(BigInteger customerId)
    {

        List<ServiceDTO> services = this.getCustomersNotDisconnectedServices(customerId);
        if (services.isEmpty())
        {
            return "-";
        }
        Date minimumDate = new Date();
        for (ServiceDTO service : services)
        {
            if (ServiceStatus.ACTIVE.equals(service.getServiceStatus()) && service.getPayDay() != null &&
                    !isThereDisconnectionOrder(service.getId()) && !isThereSuspensionOrder(service.getId()))
            {
                minimumDate = service.getPayDay().after(minimumDate) ? service.getPayDay() : minimumDate;
            }
        }
        return this.transformDateInCorrectForm(minimumDate);
    }

    public boolean isAvailableSpecByDistrict(BigInteger customerId, BigInteger specId)
    {
        CustomerDTO customer = model.getCustomer(customerId);
        SpecificationDTO specification = model.getSpecification(specId);
        if (specification.isAddressDependence())
        {
            List<BigInteger> districtsIds = specification.getDistrictsIds();
            for (BigInteger districtId : districtsIds)
            {
                if (districtId.equals(customer.getDistrictId()))
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    public  boolean isAvailableSpecByMoney(BigInteger customerId, BigInteger specId){
        CustomerDTO customer = model.getCustomer(customerId);
        SpecificationDTO specification = model.getSpecification(specId);
        return customer.getBalance() >= specification.getPrice();
    }

    public boolean isThereDisconnectionOrder(BigInteger serviceId)
    {
        long count = model.getOrders().values().stream()
                .filter(order -> serviceId.equals(order.getServiceId()) &&
                        OrderAim.DISCONNECT.equals(order.getOrderAim()) &&
                        !OrderStatus.CANCELLED.equals(order.getOrderStatus()) &&
                        !OrderStatus.COMPLETED.equals(order.getOrderStatus()))
                .count();
        return count > 0;
    }

    public boolean isThereSuspensionOrder(BigInteger serviceId)
    {
        long count = model.getOrders().values().stream()
                .filter(order -> serviceId.equals(order.getServiceId()) &&
                        OrderAim.SUSPEND.equals(order.getOrderAim()) &&
                        !OrderStatus.CANCELLED.equals(order.getOrderStatus()) &&
                        !OrderStatus.COMPLETED.equals(order.getOrderStatus()))
                .count();
        return count > 0;
    }

    public boolean isThereRestorationOrder(BigInteger serviceId)
    {
        long count = model.getOrders().values().stream()
                .filter(order -> serviceId.equals(order.getServiceId()) &&
                        OrderAim.RESTORE.equals(order.getOrderAim()) &&
                        !OrderStatus.CANCELLED.equals(order.getOrderStatus()) &&
                        !OrderStatus.COMPLETED.equals(order.getOrderStatus()))
                .count();
        return count > 0;
    }

    public List<SpecificationDTO> getAccessibleSpecs(BigInteger customerId){
        CustomerDTO customer = model.getCustomer(customerId);
        List<SpecificationDTO> connectedSpecs = this.getCustomerSpecifications(customer);

        return model.getSpecifications().values().stream()
                .filter(specification -> !connectedSpecs.contains(specification) && this.isAvailableSpecByDistrict(customerId,specification.getId()))
                .collect(Collectors.toList());
    }

    public List<SpecificationDTO> getNotAvailableSpecs(BigInteger customerId){
        CustomerDTO customer = model.getCustomer(customerId);
        List<SpecificationDTO> connectedSpecs = this.getCustomerSpecifications(customer);

        List<SpecificationDTO> notAvailableSpecs = model.getSpecifications().values().stream()
                .filter(specification -> !connectedSpecs.contains(specification) && !this.isAvailableSpecByDistrict(customerId,specification.getId()))
                .collect(Collectors.toList());
        notAvailableSpecs.addAll(connectedSpecs);
        return notAvailableSpecs;
    }

    public List<OrderDTO> getNotFinishedOrders(BigInteger customerId){
        return this.getCustomerOrders(customerId).stream()
                .filter(order -> !OrderStatus.COMPLETED.equals(order.getOrderStatus()) && !OrderStatus.CANCELLED.equals(order.getOrderStatus()))
                .collect(Collectors.toList());
    }

    public OrderDTO getNotCompletedOrderOnService (BigInteger serviceId){
        return model.getOrders().values().stream()
                .filter(order -> serviceId.equals(order.getServiceId()) && !order.getOrderStatus().equals(OrderStatus.CANCELLED) && !OrderStatus.COMPLETED.equals(order.getOrderStatus()) && !OrderAim.NEW.equals(order.getOrderAim()))
                .findFirst()
                .get();
    }

    public List<ServiceDTO> getCustomersNotDisconnectedServices(BigInteger customerId){
        return this.getCustomerServices(customerId).stream()
                .filter(serviceDTO -> !ServiceStatus.DISCONNECTED.equals(serviceDTO.getServiceStatus()))
                .collect(Collectors.toList());
    }

    public String transformDateInCorrectForm(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyy", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public String transformPriceInCorrectForm(float value){
        if(value == (long) value)
            return String.format("%d",(long)value);
        else
            return String.format("%s",value);
    }
}