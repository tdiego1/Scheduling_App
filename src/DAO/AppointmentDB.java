package DAO;

import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** Appointment DB Class */
public class AppointmentDB {

    /**
     * Gets all the appointments from the DB. 
     * @return List of Appointments 
     */
    public static ObservableList<Appointment> getAllAppointments(){
        try {
            // Opens DB connection and creates SQL query statement
            ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT Appointment_ID, Title, Description, " +
                    "Location, Contact_ID, Type, Start, End, Customer_ID, User_ID FROM client_schedule.appointments");
            ResultSet result = query.executeQuery();

            // Adds new Appointment to list of appointments for each row returned from DB
            while(result.next()){
                int appointmentId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                Contact contact = ContactDB.getContact(result.getInt("Contact_ID"));
                String type = result.getString("Type");
                Timestamp startDateTime = result.getTimestamp("Start");
                Timestamp endDateTime = result.getTimestamp("End");
                Customer customer = CustomerDB.getCustomer(result.getInt("Customer_ID"));
                User user = UserDB.getUser(result.getInt("User_ID"));
                Appointment appointResult = new Appointment(appointmentId, title, description, location, contact, type,
                        Utilities.utcToLocal(startDateTime.toLocalDateTime()), Utilities.utcToLocal(endDateTime.toLocalDateTime()),
                        customer, user);
                allAppointments.add(appointResult);
            }
            // Closes DB connection and returns all appointments
            DBConnection.closeConnection();
            return allAppointments;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }

    /**
     * Gets list of appointments based on a customer
     * @param customer
     * @return
     */
    public static ObservableList<Appointment> getAppointByCust(Customer customer){
        try {
            ObservableList<Appointment> filteredAppoint = FXCollections.observableArrayList();
            for(Appointment a : getAllAppointments()){
                if(a.getCustomer().getCustomerId() == customer.getCustomerId()){
                    filteredAppoint.add(a);
                }
            }
            return filteredAppoint;
        }catch(Exception e){
            Utilities.errorAlert("Error", "Filter Error", "Could not get filtered appointments. ");
        }
        return null;
    }

    /**
     * Gets list of appointments based on Month.
     * @param month selected month
     * @return
     */
    public static ObservableList<Appointment> getAppointMonth(LocalDateTime month){
        try {
            ObservableList<Appointment> filteredAppoint = FXCollections.observableArrayList();
            for(Appointment a : getAllAppointments()){
                if(a.getStartDateTime().getMonth().equals(month.getMonth())){
                    filteredAppoint.add(a);
                }
            }
            return filteredAppoint;
        }catch(Exception e){
            Utilities.errorAlert("Error", "Filter Error", "Could not get filtered appointments. ");
        }
        return null;
    }

    /**
     * Gets list of appointments based on week.
     * @param week selected week
     * @return
     */
    public static ObservableList<Appointment> getAppointWeek(LocalDateTime week){
        try {
            ObservableList<Appointment> filteredAppoint = FXCollections.observableArrayList();
            for(Appointment a : getAllAppointments()){
                if((a.getStartDateTime().toLocalDate().isAfter(week.toLocalDate()) || a.getStartDateTime().toLocalDate().isEqual(week.toLocalDate()))
                        && a.getEndDateTime().toLocalDate().isBefore(week.toLocalDate().plusWeeks(1))){
                    filteredAppoint.add(a);
                }
            }
            return filteredAppoint;
        }catch(Exception e){
            Utilities.errorAlert("Error", "Filter Error", "Could not get filtered appointments. ");
        }
        return null;
    }

    /**
     * Gets list of appointments based on contact.
     * @param c selected contact
     * @return
     */
    public static ObservableList<Appointment> getAppointContact(Contact c){
        try {
            ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM appointments WHERE Contact_ID=? " +
                    "ORDER BY Start");
            query.setInt(1, c.getContactId());
            ResultSet result = query.executeQuery();

            while(result.next()){
                int appointmentId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                Contact contact = ContactDB.getContact(result.getInt("Contact_ID"));
                String type = result.getString("Type");
                Timestamp startDateTime = result.getTimestamp("Start");
                Timestamp endDateTime = result.getTimestamp("End");
                Customer customer = CustomerDB.getCustomer(result.getInt("Customer_ID"));
                User user = UserDB.getUser(result.getInt("User_ID"));
                Appointment appointResult = new Appointment(appointmentId, title, description, location, contact, type,
                        Utilities.utcToLocal(startDateTime.toLocalDateTime()), Utilities.utcToLocal(endDateTime.toLocalDateTime()),
                        customer, user);
                filteredAppointments.add(appointResult);
            }
            DBConnection.closeConnection();
            return filteredAppointments;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }

