package model;

import java.time.LocalDateTime;

public class Appointment {
    /** Appointment ID */
    private int appointmentId;
    /** Title of Appointment */
    private String title;
    /** Description of appointment */
    private String description;
    /** Appointment Location */
    private String location;
    /** Appointment contact ID */
    private Contact contact;
    /** Appointment type */
    private String type;
    /** Appointment start date/time */
    private LocalDateTime startDateTime;
    /** Appointment end date/time */
    private LocalDateTime endDateTime;
    /** Associated Customer */
    private Customer customer;
    /** Associated User */
    private User user;

    /**
     * Constructs appointment object.
     * @param appointmentId ID
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param contact Appointment contact
     * @param type Appointment type
     * @param startDateTime Appointment start Date/Time
     * @param endDateTime Appointment end Date/Time
     * @param customer Appointment customer
     * @param user Appointment user
     */
    public Appointment(int appointmentId, String title, String description, String location, Contact contact, String type,
                       LocalDateTime startDateTime, LocalDateTime endDateTime, Customer customer, User user) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customer = customer;
        this.user = user;
    }

    /**
     * Sets Appointment ID.
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    /**
     * Sets Title.
     * @param title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Sets description.
     * @param description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Sets location.
     * @param location
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * Sets contact ID.
     * @param contact
     */
    public void setContact(Contact contact) { this.contact = contact; }

    /**
     * Sets type.
     * @param type
     */
    public void setType(String type) { this.type = type; }

    /**
     * Sets start date and time.
     * @param startDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    /**
     * Sets end date/time.
     * @param endDateTime
     */
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    /**
     * Sets customer.
     * @param customer
     */
    public void setCustomer(Customer customer) { this.customer = customer; }

    /**
     * Sets user.
     * @param user
     */
    public void setUser(User user) { this.user = user; }

    /**
     * Gets appointment ID.
     * @return
     */
    public int getAppointmentId() { return appointmentId; }

    /**
     * Gets title.
     * @return
     */
    public String getTitle() { return title; }

    /**
     * Gets description.
     * @return
     */
    public String getDescription() { return description; }

    /**
     * Gets Location.
     * @return
     */
    public String getLocation() { return location; }

    /**
     * Gets contact ID.
     * @return
     */
    public Contact getContact() { return contact; }

    /**
     * Gets type.
     * @return
     */
    public String getType() { return type; }

    /**
     * Gets start date/time.
     * @return
     */
    public LocalDateTime getStartDateTime() { return startDateTime; }

    /**
     * Gets end date/time.
     * @return
     */
    public LocalDateTime getEndDateTime() { return endDateTime; }

    /**
     * Gets Customer.
     * @return
     */
    public Customer getCustomer() { return customer; }

    /**
     * Gets User.
     * @return
     */
    public User getUser() { return user; }
}
