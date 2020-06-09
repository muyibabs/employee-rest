package com.muyi.lukman.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "", required = true)
    private String fieldName;
    @ApiModelProperty(value = "", required = true)
    private String message;

    public ErrorDetails(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

}
