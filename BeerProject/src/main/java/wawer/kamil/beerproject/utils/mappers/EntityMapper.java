package wawer.kamil.beerproject.utils.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityMapper<Entity, Request, Response> {

    protected final ModelMapper modelMapper = new ModelMapper();
    private Class<Entity> baseEntityClass;
    private Class<Response> responseEntityClass;

    public EntityMapper(Class<Entity> baseEntityClass, Class<Response> responseEntityClass) {
        this.baseEntityClass = baseEntityClass;
        this.responseEntityClass = responseEntityClass;
    }

    public List<Response> mapEntitiesToEntitiesResponse(List<Entity> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, responseEntityClass)).collect(Collectors.toList());
    }

    public Page<Response> mapEntityPageToResponsePage(Page<Entity> entitiesPage) {
        return entitiesPage.map(entity -> modelMapper.map(entity, responseEntityClass));
    }

    public Response mapEntityToResponse(Entity entity) {
        return modelMapper.map(entity, responseEntityClass);
    }

    public Entity mapRequestEntityToEntity(Request request) {
        return modelMapper.map(request, baseEntityClass);
    }
}
