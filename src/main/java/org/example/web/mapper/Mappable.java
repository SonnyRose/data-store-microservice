package org.example.web.mapper;

import java.util.List;

public interface Mappable<E, D>{
    E toEntity(D DTO);
    D toDTO(E entity);
    List<E> toEntity(List<D> DTO);
    List<D> toDTO(List<E> entity);
}
