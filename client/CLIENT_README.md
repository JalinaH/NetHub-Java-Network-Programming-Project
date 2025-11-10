# NetHub Client - JavaFX Frontend

A complete JavaFX client application that connects to all three backend services and demonstrates 5 networking concepts.

## Features

### 1. TCP Chat Client (Port 5000)
- **Networking Concepts**: Socket, BufferedReader, PrintWriter
- **Features**:
  - Username-based authentication
  - Real-time message broadcasting
  - Multi-threaded message receiving
  - Clean disconnect handling

### 2. UDP Health Check Client (Port 5002)
- **Networking Concepts**: DatagramSocket, DatagramPacket
- **Features**:
  - Connectionless communication
  - PING command for server availability
  - STATUS command for server health
  - INFO command for server details
  - Timeout handling (5 seconds)

### 3. NIO File Transfer Client (Port 5001)
- **Networking Concepts**: SocketChannel, ByteBuffer, Non-blocking I/O
- **Features**:
  - List available files on server
  - Download files using NIO
  - Directory chooser for save location
  - Progress feedback

### 4. Link Checker (HTTP)
- **Networking Concepts**: URL, HttpURLConnection
- **Features**:
  - URL format validation
  - HTTP status code checking (200, 404, etc.)
  - Response time measurement
  - User-friendly error messages
  - HEAD request for efficiency

## Project Structure

```
client/
├── src/main/java/com/client/
│   ├── NetHubClient.java              # Main JavaFX application
│   ├── controller/
│   │   └── MainController.java        # Main UI controller
│   └── service/
│       ├── TcpChatService.java        # TCP Socket communication
│       ├── UdpHealthService.java      # UDP Datagram communication
│       ├── NioFileService.java        # NIO SocketChannel communication
│       └── LinkCheckerService.java    # HttpURLConnection utilities
└── src/main/resources/
    └── fxml/
        └── main-view.fxml             # UI layout (tabbed interface)
```

## Running the Client

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Backend servers running (MainServer.java)

### Compile and Run

```bash
# Compile
mvn clean compile

# Run with Maven
mvn javafx:run

# Or package and run
mvn clean package
java -jar target/client-1.0-SNAPSHOT.jar
```

## Usage Guide

### TCP Chat Tab
1. Enter a username
2. Click "Connect" to join the chat server
3. Type messages and press "Send" or Enter
4. Messages from all connected users appear in the chat area
5. Click "Disconnect" to leave

### UDP Health Tab
1. Click "Initialize" to create UDP socket
2. Use command buttons:
   - **PING**: Check if server is alive
   - **STATUS**: Get detailed server status
   - **INFO**: Get server information
3. Responses appear in the text area
4. Click "Close" to cleanup socket

### NIO File Transfer Tab
1. Click "Connect" to establish NIO connection
2. Click "List Files" to see available files
3. Enter filename in the text field
4. Click "Download" to save file locally
5. Choose destination folder in dialog
6. Click "Disconnect" when done

