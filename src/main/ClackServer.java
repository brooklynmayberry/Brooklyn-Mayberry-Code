package main;

import data.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

/**
 * ClackServer class. The server that will handle all interactions between users.
 *
 * data: int port, boolean closeConnection, ClackData dataToReceiveFromClient,
 * ClackData dataToSendToClient
 *
 * @author Benjamin T. Mustico and Brooklyn Mayberry
 * @version 7 November, 2022
 *
 */
public class ClackServer {

    public static final int DEFAULT_PORT = 7000;
    private int port;
    private boolean closeConnection;
    private ArrayList<ServerSideClientIO> serverSideClientIOList;

    //private ArrayList<String> dataToSendToGoogle;
    /*
    private ClackData dataToReceiveFromClient;
    private ClackData dataToSendToClient;
    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;
    */

    /**
     * Constructor that uses the input as the port number and sets the data objects to
     * null.
     *
     * @param port port
     * @throws IllegalArgumentException IllegalArgumentException
     */
    public ClackServer( int port ) throws IllegalArgumentException {
        if(port < 1024) {
            throw new IllegalArgumentException("The Port Number must be greater than 1024!");
        }
        this.port = port;
        this.closeConnection = false;
        serverSideClientIOList = new ArrayList();
        /*
        this.dataToReceiveFromClient = null;
        this.dataToSendToClient = null;
        this.outToClient = null;
        this.inFromClient = null;
        */
    }

    /**
     * Constructor that sets the port number to the default and the data objects
     * to null.
     *
     * @throws IllegalArgumentException IllegalArgumentException
     */
    public ClackServer() throws IllegalArgumentException {
        this( DEFAULT_PORT );
    }

    /**
     * Start method
     */
    public void start() {
        // Implement later
        try {
            ServerSocket ssk = new ServerSocket(this.port);
            while(!closeConnection) {
                Socket clientSkt = ssk.accept();
                //Scanner scan = new Scanner(clientSkt.getInputStream());
                //String username = scan.nextLine();
                //String userName = (String)
                //dataToSendToGoogle.add(username);

                ServerSideClientIO serverSideClientIO = new ServerSideClientIO(this, clientSkt);

                /*
                try{
                    wait();
                } catch(InterruptedException ie) {
                    System.err.println("error waking thread");
                }
                */

                serverSideClientIOList.add(serverSideClientIO);
                Thread clientCommunicatorThread = new Thread(serverSideClientIO);
                clientCommunicatorThread.start();
                System.out.println("A new user appeared!");

                // notifyAll();

                // Maybe implement the safer method later
            }
            ssk.close();

            // Old code (delete before submission)
            /*
            ServerSocket ssk = new ServerSocket(this.port);
            Socket clientConnection = ssk.accept();

            this.outToClient = new ObjectOutputStream(clientConnection.getOutputStream());
            this.inFromClient = new ObjectInputStream(clientConnection.getInputStream());

            while (!this.closeConnection) {
                // CHANGE ME PLEASE

                //receiveData();
                //dataToSendToClient = dataToReceiveFromClient;
                //System.out.println(dataToSendToClient);
                //sendData();

            }

            inFromClient.close();
            outToClient.close();
            clientConnection.close();
            ssk.close();

             */
        } catch (NoRouteToHostException nrthe) {
            System.err.println("No route was found");
        } catch (UnknownHostException uhe) {
            System.err.println("Unknown Host");
        } catch (ConnectException ce) {
            System.err.println("No connection could be established");
        } catch(IOException ioe) {
            System.err.println("An IOException occurred.");
        }
    }

    /*
     * Receives data from client
     *
     */
    /*
    public void receiveData() {
        try {
            dataToReceiveFromClient = (ClackData) inFromClient.readObject();

            this.closeConnection = (dataToReceiveFromClient.getType() == 1);

            //Print statement for debug purposes
            //System.out.println(dataToReceiveFromClient);

        } catch (NullPointerException npe) {
            System.err.println("The inFromClient stream is null");
        } catch(ClassNotFoundException cnfe) {
            System.err.println("A ClackData object was not found in the stream!");
        }catch(IOException ioe) {
            System.err.println("An IOException Occurred.");
        }
    }*/

