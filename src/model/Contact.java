package model;

public class Contact {

    /** Contact ID */
    private int contactId;
    /** Contact Name */
    private String contactName;

    /**
     * Contact constructor.
     * @param contactId Contact ID
     * @param contactName Contact Name
     */
    public Contact(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Sets contact ID.
     * @param contactId contact ID
     */
    public void setContactId(int contactId) { this.contactId = contactId; }

    /**
     * Sets contact name.
     * @param contactName contact name
     */
    public void setContactName(String contactName) { this.contactName = contactName; }

    /**
     * Gets contact ID.
     * @return Contact ID
     */
    public int getContactId() { return contactId; }

    /**
     * Gets contact name.
     * @return contact name
     */
    public String getContactName() { return contactName; }

    @Override
    public String toString(){
        return this.contactName;
    }
}
