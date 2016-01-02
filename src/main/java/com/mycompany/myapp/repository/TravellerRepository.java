package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Trip entity.
 */
public interface TravellerRepository extends JpaRepository<Traveller, Long>{

    @Query("select traveller from Traveller traveller where traveller.email like :email")
    Optional<Traveller> findOneByEmail(String email);
}
