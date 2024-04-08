package __PACKAGE_PREFIX__.repository;

import __PACKAGE_PREFIX__.entity.CustomEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "entities", path = "entities")
public interface CustomRepository extends PagingAndSortingRepository<CustomEntity, Long> {
}