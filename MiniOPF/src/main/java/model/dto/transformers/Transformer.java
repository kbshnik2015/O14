package model.dto.transformers;

import model.dto.EntityDTO;
import model.entities.Entity;

public interface Transformer
{
    EntityDTO toDto(Entity entity);

    Entity toEntity(EntityDTO entityDTO);
}
