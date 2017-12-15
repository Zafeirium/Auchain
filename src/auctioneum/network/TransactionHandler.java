package auctioneum.network;

import auctioneum.blockchain.Transaction;

import java.io.ObjectInputStream;
import java.net.Socket;

public class TransactionHandler implements Runnable{

    private Node owner;

    private Socket connection;

    private Transaction transaction;


    public TransactionHandler(Node owner, Socket connection){
        this.owner = owner;
        this.connection = connection;
    }


    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
            this.transaction = (Transaction) ois.readObject();
            if (this.transaction.isValid()) {
                if (this.owner instanceof Miner){
                    Miner self = (Miner) this.owner;
                    self.addTransaction(this.transaction);
                }
                for (Node peer : this.owner.getPeers()){
                    this.owner.sendTransaction(peer);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
