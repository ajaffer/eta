package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Traveller;
import com.mycompany.myapp.domain.Trip;
import com.mycompany.myapp.repository.TravellerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.URISyntaxException;

/**
 * Service class for Traveller lifecycle management
 */
public class TravellerService {

    private Logger log = LoggerFactory.getLogger(TravellerService.class);

    @Inject
    private TravellerRepository travellerRepository;

    public Traveller createTraveller(Traveller traveller) throws URISyntaxException {
        log.debug(String.format("Adding a traveller : %s", traveller));

        Traveller result = travellerRepository.save(traveller);

        return result;
    }
}
