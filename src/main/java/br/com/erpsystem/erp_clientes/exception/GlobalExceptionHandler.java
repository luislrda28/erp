package br.com.erpsystem.erp_clientes.exception; // Ajuste o pacote

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError; // Importe FieldError

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Indica que esta classe é um "controller" para exceções globais
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request
    }

    // Você pode adicionar outros @ExceptionHandler para outros tipos de exceções aqui
    // Ex: Para tratar exceções de cliente não encontrado (por exemplo, NotFoundException)
    // @ExceptionHandler(NotFoundException.class)
    // public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    //     return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    // }

    // Exemplo de tratamento para exceções gerais não mapeadas
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<String> handleGeneralException(Exception ex) {
    //     return new ResponseEntity<>("Ocorreu um erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    // } a
}