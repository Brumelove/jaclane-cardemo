package org.clane.cardemo.api;

public interface ClientApi {

    String CAR_API = "/api";

    String CREATE_CAR_API = "/createCar";

    String SEARCH_CAR_API = "/searchCar";

    String ALL_CAR_API = "/all";

    String SEARCH_CAR_BY_NAME_API = "/name/{carName}";

    String UPDATE_CAR_API = "/updateCar/id/{carId}";
}
