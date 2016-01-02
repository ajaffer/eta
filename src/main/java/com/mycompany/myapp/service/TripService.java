package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Traveller;
import com.mycompany.myapp.domain.Trip;
import com.mycompany.myapp.repository.TravellerRepository;
import com.mycompany.myapp.repository.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Service class for Trip lifecycle management
 */
public class TripService {

    private Logger log = LoggerFactory.getLogger(TripService.class);

    @Inject
    private TripRepository tripRepository;

    @Inject
    private TravellerRepository travellerRepository;

    public Trip registerTrip(Trip trip) throws URISyntaxException {
        log.debug(String.format("Registering a trip : %s", trip));

        Trip result = tripRepository.save(trip);

        return result;
    }
}