    /**
     * Gets a list of appointments within a 15 min range of login time.
     * @param login Time of when user logged in.
     * @return List of appointments within 15 min of login time.
     */
    public static ObservableList<Appointment> getLoginAppoint(LocalDateTime login){
        try {
            ObservableList<Appointment> filteredAppoint = FXCollections.observableArrayList();
            for(Appointment a : getAllAppointments()){
                if((a.getStartDateTime().isAfter(login) || a.getStartDateTime().isEqual(login)) && (a.getStartDateTime().isBefore(login.plusMinutes(15))
                || a.getStartDateTime().isEqual(login.plusMinutes(15)))){
                    filteredAppoint.add(a);
                }
            }
            return filteredAppoint;
        }catch(Exception e){
            Utilities.errorAlert("Error", "Filter Error", "Could not get filtered appointments. ");
        }
        return null;
    }

    /**
     * Inserts new appointment into the DB
     * @param newAppoint new appointment to insert
     * @return True/False if the new appointment was successful inserted
     */
    public static boolean insertNewAppointment(Appointment newAppoint){
        try {
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("INSERT INTO appointments (Title, Description," +
                    " Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (?,?,?,?,?,?,?,?,?)");
            query.setString(1, newAppoint.getTitle());
            query.setString(2, newAppoint.getDescription());
            query.setString(3, newAppoint.getLocation());
            query.setInt(4, newAppoint.getContact().getContactId());
            query.setString(5, newAppoint.getType());
            query.setTimestamp(6, Timestamp.valueOf(Utilities.localToUtc(newAppoint.getStartDateTime())));
            query.setTimestamp(7, Timestamp.valueOf(Utilities.localToUtc(newAppoint.getEndDateTime())));
            query.setInt(8, newAppoint.getCustomer().getCustomerId());
            query.setInt(9, newAppoint.getUser().getUserId());

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
     * Updates a record in the appointments DB
     * @param selAppoint selected appointment to update
     * @return True/False if record was successfully updated
     */
    public static boolean updateAppointment(Appointment selAppoint){
        try {
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("UPDATE appointments SET Title=?, Description=?," +
                    " Location=?, Contact_ID=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=? WHERE Appointment_ID=?");
            query.setString(1, selAppoint.getTitle());
            query.setString(2, selAppoint.getDescription());
            query.setString(3, selAppoint.getLocation());
            query.setInt(4, selAppoint.getContact().getContactId());
            query.setString(5, selAppoint.getType());
            query.setTimestamp(6, Timestamp.valueOf(Utilities.localToUtc(selAppoint.getStartDateTime())));
            query.setTimestamp(7, Timestamp.valueOf(Utilities.localToUtc(selAppoint.getEndDateTime())));
            query.setInt(8, selAppoint.getCustomer().getCustomerId());
            query.setInt(9, selAppoint.getUser().getUserId());
            query.setInt(10, selAppoint.getAppointmentId());

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
     * Deletes an appointment record from DB.
     * @param selAppoint selected appointment
     * @return True/False based on if record was deleted successfully
     */
    public static boolean deleteAppointment(Appointment selAppoint){
        try {
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("DELETE FROM appointments WHERE Appointment_ID=?");
            query.setInt(1, selAppoint.getAppointmentId());
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
     * Deletes all appointments associated with a customer.
     * @param selCustomer Selected customer
     * @return True/False based on if appointments were successfully deleted.
     */
    public static boolean deleteCustomerAppointments(Customer selCustomer){
        try {
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("DELETE FROM appointments WHERE Customer_ID=?");
            query.setInt(1, selCustomer.getCustomerId());
            int rowsAffected = query.executeUpdate();
            DBConnection.closeConnection();
            return true;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return false;
    }
}
