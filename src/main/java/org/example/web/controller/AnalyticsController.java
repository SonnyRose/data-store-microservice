package org.example.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Summary;
import org.example.model.enums.MeasurementType;
import org.example.model.enums.SummaryType;
import org.example.service.interfaces.SummaryService;
import org.example.web.DTO.SummaryDTO;
import org.example.web.mapper.SummaryMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final SummaryService summaryService;
    private final SummaryMapper summaryMapper;

    @GetMapping("/summary/{sensorId}")
    private SummaryDTO getSummary(@PathVariable long sensorId,
                                  @RequestParam(value = "mt", required = false)
                                  Set<MeasurementType> measurementTypes,
                                  @RequestParam(value = "st", required = false)
                                      Set<SummaryType> summaryTypes
    ) {
        Summary summary = summaryService.get(sensorId,
                measurementTypes,
                summaryTypes);
        return summaryMapper.toDTO(summary);
    }
}
