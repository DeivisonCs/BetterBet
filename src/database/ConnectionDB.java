package database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Gerencia a conexão com o banco de dados usando um padrão Singleton.
 */
public class ConnectionDB {
    private Connection connection;
    private static ConnectionDB instanceDB;

    /**
     * Construtor privado que inicializa a conexão com o banco de dados.
     * Carrega as propriedades do banco de dados a partir do arquivo de configuração "config.properties".
     */
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
    
    /**
     * Retorna a conexão com o banco de dados.
     * 
     * @return A conexão com o banco de dados.
     */
    public Connection getConnection(){
        return connection;
    }
    
    /**
     * Retorna a instância Singleton da ConnectionDB. Cria uma nova instância se não existir.
     * 
     * @return A instância Singleton da ConnectionDB.
     */
    public static ConnectionDB getInstance(){
        if(instanceDB == null)
            instanceDB = new ConnectionDB();
        
        return instanceDB;
    }
}
