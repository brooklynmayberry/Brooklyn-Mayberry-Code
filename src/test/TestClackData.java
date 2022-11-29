package test;

import data.*;
import java.io.*;

/**
 * A class to test all the methods of ClackData and its subclasses.
 *
 * @author Benjamin T. Mustico and Brooklyn Mayberry
 * @version 26 October, 2022
 */
public class TestClackData {

    public static void main(String[] args) {


        // FileClackData Testing

        // File Default Constructor
        FileClackData data1 = new FileClackData();

        // File 3-argument constructor.
        FileClackData data2 = new FileClackData("Ben", "test.txt", 3);

        FileClackData data3 = new FileClackData("Ben", "test.txt", 3);

        FileClackData data4 = new FileClackData("Brooklyn", "test.txt", 3);

        // Testing the equals method (they aren't equal)
        if( data2.equals(data4)) {
            System.out.println("They are equal!");
        }
        else {
            System.out.println("They are NOT equal!");
        }

        // Testing the equals method (they are equal)
        if( data2.equals(data3)) {
            System.out.println("They are equal!");
        }
        else {
            System.out.println("They are NOT equal!");
        }

        // Testing the hash codes.
        System.out.println(data4.hashCode());
        System.out.println(data2.hashCode());

        //testing the toString method of the FileClackData
        System.out.println(data4);
        System.out.println(data1);

        // Testing the accessor and mutator methods.
        data4.setFileName("new_file.txt");

        System.out.println(  data4.getFileName() );
        System.out.println( data4.getData() );
        System.out.println( data4.getType() );
        System.out.println( data4.getUserName());
        System.out.println( data4.getDate());

        // Testing the read and write file contents
        try {
            data4.readFileContents();
            data4.writeFileContents();
        } catch (FileNotFoundException fnfe) {
            //System.out.println("ERROR IS HERE!");
            System.err.println("File Not Found Exception occurred in Part 1 (Code lines 64-65):");
            System.err.println(fnfe.getMessage());
        } catch (IOException ioe) {
            System.err.println("IO Exception occurred in Part 1 (Code lines 64-65):");
            System.err.println(ioe.getMessage());
        }

        // MessageClackData testing

        System.out.println();
        System.out.println("MessageClackData Testing");
        System.out.println();

        // Testing the constructors
        MessageClackData msg1 = new MessageClackData();
        MessageClackData msg2;
        msg2 = new MessageClackData( "Ben", "E = -grad V.", 2);

        MessageClackData msg3;
        msg3 = new MessageClackData("Brooklyn", "Hello, World!", 2);

        MessageClackData msg4;
        msg4 = new MessageClackData( "Ben", "E = -grad V.", 2);

        // Testing the equals method (they aren't equal)
        if( msg1.equals(msg4)) {
            System.out.println("They are equal!");
        }
        else {
            System.out.println("They are NOT equal!");
        }

        // Testing the equals method (they are equal)
        if( msg2.equals(msg4)) {
            System.out.println("They are equal!");
        }
        else {
            System.out.println("They are NOT equal!");
        }

        //Testing overridden methods
        System.out.println(msg4.toString());
        System.out.println(msg1.hashCode() + " " + msg2.hashCode()
                + " " + msg3.hashCode() + " " + msg4.hashCode());

        System.out.println(msg3);

        // Testing accessor methods
        System.out.println(msg4.getData());
        System.out.println(msg4.getType() );
        System.out.println(msg4.getUserName());
        System.out.println(msg4.getDate());




        // ensures that the code ran all the way through
        System.out.println();
        System.out.println("The code for part 1 ran without a fatal error");
        System.out.println();



        // Part 2 testing stuff

        //Encryption

        String toEncrypt = "Encrypt me!";
        String key = "KEY";
        ClackData encrypt = new MessageClackData("Brooklyn", toEncrypt, key, 0);
        System.out.println("The encryption of " + toEncrypt + " Using the key " + key + " is:");
        System.out.println(encrypt.getData());
        System.out.println("The decryption of thi message is:");
        System.out.println(encrypt.getData(key));
        System.out.println();

        //MessageClackData

        MessageClackData msg21;
        msg21 = new MessageClackData( "Ben", "E = -gradV", "KEY", 2);
        System.out.println(msg21.getData());
        System.out.println(msg21.getData("KEY"));

        //FileClackData
        FileClackData file20 = new FileClackData("Ben",
                "test.txt",
                0);

        try{
            file20.readFileContents("TIME");
            System.out.println(file20.getData());
            System.out.println(file20.getData("TIME"));
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());

            try{
                System.out.println(file20.getData());
            } catch (NullPointerException npe) {
                System.err.println("Something is null");

            }
        } catch (NullPointerException npe) {
            System.err.println("Something is null");
        }

        FileClackData file21 = new FileClackData("Ben",
                "test.txt", 0);
        try {
            file21.readFileContents();
            file21.writeFileContents();
        } catch (IOException ioe){
            System.err.println("An IOException Occurred");
        }

        try {
            file21.readFileContents("B");
            System.out.println(file21.getData());
            file21.writeFileContents("B");
        } catch (IOException ioe){
            System.err.println("An IOException Occurred");
        }

        System.out.println("The code for part 2 ran without a fatal error");

    }
}
