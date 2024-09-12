package com.abbrevio.abbrevio.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {
    private List<String> messages = new ArrayList<>();
    private Integer statusCode;
    private Date timestamp;
}
