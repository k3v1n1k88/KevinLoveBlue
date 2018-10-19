/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Configuration.ConfigOfSystem;
import DBConstantString.DBString;
import Constant.LogInfo;
import Strategy.DatabaseMappingMissing.DatabaseMappingMissingStrategy;
import Strategy.DatabasePerform.DatabasePerformMySQL;
import Strategy.DatabasePerform.DatabasePerformStrategy;
import ErrorCode.ParserErrorCode;
import Exception.ParseLogException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author root
 */
public class LogPayment implements Log{
    private static final long serialVersionUID = 0L;
    
    private static final Logger LOGGER = Logger.getLogger(LogPayment.class);
    
    private DatabasePerformStrategy databasePerformStrategy = new DatabasePerformMySQL();
    private DatabaseMappingMissingStrategy databaseMappintMissingStrategy;
    private 
            
    private String userID;
    private String gameID;
    private long amount;
    
    public String getUserID() {
        return userID;
    }

    public String getGameID() {
        return gameID;
    }

    public long getAmount() {
        return amount;
    }
    
    public LogPayment(String userID, String gameID, long amount){
        this.userID = userID;
        this.gameID = gameID;
        this.amount = amount;
    }
    
    public static LogPayment logToObj(String log) throws ParseException, ParseLogException, IOException{
        LogPayment logPayment = null;
        JSONObject jsonObj = null;
        
        JSONParser jsonParser = new JSONParser();
        try {
            jsonObj = (JSONObject) jsonParser.parse(log);
            if(!jsonObj.containsKey(LogInfo.userIDString)||!jsonObj.containsKey(LogInfo.userIDString)||!jsonObj.containsKey(LogInfo.userIDString)){
                throw new ParseLogException(ParserErrorCode.MISSING_2_1);
            }
            try{
                String userID = String.valueOf(jsonObj.get(LogInfo.userIDString));
                String gameID = String.valueOf(LogInfo.gameIDString);
                long amount =  Long.parseLong(String.valueOf(jsonObj.get("amount")));
                
                logPayment = new LogPayment(userID,gameID,amount);
                
                return logPayment;
            } catch(NumberFormatException ex){
                throw new ParseLogException(ParserErrorCode.MISSING_2_2);
            }
        } catch (ParseException ex) {
            LOGGER.error("Error when parse log message to JSON. Check your log message and try it again.\nError detail:\n"+ex);
            throw ex;
        } catch (ParseLogException ex) {
            LOGGER.error("Error when create LogPayment from log message. Check your log message and try it aganin.\nError detail:\n"+ex);
            throw ex;
        }
    }
    private void mappingDatabase() throws Exception{
        databasePerformStrategy.mappingToDatabase(this);
    }

    @Override
    public void process() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
