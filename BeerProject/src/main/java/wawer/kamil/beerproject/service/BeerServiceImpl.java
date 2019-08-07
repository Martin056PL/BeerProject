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
public class BeerServiceImpl implements BeerService {

    private final BeerRepository repository;

    @Override
    public Page<Beer> findAllBeersPage(Pageable pageable) throws NoContentException {
        Page<Beer> beerListFromRepository = repository.findAll(pageable);
        Optional<Page<Beer>> optionalListOfBeers = Optional.ofNullable(beerListFromRepository);
        if (optionalListOfBeers.isPresent()) {
            return optionalListOfBeers.get();
        } else {
            throw new NoContentException();
        }
    }

    @Override
    public Beer findBeerByBeerId(Long beerId) throws NoContentException {
        if (repository.existsBeerByBeerId(beerId)) {
            return repository.getAllByBeerId(beerId);
        } else {
            throw new NoContentException();
        }
    }

    @Override
    public Beer addNewBeerToRepository(Beer beer) {
        return repository.save(beer);
    }

    @Override
    public Beer updateBeerByBeerID(Long beerId, Beer beer) throws NoContentException {
        if (repository.existsBeerByBeerId(beerId)) {
            repository.save(beer);
            return beer;
        } else {
            throw new NoContentException();
        }
    }

    @Override
    public void deleteBeerByBeerId(Long beerId) throws NoContentException {
        if (repository.existsBeerByBeerId(beerId)) {
            repository.deleteById(beerId);
        } else {
            throw new NoContentException();
        }
    }
}
