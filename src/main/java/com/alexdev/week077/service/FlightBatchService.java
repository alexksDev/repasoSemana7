package com.alexdev.week077.service;

import com.alexdev.week077.dto.NewFlightRequestDTO;
import com.alexdev.week077.entity.Flight;
import com.alexdev.week077.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightBatchService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createManyFlights(List<NewFlightRequestDTO> flightRequests) {
        for (NewFlightRequestDTO request : flightRequests) {
            try {
                if (!flightRepository.existsByFlightNumber(request.getFlightNumber())) {
                    if (request.getEstDepartureTime().isBefore(request.getEstArrivalTime())) {
                        Flight flight = modelMapper.map(request, Flight.class);
                        flightRepository.save(flight);
                    }
                }
            } catch (Exception e) {
                // Log and continue
            }
        }
    }
}
