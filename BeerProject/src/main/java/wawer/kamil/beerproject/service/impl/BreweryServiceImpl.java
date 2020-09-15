package wawer.kamil.beerproject.service.impl;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.repositories.BreweryRepository;
import wawer.kamil.beerproject.service.BreweryService;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class BreweryServiceImpl implements BreweryService {

    private final BreweryRepository repository;
    private final ImageUpload imageUpload;

    private static final String THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND = "The brewery base on id: {} has not been found";

    @Override
    @Cacheable(cacheNames = "breweryCache")
    public Page<Brewery> getAllBreweryPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Cacheable(cacheNames = "breweryCache")
    public List<Brewery> getAllBreweryList() {
        return repository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "breweryCache", key = "#breweryId")
    public Brewery getBreweryByBreweryId(Long breweryId) throws NoContentException {
        if (repository.existsBreweryByBreweryId(breweryId)) {
            return repository.findByBreweryId(breweryId);
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new NoContentException();
        }
    }

    @Override
    @CachePut(cacheNames = "breweryCache", key = "#result.breweryId")
    public Brewery createNewBrewery(Brewery brewery) {
        return repository.save(brewery);
    }

    @Override
    @CachePut(cacheNames = "breweryCache", key = "#result.breweryId")//
    public Brewery updateBreweryById(Long breweryId, Brewery brewery) throws NoContentException {
        if (repository.existsBreweryByBreweryId(breweryId)) {
            brewery.setBreweryId(breweryId);
            return repository.save(brewery);
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new NoContentException();
        }
    }

    @Override
    @CacheEvict(value = "breweryCache", allEntries = true)
    public void deleteBreweryByBreweryId(Long breweryId) throws NoContentException {
        if (repository.existsBreweryByBreweryId(breweryId)) {
            repository.deleteById(breweryId);
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new NoContentException();
        }
    }

    @Override
    @Transactional
    public void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file) throws IOException, NoContentException, InvalidImageParameters {
        Brewery brewery = getBreweryByBreweryId(breweryId);
        if (imageUpload.validateSizeAndTypeOfFile(file)) {
            byte[] imageAsByteArray = imageUpload.convertFileToByteArray(file);
            brewery.setBreweryImage(imageAsByteArray);
            repository.save(brewery);
        } else {
            throw new InvalidImageParameters();
        }
    }

    @Override
    public byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) throws NoContentException {
        Brewery brewery = getBreweryByBreweryId(breweryId);
        return brewery.getBreweryImage();
    }
}
