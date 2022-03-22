package br.com.fiap.hmv.infra.rest.api.handler;

import br.com.fiap.hmv.application.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> httpExceptionHandler(HttpException ex) {
        String message = ex.getMessage();
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", now());
        error.put("status", ex.getHttpStatus().value());
        error.put("error", ex.getHttpStatus().getReasonPhrase());
        error.put("message", message);
        log.error("[INFRA_REST_API_HANDLER] Erro ao processar requisição. [{}].", message);
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

}