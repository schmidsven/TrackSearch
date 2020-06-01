package io.sfrei.tracksearch_web.config;

import io.sfrei.tracksearch.exceptions.TrackSearchException;
import io.sfrei.tracksearch_web.exceptions.RequestException;
import io.sfrei.tracksearch_web.pojos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class TSControllerExceptionHandler {

    @ExceptionHandler({
            RequestException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception) {
        return getErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TrackSearchException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception) {
        return getErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(Exception e, HttpStatus status) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), OffsetDateTime.now().toString()),
                status);
    }

}
