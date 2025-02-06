package com.xpto.cityapi.controller;

import com.xpto.cityapi.dto.CityDTO;
import com.xpto.cityapi.model.City;
import com.xpto.cityapi.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    private City city;
    private CityDTO cityDTO;

    @BeforeEach
    void setUp() {
        city = new City("1", 123456, "Test City", "TS", true, -23.55, -46.63);
        cityDTO = new CityDTO("1", 123456, "Test City", "TS", true, -23.55, -46.63);
    }

    @Test
    void shouldReturnAllCities() {
        when(cityService.getAllCities()).thenReturn(List.of(city));

        ResponseEntity<List<CityDTO>> response = cityController.getAllCities();

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        verify(cityService, times(1)).getAllCities();
    }

    @Test
    void shouldReturnCityById() {
        when(cityService.getCityById("1")).thenReturn(city);

        ResponseEntity<CityDTO> response = cityController.getCityById("1");

        assertNotNull(response);
        assertEquals("Test City", response.getBody().getName());
        verify(cityService, times(1)).getCityById("1");
    }

    @Test
    void shouldCreateCitySuccessfully() {
        when(cityService.createCity(any())).thenReturn(city);

        ResponseEntity<CityDTO> response = cityController.createCity(cityDTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        verify(cityService, times(1)).createCity(any());
    }

    @Test
    void shouldUpdateCitySuccessfully() {
        when(cityService.updateCity(any())).thenReturn(city);

        ResponseEntity<CityDTO> response = cityController.updateCity("1", cityDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(cityService, times(1)).updateCity(any());
    }

    @Test
    void shouldDeleteCitySuccessfully() {
        doNothing().when(cityService).deleteCity("1");

        ResponseEntity<Void> response = cityController.deleteCity("1");

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(cityService, times(1)).deleteCity("1");
    }
}
