package com.xpto.cityapi.controller;

import com.xpto.cityapi.dto.CityDTO;
import com.xpto.cityapi.model.City;
import com.xpto.cityapi.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Retrieve all cities", description = "Fetches a list of all available cities.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cities retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<CityDTO> cities = service.getAllCities()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a city by ID", description = "Fetches a city by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City found"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    public ResponseEntity<CityDTO> getCityById(@PathVariable String id) {
        City city = service.getCityById(id);
        return ResponseEntity.ok(convertToDTO(city));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new city", description = "Creates a new city and returns the created city details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "City created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<CityDTO> createCity(@Valid @RequestBody CityDTO cityDTO) {
        City city = convertToEntity(cityDTO);
        City createdCity = service.createCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdCity));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload cities via CSV", description = "Uploads a CSV file containing city data and saves it to the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CSV file processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid CSV file format"),
            @ApiResponse(responseCode = "500", description = "Error processing the CSV file")
    })
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("The CSV file is empty.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withDelimiter(',')
                     .withTrim())) {

            List<CSVRecord> records = csvParser.getRecords();
            if (records.isEmpty()) {
                return ResponseEntity.badRequest().body("The CSV file contains no data.");
            }

            List<City> cities = records.stream()
                    .map(this::convertCsvRecordToCity)
                    .collect(Collectors.toList());

            service.saveAllCities(cities);
            return ResponseEntity.status(HttpStatus.CREATED).body(cities.size() + " cities inserted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the CSV file: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a city", description = "Updates an existing city based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City updated successfully"),
            @ApiResponse(responseCode = "404", description = "City not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<CityDTO> updateCity(@PathVariable String id, @Valid @RequestBody CityDTO cityDTO) {
        City city = convertToEntity(cityDTO);
        city.setId(id);
        City updatedCity = service.updateCity(city);
        return ResponseEntity.ok(convertToDTO(updatedCity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a city", description = "Deletes a city based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "City deleted successfully"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    public ResponseEntity<Void> deleteCity(@PathVariable String id) {
        service.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    private City convertCsvRecordToCity(CSVRecord record) {
        return new City(
                null,
                Integer.parseInt(record.get("ibge_id").trim()),
                record.get("name").trim(),
                record.get("uf").trim(),
                "1".equals(record.get("capital")),
                Double.parseDouble(record.get("lat").trim()),
                Double.parseDouble(record.get("lon").trim())
        );
    }

    private CityDTO convertToDTO(City city) {
        return new CityDTO(
                city.getId(),
                city.getIbgeId(),
                city.getName(),
                city.getState(),
                city.isCapital(),
                city.getLatitude(),
                city.getLongitude()
        );
    }

    private City convertToEntity(CityDTO cityDTO) {
        return new City(
                cityDTO.getId(),
                cityDTO.getIbgeId(),
                cityDTO.getName(),
                cityDTO.getState(),
                cityDTO.isCapital(),
                cityDTO.getLatitude(),
                cityDTO.getLongitude()
        );
    }
}