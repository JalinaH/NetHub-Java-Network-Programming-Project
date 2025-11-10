# Quick Start Guide - NetHub

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- Terminal/Command Prompt

## 1. Start Backend Servers

Open a terminal and run:

```bash
cd backend
mvn clean compile
mvn exec:java -Dexec.mainClass="com.network.MainServer"
```

You should see all three servers start:
- ✓ TCP Chat Server on port 5000
- ✓ NIO File Server on port 5001  
- ✓ UDP Health Server on port 5002

**Keep this terminal running!**

## 2. Start JavaFX Client

Open a **new terminal** and run:

```bash
cd client
mvn clean javafx:run
```

The client GUI will open with 4 tabs.

## 3. Test the Application

### TCP Chat Tab
1. Enter username: "Alice"
2. Click "Connect"
3. Type a message and click "Send"
4. Open another client instance to test chat between users

### UDP Health Tab
1. Click "Initialize"
2. Try the commands:
   - Click "PING" → Should return "PONG"
   - Click "STATUS" → Shows server status
   - Click "INFO" → Shows server information

### NIO File Transfer Tab
1. Click "Connect"
2. Click "List Files" to see available files
3. Enter a filename (e.g., "sample.txt")
4. Click "Download" and choose where to save

**Note**: Place test files in `backend/server_files/` directory first!

### Link Checker Tab
1. Enter URL: `https://www.google.com`
2. Click "Check Link"
3. View the status code and validation result
4. Try more URLs!

## Troubleshooting

### "Address already in use"
A port is being used by another process:
```bash
# Find what's using the port
lsof -i :5000

# Kill the process (replace PID with actual number)
kill -9 <PID>
```

### "Connection refused"
Make sure the backend servers are running first!

### Client won't start
Verify you have Java 17:
```bash
java -version
```

## What You're Learning

- **TCP Chat**: Socket programming with ServerSocket and BufferedReader
- **UDP Health**: Connectionless communication with DatagramSocket
- **NIO Files**: Non-blocking I/O with Selector and SocketChannel
- **Link Checker**: HTTP operations with HttpURLConnection
- **Multithreading**: ExecutorService and synchronized blocks

## Next Steps

1. Check the documentation:
   - `PROJECT_SUMMARY.md` - Complete overview
   - `CLIENT_README.md` - Client details
   - Backend README files for server implementations

2. Experiment with the code:
   - Modify the chat messages
   - Add new UDP commands
   - Create your own files to transfer
   - Test different URLs

3. Test edge cases:
   - What happens when server disconnects?
   - Try invalid inputs
   - Test with many concurrent clients

Enjoy exploring Java networking! 🚀
