/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;


import Strategy.DatabaseMappingMissing.DatabaseMappingMissingStrategy;
import Strategy.DatabasePerform.DatabasePerformMySQL;
import Strategy.DatabasePerform.DatabasePerformStrategy;
import ErrorCode.ParserErrorCode;
import Exception.ParseLogException;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author root
 */
public class LogLogin implements Log{
    private static final long serialVersionUID = 0L;
    
    private static final Logger LOGGER = Logger.getLogger(LogLogin.class);
 
    private String session;
    private String userID;
    private String gameID;
    private DatabasePerformStrategy databasePerformStrategy;

    public LogLogin(String session, String userID, String gameID){
        this.session = session;
        this.userID = userID;
        this.gameID = gameID;
        databasePerformStrategy = new DatabasePerformMySQL();
    }
    
    
    public String getSession() {
        return session;
    }

    public String getUserID() {
        return userID;
    }

    public String getGameID() {
        return gameID;
    }
    
    public void setDatabasePerformStrategy(DatabasePerformStrategy databasePerformStrategy){
        this.databasePerformStrategy = databasePerformStrategy;
    }
    
    public static LogLogin logToObj(String log) throws ParseException, ParseLogException, IOException{
        LogLogin logLogin = null;
        JSONObject jsonObj = null;
        
        JSONParser jsonParser = new JSONParser();
        try {
            jsonObj = (JSONObject) jsonParser.parse(log);
            
            if(!jsonObj.containsKey(LogInfo.userIDString)||!jsonObj.containsKey(LogInfo.gameIDString)||!jsonObj.containsKey(LogInfo.sessionString)){
                throw new ParseLogException(ParserErrorCode.MISSING_1);
            }
            String session = String.valueOf(jsonObj.get(LogInfo.sessionString));
            String userID = String.valueOf(jsonObj.get(LogInfo.userIDString));
            String gameID = String.valueOf(jsonObj.get(LogInfo.gameIDString));
            logLogin = new LogLogin(session,userID,gameID);  
            
        } catch (ParseException ex) {
            LOGGER.error("Error when parse log message to JSON. Check your log message and try it again.\nError detail:\n"+log+"\n"+ex);
            throw ex;
        } catch (ParseLogException ex) {
            LOGGER.error("Error when create LogPayment from log message. Check your log message and try it aganin.\nError detail:\n"+log+"\n"+ex);
            throw ex;
        }
        return logLogin;
    }
    
    public void accessToDatabase() throws Exception{
        databasePerformStrategy.accessToDatabase(this);
    }

    @Override
    public void process() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
