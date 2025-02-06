package com.xpto.cityapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for City data transfer")
public class CityDTO {

    @Schema(description = "Unique identifier of the city", example = "12345")
    private String id;

    @Schema(description = "IBGE ID of the city", example = "3550308")
    private Integer ibgeId;

    @Schema(description = "Name of the city", example = "São Paulo")
    private String name;

    @Schema(description = "State abbreviation", example = "SP")
    private String state;

    @Schema(description = "Indicates if the city is a capital", example = "true")
    private boolean capital;

    @Schema(description = "Latitude of the city", example = "-23.55052")
    private Double latitude;

    @Schema(description = "Longitude of the city", example = "-46.633308")
    private Double longitude;

    public CityDTO() {
    }

    public CityDTO(String id, Integer ibgeId, String name, String state, boolean capital, Double latitude, Double longitude) {
        this.id = id;
        this.ibgeId = ibgeId;
        this.name = name;
        this.state = state;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public Integer getIbgeId() {
        return ibgeId;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public boolean isCapital() {
        return capital;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIbgeId(Integer ibgeId) {
        this.ibgeId = ibgeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
