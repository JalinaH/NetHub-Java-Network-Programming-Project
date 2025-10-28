module com.client.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.client.client to javafx.fxml;
    exports com.client.client;
}