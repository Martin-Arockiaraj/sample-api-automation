package com.test.api.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.filter.LoggingFilter;

// This class is a wrapper around the REST api request which provides various helper methods

public class APIRequest {

    private UriBuilder uri;
    private Map<String, String> params = new HashMap<String, String>();
    private Map<String, String> pathParams = new HashMap<String, String>();
    private Map<String, String> headers = new HashMap<String, String>();
    private MediaType contentType = MediaType.APPLICATION_XML_TYPE;
    private MediaType acceptType;
    private String httpMethod;
    private String body;

    private APIRequest(String uri, String method) {
        this.uri = UriBuilder.fromUri(uri);
        this.httpMethod = method;
    }

    // Static Helper method for GET request
    public static APIRequest GET(String uri) {
        return new APIRequest(uri, HttpMethod.GET);
    }

    // Static Helper method for POST request
    public static APIRequest POST(String uri) {
        return new APIRequest(uri, HttpMethod.POST);
    }

    // Static Helper method for PUT request
    public static APIRequest PUT(String uri) {
        return new APIRequest(uri, HttpMethod.PUT);
    }

    // Static Helper method for DELETE request
    public static APIRequest DELETE(String uri) {
        return new APIRequest(uri, HttpMethod.DELETE);
    }

    // Helper method to add path to url dynamically
    public APIRequest pathParam(String key, String value) {
        pathParams.put(key, value);
        return this;
    }

    // Helper method to decide type of content of request
    public APIRequest type(MediaType type) {
        this.contentType = type;
        return this;
    }

    // Helper method to add header dynamically
    public APIRequest header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    // Helper method to add body dynamically
    public APIRequest body(String body) {
        this.body = body;
        return this;
    }

    // This method will put all the neccary data to the url and hits the API
    public APIResponse invoke() {
        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        // Refer this: https://stackoverflow.com/a/34760001
        client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);

        //Print all logs for each request and response
        client.register(new LoggingFilter(Logger.getLogger(APIResponse.class.getName()), true));

        WebTarget webTarget = client.target(uri);
        if (!params.isEmpty()) {
            for (Entry<String, String> key : params.entrySet()) {
                webTarget = webTarget.queryParam(key.getKey(), key.getValue());
            }
        }

        if (!pathParams.isEmpty()) {
            for (Entry<String, String> key : pathParams.entrySet()) {
                webTarget = webTarget.resolveTemplate(key.getKey(), key.getValue());
            }
        }

        Invocation.Builder invocationBuilder = webTarget.request();
        if (acceptType != null) {
            invocationBuilder = invocationBuilder.accept(acceptType);
        }

        if (!headers.isEmpty()) {
            for (String key : headers.keySet()) {
                invocationBuilder.header(key, headers.get(key));
            }
        }

        Response response;

        if (body == null) {
            response = invocationBuilder.method(httpMethod, Response.class);
        } else {
            response = invocationBuilder.method(httpMethod, Entity.entity(body, contentType), Response.class);
        }

        return new APIResponse(response);
    }

}

