package wawer.kamil.beerproject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wawer.kamil.beerproject.domain.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    Page<Beer> findAll(Pageable pageable);

    Beer findAllByBeerId(Long beerId);

    void deleteByBeerId(Long beerId);

    boolean existsBeerByBeerId(Long beerId);


}
