package com.example.restapi.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ApiError<T> {
    int code;
    List<T> errors;
}
