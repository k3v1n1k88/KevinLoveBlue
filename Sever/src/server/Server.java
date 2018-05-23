/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

/**
 *
 * @author root
 */
public class Server {

    public static Handler mHandler = null;
    public static Service.Processor mProcessor = null;
    public static int port = 9090;

    public static void main(String[] args) {
        try {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Handler.request+" request/s");
                    Handler.request = 0;
                }
            };
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
            
            mHandler = new Handler();
            mProcessor = new Service.Processor(mHandler);

            Runnable mThread = new Runnable() {
                @Override
                public void run() {
                    try {
                        serviceServer(mProcessor, port);
                    } catch (TTransportException ex) {
                        System.out.println("Problem when opening transport...");
                    } catch (TProtocolException ex) {
                        System.out.println("Problem when setting protocol...");
                    } catch (TApplicationException ex) {
                        System.out.println("Error executing:"+ex.getMessage());;
                    }
                }
            };

            new Thread(mThread).start();
            
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public static void serviceServer(Service.Processor processor, int port) throws TTransportException,TProtocolException,TApplicationException{
        try {
            
            //Decalre
            TNonblockingServerSocket socket = null;
            TTransportFactory transport = null;
            TProtocolFactory protocol = null;
            TServer server = null;
            
            
            //Khoi tao cac giao thuc truyen
            socket = new TNonblockingServerSocket(port);
            transport = new TFramedTransport.Factory();
            protocol = new TBinaryProtocol.Factory();
            
            //Khoi tao tham so
            TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(socket)
                                                    .transportFactory(transport)
                                                    .protocolFactory(protocol)
                                                    .processor(processor);
           
            //Khoi tao server
            server = new TThreadedSelectorServer(args);
            
            System.out.println("Sever starting...");
            //Bat dau server
            server.serve();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
