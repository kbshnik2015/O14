package model.dto.transformers;

import lombok.NoArgsConstructor;
import model.dto.DistrictDTO;
import model.dto.EntityDTO;
import model.entities.District;
import model.entities.Entity;

@NoArgsConstructor
public class DistrictTransformer implements Transformer
{
    @Override
    public EntityDTO toDto(Entity entity)
    {
        DistrictDTO districtDTO = new DistrictDTO();

        districtDTO.setId(entity.getId());
        districtDTO.setName(((District) entity).getName());
        districtDTO.setParentId(((District) entity).getParentId());

        return districtDTO;
    }

    @Override
    public Entity toEntity(EntityDTO entityDTO)
    {
        District district = new District();

        district.setId(entityDTO.getId());
        district.setName(((DistrictDTO) entityDTO).getName());
        district.setParentId(((DistrictDTO) entityDTO).getParentId());

        return district;
    }
}