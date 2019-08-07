package wawer.kamil.beerproject.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.repositories.BeerRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BeerService {

    private final BeerRepository repository;


    public Page<Beer> findAllBeersPage(Pageable pageable) throws NoContentException {
        Page<Beer> beerListFromRepository = repository.findAll(pageable);
        Optional<Page<Beer>> optionalListOfBeers = Optional.ofNullable(beerListFromRepository);
        if (optionalListOfBeers.isPresent()) {
            return optionalListOfBeers.get();
        } else {
            throw new NoContentException();
        }
    }

    public Beer findBeerByBeerId(Long beerId) throws NoContentException {
        if (repository.existsBeerByBeerId(beerId)) {
            return repository.findAllByBeerId(beerId);
        } else {
            throw new NoContentException();
        }
    }

    public Beer addNewBeerToRepository(Beer beer) {
        beer = repository.save(beer);
        return beer;
    }

    public Beer updateBeerByBeerID(Long beerId, Beer beer) throws NoContentException {
        if (!repository.existsBeerByBeerId(beerId)) {
           repository.save(beer);
           return beer;
        } else {
            throw new NoContentException();
        }
    }

    public void deleteBeerByBeerId(Long beerId) throws NoContentException {
        if (repository.existsBeerByBeerId(beerId)) {
            repository.deleteByBeerId(beerId);
        } else {
            throw new NoContentException();
        }
    }
}
