/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy.DatabasePerform;

import Strategy.DatabaseMappingMissing.DatabaseMappingMissingStrategy;
import DatabaseConnectionPool.DatabaseInfo;
import Configuration.ConfigOfSystem;
import DBConstantString.DBString;
import DatabaseConnectionPool.DatabaseConnectionPoolMySQL;
import DatabaseConnectionPool.DatabaseMySQLConnection;
import DatabaseConnectionPool.DatabaseMySQLConnectionFactory;
import Exception.DatabaseException;
import Exception.ParseLogException;
import Object.LogLogin;
import Object.LogPayment;
import Strategy.DatabaseWriteLogMapping.DatabaseMappingLogStrategy;
import Strategy.DatabaseRecomment.DatabaseRecommentStrategy;
import VNG.generate.GenerateRequestFromLogin;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author root     
 */
public class DatabasePerformMySQL implements DatabasePerformStrategy{
    
    private static final Logger LOGGER = Logger.getLogger(DatabasePerformMySQL.class);
    
    private static GenericObjectPool pool = null; 
    
    private static Calendar calendar = new GregorianCalendar();
    
    private DatabaseMappingMissingStrategy databaseMappingMissingStrategy;
    private DatabaseRecommentStrategy databaseRecommentStrategy;
    private DatabaseMappingLogStrategy databaseMappingLogStrategy;
    
    private DatabaseInfo databaseInfo;
    
    public DatabasePerformMySQL(String hostAddress,
                                int port,
                                String databaseName,
                                String userName,
                                String password,
                                DatabaseMappingMissingStrategy databaseMappingMissingStrategy,
                                DatabaseRecommentStrategy databaseRecommentStrategy,
                                DatabaseMappingLogStrategy databaseMappingLogStrategy) throws IOException{
        
        this(new DatabaseInfo(hostAddress,port,databaseName,userName,password),
                databaseMappingMissingStrategy,
                databaseRecommentStrategy,
                databaseMappingLogStrategy);
        
    }
    
    public DatabasePerformMySQL(DatabaseInfo databaseInfo,
                                DatabaseMappingMissingStrategy databaseMappingMissingStrategy,
                                DatabaseRecommentStrategy databaseRecommentStrategy,
                                DatabaseMappingLogStrategy databaseMappingLogStrategy){
        
        this.databaseInfo = databaseInfo;
        this.databaseMappingLogStrategy = databaseMappingLogStrategy;
        this.databaseRecommentStrategy = databaseRecommentStrategy;
        this.databaseMappingMissingStrategy = databaseMappingMissingStrategy;
        
        if(DatabasePerformMySQL.pool == null){
            DatabaseMySQLConnectionFactory factory = new DatabaseMySQLConnectionFactory(databaseInfo.getHostAddress(),
                                                                                        databaseInfo.getPort(),
                                                                                        databaseInfo.getDatabaseName(),
                                                                                        databaseInfo.getUserName(),
                                                                                        databaseInfo.getPassword());
            DatabasePerformMySQL.pool = new DatabaseConnectionPoolMySQL(factory);
        }
        this.databaseMappingMissingStrategy = databaseMappingMissingStrategy;
        
    }
    

    public void setDatabaseMappintMissingStrategy(DatabaseMappingMissingStrategy databaseMappingMissingStrategy){
        this.databaseMappingMissingStrategy = databaseMappingMissingStrategy;
    }
    
    @Override
    public void accessToDatabase(LogLogin logLogin) throws SQLException, Exception{
        Connection cnn;
        DatabaseMySQLConnection databaseMySQLConnection;
        try {
            databaseMySQLConnection = (DatabaseMySQLConnection) DatabasePerformMySQL.pool.borrowObject();
            cnn = databaseMySQLConnection.getConnection();
        } catch (Exception ex) {
            LOGGER.error("Error happen when borrow object from pool");
            throw ex;
        }
        String sql="";
        try{
            String format = "INSERT INTO %s(%s,%s,%s,%s,%s,%s)"+
                            " VALUES(\'%s\',\'%s\',\'%s\',%d,%d,%d)"+
                            " ON DUPLICATE KEY UPDATE"+
                            " %s = %s*(%s-%d) + %d,"+
                            " %s = %d,"+
                            " %s = %s + %d";
            long week = getWeek();
            Statement statement = cnn.createStatement();
            sql = String.format(format, DBString.topScoreGameTableString,
                                                DBString.sessionString,
                                                DBString.userIDString,
                                                DBString.gameIDString,
                                                DBString.pointString,
                                                DBString.weekString,
                                                DBString.loginTimesString,
                                                logLogin.getSession(),
                                                logLogin.getUserID(),
                                                logLogin.getGameID(),
                                                ConfigOfSystem.pointPerLogin,
                                                week,
                                                1,
                                                DBString.pointString,
                                                DBString.pointString,
                                                DBString.weekString,
                                                week,
                                                ConfigOfSystem.pointPerLogin,
                                                DBString.weekString,
                                                week,
                                                DBString.loginTimesString,
                                                DBString.loginTimesString,
                                                1);
            
            int res = statement.executeUpdate(sql);
 
            //return res;
        } catch (SQLException ex) {
            LOGGER.error("Error with SQL statement: "+sql+".\nDetail:"+ex);
            if(cnn.isClosed()){
                DatabasePerformMySQL.pool.invalidateObject(databaseMySQLConnection);
                databaseMySQLConnection = null;
            }
            throw ex;
        }finally{
            if(databaseMySQLConnection != null){
                DatabasePerformMySQL.pool.returnObject(databaseMySQLConnection);
            }
        }
    }
    
