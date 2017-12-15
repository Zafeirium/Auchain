package auctioneum.blockchain;

public class Account {
    
    private String username;
    
    private String password;

    /** Public identified of the account **/
    private String address;

    /** Account balance **/
    private float balance;

    public Account(){}


    /**--------------Accessors-Mutators-------------------**/
    
    static float getBalance(String address) {
        return 0.0F;
    }

    public String getAddress() { return this.address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
