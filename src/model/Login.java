package model;

import DAO.UserDB;
import Utilities.Utilities;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;

/** Stablishes login */
public class Login {

    /** List of all users */
    private static ObservableList<User> allUsers;
    /** Filename string */
    private static final String filename = "login_activity.txt";
    /** Lambda expression for recording login information to a file */
    static LoginInterface recordLogin = (user, attempt) -> {
        try {
            FileWriter fwVar = new FileWriter(filename,true);
            PrintWriter pwVar = new PrintWriter(fwVar);
            Instant timestamp = Instant.now();

            pwVar.println("Username: " + user + ", timestamp: " + timestamp.atZone(ZoneOffset.UTC).toLocalDateTime() + ", Success?: " + attempt);
            pwVar.close();

        }catch(Exception e){
            Utilities.errorAlert("Error", "Error", e.toString());
        }
    };

    /**
     * Determines if credentials are valid.
     * Matches Username and Password to DB record for a match.
     * Lambda Expression 4: records a login attempt to a file. This is useful because it allows this file to be written
     * to without the need for another method adding more weight to the program. This expression is only called when this
     * attemptLogin method is executed.
     * @param userName Username input from login screen
     * @param userPassword Password input from login screen
     * @return true/false for valid login
     * @throws SQLException
     */
    public static boolean attemptLogin(String userName, String userPassword) {
        boolean attempt = false;
        try {
            allUsers = UserDB.getAllUsers();
            for (User user : allUsers) {
                if (user.getUserName().equals(userName) && user.getPassword().equals(userPassword)) {
                    attempt = true;
                    break;
                } else {
                    attempt = false;
                }
            }
            recordLogin.recordLogin(userName, attempt);
        }catch (Exception e){
            Utilities.errorAlert("Error", "Error", "Login not attempted.");
        }
        return attempt;
    }
}
