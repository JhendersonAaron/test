package pe.edu.upeu.sysasistencia.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {
    private int statusCode;
    private String message;
    private Object details;
    private String datetime;

    public CustomResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.datetime = java.time.LocalDateTime.now().toString();
    }

    public CustomResponse(int statusCode, String message, Object details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
        this.datetime = java.time.LocalDateTime.now().toString();
    }
}