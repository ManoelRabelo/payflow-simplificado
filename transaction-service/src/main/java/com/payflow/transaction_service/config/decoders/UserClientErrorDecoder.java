package com.payflow.transaction_service.config.decoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.payflow.transaction_service.dtos.ExceptionDTO;
import com.payflow.transaction_service.exception.RemoteServiceException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class UserClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    public Exception decode(String s, Response response) {
        ExceptionDTO exceptionDTO;

        try {
            String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            exceptionDTO = mapper.readValue(body, ExceptionDTO.class);
        } catch (IOException ex) {

            exceptionDTO = new ExceptionDTO(
                    response.status(),
                    "Erro desconhecido no serviço externo: " + ex.toString(),
                    LocalDateTime.now()
            );
        }

        return new RemoteServiceException(
                HttpStatus.valueOf(exceptionDTO.status()),
                exceptionDTO.message()
        );
    }
}
