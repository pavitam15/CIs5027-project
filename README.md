You are required to implement an interactive application using a client-server architecture to simulate room temperature and light controlling.
The system consists of software simulating server component and client components. 
The server software component is designed to listen to a given port (or multiple ports) and can serve requests from multiple clients. 
The expected functionality of the server and client components are given below.

1.1 Server:
- The server is initiated by establishing a server socket on a given port number.
o The port number can be given as a command line argument
- The server should have the facility to read and parse a CSV file containing a list of temperature (in Celsius) and light levels (in lumens).
- The file name could be given as a command line argument.
- Each row in the file should be read with a n second delay (the delay is given as a command line argument)
  and the program should extract the temperature value and light level.
- The server should facilitate up to 2 client connections.
- Each client can be either connected to the same port or different ports (a design choice).
  However, they should be served simultaneously. Each client connection to the server should treated as a separate thread.
- Once a client is connected, the server performs the following:
o Check the type of the client
o If the client corresponds to temperature controller,
§ Server should send the temperature value to the client at a n seconds delay between each reading
o If the client corresponds to the light controller,
§ Server should send the light level in lumens to the client at a n seconds delay between each reading
- The server should stop communicating with the client (s) when the send the STOP command
- Once the server reaches the end of the file, it should start reading data (rows) from the beginning simulating a continuous supply of data

1.2 Clients: temperature controller and light level controller
- Once the server is initiated, the client can connect to the server using the server’s host IP address and port number.
o These could be provided as command line arguments when initiating a client.
- Once connected to the server, the client should identify its type (temperature or light level controller) to the server.
- The server will then start sending temperature or light level values to the client program
- For client 1 (temperature controller):
o Once the client receives a temperature value, the program should compare it with set thresholds and adjust the speed of the fan.
- For client 2 (light controller):
o Once the client receives a light level in lumens, the program should adjust the brightness of the background or change its colour theme. ]
- Clients can stop communicating with the server when they type in “STOP” command in, or click on a stop button in the User Interface.

To run in IntelliJ:
NT. The port number and IP address are hardcoded into the program to make sure it can run in the IDE.
First run the SimpleServer program by right clicking the class and selecting “run”
Next run the SimpleClient program by right clicking the class and selecting “run”
The server will prompt the client to enter either “light” or “temp” on the client side
If you enter something else, an error message will appear
If you enter “light”, the bulb app will open, and the server will begin to print the light level values with a one second delay between each row
If you enter “temp”, the fan app will open, and the server will begin to print the temperature values with a one second delay between each row
If you type “STOP” whilst the csv files are not being printed, the connection will be closed, and the thread exited
To exit whilst the csv is being printed, you will have to stop the programs through the IDE.
