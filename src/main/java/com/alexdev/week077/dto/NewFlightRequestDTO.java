package com.alexdev.week077.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewFlightRequestDTO {

    @NotBlank(message = "Airline name is required")
    private String airlineName;

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$", message = "Flight number must match format: 2-3 letters followed by 3 digits")
    private String flightNumber;

    @NotNull(message = "Estimated departure time is required")
    private LocalDateTime estDepartureTime;

    @NotNull(message = "Estimated arrival time is required")
    private LocalDateTime estArrivalTime;

    @NotNull(message = "Available seats is required")
    @Positive(message = "Available seats must be greater than 0")
    private Integer availableSeats;
}
