package controller;

import DAO.AppointmentDB;
import DAO.ContactDB;
import DAO.CustomerDB;
import DAO.UserDB;
import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** Appointment screen controller class */
public class AppointmentController implements Initializable {

    /** Appointment ID Text field */
    public TextField AppointIdText;
    /** Appointment Title text field */
    public TextField AppointTitleText;
    /** Appointment Description text field */
    public TextField AppointDescText;
    /** Appointment Location Text field */
    public TextField AppointLocText;
    /** Appointment Type text field */
    public TextField AppointTypeText;
    /** Appointment start time text field */
    public TextField AppointStartTimeText;
    /** Appointment end time text field */
    public TextField AppointEndTimeText;
    /** Appointment customer ID text field */
    public TextField AppointCustIdText;
    /** Appointment user ID text field */
    public TextField AppointUserIdText;
    /** Appointment contact combo box */
    public ComboBox<Contact> AppointContactText;
    /** Appointment TableView */
    public TableView AppointTable;
    /** Table ID column */
    public TableColumn AppointTableId;
    /** Table title column */
    public TableColumn AppointTableTitle;
    /** Table description column */
    public TableColumn AppointTableDesc;
    /** Table location column */
    public TableColumn AppointTableLoc;
    /** Table Contact column */
    public TableColumn AppointTableContact;
    /** Table type column */
    public TableColumn AppointTableType;
    /** Table start time column */
    public TableColumn AppointTableStart;
    /** Table end time column */
    public TableColumn AppointTableEnd;
    /** Table customer ID column */
    public TableColumn AppointTableCustId;
    /** Table user ID column */
    public TableColumn AppointTableUserId;
    /** Month/Week Label */
    public Label AppointMonthWeekLabel;
    /** Week Radio Button */
    public RadioButton AppointWeekRadioButton;
    /** Month Radio Button */
    public RadioButton AppointmentMonthRadioButton;
    /** Month/Weelk toggle group */
    public ToggleGroup schedule;
    /** All Radio Button */
    public RadioButton AppointmentAll;
    /** Appointment Next Button */
    public Button AppointNextButton;
    /** Appointment Previous Button */
    public Button AppointPreviousButton;
    /** Filtered appointments */
    private ObservableList<Appointment> filteredAppoint = FXCollections.observableArrayList();
    /** All contacts */
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    /** Selected appointment */
    private Appointment selAppointment;
    /** Date and time format */
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
    /** Date format */
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    /** Today's Date and Time */
    public static LocalDateTime nowTime = LocalDateTime.now();
    /** Business open time */
    private LocalDateTime openTime;
    /** Business close time */
    private LocalDateTime closeTime;

    /**
     * Initializes all the data in the appointment screen.
     * Sets a listener for the selection on the tableview and auto-populate the form with the selection values.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allContacts = ContactDB.getAllContacts();
        AppointContactText.setItems(allContacts);
        filteredAppoint = AppointmentDB.getAppointMonth(nowTime);
        AppointMonthWeekLabel.setText("Showing All Appointments");

        // Sets the default data to populate tableview.
        defaultTableData();

        // Creates a listener to auto-populate values in form
        AppointTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                selAppointment = (Appointment) newVal;
                AppointIdText.setText(Integer.toString(selAppointment.getAppointmentId()));
                AppointTitleText.setText(selAppointment.getTitle());
                AppointDescText.setText(selAppointment.getDescription());
                AppointLocText.setText(selAppointment.getLocation());
                AppointTypeText.setText(selAppointment.getType());
                AppointStartTimeText.setText(dtf.format(selAppointment.getStartDateTime()));
                AppointEndTimeText.setText(dtf.format(selAppointment.getEndDateTime()));
                AppointCustIdText.setText(Integer.toString(selAppointment.getCustomer().getCustomerId()));
                AppointUserIdText.setText(Integer.toString(selAppointment.getUser().getUserId()));
                AppointContactText.setValue(getContactSelection(selAppointment.getContact().getContactName()));
            }
        });

    }

    /**
     * Sets items in tableview and sets the data for each column.
     */
    public void defaultTableData(){
        if(schedule.getSelectedToggle() == AppointmentMonthRadioButton) {
            filteredAppoint = AppointmentDB.getAppointMonth(nowTime);
            AppointNextButton.setDisable(false);
            AppointPreviousButton.setDisable(false);
        }
        else if(schedule.getSelectedToggle() == AppointWeekRadioButton) {
            filteredAppoint = AppointmentDB.getAppointWeek(nowTime);
            AppointNextButton.setDisable(false);
            AppointPreviousButton.setDisable(false);
        }
        else {
            filteredAppoint = AppointmentDB.getAllAppointments();
            AppointNextButton.setDisable(true);
            AppointPreviousButton.setDisable(true);
        }

        AppointTable.setItems(filteredAppoint);

        AppointTableId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        AppointTableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        AppointTableDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        AppointTableLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        AppointTableContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        AppointTableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        AppointTableStart.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        AppointTableEnd.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        AppointTableCustId.setCellValueFactory(new PropertyValueFactory<>("customer"));
        AppointTableUserId.setCellValueFactory(new PropertyValueFactory<>("user"));
    }

