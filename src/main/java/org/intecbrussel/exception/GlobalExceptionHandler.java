package org.intecbrussel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status",404,
                        "error","Resource Not Found",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedActionException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status",403,
                        "error","Unauthorized action",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(DupblicateEnrollmentException.class)
        public ResponseEntity<?> handleDuplicateEnrollment(DupblicateEnrollmentException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                            "timestamp", LocalDateTime.now(),
                            "status",409,
                            "error","Duplicate Enrollment Record",
                            "message", ex.getMessage()
                    )
            );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status",500,
                        "error","Internal server error",
                        "message", ex.getMessage()
                )
        );
    }

}


