package com.vocalharmonys.backend.dto;

/** One row's outcome from a bulk import — the batch never aborts on a single bad row. */
public record BulkImportResult(String firstName, String lastName, String email, boolean created, String error) {

    public static BulkImportResult success(String firstName, String lastName, String email) {
        return new BulkImportResult(firstName, lastName, email, true, null);
    }

    public static BulkImportResult failure(String firstName, String lastName, String email, String error) {
        return new BulkImportResult(firstName, lastName, email, false, error);
    }
}
