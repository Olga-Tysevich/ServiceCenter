package it.academy.service.mappers;


import java.util.List;

public interface CustomMapper<R, T> {

    T toDTO(R entity);

    R toEntity(T dto);

    List<T> toDTOList(List<R> entityList);

}
