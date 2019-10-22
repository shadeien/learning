package com.shadeien.tomcat.connector;

import com.shadeien.tomcat.request.HttpRequest;
import com.shadeien.tomcat.request.HttpRequestLine;
import com.shadeien.tomcat.response.HttpResponse;

import java.io.*;
import java.net.Socket;

public class HttpProcessor {
    private HttpRequest request;
    private HttpResponse response;

    public HttpProcessor(HttpConnector httpConnector) {

    }

    public void process(Socket socket) {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            request = new HttpRequest(input);
            response = new HttpResponse(output);

            response.setRequest(request);
            response.setHeader("Server", "Servlet Container");

            parseRequest(input, output);
//            parseHeaders(input);

//            if (request.getRequestURI().startWith("/servlet/")) {
//
//            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseRequest(InputStream input, OutputStream output) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        try {
            String str = bufferedReader.readLine();
            HttpRequestLine requestLine = new HttpRequestLine(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