    /*
     * Sends data to client
     *
     */
    /*
    public void sendData() {
        try {
            outToClient.writeObject(dataToSendToClient);
        } catch(NotSerializableException nse) {
            System.err.println("The object does not implement 'Serializable' interface.");
        } catch(IOException ioe) {
            System.err.println("An IOException occurred");
        }
    }*/

    /**
     * Broadcasts the message
     *
     */
    public synchronized void broadcast(ClackData dataToBroadcastToClients) {
        // Implement Later
        /*
        try{
            wait();
        } catch(InterruptedException ie) {
            System.err.println("Error waking thread");
        }*/
        int num = serverSideClientIOList.size();

        for(ServerSideClientIO serverSideClientIO : serverSideClientIOList) {
            serverSideClientIO.setDataToSendToClient(dataToBroadcastToClients);
            serverSideClientIO.sendData();
        }
        //notifyAll();
    }

    /**
     * Remove
     *
     */
    public synchronized void remove(ServerSideClientIO serverSideClientToRemove) {
        /*
        try{
            wait();
        } catch(InterruptedException ie) {
            System.err.println("Error waking thread");
        }*/
        this.serverSideClientIOList.remove(serverSideClientToRemove);
        //notifyAll();
    }

    /**
     * Returns the port number.
     *
     * @return port number
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Returns the list of users as a string
     *
     * @return userList
     */
    public String getUserList() {
        String userList = "";

        for(ServerSideClientIO sscio : serverSideClientIOList) {
            userList += sscio.getUsername() + System.getProperty("line.separator");
        }

        return userList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash * this.port;
        /*
        if( !(this.dataToReceiveFromClient == null) ) {
            hash = 31 * hash * this.dataToReceiveFromClient.hashCode();
        }
        if( !(this.dataToSendToClient == null )) {
            hash = 31 * hash * this.dataToSendToClient.hashCode();
        }*/
        if( closeConnection ) {
            hash = 31 * hash;
        }
        hash *= 31 * this.serverSideClientIOList.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        ClackServer newServer = (ClackServer)other;
        // Testing for null values weeds out NullPointerExceptions.
        /*
        if(this.dataToSendToClient == null ^ newServer.dataToSendToClient == null) {
            return false;
        }
        else if(this.dataToReceiveFromClient == null ^ newServer.dataToReceiveFromClient == null) {
            return false;
        }
        else {
            return ( this.port == newServer.port && this.closeConnection == newServer.closeConnection
                    && ( (this.dataToSendToClient == null && newServer.dataToSendToClient == null) ||
                    this.dataToSendToClient.equals(newServer.dataToSendToClient) ) &&
                    ( (this.dataToReceiveFromClient == null && newServer.dataToReceiveFromClient == null)
                    || this.dataToReceiveFromClient.equals(newServer.dataToReceiveFromClient) ) );
        }*/
        return (this.port == newServer.port && this.closeConnection == newServer.closeConnection &&
                this.serverSideClientIOList.equals(newServer.serverSideClientIOList));
    }

    @Override
    public String toString() {
        return "The port number is: " + this.port + "\n" +
                "The close connection is: " + closeConnection + "\n";
        /*      +
                "The data to be sent is: " + dataToSendToClient + "\n" +
                "The data to be received is: " + dataToReceiveFromClient + "\n";*/
    }

    /**
     * ClackServer Main Method
     *
     * @param args args
     */
    public static void main(String[] args) {
        try {
            //System.out.println("server");
            ClackServer server;

            if( args.length == 0) {
                server = new ClackServer();
                server.start();
                //System.out.println("base server");
            } else {
                int port = Integer.parseInt( args[0] );
                server = new ClackServer(port);
                server.start();
                //System.out.println(port);
            }
        } catch(NumberFormatException nfe) {
            System.err.println("The port number was not entered as an integer!");
        }
    }


}
