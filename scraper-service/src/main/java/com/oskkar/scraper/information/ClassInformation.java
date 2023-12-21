package com.oskkar.scraper.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClassInformation {
    private String groupNumber;
    private String teacher;
    private String schedule;
    private String availableSpots;
}
