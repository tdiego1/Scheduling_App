package model;
/** User object class */
public class User {
    /** User ID */
    private int userId;
    /** Username */
    private String userName;
    /** Password */
    private String password;

    /**
     * User constructor
     * @param userId User ID
     * @param userName Username
     * @param password password
     */
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Sets the user id.
     * @param userId User ID
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Sets the username.
     * @param userName Username
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Sets the password.
     * @param password Password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Gets the UserId.
     * @return User ID
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Gets the Username.
     * @return Username
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Gets the password.
     * @return Password
     */
    public String getPassword(){
        return password;
    }

    @Override
    public String toString(){ return Integer.toString(this.userId); }
}
