package model;

/** Division Class */
public class Division {

    /** Divisionn ID */
    private int divisionId;
    /** Division Name */
    private String divisionName;

    /**
     * Constructor.
     * @param divisionId
     * @param divisionName
     */
    public Division(int divisionId, String divisionName) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    /**
     * Sets division ID.
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Sets division name.
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets division ID.
     * @return Division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Gets division name.
     * @return Division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    @Override
    public String toString() {
        return this.divisionName;
    }
}
