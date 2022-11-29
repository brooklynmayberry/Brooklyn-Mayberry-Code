package main;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.*;

import java.util.Scanner;
import java.net.*;


/**
 * Clack client
 *
 * @author Benjamin T. Mustico and Brooklyn Mayberry
 * @version 12 November, 2022
 *
 * Represents the client user. Stores the user's username, message, host name, port number,
 * and data to send and receive.
 */
public class ClackClient {
    private static final String key = "CRYPT";
    public static final int DEFAULT_PORT = 7000;
    private String userName;
    private String hostName;
    private int port;
    private boolean closeConnection;
    private ClackData dataToSendToServer;
    private ClackData dataToReceiveFromServer;
    private java.util.Scanner inFromStd;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;


    /**
     * ClackClient constructor. Accepts a username, host name, and port number.
     *
     * @param userName username
     * @param hostName host name
     * @param port port number
     */
    public ClackClient( String userName, String hostName, int port) throws IllegalArgumentException {
        if(userName == null) {
            throw new IllegalArgumentException("The Username cannot be null");
        } else if (hostName == null) {
            throw new IllegalArgumentException("The host name cannot be null");
        } else if (port < 1024) {
            throw new IllegalArgumentException("The port number must be greater than 1024");
        }
        this.userName = userName;
        this.hostName = hostName;
        this.port = port;
        this.closeConnection = false;
        this.dataToSendToServer = null;
        this.dataToReceiveFromServer = null;
        this.inFromServer = null;
        this.outToServer = null;
    }

    /**
     * ClackClient constructor. Accepts a username and host name. Sets the port number to
     * the default.
     *
     * Calls the three-argument constructor.
     *
     * @param userName username
     * @param hostName host name
     *
     */
    public ClackClient( String userName, String hostName) throws IllegalArgumentException{
        this(userName, hostName, DEFAULT_PORT);
    }

    /**
     * ClackClient constructor. Accepts a username. Sets the host name to "localhost"
     * and sets the port number to the default.
     *
     * Calls the two-argument constructor.
     *
     * @param userName username
     *
     */
    public ClackClient( String userName ) throws IllegalArgumentException{
        this(userName, "localhost");
    }

    /**
     * Default ClackClient constructor. Sets the username to "Anon", host name to "localhost",
     * and the port number to the default.
     *
     * Calls the single argument constructor.
     *
     */
    public ClackClient()throws IllegalArgumentException {
        this("Anon");
    }

    /**
     * The start method
     *
     */
    public void start() {
        try {
            // UPDATE WITH THREADS

            Socket skt = new Socket(this.hostName, this.port);
            this.outToServer = new ObjectOutputStream(skt.getOutputStream());
            this.inFromServer = new ObjectInputStream(skt.getInputStream());

            // Send username
            outToServer.writeObject(userName);
            System.out.println("Username sent!");

            this.inFromStd = new Scanner(System.in);

            Thread listenerThread = new Thread(new ClientSideServerListener(this));
            listenerThread.start();

            while (!this.closeConnection) {
                readClientData();
                sendData();
                //System.out.println("Data sent");
                //receiveData();
                //printData();
            }

            this.inFromStd.close();
            this.inFromServer.close();
            this.outToServer.close();
            skt.close();

        } catch (NoRouteToHostException nrthe) {
            System.err.println("No route was found to the server");
        } catch (UnknownHostException uhe) {
            System.err.println("Could not connect to the server");
        } catch(ConnectException ce) {
            System.err.println("The connection was refused!");
        }catch (IOException ioe) {
            System.err.println("An IOException occurred!");
        }
    }

    /**
     * Reads data from the client
     *
     */
    public void readClientData() {
        // Do we get the whole line or just the next word?
        String input = inFromStd.next();
        if( input.equals("DONE") ) {
            closeConnection = true;
            String message = "Thank you for using Clack! We appreciate your business. \n" +
                             "Our website: https://www.reddit.com/r/cs242memes/";

            dataToSendToServer = new MessageClackData("DEVS", message,
                    key, ClackData.CONSTANT_LOGOUT);
        } else if( input.equals("SENDFILE") ) {
            int end = input.length();
            // Extracts the filename
            try {
                String fileName = inFromStd.next();
                //System.out.println(fileName);
                dataToSendToServer = new FileClackData(userName, fileName, ClackData.CONSTANT_SENDFILE);

                // Try to read the file
                try {
                    ((FileClackData)dataToSendToServer).readFileContents(key);
                } catch(IOException ioe) {
                    dataToSendToServer = null;
                    System.err.println("The data from the file could not be read. Error Message:");
                    System.err.println(ioe.getMessage());
                }
            } catch (StringIndexOutOfBoundsException sioobe) {
                System.err.println("Filename could not be read.");
            }

        } else if ( input.equals("LISTUSERS") ) {
            // System.err.println("Great job. Doing what you just did could cause a fatal error.");
            // System.err.println("I hope you're happy...");
            // dataToSendToServer
            // Don't test yet, or a bad error will occur.
            //System.out.println("list users");
            dataToSendToServer = new MessageClackData(userName, "LISTUSERS",
                    ClackData.CONSTANT_LISTUSERS);
        } else {
            String message = input += inFromStd.nextLine();
            dataToSendToServer = new MessageClackData(userName, message, key, ClackData.CONSTANT_SENDMESSAGE);
        }
        //System.out.println(dataToSendToServer);
    }

