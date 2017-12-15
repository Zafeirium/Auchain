package auctioneum.blockchain;

import java.io.Serializable;
import java.util.List;
import auctioneum.utils.hashing.SHA3_256;

public class Block implements Serializable{


    private static final long serialVersionUID = -8355673521334364077L;

    private String hash;//contains all fields of Block hashed

    private String beneficiary;//miner that solves the problem/ validate the transaction

    private int number;//last block's number on the blockchain

    private String nonce;//nonce number of the current block

    private long timestamp;//when this block was created

    private List<Transaction> transactions;//transactions included in the block


    public Block(String hash, String beneficiary, int number, String nonce, long timestamp, List<Transaction> transactions){
        this.hash = hash;
        this.beneficiary = beneficiary;
        this.number = number;
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.transactions = transactions;
    }

    public boolean isValid(){ 
        String block_info = this.beneficiary +" "+ this.nonce+" "+this.number +" "+ this.timestamp+" "+this.transactions.get(0).toString();
        if(!(SHA3_256.hash(block_info) == hash))
            return false;
        for(Transaction tr : this.transactions){
            if(!tr.isValid())
                return false;
        }
        return true;
    }

    /**--------------Accessors-Mutators------------------**/

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getBeneficiary() {
        return this.beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNonce() {
        return this.nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
