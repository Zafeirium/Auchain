package auctioneum.network;

import auctioneum.blockchain.Block;

import java.io.ObjectInputStream;
import java.net.Socket;


public class ValidationHandler implements Runnable {

    /** Miner that runs validation **/
    private Node owner;

    /** Client connection **/
    private Socket connection;

    /** Block to be validated **/
    private Block candidate;



    public ValidationHandler(Node owner, Socket connection){
        this.owner = owner;
        this.connection = connection;
    }

    /**
     * Adds a new block in the blockchain if valid and then broadcasts it to other peers.
     */
    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
            this.candidate = (Block) ois.readObject();
            if(this.candidate.isValid()){
                this.owner.addBlock(this.candidate);
                for (Node peer: this.owner.getPeers()){
                    this.owner.sendForValidation(this.candidate,peer);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**-------------------Accessors-Mutators---------------------**/

    public Node getOwner() {
        return this.owner;
    }

    public void setOwner(Miner owner) {
        this.owner = owner;
    }

    public Socket getConnection() {
        return this.connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public Block getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Block candidate) {
        this.candidate = candidate;
    }

}
