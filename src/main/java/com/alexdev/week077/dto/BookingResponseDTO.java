package com.alexdev.week077.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private String id;
    private LocalDateTime bookingDate;
    private String flightId;
    private String flightNumber;
    private String customerId;
    private String customerFirstName;
    private String customerLastName;
}
