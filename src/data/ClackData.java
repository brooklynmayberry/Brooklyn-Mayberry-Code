package data;

import java.util.Date;
import java.io.*;

/**
 * Represents the data sent between the client and the server.
 *
 * subclass: FileClackData, MessageClackData
 *
 * data:
 * String userName - name of client user --
 * int type - integer representing the type of data exchanged --
 * Date date - object representing the date the message was created.
 *
 * @author Benjamin T. Mustico and Brooklyn Mayberry
 * @version 7 November, 2022
 *
 */
public abstract class ClackData implements Serializable {
    public static final int CONSTANT_LISTUSERS = 0;
    public static final int CONSTANT_LOGOUT = 1;
    public static final int CONSTANT_SENDMESSAGE = 2;
    public static final int CONSTANT_SENDFILE = 3;

    protected String username;
    protected int type;
    protected Date date;

    /**
     * Two-argument constructor
     *
     * @param userName username
     * @param type type
     */
    public ClackData(String userName, int type){
       this.username = userName;
       this.type = type;
       date = new Date();
    }

    /**
     * Single argument constructor
     *
     * @param type type
     */
    public ClackData(int type){
       this("Anon", type);
   }

    /**
     * Default Constructor
     */
   public ClackData(){
       // type = 0 corresponds to giving the list of people
       this("Anon", 0);
   }

    /**
     * Returns the type
     *
     * @return type
     */
   public int getType(){
      return this.type;
   }

    /**
     * Returns the username
     *
     * @return username
     */
   public String getUserName(){
       return this.username;
   }

    /**
     * Returns the date
     *
     * @return date
     */
   public Date getDate(){
       return this.date;
   }

    /**
     * Encrypts inputStringToEncrypt using key
     *
     * @param inputStringToEncrypt inputStringToEncrypt
     * @param key key
     * @return encrypted message
     */
   protected String encrypt(String inputStringToEncrypt, String key) {
       // Convert strings into character arrays
       char[] inputArray = inputStringToEncrypt.toCharArray();

       String upperKey = key.toUpperCase();
       String lowerKey = key.toLowerCase();

       // Creating an uppercase and a lowercase character array
       char[] upperKeyArray = upperKey.toCharArray();
       char[] lowerKeyArray = lowerKey.toCharArray();

       String encryptedMessage = "";

       int keyLength = upperKeyArray.length;
       int keyPos = 0;

       for(char c : inputArray) {
           // Letter is uppercase (A = 65 and Z = 90)
           if( (65 <= (int)c) && ((int)c <= 90  ) ) {
               int numCode = (int)c - (int)'A';
               int keyCode = (int)(upperKeyArray[keyPos]) - (int)'A';
               encryptedMessage += (char)( (numCode + keyCode) % 26 + (int)'A');
               //System.out.println(numCode); // Testing
           }
           // Letter is lowercase (a = 97 and z = 122)
           else if( (97 <= (int)c) && ((int)c <= 122) ) {
               int numCode = (int)c - (int)'a';
               int keyCode = (int)lowerKeyArray[keyPos] - (int)'a';
               encryptedMessage += (char)( (numCode + keyCode) % 26 + (int)'a');
               // System.out.println(numCode); // Testing
           }
           else {
               encryptedMessage += c;

               // The do-while loop will put the keyPos back to where it just was
               --keyPos;
           }
           // System.out.println(encryptedMessage); // Testing

           // Find the next character in the key that is a letter.
           do {
               ++keyPos;
               if( keyPos == keyLength) {
                   keyPos = 0;
               }
           } while (! ((65 <= (int)upperKeyArray[keyPos]) && ((int)upperKeyArray[keyPos] <= 90) ) );
       }
       //System.out.println(encryptedMessage);
       return encryptedMessage;
   }

    /**
     * Decrypts inputStringToDecrypt using key
     *
     * @param inputStringToDecrypt inputStringToDecrypt
     * @param key key
     * @return decrypted message
     */
   protected String decrypt(String inputStringToDecrypt, String key) {

       char[] inputArray = inputStringToDecrypt.toCharArray();

       String upperKey = key.toUpperCase();
       String lowerKey = key.toLowerCase();

       //
       char[] upperKeyArray = upperKey.toCharArray();
       char[] lowerKeyArray = lowerKey.toCharArray();

       int keyLength = upperKeyArray.length;
       String decryptedMessage = "";
       //System.out.println(inputArray);

       int keyPos = 0;
       for( char c : inputArray) {
           // The letter is uppercase
           if ((65 <= (int) c) && ((int) c <= 90)) {
               int encryptedChar = (int)c - (int)'A';
               int keyCode = (int)upperKeyArray[keyPos] - (int)'A';
               int decryptedHex = encryptedChar - keyCode;
               if( decryptedHex < 0) {
                   decryptedHex += 26;
               }
               //System.out.println(decryptedHex);
               decryptedMessage += (char) (decryptedHex + (int)'A');

           }
           // The letter is lowercase
           else if ( (97 <= (int)c) && ((int)c <= 122) ) {
               int encryptedChar = (int)c - (int)'a';
               int keyCode = (int)lowerKeyArray[keyPos] - (int)'a';
               //System.out.println((int)lowerKeyArray[keyPos]);
               int decryptedHex = encryptedChar - keyCode;
               if( decryptedHex < 0) {
                   decryptedHex += 26;
               }
               decryptedMessage += (char) (decryptedHex + (int)'a');
           }
           else {
               // Restores the key position to what it previously was.
               --keyPos;
               decryptedMessage += c;

           }

           // Find the next letter character in the key
           do {
               ++keyPos;
               if( keyPos == keyLength) {
                   keyPos = 0;
               }
           } while (! ((65 <= (int)upperKeyArray[keyPos]) && ((int)upperKeyArray[keyPos] <= 90) ) );


       }

       return decryptedMessage;
   }

    /**
     * Abstract getData() method
     *
     * @return data
     */
   public abstract String getData();

    /**
     * Abstract overloaded getData() method
     *
     * @param key key
     * @return data
     */
    public abstract String getData(String key);
}
