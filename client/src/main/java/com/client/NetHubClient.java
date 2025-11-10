package com.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Client Application for Network Programming Project
 * 
 * This client connects to three backend services:
 * - TCP Chat Server (Port 5000)
 * - NIO File Server (Port 5001)
 * - UDP Health Server (Port 5002)
 * 
 * It also includes a LinkChecker utility using HttpURLConnection
 */
public class NetHubClient extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NetHubClient.class.getResource("/fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        
        stage.setTitle("NetHub Client - Network Programming");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            // Cleanup on close
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
