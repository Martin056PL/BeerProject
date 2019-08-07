package wawer.kamil.beerproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.repositories.BreweryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BreweryServiceImpl implements BreweryService {

    private final BreweryRepository repository;

    @Override
    public Page<Brewery> getAllBrewery(Pageable pageable) throws NoContentException {
        Page<Brewery> getBreweryList = repository.findAll(pageable);
        Optional<Page<Brewery>> optionalBreweryPage = Optional.ofNullable(getBreweryList);
        if (optionalBreweryPage.isPresent()) {
            return optionalBreweryPage.get();
        } else {
            throw new NoContentException();
        }
    }

    @Override
    public Brewery getBreweryByBreweryId(Long breweryId) throws NoContentException {
        Brewery getBreweryList = repository.findByBreweryId(breweryId);
        Optional<Brewery> optionalBreweryList = Optional.ofNullable(getBreweryList);
        if (optionalBreweryList.isPresent()) {
            return optionalBreweryList.get();
        } else {
            throw new NoContentException();
        }
    }

    @Override
    public Brewery createNewBrewery(Brewery brewery) {
        return repository.save(brewery);
    }

    @Override
    public Brewery updateBreweryById(Long breweryId, Brewery brewery) throws NoContentException {
        if (repository.existsBreweryByBreweryId(breweryId)) {
            repository.save(brewery);
            return brewery;
        } else {
            throw new NoContentException();
        }
    }

    @Override
    public void deleteBreweryByBreweryId(Long breweryId) throws NoContentException {
        if (repository.existsBreweryByBreweryId(breweryId)) {
            repository.deleteById(breweryId);
        } else {
            throw new NoContentException();
        }
    }
}
