package com.client.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP Health Service - Connects to UDP Health Server (Port 5002)
 * Uses DatagramSocket and DatagramPacket for connectionless communication
 */
public class UdpHealthService {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5002;
    private static final int TIMEOUT = 5000; // 5 seconds
    
    /**
     * Initialize UDP service
     * @throws IOException If initialization fails
     */
    public void initialize() throws IOException {
        socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT);
        serverAddress = InetAddress.getByName(SERVER_HOST);
    }
    
    /**
     * Send command to UDP server and receive response
     * @param command Command to send (PING, STATUS, INFO)
     * @return Server response
     * @throws IOException If communication fails
     */
    public String sendCommand(String command) throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new IOException("Socket not initialized");
        }
        
        // Send request
        byte[] sendData = command.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(
            sendData, 
            sendData.length, 
            serverAddress, 
            SERVER_PORT
        );
        socket.send(sendPacket);
        
        // Receive response
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        
        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }
    
    /**
     * Close UDP socket
     */
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
    
    /**
     * Check if service is active
     * @return true if socket is open
     */
    public boolean isActive() {
        return socket != null && !socket.isClosed();
    }
}
