package data;

import java.io.*;
import java.util.*;

/**
 * Represents the data sent between the client and the server if it is a file.
 * superclass: ClackData
 *
 * data:
 * String fileName - name of data file --
 * String fileContents - Stores the contents of the file as a string.
 *
 * @author Benjamin T. Mustico and Brooklyn Mayberry
 * @version 7 November, 2022
 *
 */
public class FileClackData extends ClackData implements Serializable{

    private String fileName;
    private String fileContents;

    /**
     * Constructor that takes the username, file name, and type.
     *
     * @param userName userName
     * @param fileName fileName
     * @param type type
     */
    public FileClackData(String userName, String fileName, int type){
        super(userName, type);
        this.fileName = fileName;
        this.fileContents = null;
    }

    /**
     * Default constructor. Sets the type to 3 and the file name and contents to null.
     */
    public FileClackData(){
        super( 3 );
        this.fileName = null;
        this.fileContents = null;
    }

    /**
     * sets the file name to the input
     *
     * @param fileName fileName
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    /**
     * Returns the file name
     *
     * @return fileName
     */
    public String getFileName(){
        return this.fileName;
    }


    /**
     * returns the contents of the file
     *
     * @return fileContents
     */
    public String getData() {
        return fileContents;
    }

    /**
     * Decrypts the string in fileContents using key and returns it.
     *
     * @param key key
     * @return decrypted fileContents
     */
    public String getData(String key) {
        return super.decrypt(fileContents, key);
    }

    /**
     * Reads the file contents into a String and returns it.
     *
     * @return readString
     * @throws IOException file not found
     * @throws IllegalStateException attempted use of scanner after closing
     * @throws NoSuchElementException scanner went out of bounds
     */
    private String readFileToString() throws IOException, IllegalStateException,
            NoSuchElementException {
        try {
            File file = new File(this.fileName);
            String readString = "";
            Scanner inputFileScanner = new Scanner(file);

            while(inputFileScanner.hasNextLine()) {
                readString += (inputFileScanner.nextLine()+ "\n");
            }

            inputFileScanner.close();

            return readString;

            // Scanner methods used only throw FileNotFoundException, IllegalStateException,
            // and NoSuchElementException
        } catch (FileNotFoundException fnfe) {
            throw new FileNotFoundException("The file was not found!");
        } catch (IllegalStateException ise) {
            throw new IllegalStateException("The scanner was used after being closed.");
        } catch (NoSuchElementException nsee) {
            throw new NoSuchElementException("The file scanner went out of bounds.");
        }
    }

    /**
     * Read the contents of the file
     *
     * @throws IOException file not found
     */
    public void readFileContents() throws IOException {
        try {
            this.fileContents = readFileToString();

            //System.out.println(fileContents);

            // The methods from the Scanner class that we used only throw
            // FileNotFoundException, IllegalStateException, and NoSuchElementException
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IllegalStateException ise) {
            System.err.println("The scanner was used after being closed.");
        } catch (NoSuchElementException nsee) {
            System.err.println("The file scanner went out of bounds.");
        }
    }

    /**
     * Reads the file contents and encrypts them using the key.
     *
     * @param key key
     * @throws IOException file not found
     */
    public void readFileContents( String key ) throws IOException {
        try {
            //System.out.println(readFileToString());
            this.fileContents = super.encrypt(readFileToString(), key);

            //System.out.println(this.fileContents);

            // Scanner methods used only throw FileNotFoundException, IllegalStateException,
            // and NoSuchElementException
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IllegalStateException ise) {
            System.err.println("The scanner was used after being closed.");
        } catch (NoSuchElementException nsee) {
            System.err.println("The file scanner went out of bounds.");
        }
    }

    /**
     * Writes the given string to the file
     *
     * @param writeMe writeMe
     * @throws IOException IO exception
     * @throws NullPointerException the file contents are null
     */
    private void writeContentsToFile(String writeMe) throws IOException, NullPointerException {
        try {
            File outputFile = new File(this.fileName);
            FileWriter writer = new FileWriter(outputFile);
            writer.write(writeMe);

            writer.close();
        } catch (FileNotFoundException fnfe) {
            throw new FileNotFoundException("The file was not found");
        } catch (IOException ioe) {
            throw new IOException("An IOException occurred");
        } catch (NullPointerException npe) {
            throw new NullPointerException("The file contents are null");
        }
    }

    /**
     * write fileContents to the file.
     *
     *
     */
    public void writeFileContents() {
        try {
            //System.out.println(this.fileContents);
            //System.out.println(fileName);
            writeContentsToFile(this.fileContents);
        } catch (IOException ioe) {
            System.err.println("An IOException occurred");
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }
    }

    /**
     * Decrypts the message and writes it to the file
     *
     * @param key key
     * @throws IOException IO Exception
     */
    public void writeFileContents(String key) {
        try{
            writeContentsToFile( super.decrypt(this.fileContents, key));
        } catch (IOException ioe) {
            System.err.println("An IOException occurred");
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash * super.username.hashCode();
        hash = 31 * hash * super.date.hashCode();

        // verifies that the file name and file contents are not null.
        if(! (this.fileName == null )) {
            hash = 31 * hash * this.fileName.hashCode();
        }
        if (! (this.fileContents == null) ) {
            hash = 31 * hash * this.fileContents.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object other){
        FileClackData newData = (FileClackData)other;
        // if one or the other is null, but not both, then they cannot be equal.
        if(this.fileName == null ^ newData.fileName == null) {
            return false;
        }
        else if(this.fileContents == null ^ newData.fileContents == null ) {
            return false;
        }
        //Checks to see if the file names and data are null to avoid a NullPointerException.
        else {
            return ( ( (this.fileName == null && newData.fileName == null)
                    || this.fileName.equals(newData.fileName) )
                    && ( (this.fileContents == null && newData.fileContents == null) ||
                    this.fileContents.equals(newData.fileContents) )
                    && super.username.equals(newData.username) && super.type == newData.type &&
                    super.date.equals(newData.date) );
        }
    }

    @Override
    public String toString(){
        return "The file name is: " +this.fileName +  "\n" +
                "The file contents are: " + this.fileContents + "\n" +
                "The date the message was sent is: " + super.date + "\n" +
                "The person that sent the message is: " + super.username + "\n" +
                "The type of data is: " + super.type + "\n";
    }



}
