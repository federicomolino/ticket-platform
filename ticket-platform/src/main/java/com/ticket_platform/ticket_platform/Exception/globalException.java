package com.ticket_platform.ticket_platform.Exception;

import com.ticket_platform.ticket_platform.Entity.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class globalException {

    @ExceptionHandler(ExceptionNessunTicket.class)
    public ResponseEntity<ErrorResponse> errorApiNessunTicket(ExceptionNessunTicket e, HttpServletRequest request){
        ErrorResponse err = new ErrorResponse(
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExceptionNessunaCategoria.class)
    public ResponseEntity<ErrorResponse> errorApiNessunaCategoria(ExceptionNessunaCategoria ex, HttpServletRequest request){
        ErrorResponse err = new ErrorResponse(
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionStatoNonValido.class)
    public ResponseEntity<ErrorResponse> errorApiStatoNonValido(ExceptionStatoNonValido ex, HttpServletRequest request){
        ErrorResponse err = new ErrorResponse(
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }
}
