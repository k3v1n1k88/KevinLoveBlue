/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnectionPool;

import DBConstantString.DBString;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class DatabaseMySQLConnection implements DatabaseConnection {

    private static final long serialVersionUID = 0L;

    private static final Logger LOGGER = Logger.getLogger(DatabaseMySQLConnection.class);

    private DatabaseInfo databaseInfo = null;
    private static Calendar calendar = new GregorianCalendar();
    private Connection connection = null;

    public DatabaseMySQLConnection(String hostAddress, String databaseName, int port, String userName, String password) throws SQLException {
        this(new DatabaseInfo(hostAddress, port, databaseName, userName, password));
    }
    public DatabaseMySQLConnection(DatabaseInfo databaseInfo) throws SQLException {
        this.databaseInfo = databaseInfo;
        this.makeConnection();
    }
   
    @Override
    public void makeConnection() throws SQLException {
        if(this.connection != null){
            return;
        }
        String URLPattern = "jdbc:mysql://%s:%d/%s?useSSL=true";
        
        String serverName = databaseInfo.getHostAddress();
        int port = databaseInfo.getPort();
        String databaseName = databaseInfo.getDatabaseName();
        String userName = databaseInfo.getUserName();
        String password = databaseInfo.getPassword();

        String address = String.format(URLPattern, serverName, port, databaseName);

        try {
            this.connection = DriverManager.getConnection(address, userName, password);
        } catch (SQLException ex) {
            LOGGER.error("Create connection failure with SQL instrustment: " + address + ", user:" + userName + " password:" + password + "\n");
            throw ex;
        }
    }

    public DatabaseInfo getDatabaseInfo() {
        return this.databaseInfo;
    }

    public Connection getConnection() {
        return connection;
    }
    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
    }
}
