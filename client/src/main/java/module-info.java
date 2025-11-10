module com.client.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens com.client to javafx.fxml;
    opens com.client.controller to javafx.fxml;
    
    exports com.client;
    exports com.client.controller;
    exports com.client.service;
}