package cz.cvut.fit.tjv.musicalevent.server.domain.converter;

public interface DTOConverter<DTO, Entity> {
    DTO toDTO(Entity e);
    Entity toEntity(DTO dto);
}
