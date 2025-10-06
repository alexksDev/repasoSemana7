package com.alexdev.week077.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NewFlightManyRequestDTO {
    @JsonProperty("inputs")
    private List<NewFlightRequestDTO> flights;
}
