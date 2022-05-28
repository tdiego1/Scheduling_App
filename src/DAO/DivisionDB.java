package DAO;

import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Division SQL Queries */
public class DivisionDB {

    /**
     * Gets all Divisions from DB
     * @return List of all divisions
     */
    public static ObservableList<Division> getAllDivisions(){
        try {
            ObservableList<Division> allDivisions = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM first_level_divisions");
            ResultSet result = query.executeQuery();

            while(result.next()){
                    int divisionId = result.getInt("Division_ID");
                    String divisionName = result.getString("Division");
                    Division divisionResult = new Division(divisionId, divisionName);
                    allDivisions.add(divisionResult);
            }
            DBConnection.closeConnection();
            return allDivisions;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }

    /**
     * Gets list of divisions associated with country ID
     * @param countryId Country ID
     * @return List of divisions based on country ID
     */
    public static ObservableList<Division> getAssocDivisions(int countryId){
        try {
            ObservableList<Division> assocDivision = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM first_level_divisions");
            ResultSet result = query.executeQuery();

            while(result.next()){
                if(countryId == result.getInt("Country_ID")) {
                    int divisionId = result.getInt("Division_ID");
                    String divisionName = result.getString("Division");
                    Division divisionResult = new Division(divisionId, divisionName);
                    assocDivision.add(divisionResult);
                }
            }
            DBConnection.closeConnection();
            return assocDivision;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }
}
