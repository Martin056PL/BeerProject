package wawer.kamil.beerproject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreweryRepository extends JpaRepository<Brewery, Long> {

    @Override
    @Query(
            value = "select distinct b from Brewery b",
            countQuery = "select count(b) from Brewery b"
    )
    Page<Brewery> findAll(Pageable pageable);

    @Override
    @Query(
            value = "select distinct b from Brewery b",
            countQuery = "select count(b) from Brewery b"
    )
    List<Brewery> findAll();

    @Override
    @Query(
            value = "select distinct b from Brewery b left join fetch b.beerList where b.id = ?1"
    )
    Optional<Brewery> findById(Long id);
}
