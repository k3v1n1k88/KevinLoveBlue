/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnectionPool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 *
 * @author root
 */
public class DatabaseMySQLConnectionFactory extends BasePooledObjectFactory<DatabaseMySQLConnection>{
    private DatabaseInfo databaseInfo;
    
    public DatabaseMySQLConnectionFactory(String hostAddress,int port,String databaseName,String userName,String password){
        this(new DatabaseInfo(hostAddress, port, databaseName, userName, password));
    }
    public DatabaseMySQLConnectionFactory(DatabaseInfo databaseInfo){
        this.databaseInfo = databaseInfo;
    }   

    @Override
    public DatabaseMySQLConnection create() throws Exception {
        return new DatabaseMySQLConnection(this.databaseInfo);
    }

    @Override
    public PooledObject<DatabaseMySQLConnection> wrap(DatabaseMySQLConnection mySQLConnection) {
        return new DefaultPooledObject(mySQLConnection);
    }
    
    
}
