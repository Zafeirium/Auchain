package auctioneum.network;

import auctioneum.blockchain.Account;
import auctioneum.blockchain.Block;
import auctioneum.blockchain.Transaction;

import auctioneum.utils.hashing.SHA3_256;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Miner extends Node {

    /** Unvalidated Transactions **/
    private List<Transaction> transactions;



    public Miner(Account account) throws RemoteException{
        super(account, Settings.IP);
        this.transactions = new ArrayList<>();
    }



    public void createBlock(int size){}

    public void addTransaction(Transaction transaction){ this.transactions.add(transaction); }

    private Block mine(Block block){
        while (!SHA3_256.hash(block.toString()).contains("k")){
            block.setNonce(block.getNonce()+1);
        }
        return block;
    }


    /**------------Accessors-Mutators---------------**/

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


}
