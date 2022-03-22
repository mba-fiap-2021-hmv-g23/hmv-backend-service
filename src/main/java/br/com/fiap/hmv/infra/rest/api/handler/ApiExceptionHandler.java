package br.com.fiap.hmv.infra.rest.api.handler;

import br.com.fiap.hmv.application.exception.HttpException;
import br.com.fiap.hmv.infra.rest.api.v1.model.ApiModelError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiModelError> httpExceptionHandler(HttpException ex) {
        String message = ex.getMessage();
        log.error("[INFRA_REST_API_HANDLER] Erro ao processar requisição. [{}].", message);
        return ResponseEntity.status(ex.getHttpStatus()).body(ApiModelError.builder()
                .timestamp(now())
                .status(ex.getHttpStatus().value())
                .error(ex.getHttpStatus().getReasonPhrase())
                .message(message)
                .build());
    }

}