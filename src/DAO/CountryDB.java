package DAO;

import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Country DB Class */
public class CountryDB {

    /**
     * Gets all countries from DB.
     * @return List of all countries
     */
    public static ObservableList<Country> getAllCountries(){
        try {
            ObservableList<Country> allCountries = FXCollections.observableArrayList();
            ObservableList<Division> assocDivisions = FXCollections.observableArrayList();
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM countries");
            ResultSet result = query.executeQuery();

            while(result.next()){
                int countryId = result.getInt("Country_ID");
                String customerName = result.getString("Country");
                assocDivisions = DivisionDB.getAssocDivisions(countryId);
                Country countryResult = new Country(countryId, customerName, assocDivisions);
                allCountries.add(countryResult);
            }
            DBConnection.closeConnection();
            return allCountries;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }

}

