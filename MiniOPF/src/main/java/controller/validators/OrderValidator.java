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

public class OrderValidator implements Validator
{
    @Override
    public boolean validate(final EntityDTO entity)
    {
        Model model = ModelFactory.getModel();
        BigInteger customerId = ((OrderDTO) entity).getCustomerId();
        BigInteger specId = ((OrderDTO) entity).getSpecId();
        BigInteger serviceId = ((OrderDTO) entity).getServiceId();
        OrderAim orderAim = ((OrderDTO) entity).getOrderAim();
        OrderStatus orderStatus = ((OrderDTO) entity).getOrderStatus();

        if (customerId == null)
        {
            throw new IllegalTransitionException("Order have to have a customer");
        }

        if (orderAim.equals(OrderAim.NEW) && specId == null)
        {
            throw new IllegalTransitionException("Order with aim 'NEW' have to have a specification");
        }

        if (orderAim.equals(OrderAim.NEW) && serviceId != null)
        {
            throw new IllegalTransitionException("Order with aim 'NEW' can't have a service");
        }

        if (!orderAim.equals(OrderAim.NEW) && serviceId == null)
        {
            throw new IllegalTransitionException(
                    "Order with aim 'SUSPEND'/'DISCONNECT'/'RESTORE' have to have a service");
        }

        if (!orderAim.equals(OrderAim.NEW) && !orderAim.equals(OrderAim.RESTORE) &&
                !model.getService(serviceId).getServiceStatus().equals(ServiceStatus.ACTIVE))
        {
            throw new IllegalTransitionException(
                    "Order with aim 'SUSPEND'/'DISCONNECT' have to have an active service");
        }

        if (orderAim.equals(OrderAim.RESTORE) &&
                model.getService(serviceId).getServiceStatus().equals(ServiceStatus.ACTIVE))
        {
            throw new IllegalTransitionException(
                    "Order with aim 'RESTORE' have to have a not active service");
        }

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

        if (serviceId != null && specId != null)
        {
            if (!model.getService(serviceId).getSpecificationId().equals(specId))
            {
                throw new IllegalTransitionException(
                        "Service (id: " + serviceId + ") belongs another specification, not specification (id: " +
                                specId + ") you set");
            }
        }

        return true;
    }
}
