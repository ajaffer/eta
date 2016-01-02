package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BankAccount;
import com.mycompany.myapp.domain.PersistentToken;
import com.mycompany.myapp.domain.Traveller;
import com.mycompany.myapp.domain.Trip;
import com.mycompany.myapp.repository.TripRepository;
import com.mycompany.myapp.service.TripService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for TripResource
 */
@RestController
@RequestMapping("/api")
public class TripResource {
    private final Logger log = LoggerFactory.getLogger(TripResource.class);

    @Inject
    private TripRepository tripRepository;


    /**
     * GET  /bankAccounts -> get all the bankAccounts.
     */
    @RequestMapping(value = "/trips",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Trip> getAllBankAccounts() {
        log.debug("REST request to get all Trips");
        return tripRepository.findAll();
    }


    @RequestMapping(value = "/trips/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getTrip(@PathVariable Long id) {
        log.debug(String.format("REST req to get trip : %d", id));

        Trip trip = tripRepository.findOne(id);

        return Optional.ofNullable(trip)
                .map(result -> new ResponseEntity<>(
                        result.getTravellers(),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
