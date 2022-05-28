package controller;

import DAO.AppointmentDB;
import DAO.ContactDB;
import DAO.ReportDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Report Scene Class */
public class ReportController implements Initializable {
    /** Main report text area */
    public TextArea ReportsTextArea;
    /** Lambda expression to get produce the Appointments by type report */
    ReportInterface report = actionEvent -> {
        ReportsTextArea.setText("Total number of customer appointment by type and month:");
        for(CustomerReport cr : ReportDB.getReportType()) {
            ReportsTextArea.appendText("\n" + cr.getMonth() + " " + cr.getType() + ": " + cr.getCount());
        }
    };

    /**
     * Initializes Reports screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Switches the screen based on the path input
     * @param event
     * @param switchPath path input
     * @throws IOException
     */
    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Changes screen to Appointments screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnReportsBackAction(ActionEvent actionEvent) throws IOException{
        switchScreen(actionEvent, "/view/AppointmentForm.fxml");
    }

    /**
     * Generates Customer Appointment by Type report.
     * Labmda Expression 3: Calls the report lambda expression that displays the appointments by type report.
     * @param actionEvent
     */
    public void OnReportsCustAppointAction(ActionEvent actionEvent) {
        report.custAppoint(actionEvent);
    }

    /**
     * Generates Contact Schedule Report.
     * @param actionEvent
     */
    public void OnReportsContactSchAction(ActionEvent actionEvent) {
        ReportsTextArea.setText("Schedule for each customer:");
        for(Contact c : ContactDB.getAllContacts()){
            ReportsTextArea.appendText("\n\n" + c.getContactName());
            for(Appointment a : AppointmentDB.getAppointContact(c)){
                ReportsTextArea.appendText("\nAppointment ID: " + a.getAppointmentId() + ", ");
                ReportsTextArea.appendText("Title: " + a.getTitle() + ", ");
                ReportsTextArea.appendText("Type: " + a.getType() + ", ");
                ReportsTextArea.appendText("Description: " + a.getDescription() + ", ");
                ReportsTextArea.appendText("Start: " + a.getStartDateTime() + ", ");
                ReportsTextArea.appendText("End: " + a.getEndDateTime() + ", ");
                ReportsTextArea.appendText("Customer ID: " + a.getCustomer().getCustomerId() + ", ");
            }
        }
    }

    /**
     * Generates Appointments by location report.
     * @param actionEvent
     */
    public void OnReportsAppointLocAction(ActionEvent actionEvent) {
        ReportsTextArea.setText("Total number of customer appointment by Location:");
        for(LocationReport lr : ReportDB.getReportLocation()) {
            ReportsTextArea.appendText("\n" + lr.getLocation() + ": " + lr.getCount());
        }
    }
}
