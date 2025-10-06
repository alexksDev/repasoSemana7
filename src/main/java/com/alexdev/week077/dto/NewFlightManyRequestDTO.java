package com.alexdev.week077.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewFlightManyRequestDTO {
    private List<NewFlightRequestDTO> flights;
}
