package com.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

import javafx.application.Platform;

/**
 * TCP Chat Service - Connects to TCP Chat Server (Port 5000)
 * Uses Socket, BufferedReader, PrintWriter for communication
 */
public class TcpChatService {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Thread listenerThread;
    private Consumer<String> messageCallback;
    private Consumer<String> statusCallback;
    private boolean running = false;
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;
    
    /**
     * Connect to TCP Chat Server
     * @param username Username for the chat
     * @param messageCallback Callback for incoming messages
     * @param statusCallback Callback for connection status
     * @throws IOException If connection fails
     */
    public void connect(String username, Consumer<String> messageCallback, Consumer<String> statusCallback) throws IOException {
        this.messageCallback = messageCallback;
        this.statusCallback = statusCallback;
        
        // Create socket connection
        socket = new Socket(SERVER_HOST, SERVER_PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        
        // Send username
        writer.println(username);
        
        running = true;
        updateStatus("Connected to TCP Chat Server");
        
        // Start listener thread
        startListener();
    }
    
    /**
     * Start listener thread to receive messages from server
     */
    private void startListener() {
        listenerThread = new Thread(() -> {
            try {
                String message;
                while (running && (message = reader.readLine()) != null) {
                    final String msg = message;
                    Platform.runLater(() -> messageCallback.accept(msg));
                }
            } catch (IOException e) {
                if (running) {
                    Platform.runLater(() -> updateStatus("Connection lost: " + e.getMessage()));
                }
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }
    
    /**
     * Send message to server
     * @param message Message to send
     */
    public void sendMessage(String message) {
        if (writer != null && running) {
            writer.println(message);
        }
    }
    
    /**
     * Disconnect from server
     */
    public void disconnect() {
        running = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            updateStatus("Disconnected");
        } catch (IOException e) {
            updateStatus("Error disconnecting: " + e.getMessage());
        }
    }
    
    /**
     * Check if connected
     * @return true if connected
     */
    public boolean isConnected() {
        return running && socket != null && !socket.isClosed();
    }
    
    private void updateStatus(String status) {
        if (statusCallback != null) {
            statusCallback.accept(status);
        }
    }
}
