package com.shadeien.tomcat.request;

public class HttpRequestLine {
    private String method;
    private String uri;
    private String protocol;

    public HttpRequestLine(String str) {
        String[] arrays = str.split(" ");
        method = arrays[0];
        uri = arrays[1];
        protocol = arrays[2];
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
