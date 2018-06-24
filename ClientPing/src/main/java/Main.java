
import Client.MyTask;
import Service2.Calculator;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pool.ThriftConnectionPool;
import pool.config.ThriftConnectionPoolConfig;
import pool.exception.ThriftConnectionPoolException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author root
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            ThriftConnectionPoolConfig config = new ThriftConnectionPoolConfig();
            config.setClientClass(Calculator.Client.class);
            config.setThriftProtocol(ThriftConnectionPoolConfig.TProtocolType.BINARY);
            config.setThriftTransport(ThriftConnectionPoolConfig.TTransportType.FASTFRAMED);
            config.addThriftServer("localhost", 9090);
            config.setMaxConnectionPerServer(10);
            config.setMinConnectionPerServer(1);
            config.setConnectTimeout(10);
            config.setMaxConnectionAge(10);
            config.setLazyInit(true);
            
            ThriftConnectionPool connectionPool = new ThriftConnectionPool(config);
            
//            
//            Calculator.Client client2 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client3 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client4 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client5 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client6 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client7 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client8 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client9 = (Calculator.Client) connectionPool.getConnection().getClient();
//            Calculator.Client client10 = (Calculator.Client) connectionPool.getConnection().getClient();
//            
            
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            //ClientCalculator client1 = new ClientCalculator(Service2.Calculator.Client.class);
            //Timer timer = new Timer();
            //timer.scheduleAtFixedRate(new ThoughtDisplay(),0,1000);
            for(int i=0;i<2;i++){
                Calculator.Client client = (Calculator.Client) connectionPool.getConnection().getClient();
                executorService.execute(new MyTask(client));
            }
            executorService.shutdown();
            //System.out.println(client1.add(a, b));
        connectionPool.shutdown();
        } catch (ThriftConnectionPoolException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
