package com.xpto.cityapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a City document in MongoDB.
 */
@Document(collection = "cities")
public class City {
    @Id
    private String id;
    private Integer ibgeId; // IBGE identifier
    private String name; // City name
    private String state; // State abbreviation
    private boolean capital; // Indicates if it's a capital city
    private Double latitude; // Latitude coordinate
    private Double longitude; // Longitude coordinate

    // Default constructor
    public City() {
    }

    // Constructor with all parameters
    public City(String id, Integer ibgeId, String name, String state, boolean capital, Double latitude, Double longitude) {
        this.id = id;
        this.ibgeId = ibgeId;
        this.name = name;
        this.state = state;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIbgeId() {
        return ibgeId;
    }

    public void setIbgeId(Integer ibgeId) {
        this.ibgeId = ibgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isCapital() {
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}