    private int updatePointOfGame(LogLogin logLogin) throws SQLException, Exception{
        Connection cnn;
        DatabaseMySQLConnection databaseMySQLConnection;
        try {
            databaseMySQLConnection = (DatabaseMySQLConnection) DatabasePerformMySQL.pool.borrowObject();
            cnn = databaseMySQLConnection.getConnection();
        } catch (Exception ex) {
            LOGGER.error("Error happen when borrow object from pool");
            throw ex;
        }
        String sql="";
        try{
            String format = "UPDATE %s,%s"+
                            " SET %s.%s = %s.%s + 1"+
                            " WHERE %s.%s = %s.%s ";
            Statement statement = cnn.createStatement();
            sql = String.format(format, DBString.pointOfGameTableString,
                                        DBString.topScoreGameTableString,
                                        DBString.pointOfGameTableString,
                                        DBString.pointString,
                                        DBString.pointOfGameTableString,
                                        DBString.pointString,
                                        DBString.pointOfGameTableString,
                                        DBString.gameIDString,
                                        DBString.topScoreGameTableString,
                                        DBString.gameIDString);
            //LOGGER.info("SQL instructment: "+sql+"\n");
            int res = statement.executeUpdate(sql);
            return res;
        } catch (SQLException ex) {
            LOGGER.error("Error with SQL statement: "+sql+".\nDetail:"+ex);
            if(cnn.isClosed()){
                DatabasePerformMySQL.pool.invalidateObject(databaseMySQLConnection);
                databaseMySQLConnection = null;
            }
            throw ex;
        }finally{
            if(databaseMySQLConnection != null){
                DatabasePerformMySQL.pool.returnObject(databaseMySQLConnection);
            }
        }
    }

    @Override
    public void mappingToDatabase(LogPayment logPayment) throws DatabaseException, SQLException, IOException, Exception {
        Connection cnn;
        DatabaseMySQLConnection databaseMySQLConnection;
        try {
            databaseMySQLConnection = (DatabaseMySQLConnection) DatabasePerformMySQL.pool.borrowObject();
            cnn = databaseMySQLConnection.getConnection();
        } catch (Exception ex) {
            LOGGER.error("Error happen when borrow object from pool");
            throw ex;
        }
        String sql="";
        try{
            long bonusPoint = logPayment.getAmount()/10000;
            String format = "UPDATE %s"+
                            " SET %s = %s + %d"+
                            " %s = %s + %d"+
                            " WHERE %s = %s ";
            Statement statement = cnn.createStatement();
            sql = String.format(format, DBString.topScoreGameTableString,
                                        DBString.pointString,
                                        DBString.pointString,
                                        bonusPoint,
                                        DBString.amountString,
                                        DBString.amountString,
                                        logPayment.getAmount(),
                                        DBString.userIDString,
                                        DBString.userIDString);
            //LOGGER.info("SQL instructment: "+sql+"\n");
            int res = statement.executeUpdate(sql);
            if(res <= 0 ){
                databaseMappingMissingStrategy.writeLogToDatabase(logPayment);
            }
        } catch (SQLException ex) {
            LOGGER.error("Error with SQL statement: "+sql+".\nDetail:"+ex);
            if(cnn.isClosed()){
                DatabasePerformMySQL.pool.invalidateObject(databaseMySQLConnection);
                databaseMySQLConnection = null;
            }
            throw ex;
        }finally{
            if(databaseMySQLConnection != null){
                DatabasePerformMySQL.pool.returnObject(databaseMySQLConnection);
            }
        }
    }

    private long getWeek(){
        long week = calendar.get(Calendar.WEEK_OF_YEAR)+ calendar.get(Calendar.YEAR);
        return week;
    }
    
    private int initGameOfPoint(LogLogin logLogin) throws SQLException, Exception{
        Connection cnn;
        DatabaseMySQLConnection databaseMySQLConnection;
        String sql ="";
        try {
            databaseMySQLConnection = (DatabaseMySQLConnection) DatabasePerformMySQL.pool.borrowObject();
            cnn = databaseMySQLConnection.getConnection();
        } catch (Exception ex) {
            LOGGER.error("Error happen when borrow object from pool");
            throw ex;
        }
        try {
            Statement statement = cnn.createStatement();
            String format = "INSERT IGNORE INTO %s(%s)"+
                            " VALUES(\'%s\')";
            sql = String.format(format, DBString.pointOfGameTableString,
                                                    DBString.gameIDString,
                                                    logLogin.getGameID());
            //LOGGER.info("SQL instrusment:"+sql);
            int affectRows = statement.executeUpdate(sql);
            return affectRows;
        } catch (SQLException ex) {
            LOGGER.error("Error with SQL statement: "+sql+".\nDetail:"+ex);
            if(cnn.isClosed()){
                DatabasePerformMySQL.pool.invalidateObject(databaseMySQLConnection);
                databaseMySQLConnection = null;
            }
            throw ex;
        }finally{
            if(databaseMySQLConnection != null){
                DatabasePerformMySQL.pool.returnObject(databaseMySQLConnection);
            }
        }
    }

