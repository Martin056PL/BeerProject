package wawer.kamil.beerproject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wawer.kamil.beerproject.model.Beer;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    @Query("select b from Beer b where b.brewery.breweryId in ?1")
    List<Beer> findBeersByListOfBreweriesId(List<Long> breweryIdList);

    @Query(
            value = "select be from Beer be join Brewery br on br.breweryId = be.brewery.breweryId where br.breweryId = ?1",
            countQuery = "select count(b) from Beer b"
    )
    Page<Beer> findAllByBreweryId(Long breweryId, Pageable pageable);

    @Query("select be from Beer be join fetch Brewery br on br.breweryId = be.brewery.breweryId where br.breweryId = ?1")
    List<Beer> findAllByBreweryId(Long breweryId);

    @Query("select be from Beer be join Brewery br on br.breweryId = be.brewery.breweryId where br.breweryId = ?1 and be.beerId = ?2")
    Optional<Beer> findBeerByBreweryAndBeerId(Long breweryId, Long beerId);

    @Query("select b.beerImage from Beer b where b.beerId = ?1")
    byte[] findBeerImageByBeerId(Long beerId);

}
