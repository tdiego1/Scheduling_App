package controller;

import DAO.AppointmentDB;
import DAO.CountryDB;
import DAO.CustomerDB;
import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Customer Controller Class */
public class CustomerController implements Initializable {
    /** Customers Table ID column */
    public TableColumn CustomerTableId;
    /** Customer TableView */
    public TableView CustomerTable;
    /** Customers Table Name Column */
    public TableColumn CustomerTableName;
    /** Customers Table Address Column */
    public TableColumn CustomerTableAddress;
    /** Customers Table Country Column */
    public TableColumn CustomerTableCountry;
    /** Customers Table Division Column */
    public TableColumn CustomerTableDiv;
    /** Customer Table Postal Code Column */
    public TableColumn CustomerTablePostCode;
    /** Customer Table Phone Number Column*/
    public TableColumn CustomerTablePhone;
    /** ID text field */
    public TextField CustomerFormId;
    /** Name text field */
    public TextField CustomerFormName;
    /** Address text field */
    public TextField CustomerFormAddress;
    /** Postal code text field */
    public TextField CustomerFormPostalCode;
    /** Phone number text field */
    public TextField CustomerFormPhone;
    /** Country combobox */
    public ComboBox<Country> CustomersCtryComBox;
    /** Division combobox*/
    public ComboBox<Division> CustomersStComBox;
    /** List of all customers from DB */
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /** List of Countries */
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();
    /** List of associated Divisions */
    private ObservableList<Division> assocDivisions = FXCollections.observableArrayList();
    /** Selected Customer */
    private Customer selCustomer;

    /**
     * Initializes the scene and sets up Customer TableView.
     * Sets listeners on tableview selection and on  country combo box.
     * Lambda expressions 1: The first lambda expression is used to add a listener to the TableView. Once a selection is
     * made the fields populate with the selection data.
     * Lambda expression 2: The second lambda expression is used to add a listener to the Country combo box. When a selection
     * is made the items in the division box will be populated with the list of divisions based on the country selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allCustomers = CustomerDB.getAllCustomers();
        allCountries = CountryDB.getAllCountries();

        // Sets items in tableview and country combo box
        CustomerTable.setItems(allCustomers);
        CustomersCtryComBox.setItems(allCountries);

        // Setup columns in tableview
        CustomerTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CustomerTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomerTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustomerTableCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        CustomerTableDiv.setCellValueFactory(new PropertyValueFactory<>("division"));
        CustomerTablePostCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        CustomerTablePhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Waits for a selection in tableview to be made
        CustomerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                selCustomer = (Customer)newVal;
                CustomerFormId.setText(Integer.toString(selCustomer.getCustomerId()));
                CustomerFormName.setText(selCustomer.getCustomerName());
                CustomerFormAddress.setText(selCustomer.getAddress());
                CustomerFormPostalCode.setText(selCustomer.getPostalCode());
                CustomerFormPhone.setText(selCustomer.getPhoneNumber());
                CustomersCtryComBox.setValue(getCountrySelection(selCustomer.getCountry().getCounrty()));
                CustomersStComBox.setValue(getDivisionSelection(selCustomer.getDivision().getDivisionName()));
            }
        });

        // Waits for a selection in country combo box to be made.
        CustomersCtryComBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal == null) {
                CustomersStComBox.getItems().clear();
                CustomersStComBox.setDisable(true);
            }else{
                assocDivisions = ((Country) newVal).getAssocDivisions();
                CustomersStComBox.setItems(assocDivisions);
                CustomersStComBox.setValue(null);
                CustomersStComBox.setDisable(false);
            }
        });
    }

    /**
     * Switches to a new scene based on switch path.
     * @param event
     * @param switchPath Path of FXML file to switch to
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
     * Goes back to the Appointments page.
     * @param actionEvent
     * @throws IOException
     */
    public void OnCustomerFormBackAction(ActionEvent actionEvent) throws IOException{
        switchScreen(actionEvent, "/view/AppointmentForm.fxml");
    }