    @Override
    public String getList(String session) throws DatabaseException, SQLException, Exception{
        JsonArray orderListGame = this.getListTopGame(session);
        JsonArray recommentListGame = this.getListRecommentGame();
        
//        JsonObject jsonObjOrderListGame = new JsonObject();
//        jsonObjOrderListGame.add(DBInfo.recommentListGame, recommentListGame);
//        
//        JsonObject jsonObjRecommentListGame = new JsonObject();
//        jsonObjRecommentListGame.add(DBInfo.orderListGame, orderListGame);
        
        JsonObject res = new JsonObject();
        res.add(DBString.recommentListGame, recommentListGame);
        res.add(DBString.orderListGame, orderListGame);
        return res.toString();
    }
    private JsonArray getListTopGame(String session) throws SQLException, Exception{
        Connection cnn;
        DatabaseMySQLConnection databaseMySQLConnection;
        
        try {
            databaseMySQLConnection = (DatabaseMySQLConnection) DatabasePerformMySQL.pool.borrowObject();
            cnn = databaseMySQLConnection.getConnection();
        } catch (Exception ex) {
            LOGGER.error("Error happen when borrow object from pool");
            throw ex;
        }
        PreparedStatement preparedStatement = null;
        JsonArray jsonArray = new JsonArray();
        try{
            String format = "SELECT %s"
                           +" FROM %s"
                           +" WHERE %s = ? "
                           +" ORDER BY %s DESC";
            String preSql = String.format(format, DBString.gameIDString,
                                        DBString.topScoreGameTableString,
                                        DBString.sessionString,
                                        DBString.gameIDString);
            preparedStatement = cnn.prepareStatement(preSql);
            preparedStatement.setString(1, session);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(preparedStatement);
            while(rs.next()){
                JsonPrimitive element = new JsonPrimitive(rs.getString(DBString.gameIDString));
                jsonArray.add(element);
            }
            return jsonArray;
        }catch (SQLException ex) {
            LOGGER.error("Error with SQL statement: "+preparedStatement+".\nDetail:"+ex);
            if(cnn.isClosed()){
                DatabasePerformMySQL.pool.invalidateObject(databaseMySQLConnection);
                databaseMySQLConnection = null;
            }
            throw ex;
        }finally{
            if(databaseMySQLConnection != null){
                DatabasePerformMySQL.pool.returnObject(databaseMySQLConnection);
            }
        }
    }
    private JsonArray getListRecommentGame() throws SQLException, Exception{
        Connection cnn;
        DatabaseMySQLConnection databaseMySQLConnection;
        try {
            databaseMySQLConnection = (DatabaseMySQLConnection) DatabasePerformMySQL.pool.borrowObject();
            cnn = databaseMySQLConnection.getConnection();
        } catch (Exception ex) {
            LOGGER.error("Error happen when borrow object from pool");
            throw ex;
        }
        String sql = "";
        JsonArray jsonArray = new JsonArray();
        try{
            String format = "SELECT %s"
                           +" FROM %s"
                           +" ORDER BY %s DESC"
                           +" LIMIT %s";
            sql = String.format(format, DBString.gameIDString,
                                        DBString.pointOfGameTableString,
                                        DBString.gameIDString,
                                        ConfigOfSystem.maxRecommentGame);
            Statement statement = cnn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                JsonPrimitive element = new JsonPrimitive(rs.getString(DBString.gameIDString));
                jsonArray.add(element);
            }
            return jsonArray;
        }catch (SQLException ex) {
            LOGGER.error("Error with SQL statement: "+sql+".\nDetail:"+ex);
            if(cnn.isClosed()){
                DatabasePerformMySQL.pool.invalidateObject(databaseMySQLConnection);
                databaseMySQLConnection = null;
            }
            throw ex;
        }finally{
            if(databaseMySQLConnection != null){
                DatabasePerformMySQL.pool.returnObject(databaseMySQLConnection);
            }
        }
    }
    
    public static void main(String[] args) throws ParseLogException, SQLException, ParseException, DatabaseException, IOException, Exception {
        DatabasePerformMySQL databasePerformSQL = new DatabasePerformMySQL(DBString.hostAddress,
                                                                           DBString.port,
                                                                           DBString.databaseName,
                                                                           DBString.userName,
                                                                           DBString.password,
                                                                            new Database);
        LogLogin logLogin = LogLogin.logToObj(GenerateRequestFromLogin.get());
        String res = databasePerformSQL.getList("pkeb0oklbe");
        System.out.println(res);
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(res, JsonElement.class);
        JsonObject obj = element.getAsJsonObject();
        System.out.println(obj);
    }
}
