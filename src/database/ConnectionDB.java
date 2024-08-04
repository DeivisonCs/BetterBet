package database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDB {
    private Connection connection;
    private static ConnectionDB instanceDB;

    private ConnectionDB(){
    	
    	Properties env = new Properties();
    			
        try(InputStream input = new FileInputStream("config.properties")){
        	
        	env.load(input);
        	
            String url = env.getProperty("database.url");
            String user = env.getProperty("database.username");
            String pass = env.getProperty("database.password");

            connection = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public static ConnectionDB getInstance(){
        if(instanceDB == null)
            instanceDB = new ConnectionDB();
        
        return instanceDB;
    }
}
