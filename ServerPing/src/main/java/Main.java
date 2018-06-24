
import CreateServer.Type;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args){ 
        try {
            Server server = new Server( Type.Server.TTHREADPOOL,
                                        Type.Transport.TFASTFRAMED,
                                        Type.Protocol.TBINARY,
                                        9090);
            
            Timer timer = new Timer();
            timer.schedule(new TrafficDisplay(), 0, 1000);
            
            server.beginServer();
            System.out.println("Server stopped.");
        } catch (TApplicationException ex) {
            LOGGER.error("Errow when excute", ex);
        } catch (TProtocolException ex) {
            LOGGER.error("Error protocol", ex);
        } catch (TTransportException ex) {
            LOGGER.error("Error transport",ex);
        }
    }
}
