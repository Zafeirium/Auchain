package auctioneum.network;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;



/**
 * Configuration Settings of Nodes.
 */
public class Settings {

    private static final String CWD = System.getProperty("user.dir");

    private static final String SEP = File.separator;

    private static final String CONFIG_FILE = SEP+"src"+SEP+ "auctioneum" +SEP+"network"+SEP+"miners"+SEP+"config.json";

    private static final Properties PROPERTIES;

    public static final InetAddress IP ;

    public static final int TRANSACTIONS_PORT;

    public static final int VALIDATIONS_PORT;

    public static final String FILES_DIR;

    public static final String UPDATE_LINK;

    public static final String APP_KEY;

    public static String OS;


    static {
        PROPERTIES = load();
        IP = initializeIP();
        TRANSACTIONS_PORT = 8080;
        VALIDATIONS_PORT = findFreePort();
        FILES_DIR = initializeFilesDir();
        UPDATE_LINK = initializeUpdateLink();
        APP_KEY = initializeAppKey();
        OS = System.getProperty("os.name").toLowerCase();
    }

    public static Properties load(){
        try {
            Properties properties = new Properties();
            File configurationFile = new File(CWD+CONFIG_FILE);
            FileInputStream fis = new FileInputStream(configurationFile);
            properties.loadFromXML(fis);
            fis.close();
            return properties;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private static int findFreePort(){
        try {
            ServerSocket testSocket = new ServerSocket(0);
            int port = testSocket.getLocalPort();
            testSocket.close();
            return port;
        }catch (Exception e){
            return 0;
        }
    }

    private static String initializeFilesDir(){
        try {
            return System.getProperty("user.dir")+"/src/memCache"+ PROPERTIES.getProperty("FILES_DIR");
        }catch (Exception e){
            return null;
        }
    }

    private static String initializeUpdateLink(){
        try {
            return PROPERTIES.getProperty("UPDATE_LINK");
        }catch (Exception e){
            return null;
        }
    }

    private static String initializeAppKey(){
        try {
            return PROPERTIES.getProperty("APP_KEY");
        }catch (Exception e){
            return null;
        }
    }

    private static InetAddress initializeIP(){
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            return InetAddress.getByName(in.readLine());
        }catch (Exception e){
            return null;
        }
    }


    public static void main(String[] args){
        System.out.println(CWD+CONFIG_FILE);
    }

}
