package net.etfbl.pisio.incidentservice.exceptions;

public class DuplicateIncidentException extends RuntimeException {
    public DuplicateIncidentException(String message) {
        super(message);
    }
}
