package de.uni_bayreuth.se.testingdemo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequest(
        @NotBlank String title,
        @NotBlank String author,
        @Min(1900) int year
) {
}
