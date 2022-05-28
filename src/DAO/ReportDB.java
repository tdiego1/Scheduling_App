package DAO;

import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CustomerReport;
import model.LocationReport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Report DB Class */
public class ReportDB {

    /**
     * Gets the number of appointments by type and month from database.
     * @return List of appointment types and their count.
     */
    public static ObservableList<CustomerReport> getReportType(){
        try {
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT DATE_FORMAT(date(Start), '%M') as Month, " +
                    "Type, COUNT(*) AS Count FROM appointments GROUP BY DATE_FORMAT(date(Start), '%M'), Type");
            ResultSet result = query.executeQuery();
            ObservableList<CustomerReport> newReport = FXCollections.observableArrayList();

            while(result.next()){
                String type = result.getString("Type");
                int count = result.getInt("Count");
                String month = result.getString("Month");
                CustomerReport cr = new CustomerReport(type, count, month);
                newReport.add(cr);
            }
            DBConnection.closeConnection();
            return newReport;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }

    /**
     * Gets the number of appointments by location from DB.
     * @return List of appointments locations and their count.
     */
    public static ObservableList<LocationReport> getReportLocation(){
        try {
            DBConnection.openConnection();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT Location, COUNT(*) AS Count FROM appointments GROUP BY Location");
            ResultSet result = query.executeQuery();
            ObservableList<LocationReport> newReport = FXCollections.observableArrayList();

            while(result.next()){
                String location = result.getString("Location");
                int count = result.getInt("Count");
                LocationReport lr = new LocationReport(location, count);
                newReport.add(lr);
            }
            DBConnection.closeConnection();
            return newReport;
        }catch(Exception e){
            Utilities.errorAlert("Error", "SQL Error", e.toString());
        }
        return null;
    }
}
