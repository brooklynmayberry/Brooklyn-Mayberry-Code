package main;

import data.*;
import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Server Side Client IO
 *
 * @author Benjamin T. Mustico & Brooklyn Mayberry
 * @version 18 November, 2022
 */
public class ServerSideClientIO implements Runnable{
    private boolean closeConnection;
    private ClackData dataToReceiveFromClient;
    private ClackData dataToSendToClient;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    private ClackServer server;
    private Socket clientSocket;
    private String username;

    /**
     * Two-argument constructor
     *
     * @param server server
     * @param clientSocket clientSocket
     */
    public ServerSideClientIO(ClackServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.username = "anon";
        this.closeConnection = false;
        this.inFromClient = null;
        this.outToClient = null;
        this.dataToReceiveFromClient = null;
        this.dataToSendToClient = null;
    }

    /**
     * Username accessor method
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Receives data
     *
     */
    public void receiveData() {
        try {
            dataToReceiveFromClient = (ClackData) inFromClient.readObject();

            if( (dataToReceiveFromClient.getType() == ClackData.CONSTANT_LOGOUT) ) {
                this.closeConnection = true;
                server.remove(this);
            } else if( (dataToReceiveFromClient.getType() == ClackData.CONSTANT_LISTUSERS)) {
                System.out.println("Listing Users!");
                String userList = server.getUserList();
                dataToReceiveFromClient = new MessageClackData("your friendly neighborhood server",
                        userList,  ClackData.CONSTANT_SENDMESSAGE);
            }



        } catch (NullPointerException npe) {
            System.err.println("The inFromClient stream is null");
        } catch(ClassNotFoundException cnfe) {
            System.err.println("A ClackData object was not found in the stream!");
        }catch(IOException ioe) {
            System.err.println("An IOException Occurred.");
        }
    }

    /**
     * Sends data to client
     *
     */
    public void sendData() {
        try {
            outToClient.writeObject(dataToSendToClient);
        } catch(NotSerializableException nse) {
            System.err.println("The object does not implement 'Serializable' interface.");
        } catch(IOException ioe) {
            System.err.println("An IOException occurred");
        }
    }

    public void setDataToSendToClient(ClackData dataToSendToClient) {
        this.dataToSendToClient = dataToSendToClient;
    }

    @Override
    public void run() {
        try {
            // This does the stuffs
            this.outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            this.inFromClient = new ObjectInputStream(clientSocket.getInputStream());

            try {
                this.username = (String) inFromClient.readObject();
            } catch(ClassNotFoundException cnfe) {
                System.err.println("Error reading the username");
                this.username = "ERROR";
            } catch(ClassCastException cce) {
                System.err.println("The username was not in the stream in the proper location");
                this.username = "ERROR";
            }


        while( !closeConnection ) {
                // just wait.

                receiveData();
                System.out.println("Data received!");
                System.out.println(dataToReceiveFromClient);
                server.broadcast( dataToReceiveFromClient );
                System.out.println("Broadcasted!");
                System.out.println(dataToSendToClient);

            }

            this.inFromClient.close();
            this.outToClient.close();

        } catch(IOException ioe) {
            System.err.println("An IOException occurred");
        }
    }
}
