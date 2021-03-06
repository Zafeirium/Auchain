package auctioneum.network;

import auctioneum.blockchain.Account;
import auctioneum.blockchain.Block;
import auctioneum.blockchain.BlockChain;
import auctioneum.utils.files.FileManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Node implements Serializable{

    /** Node's Account **/
    private Account account;

    /** Blockchain copy **/
    private BlockChain blockChain;

    /** Node's peers **/
    private List<Node> peers;

    /** The ip address of the logged node **/
    private InetAddress ip;

    /** Port for incoming transactions **/
    private int transactionsPort;

    /** Port for incoming blocks to be validated **/
    private int validationsPort;

    /** Server that handles the incoming blocks for validation **/
    private Server validationServer;

    /** Server for receiving transactions **/
    private Server transactionServer;

    /** Stores/Retrieves files stored in the node's disk **/
    private FileManager fileManager;



    public Node(Account account, InetAddress ip){
        this.ip = ip;
        this.validationsPort = Settings.VALIDATIONS_PORT;
        this.transactionsPort = Settings.TRANSACTIONS_PORT;
        this.account = account;
        this.fileManager = new FileManager();
        this.peers = new ArrayList<>();
        this.validationServer = new Server(this, Server.ServiceType.VALIDATIONS);
        this.transactionServer = new Server(this, Server.ServiceType.TRANSACTIONS);
        this.blockChain = this.updateCopy();
        this.transactionServer.start();
        this.validationServer.start();

    }



    public void addBlock(Block validBlock){ this.blockChain.add(validBlock); }

    public void sendTransaction(Node target){}

    public void sendForValidation(Block block, Node target){
        try {
            Socket vSocket = new Socket(target.getIp(),target.getValidationsPort());
            ObjectOutputStream oos = new ObjectOutputStream(vSocket.getOutputStream());
            oos.writeObject(block);
            oos.flush();
            oos.close();
            vSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlockChain updateCopy(){return null;}


    /**
     * Leave auctioneum network
     */
    public void leave(){
        this.transactionServer.stop();
        this.validationServer.stop();
    }


    /**
     * ------------Accessors - Mutators------------
     */

    public InetAddress getIp() {
        return this.ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getTransactionsPort() {
        return this.transactionsPort;
    }

    public void setTransactionsPort(int transactionsPort) {
        this.transactionsPort = transactionsPort;
    }

    public int getValidationsPort() {
        return this.validationsPort;
    }

    public void setValidationsPort(int validationsPort) {
        this.validationsPort = validationsPort;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Server getValidationServer() {
        return this.validationServer;
    }

    public void setValidationServer(Server validationServer) {
        this.validationServer = validationServer;
    }

    public Server getTransactionServer() {
        return this.transactionServer;
    }

    public void setTransactionServer(Server transactionServer) {
        this.transactionServer = transactionServer;
    }

    public BlockChain getBlockChain() {
        return this.blockChain;
    }

    public void setBlockChain(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    public List<Node> getPeers() {
        return this.peers;
    }

    public void setPeers(List<Node> peers) {
        this.peers = peers;
    }



    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = this.account.getAddress().hashCode()+ (37*hashCode)  ;
        hashCode = (hashCode*23)+ this.ip.hashCode();
        return hashCode;
    }


    @Override
    public String toString() {
        try {
            String res = "\nNode: "+this.ip;
            res+= "\nAddress:"+this.account.getAddress();
            return res;
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
