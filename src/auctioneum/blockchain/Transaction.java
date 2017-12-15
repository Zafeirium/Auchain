package auctioneum.blockchain;

import java.io.Serializable;
import auctioneum.utils.keys.Validator;


public class Transaction implements Serializable{

    private static final long serialVersionUID = -1565349679238127979L;

    private int id;// is the user's id plus a sequence number based on the transactions he makes

    private String to;//the receiver's address
    
    private String from;//the sender's address

    private float value;// value of the transaction

    private String receiver_signature;

    private String sender_signature;

    public Transaction(int id, String to, float value, String receiver_signature, String sender_signature) {
        this.id = id;
        this.to = to;
        this.value = value;
        this.receiver_signature = receiver_signature;
        this.sender_signature = sender_signature;
    }
    /* valid signatures, valid transaction id, balance check */
    public boolean isValid(){
        Validator v = new Validator();
       if(v.validateSignatures() && Account.getBalance(from) >= this.value)
           return true;
       else
           return false;
     }

    /**--------------Accessors-Mutators------------------**/

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    
    public String toString() {
        return this.id+" "+this.from+" "+this.to+" "+this.receiver_signature+" "+this.sender_signature+ " "+this.value;
    }

}
