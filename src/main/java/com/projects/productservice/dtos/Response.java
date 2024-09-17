package com.projects.productservice.dtos;

import lombok.Data;

@Data
public class Response {
    private ResponseStatus responseStatus;
    private String message;

    public Response(ResponseStatus responseStatus, String message) {
        this.responseStatus = responseStatus;
        this.message = message;
    }
    public static Response getFailureResponse(String errorMessage) {
        return new Response(ResponseStatus.FAILURE, errorMessage);
    }
    public static Response getSuccessResponse() {
        return new Response(ResponseStatus.SUCCESS, "");
    }
}
