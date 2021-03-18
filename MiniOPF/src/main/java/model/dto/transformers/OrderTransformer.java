package model.dto.transformers;

import lombok.NoArgsConstructor;
import model.dto.EntityDTO;
import model.dto.OrderDTO;
import model.entities.Entity;
import model.entities.Order;

@NoArgsConstructor
public class OrderTransformer implements Transformer
{
    @Override
    public EntityDTO toDto(Entity entity)
    {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(entity.getId());
        orderDTO.setCustomerId(((Order) entity).getCustomerId());
        orderDTO.setEmployeeId(((Order) entity).getEmployeeId());
        orderDTO.setSpecId(((Order) entity).getSpecId());
        orderDTO.setServiceId(((Order) entity).getServiceId());
        orderDTO.setOrderAim(((Order) entity).getOrderAim());
        orderDTO.setOrderStatus(((Order) entity).getOrderStatus());
        orderDTO.setAddress(((Order) entity).getAddress());

        return orderDTO;
    }

    @Override
    public Entity toEntity(EntityDTO entityDTO)
    {
        Order order = new Order();

        order.setId(entityDTO.getId());
        order.setCustomerId(((OrderDTO) entityDTO).getCustomerId());
        order.setEmployeeId(((OrderDTO) entityDTO).getEmployeeId());
        order.setSpecId(((OrderDTO) entityDTO).getSpecId());
        order.setServiceId(((OrderDTO) entityDTO).getServiceId());
        order.setOrderAim(((OrderDTO) entityDTO).getOrderAim());
        order.setOrderStatus(((OrderDTO) entityDTO).getOrderStatus());
        order.setAddress(((OrderDTO) entityDTO).getAddress());

        return order;
    }
}