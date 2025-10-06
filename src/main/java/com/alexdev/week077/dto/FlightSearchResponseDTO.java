package com.alexdev.week077.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlightSearchResponseDTO {
    private List<FlightDTO> items;

    @Data
    public static class FlightDTO {
        private String id;
        private String airlineName;
        private String flightNumber;
        private LocalDateTime estDepartureTime;
        private LocalDateTime estArrivalTime;
        private Integer availableSeats;
    }
}