    /**
     * Creates a new Customer based on info in text fields.
     * @param actionEvent
     */
    public void OnCustomerFormNewAction(ActionEvent actionEvent) {
        try {
            if (Utilities.confirmAlert("New Customer", "Add New Customer", "Would you like to add new Custmer?")) {
                Customer newCustomer = null;
                String name = CustomerFormName.getText();
                String address = CustomerFormAddress.getText();
                String postalCode = CustomerFormPostalCode.getText();
                String phone = CustomerFormPhone.getText();
                int divisionId = Utilities.getDivisionId(CustomersStComBox.getValue().toString());
                String country = CustomersCtryComBox.getSelectionModel().toString();
                newCustomer = new Customer(Utilities.generateId('c'), name, address, postalCode, phone, divisionId,
                        (Division)CustomersStComBox.getValue(), (Country)CustomersCtryComBox.getValue());
                if(CustomerDB.insertNewCustomer(newCustomer))
                    Utilities.infoAlert("New Customer", "Added Succesfully", "New customer was added succesfully! ");
                else
                    Utilities.errorAlert("Error", "New Customer Error", "Could not add new customer. ");
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "New Customer Error", "Could not add new customer. ");
        }
        // Refreshes data
        allCustomers = CustomerDB.getAllCustomers();
        CustomerTable.setItems(allCustomers);
    }

    /**
     * Updates the currently selected customer based on text fields.
     * @param actionEvent
     */
    public void OnCustomerFormUpdateAction(ActionEvent actionEvent) {
        try{
            Customer updateCustomer = null;
            int id = selCustomer.getCustomerId();
            String name = CustomerFormName.getText();
            String address = CustomerFormAddress.getText();
            String postalCode = CustomerFormPostalCode.getText();
            String phone = CustomerFormPhone.getText();
            int divisionId = Utilities.getDivisionId(CustomersStComBox.getValue().toString());
            Division division = CustomersStComBox.getValue();
            updateCustomer = new Customer(id, name, address, postalCode, phone, divisionId, division,
                    (Country)CustomersCtryComBox.getValue());
            if(Utilities.confirmAlert("Update Customer", "Update Customer", "Are you sure you want to update " +
                    "this customer?")) {
                if (CustomerDB.updateCustomer(updateCustomer))
                        Utilities.infoAlert("Updated Customer", "Updated Customer", "Customer has been updated successfully!");
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "Customer not Updated", e.toString());
        }
        // Refreshes data
        allCustomers = CustomerDB.getAllCustomers();
        CustomerTable.setItems(allCustomers);
    }

    /**
     * Deletes a customer based on selection.
     * @param actionEvent
     */
    public void OnCustomerFormDeleteAction(ActionEvent actionEvent) {
        try{
            if(Utilities.confirmAlert("Delete Customer", "Delete Customer", "Are you sure you want to delete " +
                    "this customer?")) {
                if (Utilities.confirmAlert("Delete Customer", "Appointments", "All customer appointments must be " +
                        "deleted before customer can be deleted. Would you like to delete all the associated appointments?")) {
                    if (AppointmentDB.deleteCustomerAppointments(selCustomer)) {
                        if (CustomerDB.deleteCustomer(selCustomer))
                            Utilities.infoAlert("Deleted Customer", "Deleted Customer", "Customer has been deleted successfully!");
                    }else{
                        Utilities.errorAlert("Delete Appointments", "Delete Appointments", "Could not delete appointments ");
                    }
                }
            }
        }catch(Exception e){
            Utilities.errorAlert("Error", "Customer not Deleted", "Could not delete customer.");
        }
        // Refreshes data
        allCustomers = CustomerDB.getAllCustomers();
        CustomerTable.setItems(allCustomers);
    }

    /**
     * Gets the country object based on the name.
     * @param countryName Name of country
     * @return Country object
     */
    public Country getCountrySelection(String countryName){
        for(Country c : allCountries){
            if(c.getCounrty().equals(countryName))
                return c;
        }
        return null;
    }

    /**
     * Gets division object based on division name.
     * @param divisionName Division name
     * @return Division object
     */
    public Division getDivisionSelection(String divisionName){
        for(Division d : assocDivisions){
            if(d.getDivisionName().equals(divisionName))
                return d;
        }
        return null;
    }
}
