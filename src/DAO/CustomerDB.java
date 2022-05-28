package DAO;

import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Customer SQL Queries */
public class CustomerDB {

    /**
     * Gets an observable list of customers from DB.
     * Sends Query to customers table in DB to retrieve all customers.
     * @return Observable list of all customers
     */
    public static ObservableList<Customer> getAllCustomers(){
        try {
            // Prepares SQL statement
            ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT Customer_ID, Customer_Name, Address," +
                    " Postal_Code, Phone, fl.Division_ID, Division, cn.Country_ID, Country FROM client_schedule.customers as co" +
                    " INNER JOIN first_level_divisions as fl on co.Division_ID = fl.Division_ID" +
                    " INNER JOIN countries as cn on fl.Country_ID = cn.Country_ID");
            ResultSet result = query.executeQuery();

            // Creates new Customer objects and adds them to list of customers
            while(result.next()){
                int customerId = result.getInt("Customer_ID");
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phoneNumber = result.getString("Phone");
                int divisionId = result.getInt("Division_ID");
                String divisionName = result.getString("Division");
                Division division = null;
                Country country =null;
                for(Division d : DivisionDB.getAllDivisions()){
                    if(d.getDivisionId() == divisionId)
                        division = d;
                }
                int countryId = result.getInt("Country_ID");
                for(Country c : CountryDB.getAllCountries()){
                    if(c.getCountryId() == countryId)
                        country = c;
                }
                Customer customerResult = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId,
                        division, country);
                allCustomers.add(customerResult);
            }
            // CLoses DB connection and returns all customers
            DBConnection.closeConnection();
            return allCustomers;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }

    /**
     * Gets a customer based on customer ID.
     * @param customerId Customer ID
     * @return Customer Object
     */
    public static Customer getCustomer(int customerId) {
        try {
            for(Customer c : getAllCustomers()){
                if(c.getCustomerId() == customerId)
                    return c;
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "Customer Error", "No customer found. ");
        }
        return null;
    }

    /**
     * Inserts a new customer into table customers in DB.
     * @param newCustomer Customer to be added
     * @return
     * True if it was successfully added.
     * False if it was successfully added.
     */
    public static boolean insertNewCustomer(Customer newCustomer){
        try {
            // Opens DB connection and prepares SQL statement
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("INSERT INTO customers (Customer_Name, " +
                    "Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)");
            query.setString(1, newCustomer.getCustomerName());
            query.setString(2, newCustomer.getAddress());
            query.setString(3, newCustomer.getPostalCode());
            query.setString(4, newCustomer.getPhoneNumber());
            query.setInt(5, newCustomer.getDivisionId());
            int rowsAffected = query.executeUpdate();
            DBConnection.closeConnection();
            if(rowsAffected != 0)
                return true;
            else
                return false;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return false;
    }

    /**
     * Deletes a customer from the customers table in DB.
     * @param selCustomer Customer selected to delete
     * @return
     * True if customer was deleted.
     * False if customer was not deleted.
     */
    public static boolean deleteCustomer(Customer selCustomer){
        try {
            // Opens DB connection and prepares query
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("DELETE FROM customers WHERE Customer_ID=?");
            query.setInt(1, selCustomer.getCustomerId());
            // Executes query and checks if rows were added
            int rowsAffected = query.executeUpdate();
            DBConnection.closeConnection();
            if(rowsAffected != 0)
                return true;
            else
                return false;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return false;
    }

    /**
     * Updates a customer form the customers table in DB.
     * @param selCustomer Customer selected to update
     * @return True/false if query succeeded
     */
    public static boolean updateCustomer(Customer selCustomer){
        try {
            // Opens DB connection and prepares SQL statement
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("Update customers SET Customer_Name=?, " +
                    "Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?");
            query.setString(1, selCustomer.getCustomerName());
            query.setString(2, selCustomer.getAddress());
            query.setString(3, selCustomer.getPostalCode());
            query.setString(4, selCustomer.getPhoneNumber());
            query.setInt(5, selCustomer.getDivisionId());
            query.setInt(6, selCustomer.getCustomerId());
            int rowsAffected = query.executeUpdate();
            DBConnection.closeConnection();
            if(rowsAffected != 0)
                return true;
            else
                return false;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return false;
    }

}
