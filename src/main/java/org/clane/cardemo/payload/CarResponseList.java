package org.clane.cardemo.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CarResponseList {

    private List<CarResponse> carResponseList;
}
