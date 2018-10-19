/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnectionPool;

import Configuration.ConfigOfConnectionPool;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;




public class DatabaseConnectionPoolMySQL extends GenericObjectPool<DatabaseMySQLConnection> {
    
    
    public DatabaseConnectionPoolMySQL(DatabaseMySQLConnectionFactory factory){
        this(factory,new GenericObjectPoolConfig(),new AbandonedConfig());
    }
    
    public DatabaseConnectionPoolMySQL(DatabaseMySQLConnectionFactory factory, GenericObjectPoolConfig config, AbandonedConfig abandonedConfig){
        
        super(factory,config,abandonedConfig);
        
        this.setBlockWhenExhausted(ConfigOfConnectionPool.BLOCK_WHEN_EXHAUSTED);
        
        this.setLifo(ConfigOfConnectionPool.RETURN_POLICY);
        
        this.setMaxIdle(ConfigOfConnectionPool.MAX_IDLE);
        
        this.setMaxTotal(ConfigOfConnectionPool.MAX_TOTAL);
        
        this.setMaxWaitMillis(ConfigOfConnectionPool.MAX_WAIT_MILLIS);
        
        this.setMinIdle(ConfigOfConnectionPool.MIN_IDLE);
        
        this.setMinEvictableIdleTimeMillis(ConfigOfConnectionPool.MIN_EVICTABLE_IDLE_TIME_MILLIS);
        
        this.setNumTestsPerEvictionRun(ConfigOfConnectionPool.NUM_TESTS_PER_EVICTION_RUN);
        
        this.setSoftMinEvictableIdleTimeMillis(ConfigOfConnectionPool.SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        
        this.setTestOnBorrow(ConfigOfConnectionPool.TEST_ON_BORROW);
        
        this.setTestOnReturn(ConfigOfConnectionPool.TEST_ON_RETURN);
        
        this.setTestWhileIdle(ConfigOfConnectionPool.TEST_WHILE_IDLE);
        
        this.setTimeBetweenEvictionRunsMillis(ConfigOfConnectionPool.TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        
    }
    
}
