package org.example.web.mapper;

import org.example.model.Summary;
import org.example.web.DTO.SummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummaryMapper extends Mappable<Summary, SummaryDTO> {
}
