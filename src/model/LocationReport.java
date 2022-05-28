package model;

/** Location Report Class */
public class LocationReport {

    /** Location */
    private String location;
    /** Count */
    private int count;

    /**
     * Constructor
     * @param location
     * @param count
     */
    public LocationReport(String location, int count) {
        this.location = location;
        this.count = count;
    }

    /**
     * Sets count.
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Sets location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets location.
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets count.
     * @return count
     */
    public int getCount() {
        return count;
    }
}
