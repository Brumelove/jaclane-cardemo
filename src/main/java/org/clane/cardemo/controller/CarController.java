package org.clane.cardemo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clane.cardemo.facade.CarFacade;
import org.clane.cardemo.payload.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.clane.cardemo.api.ClientApi.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(CAR_API)
public class CarController {

    private CarFacade carFacade;

    @PostMapping(CREATE_CAR_API)
    public ResponseEntity<CarResponse>  createCar(@Valid @RequestBody CreateCarRequest createCarRequest){
        return new ResponseEntity<>(carFacade.handleCreateCar(createCarRequest) , HttpStatus.CREATED);
    }

    @GetMapping(SEARCH_CAR_API)
    public ResponseEntity<CarResponseList> searchCar(@ModelAttribute SearchCarRequest searchCarRequest){
        return new ResponseEntity<>(carFacade.searchCars(searchCarRequest) , HttpStatus.OK);
    }

    @GetMapping(ALL_CAR_API)
    public ResponseEntity<CarResponseList> searchCar(){
        return new ResponseEntity<>(carFacade.getAllCars() , HttpStatus.OK);
    }

    @GetMapping(SEARCH_CAR_BY_NAME_API)
    public ResponseEntity<CarResponse> searchCarByName(@PathVariable("carName") String carName){
        return new ResponseEntity<>(carFacade.findCarByName(carName) , HttpStatus.OK);
    }

    @PutMapping(UPDATE_CAR_API)
    public ResponseEntity<CarResponse> updateCarDetails(@PathVariable("carId") Long carId , @Valid @RequestBody UpdateCarRequest updateCarRequest){
        return new ResponseEntity<>(carFacade.handleUpdateCar(updateCarRequest, carId) , HttpStatus.OK);
    }
}
