package test;

import main.*;

/**
 * A class to test the Clack Client
 *
 * @author Benjamin T. Mustico and Brooklyn Mayberry
 * @version 26 October, 2022
 *
 */
public class TestClackClient {

    public static void main(String[] args) {


        // Testing the ClackClient Constructors
        try {
            ClackClient client1 = new ClackClient("Brooklyn", "localhost", 10000);
            ClackClient client2 = new ClackClient("Brooklyn", "localhost", 10000);
            ClackClient client3 = new ClackClient("Ben", "my_computer");
            ClackClient client4 = new ClackClient("Ben");
            ClackClient client5 = new ClackClient();
            ClackClient client6 = new ClackClient("Neb", "local_comp", 9000);

            if( client1.equals(client2)) {
                System.out.println("They are equal!");
            }
            else {
                System.out.println("They are NOT equal!");
            }

            // Testing the equals method (they aren't equal)
            if( client1.equals(client4)) {
                System.out.println("They are equal!");
            }
            else {
                System.out.println("They are NOT equal!");
            }

            // Testing the hashCode() method
            System.out.println(client1.hashCode());
            System.out.println(client2.hashCode());
            System.out.println(client4.hashCode());

            //Testing the toString() method
            System.out.println(client1);
            System.out.println(client3);

            // port < 0
            System.out.println(client6);

            // Testing the accessor methods
            System.out.println(client5.getUserName());
            System.out.println(client5.getHostName());
            System.out.println(client5.getPort());

            //Testing the methods that haven't been implemented yet

            //Tested in Part 2

            //client1.start();

            //client1.readClientData();

            client1.sendData();
            client1.receiveData();
            client1.printData();

        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
        }


        // Ensures the code ran through
        System.out.println();
        System.out.println("The part 1 code ran without a fatal error");
        System.out.println();

        //Part 2 Testing

        // Constructors

        // Type = 1
        ClackClient cl100 = new ClackClient("Brooklyn", "The Host", 27000);
        ClackClient cl101 = new ClackClient("Ben", "The Second Host");
        ClackClient cl102 = new ClackClient("The Second Host Jr.");
        ClackClient defaultClient = new ClackClient();
        System.out.println("L");

        // Null username
        try {
            ClackClient nullUser = new ClackClient(null);
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
        }


        try {
            ClackClient cl1 = new ClackClient("Ben", "localhost", 5000);
            cl1.start();
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
        }

        System.out.println("The part 2 code ran without a fatal error");
    }
}
