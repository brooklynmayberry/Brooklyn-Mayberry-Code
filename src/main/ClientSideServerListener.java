package main;

/**
 * Client Side Server Listener
 *
 * @author Benjamin T. Mustico & Brooklyn Mayberry
 * @version 18 November, 2022
 */
public class ClientSideServerListener implements Runnable{
    private ClackClient client;

    public ClientSideServerListener(ClackClient client) {
        this.client = client;
    }

    @Override
    public void run() {

        while( !client.getCloseConnection()) {
            client.receiveData();
            client.printData();

        }
    }
}
