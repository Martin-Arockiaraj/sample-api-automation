package com.test.api.network;

import javax.ws.rs.core.Response;

import static org.testng.Assert.assertEquals;

public class APIResponse {

    private Response response;
    private String body;

    public APIResponse(Response response) {
        this.response = response;
    }

    // Helper method to assert the response status
    public APIResponse assertStatus(int status) {
        assertEquals(status, response.getStatus());
        return this;
    }

    // Helper method to assert the given body with actual response body
    public APIResponse assertBody(String content) {
        String body = getBody();

        assertEquals(content, body);
        return this;
    }

    // Returns the body of the response (Returns only string)
    public String getBody() {
        if (body == null) {
            return response.readEntity(String.class);
        }
        return body;
    }
}