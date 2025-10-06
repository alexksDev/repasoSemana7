package com.alexdev.week077.controller;

import com.alexdev.week077.dto.*;
import com.alexdev.week077.entity.Booking;
import com.alexdev.week077.entity.Flight;
import com.alexdev.week077.entity.User;
import com.alexdev.week077.service.BookingService;
import com.alexdev.week077.service.FlightBatchService;
import com.alexdev.week077.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final FlightBatchService flightBatchService;
    private final BookingService bookingService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<NewIdDTO> create(@Valid @RequestBody NewFlightRequestDTO newFlightRequestDTO) {
        Flight flight = modelMapper.map(newFlightRequestDTO, Flight.class);
        Flight savedFlight = flightService.createFlight(flight);
        return ResponseEntity.status(201).body(new NewIdDTO(String.valueOf(savedFlight.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.findById(id);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponseDTO> search(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) String estDepartureTimeFrom,
            @RequestParam(required = false) String estDepartureTimeTo) {

        LocalDateTime from = estDepartureTimeFrom != null && !estDepartureTimeFrom.isEmpty()
                ? LocalDateTime.parse(estDepartureTimeFrom) : null;
        LocalDateTime to = estDepartureTimeTo != null && !estDepartureTimeTo.isEmpty()
                ? LocalDateTime.parse(estDepartureTimeTo) : null;

        List<Flight> flights = flightService.searchFlights(flightNumber, airlineName, from, to);

        FlightSearchResponseDTO response = new FlightSearchResponseDTO();
        List<FlightSearchResponseDTO.FlightDTO> flightDTOs = flights.stream()
                .map(flight -> {
                    FlightSearchResponseDTO.FlightDTO dto = new FlightSearchResponseDTO.FlightDTO();
                    dto.setId(String.valueOf(flight.getId()));
                    dto.setAirlineName(flight.getAirlineName());
                    dto.setFlightNumber(flight.getFlightNumber());
                    dto.setEstDepartureTime(flight.getEstDepartureTime());
                    dto.setEstArrivalTime(flight.getEstArrivalTime());
                    dto.setAvailableSeats(flight.getAvailableSeats());
                    return dto;
                })
                .collect(Collectors.toList());
        response.setItems(flightDTOs);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-many")
    public ResponseEntity<NewFlightManyResponseDTO> createMany(@Valid @RequestBody NewFlightManyRequestDTO requestDTO) {
        flightBatchService.createManyFlights(requestDTO.getFlights());
        return ResponseEntity.ok(new NewFlightManyResponseDTO("Processing flights"));
    }

    @PostMapping("/book")
    public ResponseEntity<NewIdDTO> book(
            @RequestBody FlightBookRequestDTO requestDTO,
            @AuthenticationPrincipal User user) {
        Long flightId = Long.parseLong(requestDTO.getFlightId());
        Booking booking = bookingService.bookFlight(flightId, user);
        return ResponseEntity.status(200).body(new NewIdDTO(String.valueOf(booking.getId())));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.findById(id);

        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(String.valueOf(booking.getId()));
        response.setBookingDate(booking.getBookingDate());
        response.setFlightId(String.valueOf(booking.getFlight().getId()));
        response.setFlightNumber(booking.getFlight().getFlightNumber());
        response.setCustomerId(String.valueOf(booking.getCustomer().getId()));
        response.setCustomerFirstName(booking.getCustomer().getFirstName());
        response.setCustomerLastName(booking.getCustomer().getLastName());
        response.setEstDepartureTime(booking.getFlight().getEstDepartureTime());
        response.setEstArrivalTime(booking.getFlight().getEstArrivalTime());

        return ResponseEntity.ok(response);
    }
}
