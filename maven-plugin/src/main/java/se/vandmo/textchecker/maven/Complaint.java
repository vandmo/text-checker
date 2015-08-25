package se.vandmo.textchecker.maven;


public final class Complaint {

    private final String message;

    public Complaint(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
