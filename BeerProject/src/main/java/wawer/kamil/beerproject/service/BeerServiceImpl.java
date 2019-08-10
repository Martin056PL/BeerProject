package wawer.kamil.beerproject.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;


@Service
@AllArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;

    //get beers

    @Override
    public Page<Beer> findAllBeersPage(Pageable pageable) {
        return beerRepository.findAll(pageable);
    }

    @Override
    public Beer findBeerByBeerId(Long beerId) throws NoContentException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            return beerRepository.findBeerByBeerId(beerId);
        } else {
            throw new NoContentException();
        }
    }

    //get beers by breweryID

    @Override
    public Page<Beer> findAllBeersByBreweryId(Long breweryId, Pageable pageable) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId)) {
            Brewery brewery = breweryRepository.findByBreweryId(breweryId);
            return beerRepository.findAllByBrewery(brewery, pageable);
        } else {
            throw new NoContentException();
        }
    }

    @Override
    public Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId)) {
            if (beerRepository.existsBeerByBeerId(beerId)) {
                Brewery brewery = breweryRepository.findByBreweryId(breweryId);
                return beerRepository.findBeerByBreweryAndBeerId(brewery, beerId);
            } else {
                throw new NoContentException();
            }
        } else throw new NoContentException();
    }

    //post beers

    @Override
    public Beer addNewBeerToRepository(Beer beer) {
        return beerRepository.save(beer);
    }

    public Brewery addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryID)) {
            Brewery brewery = breweryRepository.findByBreweryId(breweryID);
            brewery.addBeer(beer);
            Brewery resultBrewery = breweryRepository.save(brewery);
            return breweryRepository.save(resultBrewery);
        } else {
            throw new NoContentException();
        }
    }

    //put beers

    @Override
    public Beer updateBeerByBeerID(Long beerId, Beer beer) throws NoContentException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            beer.setBeerId(beerId);
            return beerRepository.save(beer);
        } else {
            throw new NoContentException();
        }
    }

    //delete beers

    @Override
    public void deleteBeerByBeerId(Long beerId) throws NoContentException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            beerRepository.deleteById(beerId);
        } else {
            throw new NoContentException();
        }
    }


}
