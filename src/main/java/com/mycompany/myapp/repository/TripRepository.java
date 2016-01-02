package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Trip entity.
 */
public interface TripRepository extends JpaRepository<Trip, Long>{

    @Query("select trip from Trip trip")
    List<Trip> find();
}
