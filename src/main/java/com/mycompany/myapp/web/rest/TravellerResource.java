package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BankAccount;
import com.mycompany.myapp.domain.Traveller;
import com.mycompany.myapp.repository.TravellerRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST Controller for the Traveller Resource
 */
@RestController
@RequestMapping("/api")
public class TravellerResource {

    private Logger log = LoggerFactory.getLogger(TravellerResource.class);

    @Inject
    private TravellerRepository travellerRepository;

    /**
     * PUT  /traveller/location -> Updates the location of an existing traveller.
     */
    @RequestMapping(value = "/traveller/location",
            method = RequestMethod.PUT,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> updateLocation(@Valid @RequestBody Traveller updated) {
        return travellerRepository.findOneByEmail(updated.getEmail())
                .map(existing -> {
                    existing.setLat(updated.getLat());
                    existing.setLng(updated.getLng());
                    travellerRepository.save(existing);
                    return new ResponseEntity<>(HttpStatus.OK);
                }).orElseGet(() -> {
                    log.error(String.format("Traveller with email: '%s' not found", updated.getEmail()));
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }





}