    /**
     * Sends data to the server
     */
    public void sendData() {
        try {
            this.outToServer.writeObject(dataToSendToServer);

        } catch(IOException ioe) {
            System.err.println("An IOException occurred!");
        }
    }

    /**
     * Receives data from the server
     */
    public void receiveData() {
        try {
            this.dataToReceiveFromServer =  (ClackData)inFromServer.readObject();
        } catch(ClassNotFoundException cnfe) {
            System.err.println("The class to be read was not found");
        } catch(IOException ioe) {
            System.err.println("An IOException Occurred");
        }
    }

    /**
     * Prints the received data to standard output.
     */
    public void printData() {
        try {
            System.out.println("Host name: " + this.hostName);
            System.out.println("Message Sender: " + dataToReceiveFromServer.getUserName());
            System.out.println("The date the message was sent is: " + dataToReceiveFromServer.getDate());
            System.out.println("The data is: ");
            System.out.println(dataToReceiveFromServer.getData(key));
        } catch (NullPointerException npe) {
            System.err.println("The data is null!");
        }
    }

    /**
     * Returns the username
     *
     * @return userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Returns the host name
     *
     * @return hostName
     */
    public String getHostName() {
        return this.hostName;
    }

    /**
     * Returns the port number
     *
     * @return port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * returns the close connection
     *
     * @return closeConnection
     */
    public boolean getCloseConnection() {
        return this.closeConnection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash * this.userName.hashCode();
        hash = 31 * hash * this.hostName.hashCode();
        hash = 31 * hash * this.port;
        if( !(this.dataToSendToServer == null)) {
            hash = 31 * hash * this.dataToSendToServer.hashCode();
        }
        if( !(this.dataToReceiveFromServer == null) ) {
            hash = 31 * hash * this.dataToReceiveFromServer.hashCode();
        }
        if( closeConnection ) {
            hash = 31 * hash;
        }

        return hash;
    }

    @Override
    public boolean equals(Object other) {
        ClackClient newClient = (ClackClient)other;
        // Weeds out NullPointerExceptions
        if( (this.dataToSendToServer == null ^ newClient.dataToSendToServer == null) ) {
            return false;
        }
        if( (this.dataToReceiveFromServer == null ^ newClient.dataToReceiveFromServer == null)) {
            return false;
        }
        return ( this.userName.equals(newClient.userName) && this.hostName.equals(newClient.hostName)
                && this.port == newClient.port && this.closeConnection == newClient.closeConnection
                && ((this.dataToSendToServer == null && newClient.dataToSendToServer == null) ||
                this.dataToSendToServer.equals(newClient.dataToSendToServer))
                && ((this.dataToReceiveFromServer == null && newClient.dataToReceiveFromServer == null)
                || this.dataToReceiveFromServer.equals(newClient.dataToReceiveFromServer)) );
    }

    @Override
    public String toString() {
        return "The username is: " + userName + "\n" +
                "The host name is: " + hostName + "\n" +
                "The port number is: " + port + "\n" +
                "The close connection is: " + closeConnection + "\n" +
                "The data to be sent is: " + dataToSendToServer + "\n" +
                "The data to be received is: " + dataToReceiveFromServer + "\n";
    }

    /**
     * ClackClient Main Method
     *
     * @param args args
     */
    public static void main(String[] args) {
        try {
            String username;
            String hostName;
            int portNumber;
            ClackClient client;

            // System.out.println(args.length);

            if (args.length == 0) {
                client = new ClackClient();
                //System.out.println("Empty!");
                client.start();
                return;
            }
            //Split the string;
            int colonIndex = args[0].indexOf(":");
            int atIndex = args[0].indexOf("@");

            if ((atIndex > 0) && (colonIndex > 0)) {
                username = args[0].substring(0, atIndex);
                hostName = args[0].substring(atIndex + 1, colonIndex);
                portNumber = Integer.parseInt(args[0].substring(colonIndex + 1));
                client = new ClackClient(username, hostName, portNumber);
                client.start();
                //System.out.println("All " + username + " " + hostName + " " + portNumber);
            } else if ((atIndex > 0) && (colonIndex == -1)) {
                username = args[0].substring(0, atIndex);
                hostName = args[0].substring(++atIndex);
                client = new ClackClient(username, hostName);
                client.start();
                //System.out.println("No port");
                //System.out.println("All " + username + " " + hostName);
            } else if ((atIndex == -1) && (args[0].length() > 0)) {
                username = args[0];
                client = new ClackClient(username);
                client.start();
                //System.out.println("only username");
                //System.out.println(username);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("The port number was not entered as an integer");
        }
    }

} // End of class ClackClient
