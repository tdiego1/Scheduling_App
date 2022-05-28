package model;

import javafx.collections.ObservableList;

/** Country Class */
public class Country {

    /** Country ID */
    private int countryId;
    /** Country name */
    private String country;
    /** List of associated divisions */
    private ObservableList<Division> assocDivisions;

    /**
     * Constructor
     * @param countryId
     * @param country
     * @param assocDivisions
     */
    public Country(int countryId, String country, ObservableList<Division> assocDivisions) {
        this.countryId = countryId;
        this.country = country;
        this.assocDivisions = assocDivisions;
    }

    /**
     * Sets country ID.
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Sets country name.
     * @param counrty
     */
    public void setCounrty(String counrty) {
        this.country = counrty;
    }

    /**
     * Sets Associated divisions.
     * @param assocDivisions
     */
    public void setAssocDivisions(ObservableList<Division> assocDivisions) {
        this.assocDivisions = assocDivisions;
    }

    /**
     * Gets country ID.
     * @return Country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Gets country name.
     * @return Country name
     */
    public String getCounrty() {
        return country;
    }

    /**
     * Gets Associated Divisions
     * @return Associated Divisions
     */
    public ObservableList<Division> getAssocDivisions() {
        return assocDivisions;
    }

    @Override
    public String toString(){
        return this.country;
    }
}
