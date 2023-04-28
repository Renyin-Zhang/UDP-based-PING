

# UDP Ping Program using Java

This program implements a Ping server and client using the User Datagram Protocol (UDP). The Ping server simulates packet loss and transmission delay, while the Ping client sends Ping requests to the server and calculates the Round Trip Time (RTT).

## Requirements
- JDK 1.8 or higher
- IDE or text editor to run Java code

## Functionality
### Ping Server (PingServer.java)
- Serves multiple clients concurrently
- Displays the message content sent by the clients (including header and payload)
- Simulates packet loss and transmission delay
- Responds to Ping requests with a random delay (less than 1 second) as a reply
- Starts using the command line: `java PingServer port`. `port` is the working port number of the Ping server.

### Ping Client (PingClient.java)
- Sends 10 Ping requests upon start
- Waits for up to 1 second to receive a reply message from the server after sending a request. If no reply is received within this time, the request or reply to the request is considered lost.
- Calculates the Round Trip Time (RTT) for each request and calculates the average, maximum, and minimum RTT of the 10 requests.
- Payload of each request message includes the sequence number and timestamp.
- Starts using the command line: `java PingClient host port`. `host` is the IP address or hostname of the Ping server and `port` is the working port number of the Ping server.

## Usage
1. Open a command prompt or terminal window.
2. Navigate to the directory containing the compiled `.class` files.
3. Start the Ping server using the command: `java PingServer port`
4. Start the Ping client using the command: `java PingClient host port`

## Future Improvements
- Implement error detection and correction using checksums
- Allow the user to specify the number of Ping requests to send
- Add a graphical user interface to display Ping results
