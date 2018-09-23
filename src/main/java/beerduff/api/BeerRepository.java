package beerduff.api;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BeerRepository extends PagingAndSortingRepository<Beer, Long> {

	List<Beer> findByName(@Param("name") String name);

}
