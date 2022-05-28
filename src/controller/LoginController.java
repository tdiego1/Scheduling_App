package controller;

import DAO.AppointmentDB;
import Utilities.Utilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Login;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/** Login Screen controller class */
public class LoginController implements Initializable {

    /** Password Field */
    public TextField LoginFormPassField;
    /** Location Label */
    public Label LoginFormLocationLabel;
    /** User name field */
    public TextField LoginFormUserNameField;
    /** User name label */
    public Label LoginFormUserNameLabel;
    /** Password Label */
    public Label LoginFormPasswordLabel;
    /** Login Button */
    public Button LoginFormLoginButton;
    /** Welcome Label */
    public Label LoginFormWelcomeLabel;
    /** Location Text Label */
    public Label LoginFormLocationTextLabel;

    /**
     * Initializes the labels and text fields for the Login screen.
     * Changes the language based on the system default.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourceBundle = ResourceBundle.getBundle("LoginForm", Locale.getDefault());
        LoginFormLocationTextLabel.setText(resourceBundle.getString("LocationText"));
        LoginFormLocationLabel.setText(resourceBundle.getString("Location"));
        LoginFormWelcomeLabel.setText(resourceBundle.getString("Welcome"));
        LoginFormUserNameLabel.setText(resourceBundle.getString("userName"));
        LoginFormPasswordLabel.setText(resourceBundle.getString("password"));
        LoginFormLoginButton.setText(resourceBundle.getString("login"));
    }

    /**
     * Switches the screen based on path input.
     * @param event
     * @param switchPath screen path input.
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
     * Attempts to log into program using username and password credentials.
     * Shows alert if there are any appointments within 15 minutes of login.
     * @param actionEvent
     * @throws IOException
     */
    public void OnLoginFormLoginAction(ActionEvent actionEvent) throws IOException{
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LoginForm", Locale.getDefault());
        try {
            // Gets User name and password from text fields
            String userName = LoginFormUserNameField.getText();
            String password = LoginFormPassField.getText();
            // Gets current time
            LocalDateTime currentTime = LocalDateTime.now();

            // Determines if username and password are correct
            if(Login.attemptLogin(userName, password)) {
                // Gets a list of appointments within the next 15 min
                ObservableList<Appointment> upcomingAppoint = AppointmentDB.getLoginAppoint(currentTime);
                if(upcomingAppoint.size() >= 1){
                    String content = "Upcoming appointment(s): ";
                    for(Appointment a : upcomingAppoint){
                        content = content + "\nAppointment ID: " + a.getAppointmentId() + ", Date: " + a.getStartDateTime().toLocalDate()
                                + ", Time: " + a.getStartDateTime().toLocalTime();
                    }
                    Utilities.infoAlert("Upcoming Appointment(s)", "Upcoming Appointment(s)", content);
                }else{
                    Utilities.infoAlert("Upcoming Appointment(s)", "No Upcoming Appointment(s)", "There are no upcoming appointment(s). ");
                }
                switchScreen(actionEvent, "/view/AppointmentForm.fxml");
            } else {
                Utilities.errorAlert(resourceBundle.getString("errorTitle"), resourceBundle.getString("errorHeader"),
                        resourceBundle.getString("errorContent"));
            }
        }catch (Exception e){
            Utilities.errorAlert(resourceBundle.getString("errorTitle"), resourceBundle.getString("sqlErrorHeader"),
                    resourceBundle.getString("sqlErrorContent"));
        }
    }
}
