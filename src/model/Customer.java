package model;

/** Customer object class */
public class Customer {
    /** Customer ID */
    private int customerId;
    /** Customer Name */
    private String customerName;
    /** Customer Address */
    private String address;
    /** Postal Code */
    private String postalCode;
    /** Phone Number */
    private String phoneNumber;
    /** Division ID */
    private int divisionId;
    /** Division */
    private Division division;
    /** Country */
    private Country country;

    /**
     * Customer constructor.
     * @param customerId Customer ID
     * @param customerName Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phoneNumber Customer phone number
     * @param divisionId Customer division ID
     * @param division Customer division
     * @param country Customer country
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber,
                    int divisionId, Division division, Country country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    /**
     * Sets customer ID.
     * @param customerId Customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets Customer Name
     * @param customerName Customer Name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Sets Address
     * @param address address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets Postal Code
     * @param postalCode postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets Phone Number
     * @param phoneNumber Phone Number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets Dividion ID
     * @param divisionId Division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Sets Division ID
     * @param division Division ID
     */
    public void setDivision(Division division) {
        this.division = division;
    }

    /**
     * Sets Country
     * @param country Country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Gets Customer ID
     * @return Customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Gets Customer Name
     * @return Customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets Address
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets postal code
     * @return Postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets phone number
     * @return Phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets Division ID
     * @return Division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Gets Division
     * @return Division
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Gets Country
     * @return Country
     */
    public Country getCountry() {
        return country;
    }

    @Override
    public String toString(){
        return Integer.toString(this.customerId);
    }


}
