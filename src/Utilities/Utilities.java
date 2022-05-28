package Utilities;


import DAO.DivisionDB;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Division;

import java.time.*;
import java.util.Optional;

/** Class for various utility methods */
public class Utilities {

    /** Current Zone ID */
    private static ZoneId zoneId ;
    /** Eastern time zone ID */
    private static final ZoneId estZoneId = ZoneId.of("US/Eastern");
    /** customer ID counter */
    private static int customerId = 2;
    /** appoitnment ID counter */
    private static int appointmentId =  2;

    /**
     * Displays an error alert.
     * @param title Alert title
     * @param header Alert Header
     * @param content Alert content
     */
    public static void errorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays an information alert.
     * @param title Alert title
     * @param header ALert Header
     * @param content Alert content
     */
    public static void infoAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation alert.
     * @param title Alert title
     * @param header Alert header
     * @param content Alert content
     * @return True/false for button input.
     */
    public static boolean confirmAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> selection = alert.showAndWait();
        if(selection.get() == ButtonType.OK){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Returns the Division name given based on division ID.
     * @param divisionId ID of division
     * @return
     */
    public static String getDivisionName(int divisionId){
        for(Division d : DivisionDB.getAllDivisions()){
            if(d.getDivisionId() == divisionId)
                return d.getDivisionName();
        }
        return null;
    }

    /**
     * Returns the Division ID based on division name.
     * @param divisionName Name fo Division
     * @return
     */
    public static int getDivisionId(String divisionName){
        for(Division d : DivisionDB.getAllDivisions()){
            if(d.getDivisionName().equals(divisionName))
                return d.getDivisionId();
        }
        return 0;
    }

    /**
     * Generates a new customer /appointment ID.
     * @param type char 'a' or 'c' for type
     * @return a new customer ID or appointment ID.
     */
    public static int generateId(char type){
        switch(type) {
            case 'c':
                customerId++;
                return customerId;
            case 'a':
                appointmentId++;
                return appointmentId;
        }
        return 0;
    }

    /**
     * Converts UTC to local time.
     * @param utcTime time is utc
     * @return Time in Local
     */
    public static LocalDateTime utcToLocal(LocalDateTime utcTime){
        zoneId = ZoneId.systemDefault();
        ZonedDateTime utcZoned = ZonedDateTime.of(utcTime, ZoneOffset.UTC);
        ZonedDateTime localZoned = utcZoned.withZoneSameInstant(zoneId);
        LocalDateTime localTime = localZoned.toLocalDateTime();
        return localTime;
    }

    /**
     * Conerts Local to UTC time.
     * @param localTime time in local timezone
     * @return UTC time
     */
    public static LocalDateTime localToUtc(LocalDateTime localTime){
        zoneId = ZoneId.systemDefault();
        ZonedDateTime localZoned = ZonedDateTime.of(localTime, zoneId);
        ZonedDateTime utcZoned = localZoned.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime utcTime = utcZoned.toLocalDateTime();
        return utcTime;
    }

    /**
     * Converts local to EST.
     * @param localTime Local time
     * @return EST time
     */
    public static LocalDateTime localToEst(LocalDateTime localTime){
        zoneId = ZoneId.systemDefault();
        ZonedDateTime localZoned = ZonedDateTime.of(localTime, zoneId);
        ZonedDateTime estZoned = localZoned.withZoneSameInstant(estZoneId);
        LocalDateTime estTime = estZoned.toLocalDateTime();
        return estTime;
    }

    /**
     * Converts EST to Local time.
     * @param estTime Eastern Standard Time
     * @return Local Time
     */
    public static LocalDateTime estToLocal(LocalDateTime estTime){
        zoneId = ZoneId.systemDefault();
        ZonedDateTime estZoned = ZonedDateTime.of(estTime, estZoneId);
        ZonedDateTime localZoned = estZoned.withZoneSameInstant(zoneId);
        LocalDateTime localTime = localZoned.toLocalDateTime();
        return localTime;
    }
}
