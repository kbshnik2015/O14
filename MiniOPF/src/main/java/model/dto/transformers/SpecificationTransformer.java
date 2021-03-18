package model.dto.transformers;

import lombok.NoArgsConstructor;
import model.dto.EntityDTO;
import model.dto.SpecificationDTO;
import model.entities.Entity;
import model.entities.Specification;

@NoArgsConstructor
public class SpecificationTransformer implements Transformer
{
    @Override
    public EntityDTO toDto(Entity entity)
    {
        SpecificationDTO specificationDTO = new SpecificationDTO();

        specificationDTO.setId(entity.getId());
        specificationDTO.setName(((Specification) entity).getName());
        specificationDTO.setPrice(((Specification) entity).getPrice());
        specificationDTO.setDescription(((Specification) entity).getDescription());
        specificationDTO.setAddressDepended(((Specification) entity).isAddressDepended());
        specificationDTO.setDistrictsIds(((Specification) entity).getDistrictsIds());

        return specificationDTO;
    }

    @Override
    public Entity toEntity(EntityDTO entityDTO)
    {
        Specification specification = new Specification();

        specification.setId(entityDTO.getId());
        specification.setName(((SpecificationDTO) entityDTO).getName());
        specification.setPrice(((SpecificationDTO) entityDTO).getPrice());
        specification.setDescription(((SpecificationDTO) entityDTO).getDescription());
        specification.setAddressDepended(((SpecificationDTO) entityDTO).isAddressDepended());
        specification.setDistrictsIds(((SpecificationDTO) entityDTO).getDistrictsIds());

        return specification;
    }
}