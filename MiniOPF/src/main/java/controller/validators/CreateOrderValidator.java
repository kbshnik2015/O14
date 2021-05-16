package controller.validators;

import java.math.BigInteger;

import controller.exceptions.IllegalTransitionException;
import model.Model;
import model.ModelFactory;
import model.dto.EntityDTO;
import model.dto.OrderDTO;
import model.enums.OrderAim;
import model.enums.OrderStatus;
import model.enums.ServiceStatus;

public class CreateOrderValidator implements CreateEntityValidator
{
    private Model model = ModelFactory.getModel();

    private void checkCustomerIdIsNull(BigInteger customerId)
    {
        if (customerId == null)
        {
            throw new IllegalTransitionException("Order has to have a customer");
        }
    }

    private void checkOrderWithOrderAimNewHasSpecificationId(OrderAim orderAim, BigInteger specId)
    {
        if (orderAim.equals(OrderAim.NEW) && specId == null)
        {
            throw new IllegalTransitionException("Order with aim 'NEW' has to have a specification");
        }
    }

    private void checkOrderWithOrderAimNewDoesntHaveServiceId(OrderAim orderAim, BigInteger serviceId)
    {
        if (orderAim.equals(OrderAim.NEW) && serviceId != null)
        {
            throw new IllegalTransitionException("Order with aim 'NEW' can't have a service");
        }
    }

    private void checkSuspendDisconnectRestoreOrdersHasServiceId(OrderAim orderAim, BigInteger serviceId)
    {
        if (!orderAim.equals(OrderAim.NEW) && serviceId == null)
        {
            throw new IllegalTransitionException(
                    "Order with aim 'SUSPEND'/'DISCONNECT'/'RESTORE' has to have a service");
        }
    }

    private void checkSuspendDisconnectOrdersHasActiveService(OrderAim orderAim, BigInteger serviceId)
    {
        if (!orderAim.equals(OrderAim.NEW) && !orderAim.equals(OrderAim.RESTORE) &&
                !model.getService(serviceId).getServiceStatus().equals(ServiceStatus.ACTIVE))
        {
            throw new IllegalTransitionException(
                    "Order with aim 'SUSPEND'/'DISCONNECT' has to have an active service");
        }
    }

    private void checkRestoreOrderHasNotActiveService(OrderAim orderAim, BigInteger serviceId)
    {
        if (orderAim.equals(OrderAim.RESTORE) &&
                model.getService(serviceId).getServiceStatus().equals(ServiceStatus.ACTIVE))
        {
            throw new IllegalTransitionException(
                    "Order with aim 'RESTORE' has to have a not active service");
        }
    }

    private void checkIsServiceAlreadyInUse(BigInteger serviceId)
    {
        if (serviceId != null)
        {
            for (OrderDTO orderDTO : model.getOrders().values())
            {
                if (orderDTO.getServiceId() != null)
                {
                    if (orderDTO.getServiceId().equals(serviceId) &&
                            !(orderDTO.getOrderStatus().equals(OrderStatus.COMPLETED) ||
                                    orderDTO.getOrderStatus().equals(OrderStatus.CANCELLED)))
                    {
                        throw new IllegalTransitionException(
                                "Service (id: " + serviceId + ") is already in use in other order (id: " +
                                        orderDTO.getId() + ")");
                    }
                }
            }
        }
    }

    private void checkServiceBelongsRightSpecification(BigInteger serviceId, BigInteger specId)
    {
        if (serviceId != null && specId != null)
        {
            if (!model.getService(serviceId).getSpecificationId().equals(specId))
            {
                throw new IllegalTransitionException(
                        "Service (id: " + serviceId + ") belongs another specification, not specification (id: " +
                                specId + ") you set");
            }
        }
    }

    @Override
    public boolean validate(final EntityDTO entity)
    {
        BigInteger customerId = ((OrderDTO) entity).getCustomerId();
        BigInteger specId = ((OrderDTO) entity).getSpecId();
        BigInteger serviceId = ((OrderDTO) entity).getServiceId();
        OrderAim orderAim = ((OrderDTO) entity).getOrderAim();
        OrderStatus orderStatus = ((OrderDTO) entity).getOrderStatus();

        checkCustomerIdIsNull(customerId);
        checkOrderWithOrderAimNewHasSpecificationId(orderAim, specId);
        checkOrderWithOrderAimNewDoesntHaveServiceId(orderAim, serviceId);
        checkSuspendDisconnectRestoreOrdersHasServiceId(orderAim, serviceId);
        checkSuspendDisconnectOrdersHasActiveService(orderAim, serviceId);
        checkRestoreOrderHasNotActiveService(orderAim, serviceId);
        checkIsServiceAlreadyInUse(serviceId);
        checkServiceBelongsRightSpecification(serviceId, specId);

        return true;
    }
}
