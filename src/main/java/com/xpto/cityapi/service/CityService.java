package com.xpto.cityapi.service;

import com.xpto.cityapi.model.City;
import com.xpto.cityapi.repository.CityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<City> getAllCities() {
        return repository.findAll();
    }

    public City createCity(City city) {
        validateCity(city);
        checkDuplicateIbgeId(city.getIbgeId());
        return repository.save(city);
    }

    public City updateCity(City city) {
        if (city.getId() == null || !repository.existsById(city.getId())) {
            throw new IllegalArgumentException("City with ID " + city.getId() + " does not exist");
        }
        validateCity(city);
        return repository.save(city);
    }

    public void deleteCity(String id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("City with ID " + id + " does not exist", e);
        }
    }

    public City getCityById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City with ID " + id + " not found"));
    }

    public List<City> getCapitalCities() {
        return repository.findByCapitalTrue();
    }

    public List<City> getCitiesByState(String state) {
        return repository.findByState(state);
    }

    private void validateCity(City city) {
        if (city == null || city.getIbgeId() == null || city.getName() == null ||
                city.getState() == null || city.getLatitude() == null || city.getLongitude() == null) {
            throw new IllegalArgumentException("City data is incomplete");
        }
    }

    private void checkDuplicateIbgeId(Integer ibgeId) {
        Optional<City> existingCity = repository.findByIbgeId(ibgeId);
        if (existingCity.isPresent()) {
            throw new IllegalArgumentException("City with IBGE ID " + ibgeId + " already exists");
        }
    }

    public void saveAllCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            throw new IllegalArgumentException("City list is empty");
        }

        // Valida todas as cidades
        cities.forEach(this::validateCity);

        // Verifica duplicatas no banco de dados
        Set<Integer> existingIbgeIds = repository.findAll().stream()
                .map(City::getIbgeId)
                .collect(Collectors.toSet());

        // Filtra apenas as cidades novas
        List<City> newCities = cities.stream()
                .filter(city -> !existingIbgeIds.contains(city.getIbgeId()))
                .collect(Collectors.toList());

        if (!newCities.isEmpty()) {
            repository.saveAll(newCities);
        }
    }
}
