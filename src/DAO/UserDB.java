package DAO;

import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** User SQL queries */
public class UserDB {

    /**
     * Gets a User from DB based on Username.
     * @param userName Username inputed on login screen.
     * @return User identified by Username
     */
    public static User getUser(String userName){
        try {
            for(User u : getAllUsers()){
                if(u.getUserName() == userName)
                    return u;
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "User Error", "No User found. ");
        }
        return null;
    }

    /**
     * Gets a User from DB based on UserId.
     * @param userId User ID inputted
     * @return User identified by ID
     */
    public static User getUser(int userId){
        try {
            for(User u : getAllUsers()){
                if(u.getUserId() == userId)
                    return u;
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "User Error", "No User found. ");
        }
        return null;
    }

    /**
     * Gets all users from DB.
     * @return All users from database.
     */
    public static ObservableList<User> getAllUsers(){
        try {
            ObservableList<User> allUsers = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM users");
            ResultSet result = query.executeQuery();

            while(result.next()){
                int userId = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                User userResult = new User(userId, userName, password);
                allUsers.add(userResult);
            }
            DBConnection.closeConnection();
            return allUsers;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", "Could not execute query!");
        }
        return null;
    }

}