    /**
     * Changes the screen based on path.
     * @param event
     * @param switchPath Path to new screen.
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
     * Creates a new appointment.
     * Data from the text fields are used in a new appointment and added to the database. Error checking is used to ensure
     * that the appointment is within business hours and does not overlap other appointments.
     * @param actionEvent
     */
    public void OnAppointNewAction(ActionEvent actionEvent) {
        try {
            if (Utilities.confirmAlert("New Appointment", "Add New Appointment", "Would you like to" +
                    " add new Appointment?")) {
                // Gets date and time
                LocalDateTime start = LocalDateTime.parse(AppointStartTimeText.getText(), dtf);
                LocalDateTime end = LocalDateTime.parse(AppointEndTimeText.getText(), dtf);
                openTime = start.toLocalDate().atTime(8, 00, 00);
                closeTime = end.toLocalDate().atTime(22, 00, 00);

                // Determines if appointment is within business hours
                if (Utilities.localToEst(start).isAfter(openTime) && Utilities.localToEst(end).isBefore(closeTime)) {
                    // Gets customer and list of customer based on customer ID
                    Customer customer = CustomerDB.getCustomer(Integer.parseInt(AppointCustIdText.getText()));
                    ObservableList<Appointment> appointByCust = AppointmentDB.getAppointByCust(customer);
                    // Determines if appointments overlap
                    boolean overlap = getOverlap(appointByCust, start, end);

                    // If no overlap, create appointment
                    if (!overlap) {
                        // Gets rest of data from text fields
                        Appointment newAppoint = null;
                        String title = AppointTitleText.getText();
                        String description = AppointDescText.getText();
                        String location = AppointLocText.getText();
                        Contact contact = AppointContactText.getValue();
                        String type = AppointTypeText.getText();
                        User user = UserDB.getUser(Integer.parseInt(AppointUserIdText.getText()));
                        newAppoint = new Appointment(Utilities.generateId('a'), title, description, location, contact, type,
                                start, end, customer, user);

                        // Calls query method to insert new appointment into database
                        if (AppointmentDB.insertNewAppointment(newAppoint))
                            Utilities.infoAlert("New Appointment", "Added Successfully", "New Appointment was added successfully!");
                        else
                            Utilities.errorAlert("Error", "New Appointment Error", "One or more fields are invalid.");
                    }else{
                        Utilities.errorAlert("Scheduling Error", "New Appointment Error", "Appointment overlaps with current appointment.");
                    }
                } else {
                        Utilities.errorAlert("Scheduling Error", "Outside Business Hours", "Start/End time is outside" +
                                " business hours.");
                }
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "New Appointment Error", "Could not add new appointment. ");
        }
        // Refreshes table
        defaultTableData();
    }

