package model;

/** Customer Report Class */
public class CustomerReport {

    /** Type */
    private String type;
    /** Count */
    private int count;
    /** Month */
    private String month;

    /**
     * Constructor.
     * @param type
     * @param count
     */
    public CustomerReport(String type, int count, String month) {
        this.type = type;
        this.count = count;
        this.month = month;
    }

    /**
     * Sets count.
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Sets type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets month.
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets count.
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets type.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets month.
     * @return month
     */
    public String getMonth() {
        return month;
    }
}
