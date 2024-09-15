package com.abbrevio.abbrevio.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomNotFoundException extends EntityNotFoundException {

    private Class<?> entity;
    private String parameter;
    private Object value;

    public CustomNotFoundException(Class<?> entity, String parameter, Object value) {
        this.entity = entity;
        this.parameter = parameter;
        this.value = value;
    }
}