    /**
     * Updates an appointment.
     * Data from the text fields are used to update an appointment and update the database. Error checking is used to ensure
     * that the appointment is within business hours and does not overlap other appointments.
     * @param actionEvent
     */
    public void OnAppointUpdateAction(ActionEvent actionEvent) {
        try {
            if (Utilities.confirmAlert("Update Appointment", "Update Appointment", "Would you like to" +
                    " update the Appointment?")) {
                // Gets Date and Time
                LocalDateTime start = LocalDateTime.parse(AppointStartTimeText.getText(), dtf);
                LocalDateTime end = LocalDateTime.parse(AppointEndTimeText.getText(), dtf);
                openTime = start.toLocalDate().atTime(8,00,00);
                closeTime = end.toLocalDate().atTime(22,00,00);

                // Determines if appointment is within business hours
                if (Utilities.localToEst(start).isAfter(openTime) && Utilities.localToEst(end).isBefore(closeTime)) {
                    // Gets customer and list of customer based on customer ID
                    Customer customer = CustomerDB.getCustomer(Integer.parseInt(AppointCustIdText.getText()));
                    ObservableList<Appointment> appointByCust = AppointmentDB.getAppointByCust(customer);
                    int id = Integer.parseInt(AppointIdText.getText());
                    // Remove selected appointment from list of appointments to check for overlap
                    for(Appointment a : appointByCust){
                        if(a.getAppointmentId() == id) {
                            appointByCust.remove(a);
                            break;
                        }
                    }
                    // Determines if appointments overlap
                    boolean overlap = getOverlap(appointByCust, start, end);
                    // If no overlap, update appointment
                    if(!overlap) {
                        Appointment newAppoint = null;
                        String title = AppointTitleText.getText();
                        String description = AppointDescText.getText();
                        String location = AppointLocText.getText();
                        Contact contact = AppointContactText.getValue();
                        String type = AppointTypeText.getText();
                        User user = UserDB.getUser(Integer.parseInt(AppointUserIdText.getText()));
                        newAppoint = new Appointment(id, title, description, location, contact, type,
                                start, end, customer, user);
                        if (AppointmentDB.updateAppointment(newAppoint))
                            Utilities.infoAlert("Update Appointment", "Updated Successfully", "New Appointment was updated successfully! ");
                        else
                            Utilities.errorAlert("Error", "Update Appointment Error", "Could not update the appointment.");
                    }else{
                        Utilities.errorAlert("Scheduling Error", "New Appointment Error", "Appointment overlaps with current appointment.");
                    }
                }else{
                    Utilities.errorAlert("Scheduling Error", "Outside Business Hours", "Start/End time is outside" +
                            " business hours. ");
                }
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "Update Appointment Error", "Error in one or more fields. Could " +
                    "not update the appointment. ");
        }
        // Refreshes table
        defaultTableData();
    }

    /**
     * Deletes an appointment from the database.
     * @param actionEvent
     */
    public void OnAppointDeleteAction(ActionEvent actionEvent) {
        try{
            if(Utilities.confirmAlert("Delete Appointment", "Delete Appointment", "Are you sure you want to delete " +
                    "this Appointment?")) {
                if (AppointmentDB.deleteAppointment(selAppointment)) {
                    Utilities.infoAlert("Deleted Appointment", "Appointment ID: " + selAppointment.getAppointmentId() +
                            " Type: " + selAppointment.getType(), "Appointment has been deleted.");
                }
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "Appointment not Deleted", "Could not delete Appointment.");
        }
        // Refreshes table
        defaultTableData();
    }

    /**
     * Gets a selection from the contact combo box.
     * @param contactName Name of contact
     * @return Contact object
     */
    public Contact getContactSelection(String contactName){
        for(Contact c : allContacts){
            if(c.getContactName().equals(contactName))
                return c;
        }
        return null;
    }

    public void OnAppointAllRadioButton(ActionEvent actionEvent) {
        filteredAppoint = AppointmentDB.getAllAppointments();
        AppointMonthWeekLabel.setText("Showing All Appointments");
        AppointNextButton.setDisable(true);
        AppointPreviousButton.setDisable(true);
        AppointTable.setItems(filteredAppoint);
    }

    /**
     * Filters Items in appointment tableview by week.
     * @param actionEvent
     */
    public void OnAppFormWeekAction(ActionEvent actionEvent) {
        nowTime = LocalDateTime.now();
        filteredAppoint = AppointmentDB.getAppointWeek(nowTime);
        AppointMonthWeekLabel.setText("Current Week: " + df.format(nowTime.toLocalDate()) + " - " + df.format(nowTime.toLocalDate().plusWeeks(1)));
        AppointNextButton.setDisable(false);
        AppointPreviousButton.setDisable(false);
        AppointTable.setItems(filteredAppoint);
    }

