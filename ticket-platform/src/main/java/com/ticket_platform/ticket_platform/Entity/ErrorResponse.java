package com.ticket_platform.ticket_platform.Entity;

import java.time.LocalDate;

public class ErrorResponse {

    private String message;

    private String path;

    private LocalDate timeStamp;

    public ErrorResponse(String message, String requestURI){
        this.message = message;
        this.path = requestURI;
        this.timeStamp = LocalDate.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }
}
