package DAO;

import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Contact DB Class */
public class ContactDB {

    /**
     * Gets a list of all Contacts.
     * @return List of all contacts
     */
    public static ObservableList<Contact> getAllContacts(){
        try {
            // Opens DB connection and prepares SQL statement
            ObservableList<Contact> allContacts = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM contacts");
            ResultSet result = query.executeQuery();

            // Creates new contact from DB and adds to list
            while(result.next()){
                int contactId = result.getInt("Contact_ID");
                String contactName = result.getString("Contact_Name");
                Contact contactResult = new Contact(contactId, contactName);
                allContacts.add(contactResult);
            }
            // Closes DB connection and returns list of contacts
            DBConnection.closeConnection();
            return allContacts;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", "Could not execute query!");
        }
        return null;
    }

    /**
     * Gets a single contact based on contact ID
     * @param contactId Contact ID
     * @return Contact object
     */
    public static Contact getContact(int contactId){
        try {
            for(Contact c : getAllContacts()){
                if(c.getContactId() == contactId)
                    return c;
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "Contact Error", "No contact found. ");
        }
        return null;
    }

}