    /**
     * Filters items in appointment tableview by month.
     * @param actionEvent
     */
    public void OnAppFormMonthAction(ActionEvent actionEvent) {
        nowTime = LocalDateTime.now();
        filteredAppoint = AppointmentDB.getAppointMonth(nowTime);
        AppointMonthWeekLabel.setText("Current Month: " + nowTime.getMonth());
        AppointNextButton.setDisable(false);
        AppointPreviousButton.setDisable(false);
        AppointTable.setItems(filteredAppoint);
    }

    /**
     * Goes to next month or week depending on toggle selection.
     * @param actionEvent
     */
    public void OnAppointNextAction(ActionEvent actionEvent) {
        if(schedule.getSelectedToggle() == AppointmentMonthRadioButton) {
            nowTime = nowTime.plusMonths(1);
            filteredAppoint = AppointmentDB.getAppointMonth(nowTime);
            AppointMonthWeekLabel.setText("Current Month: " + nowTime.getMonth());
            AppointTable.setItems(filteredAppoint);
        }else {
            nowTime = nowTime.plusWeeks(1);
            filteredAppoint = AppointmentDB.getAppointWeek(nowTime);
            AppointMonthWeekLabel.setText("Current Week: " + df.format(nowTime.toLocalDate()) + " - "
                    + df.format(nowTime.toLocalDate().plusWeeks(1)));
            AppointTable.setItems(filteredAppoint);
        }
    }

    /**
     * Goes to previous month or week depending on toggle selection.
     * @param actionEvent
     */
    public void OnAppointPreviousAction(ActionEvent actionEvent) {
        if (schedule.getSelectedToggle() == AppointmentMonthRadioButton) {
            nowTime = nowTime.minusMonths(1);
            filteredAppoint = AppointmentDB.getAppointMonth(nowTime);
            AppointMonthWeekLabel.setText("Current Month: " + nowTime.getMonth());
            AppointTable.setItems(filteredAppoint);
        } else {
            nowTime = nowTime.minusWeeks(1);
            filteredAppoint = AppointmentDB.getAppointWeek(nowTime);
            AppointMonthWeekLabel.setText("Current Week: " + df.format(nowTime.toLocalDate()) + " - " +
                    df.format(nowTime.toLocalDate().plusWeeks(1)));
            AppointTable.setItems(filteredAppoint);
        }
    }

    /**
     * Determines if there is an overlap in currently scheduled appointments.
     * Uses a list of appointments based on customer ID and determines any overlaps.
     * @param appointByCust List of appointments by customer ID
     * @param start Start time
     * @param end End time
     * @return True/False if an overlap was found.
     */
    public boolean getOverlap(ObservableList<Appointment> appointByCust, LocalDateTime start, LocalDateTime end){
        boolean overlap = false;
        // Determines if appointments overlap
        for (Appointment a : appointByCust) {
            if ((start.isAfter(a.getStartDateTime()) || start.isEqual(a.getStartDateTime())) && start.isBefore(a.getEndDateTime())) {
                overlap = true;
                return overlap;
            } else if (end.isAfter(a.getStartDateTime()) && (end.isBefore(a.getEndDateTime()) || end.isEqual(a.getEndDateTime()))) {
                overlap = true;
                return overlap;
            } else if ((start.isBefore(a.getStartDateTime()) || start.isEqual(a.getStartDateTime())) && (end.isAfter(a.getEndDateTime())
                    || end.isEqual(a.getEndDateTime()))) {
                overlap = true;
                return overlap;
            } else {
                overlap = false;
            }
        }
        return false;
    }

    /**
     * Switches to Customers screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnAppointViewCustAction(ActionEvent actionEvent) throws IOException{
        switchScreen(actionEvent, "/view/CustomerForm.fxml");
    }

    /**
     * Switches to reports screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnAppointReportAction(ActionEvent actionEvent) throws IOException{
        switchScreen(actionEvent, "/view/ReportsView.fxml");
    }

    /**
     * Logs out of program and switches to login screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnAppointLogoutAction(ActionEvent actionEvent) throws IOException {
        if(Utilities.confirmAlert("Logout","Logout?","Are you sure you want to logout?"))
            switchScreen(actionEvent, "/view/LoginForm.fxml");
    }
}
