package data;

import java.io.Serializable;


/**
 * Represents the data sent between the client and the server if it is a message.
 * superclass: ClackData
 *
 * data:
 * String message - stores the message as a string.
 *
 * @author Benjamin T. Mustico and Brooklyn Mayberry
 * @version 26 October, 2022
 *
 */
public class MessageClackData extends ClackData implements Serializable {

    private String message;

    /**
     * Three argument constructor. Calls the super constructor on userName and type,
     * and stores message as private data
     *
     * @param userName userName
     * @param message message
     * @param type type
     */

    public MessageClackData(String userName, String message, int type){
        super(userName, type);
        this.message = message;
    }

    /**
     * Constructor that encrypts the message using the given key.
     *
     * @param userName username
     * @param message message
     * @param key key
     * @param type type
     */
    public MessageClackData(String userName, String message, String key, int type) {
        super(userName, type);
        this.message = encrypt(message, key);
    }

    /**
     * Default Constructor. Sets the type to 2.
     */
    public MessageClackData(){
        super( 2);
        this.message = null;
    }

    /**
     * Returns the message
     *
     * @return message
     */
    public String getData(){
        return message;
    }

    /**
     * Decrypts the message using key and returns it.
     *
     * @param key key
     * @return decrypted message
     */
    public String getData(String key) {

        return super.decrypt(message, key);
    }


    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash * super.type;
        hash = 31 * hash * super.username.hashCode();
        hash = 31 * hash * super.date.hashCode();
        //ignores null objects to avoid a NullPointerException from being thrown
        if(! (this.message == null)) {
            hash = 31 * hash * message.hashCode();
        }
        return hash;
    }


    @Override
    public boolean equals(Object other) {
        MessageClackData newData = (MessageClackData)other;

        // Testing to see if the message is null to avoid a NullPointerException
        if( this.message == null ^ newData.message == null) {
            return false;
        }
        else {
            return (((this.message == null && newData.message == null) ||
                    this.message.equals(newData.message))
                    && (super.username).equals(newData.username)
                    && super.type == newData.type && (super.date).equals(newData.date));
        }
    }

    @Override
    public String toString(){
        return "The message is: " + message + "\n" +
                "The date the message was sent is: " + super.date + "\n" +
                "The person that sent the message is: " + super.username + "\n" +
                "The type of data is: " + super.type + "\n";
    }


}
