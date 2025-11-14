package com.gs.fiap.jobfitscore.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler( RegraDeNegocioException.class )
	public ResponseEntity<Object> handleResourceNotFound( RegraDeNegocioException ex ) {
		Map<String, Object> body = new HashMap<>();
		body.put( "timestamp", LocalDateTime.now() );
		body.put( "status", HttpStatus.NOT_FOUND.value() );
		body.put( "error", "Recurso não encontrado" );
		body.put( "message", ex.getMessage() );
		
		return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( body );
	}
	
	@ExceptionHandler( MethodArgumentNotValidException.class )
	public ResponseEntity<Object> handleValidationErrors( MethodArgumentNotValidException ex ) {
		Map<String, Object> body = new HashMap<>();
		
		Map<String, String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.collect( Collectors
						.toMap( fieldError -> fieldError.getField(),
								fieldError -> fieldError.getDefaultMessage(),
								( existing, replacement ) -> existing )
				);
		body.put( "message", "ERRO DE VALIDAÇÃO" );
		body.put( "timestamp", LocalDateTime.now() );
		body.put( "status", HttpStatus.BAD_REQUEST.value() );
		body.put( "error", "Validação de campos" );
		body.put( "fields", errors );
		
		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( body );
	}
	
	@ExceptionHandler( Exception.class )
	public ResponseEntity<Object> handleGenericException( Exception ex ) {
		Map<String, Object> body = new HashMap<>();
		body.put( "timestamp", LocalDateTime.now() );
		body.put( "status", HttpStatus.INTERNAL_SERVER_ERROR.value() );
		body.put( "error", "Erro interno" );
		body.put( "message", ex.getMessage() );
		
		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( body );
	}
}
