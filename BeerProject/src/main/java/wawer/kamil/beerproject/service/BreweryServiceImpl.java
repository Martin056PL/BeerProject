package wawer.kamil.beerproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.repositories.BreweryRepository;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class BreweryServiceImpl implements BreweryService {

    private final BreweryRepository repository;

    @Override
    public Page<Brewery> getAllBrewery(Pageable pageable) {
         return repository.findAll(pageable);
    }

    @Override
    public Brewery getBreweryByBreweryId(Long breweryId) throws NoContentException {
        if (repository.existsBreweryByBreweryId(breweryId)) {
            return repository.findByBreweryId(breweryId);
        } else {
            log.debug("The brewery base on id: {} has not been found", breweryId);
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
            brewery.setBreweryId(breweryId);
            return repository.save(brewery);
        } else {
            log.debug("The brewery base on id: {} has not been found", breweryId);
            throw new NoContentException();
        }
    }

    @Override
    public void deleteBreweryByBreweryId(Long breweryId) throws NoContentException {
        if (repository.existsBreweryByBreweryId(breweryId)) {
            repository.deleteById(breweryId);
        } else {
            log.debug("The brewery base on id: {} has not been found", breweryId);
            throw new NoContentException();
        }
    }
}