### Link Checker Tab
1. Enter a URL (e.g., https://www.google.com)
2. Click "Check Link" or press Enter
3. View results:
   - ✓ Valid links (status 200-399)
   - ✗ Invalid links (status 400+, errors)
   - Response time in milliseconds
   - HTTP status code and description

## Networking Concepts Demonstrated

### 1. TCP/IP Communication (Member 1)
- **Socket**: Client-side TCP connection
- **BufferedReader**: Reading text from TCP stream
- **PrintWriter**: Writing text to TCP stream
- **Connection-oriented**: Reliable, ordered delivery

### 2. Multithreading (Member 2)
- **Thread**: Separate thread for receiving messages
- **Platform.runLater()**: JavaFX thread-safe UI updates
- **Daemon threads**: Background threads that don't block exit
- **Synchronized communication**: Between network and UI threads

### 3. UDP Communication (Member 3)
- **DatagramSocket**: Connectionless UDP socket
- **DatagramPacket**: Individual UDP packets with send/receive
- **InetAddress**: Server address resolution
- **Timeout handling**: setSoTimeout() for non-blocking receives

### 4. NIO (Non-blocking I/O) (Member 4)
- **SocketChannel**: NIO socket channel
- **ByteBuffer**: Efficient byte array operations
- **Configurable blocking**: Can be blocking or non-blocking
- **InetSocketAddress**: Address for NIO connections

### 5. URL and HTTP (Member 5)
- **URL**: Parses and validates URL format
- **HttpURLConnection**: Opens HTTP connection
- **HTTP methods**: HEAD request for link checking
- **Status codes**: 200 (OK), 404 (Not Found), 500 (Error), etc.
- **Exception handling**: MalformedURLException, UnknownHostException, IOException

## Architecture

### Service Layer Pattern
- Separates network logic from UI logic
- Reusable service classes
- Clean dependency injection into controllers

### Callback Pattern
- `Consumer<String>` callbacks for asynchronous events
- Message callbacks for incoming data
- Status callbacks for connection state

### JavaFX Best Practices
- FXML for UI design
- Controller classes for logic
- Platform.runLater() for thread-safe UI updates
- Property binding for reactive UI

## Thread Safety

### TCP Chat Service
- Listener thread receives messages
- Platform.runLater() updates UI safely
- Writer thread sends messages (PrintWriter is thread-safe)

### UDP Health Service
- Commands run in background threads
- Responses posted to UI thread via Platform.runLater()
- Socket timeout prevents indefinite blocking

### NIO File Service
- File operations run in background threads
- Progress updates via Platform.runLater()
- Blocking mode for simplicity

### Link Checker Service
- Each check runs in background thread
- Results posted to UI thread
- Non-blocking UI during checks

## Error Handling

### Connection Errors
- IOException: Network unavailable, server not running
- UnknownHostException: Invalid hostname
- ConnectException: Server refused connection
- SocketTimeoutException: Server didn't respond in time

### User Input Errors
- Empty username/URL validation
- File not found on server
- Invalid URL format
- Missing required fields

### UI Feedback
- Status labels show current state
- Alert dialogs for critical errors
- Text areas log all operations
- Button states reflect connection status

## Testing the Client

### With Backend Servers Running

1. **Start Backend**:
   ```bash
   cd backend
   mvn exec:java -Dexec.mainClass="com.network.MainServer"
   ```

2. **Start Client**:
   ```bash
   cd client
   mvn javafx:run
   ```

3. **Test Each Service**:
   - TCP: Connect with multiple clients, send messages
   - UDP: Send PING, STATUS, INFO commands
   - NIO: List files, download sample files
   - Link Checker: Test various URLs (valid/invalid)

### Test URLs for Link Checker
- Valid: https://www.google.com (should return 200)
- Valid: https://github.com (should return 200)
- Invalid: https://thiswebsitedoesnotexist12345.com (UnknownHostException)
- Not Found: https://www.google.com/nonexistent (should return 404)

## Dependencies

```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.14</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17.0.14</version>
    </dependency>
    
    <!-- Standard Java Networking (included in JDK) -->
    <!-- java.net.Socket, java.net.DatagramSocket, java.nio.channels, java.net.URL -->
</dependencies>
```

## UI Design

### Color Scheme
- Header: Dark blue (#2c3e50)
- Buttons: Green (#27ae60), Blue (#3498db), Purple (#9b59b6), Orange (#e67e22)
- Background: Light gray (#f4f4f4)
- Panels: Light blue (#ecf0f1)
- Footer: Dark gray (#34495e)

### Layout
- Tabbed interface for easy navigation
- Consistent button placement
- Status labels in all tabs
- Large text areas for output
- Responsive design with HBox.hgrow

## Performance Considerations

### Memory Usage
- Bounded buffer sizes (8KB for NIO)
- Daemon threads don't prevent GC
- Proper resource cleanup on disconnect

### Network Efficiency
- HEAD requests instead of GET for link checking
- UDP for stateless health checks (no connection overhead)
- NIO ByteBuffer for efficient file transfer
- Connection reuse where possible

### UI Responsiveness
- All network operations in background threads
- Platform.runLater() for minimal UI updates
- Non-blocking file downloads
- Timeout for UDP operations

## Troubleshooting

### "Connection refused"
- Ensure backend MainServer is running
- Check correct ports (5000, 5001, 5002)
- Verify no firewall blocking

### "Module not found" errors
- Check module-info.java exports/opens
- Ensure all packages are listed
- Verify JavaFX dependencies in pom.xml

### UI not updating
- Check Platform.runLater() usage
- Verify callbacks are set correctly
- Check for exceptions in console

### File download fails
- Ensure file exists on server
- Check write permissions in download directory
- Verify NIO connection is active

## Future Enhancements

- [ ] SSL/TLS for secure communication
- [ ] File upload to NIO server
- [ ] Private messages in TCP chat
- [ ] Chat history persistence
- [ ] UDP broadcast discovery
- [ ] Multiple server support
- [ ] Configuration file for server addresses
- [ ] Dark mode theme
- [ ] Logging to file

## Contributors

- Member 1: TCP Server implementation
- Member 2: Multithreading implementation
- Member 3: UDP Server implementation
- Member 4: NIO Server implementation
- Member 5: Client UI and Link Checker

## Course Information

- **Course**: IN3111 - Network Programming
- **Level**: 3, Semester 1
- **Project**: Java Network Programming Assignment
- **Technologies**: JavaFX 17, Java Networking APIs (java.net, java.nio)
