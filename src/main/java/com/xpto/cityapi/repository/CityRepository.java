package com.xpto.cityapi.repository;

import com.xpto.cityapi.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends MongoRepository<City, String> {
    List<City> findByState(String state);
    List<City> findByCapitalTrue();

    Optional<City> findByIbgeId(Integer ibgeId);
}
