package com.shadeien.tomcat;

import com.shadeien.tomcat.connector.HttpConnector;

public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.start();
    }
}
