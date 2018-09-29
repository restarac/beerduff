package beerduff.api.beer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BeerRepository extends PagingAndSortingRepository<Beer, Long> {

    @Query(value = "SELECT b.* FROM Beer b WHERE (:temp BETWEEN b.min_temp AND b.max_temp) ORDER BY ABS(b.average_temp - :temp), b.name", nativeQuery = true)
    List<Beer> findByAverageTemperature(@Param("temp") int temperature);

}
