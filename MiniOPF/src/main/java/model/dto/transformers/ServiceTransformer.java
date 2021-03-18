package model.dto.transformers;

import lombok.NoArgsConstructor;
import model.dto.EntityDTO;
import model.dto.ServiceDTO;
import model.entities.Entity;
import model.entities.Service;

@NoArgsConstructor
public class ServiceTransformer implements Transformer
{
    @Override
    public EntityDTO toDto(Entity entity)
    {
        ServiceDTO serviceDTO = new ServiceDTO();

        serviceDTO.setId(entity.getId());
        serviceDTO.setPayDay(((Service) entity).getPayDay());
        serviceDTO.setSpecificationId(((Service) entity).getSpecificationId());
        serviceDTO.setServiceStatus(((Service) entity).getServiceStatus());
        serviceDTO.setCustomerId(((Service) entity).getCustomerId());

        return serviceDTO;
    }

    @Override
    public Entity toEntity(EntityDTO entityDTO)
    {
        Service service = new Service();

        service.setId(entityDTO.getId());
        service.setPayDay(((ServiceDTO) entityDTO).getPayDay());
        service.setSpecificationId(((ServiceDTO) entityDTO).getSpecificationId());
        service.setServiceStatus(((ServiceDTO) entityDTO).getServiceStatus());
        service.setCustomerId(((ServiceDTO) entityDTO).getCustomerId());

        return service;
    }
}