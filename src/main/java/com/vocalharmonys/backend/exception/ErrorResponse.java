package com.vocalharmonys.backend.exception;

import java.time.Instant;
import java.util.List;

/**
 * The JSON body every error response uses, so the frontend only has to
 * handle one shape whether it's a 401, a 404, or a validation 400.
 *
 * @param fieldErrors only populated for validation failures (400), e.g.
 *                     {"email": "must be a well-formed email address"}
 */
public record ErrorResponse(
        Instant timestamp,
        int status,
        String message,
        List<FieldError> fieldErrors
) {

    public record FieldError(String field, String message) {
    }

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(Instant.now(), status, message, List.of());
    }

    public static ErrorResponse of(int status, String message, List<FieldError> fieldErrors) {
        return new ErrorResponse(Instant.now(), status, message, fieldErrors);
    }
}
