package com.muyi.lukman.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.muyi.lukman.exception.BadCredentialsException;
import com.muyi.lukman.exception.BadRequestException;
import com.muyi.lukman.exception.ConflictException;
import com.muyi.lukman.exception.NotFoundException;
import com.muyi.lukman.model.ErrorDetails;
import com.muyi.lukman.model.Response;
import com.muyi.lukman.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice(annotations = RestController.class, basePackages = "com.muyi.lukman.controller")
@ResponseBody
public class ServiceApiAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceApiAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleValidationException(MethodArgumentNotValidException e) {
        Response response = new Response();
        response.setCode("400");
        response.setDescription("Input Validation failed");
        BindingResult result = e.getBindingResult();
        List<FieldError> errorList = result.getFieldErrors();
        List<ErrorDetails> errors = new ArrayList<>();
        for (FieldError fieldError : errorList) {
            errors.add(new ErrorDetails(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        response.setErrors(errors);

        LOGGER.info("MethodArgumentNotValidException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Response response = new Response();
        response.setCode("400");
        response.setDescription(e.getLocalizedMessage());

        if (e.getCause() != null) {
            String message = e.getCause().getMessage();
            if (e.getCause() instanceof JsonMappingException) {
                String[] arr = message.split("\\(");
                if (arr.length > 0) {
                    String temp = arr[0];
                    String[] arr2 = message.split("\\[");
                    if (arr2.length > 1) {
                        message = temp + " (fieldName: [" + arr2[1];
                    } else {
                        message = temp;
                    }
                }
            }

            if (e.getCause() instanceof JsonParseException) {
                String[] arr = message.split("at");
                if (arr.length > 0) {
                    String temp = arr[0];
                    JsonParseException jpe = (JsonParseException) e.getCause();
                    message = temp + " [line: " + jpe.getLocation().getLineNr() + ", column: " + jpe.getLocation().getColumnNr() + "]";
                }
            }
            response.setDescription("test:" + message);
        }
        LOGGER.info("HttpMessageNotReadableException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleNotFoundException(BadRequestException e) {
        Response response = new Response();
        response.setCode(e.getErrorCode());
        response.setDescription(e.getMessage());

        LOGGER.info("BadRequestException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response handleBadCredentialException(BadCredentialsException e) {
        Response response = new Response();
        response.setCode(e.getErrorCode());
        response.setDescription(e.getMessage());

        LOGGER.info("BadCredentialsException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServletRequestBindingException(ServletRequestBindingException e) {
        Response response = new Response();
        response.setCode("10011");
        response.setDescription("Invalid Credentials");

        LOGGER.info("ServletRequestBindingException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response handleSecurityAccessDenied(AccessDeniedException e) {
        Response response = new Response();
        response.setCode("403");
        response.setDescription("Security: Access Denied");
        LOGGER.error("Access violation: Access Denied Exception");

        LOGGER.info("AccessDeniedException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response handleException(Exception e) {
        Response response = new Response();
        response.setCode("001");
        response.setDescription(e.getMessage());

        LOGGER.info("Exception");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleNotFoundException(NotFoundException e) {
        Response response;
        response = new Response();
        response.setCode(e.getErrorCode());
        response.setDescription(e.getMessage());

        LOGGER.info("NotFoundException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public Response handleConflictException(ConflictException e) {
        Response response;
        response = new Response();
        response.setCode(e.getErrorCode());
        response.setDescription(e.getMessage());

        LOGGER.info("ConflictException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleInvalidFormatException(com.fasterxml.jackson.databind.exc.InvalidFormatException e) {
        Response response = new Response();
        response.setCode("400");
        response.setDescription("Invalid input " + e.getValue());

        LOGGER.info("InvalidFormatException");
        LOGGER.error(e.toString());
        //LoggerUtil.logError(LOGGER, e);
        return response;
    }

//    @ExceptionHandler(NoSuchElementException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public Response handleInvalidFormatException(NoSuchElementException e) {
//        Response response = new Response();
//        response.setCode("400");
//        response.setDescription("Element not found ");
//
//        LOGGER.info("NoSuchElementException");
//        LOGGER.error(e.toString());
//        //LoggerUtil.logError(LOGGER, e);
//        return response;
//    }

}