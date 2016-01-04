package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * A Traveller Entity
 */
@Entity
@Table(name = "traveller")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Traveller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "lat", nullable = true)
    private double lat;

    @Column(name = "lng", nullable = true)
    private double lng;

    @JsonIgnore
    @ManyToOne
    private Trip trip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Traveller traveller = (Traveller) o;

        if (!email.equals(traveller.email)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (StringUtils.isEmpty(email)) {
            return 0;
        }
        int result = email.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return String.format("Traveller{id='%d', email='%s', lat='%f', lng='%f'}", id, email, lat, lng);
    }
}
