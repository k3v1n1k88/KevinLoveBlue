/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.ThreadClientPool.ClientFactory;
import static java.lang.Math.log;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.pool.impl.GenericObjectPool;
import static org.apache.log4j.LogMF.log;
import static org.apache.log4j.LogMF.log;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.myService.Service;

public class MyClient {

    /**
     * @param args the command line arguments
     */
    static ThreadClientPool<Service.Client> pool;
    public static final int port = 9091;
    public static final String host = "localhost";

    public static void main(String[] args) {
//        // TODO code application logic here        
//        Runnable myTask = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    perform();
//                } catch (TTransportException ex) {
//                    Logger.getLogger("Problem when open transport...");
//                } catch (TProtocolException ex) {
//                    Logger.getLogger("Problem protocol...");
//                } catch (TApplicationException ex) {
//                    System.out.println(ex);
//                } catch (TException ex) {
//                    Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        };
//        new Thread(myTask).start();
//        new Thread(myTask).start();
//        new Thread(myTask).start();
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.maxActive = 80;
        poolConfig.minIdle = 5;
        poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
        poolConfig.testOnBorrow = true;
        poolConfig.testWhileIdle = true;
        poolConfig.numTestsPerEvictionRun = 10;
        poolConfig.maxWait = 3000;

        pool = new ThreadClientPool<Service.Client>(
                new ClientFactory<Service.Client>() {
            @Override
            public Service.Client make(TProtocol tProtocol) {
                return new Service.Client(tProtocol);
            }
        }, poolConfig, "localhost", 9090);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 1; i++) {
            executor.submit(new Runnable() {
                public void run() {

                    Service.Client client = pool.getResource();
                    try {
                        for (int i = 0; i < 1; i++) {
                            try {
                                perform();
                                
                            }catch (TApplicationException e) {
                                System.out.println("Error" + e);
                            } catch(TTransportException e){
                                System.out.println("Error when open transport" + e);
                            }catch(TProtocolException e){
                                System.out.println("Error when connect to protocol" + e);
                            }
                        }
                        //pool.returnResource(client);
                    } catch (Exception e) {
                        pool.returnBrokenResource(client);
                    }
                }

            });
        }
    }

//    private static void perform() throws TTransportException, TProtocolException, TException {
//
//        TSocket socket = null;
//        TTransport transport = null;
//        TProtocol protocol = null;
//        try {
//            socket = new TSocket(MyClient.host, MyClient.port);
//            transport = new TFramedTransport(socket);
//            transport.open();
//            protocol = new TBinaryProtocol(transport);
//            Service.Client client = new Service.Client(protocol);
//            System.out.println(client.getSomeThing());
//        } finally {
//            transport.close();
//        }
//
//    }
    private static void perform() throws TException {
        Random random = new Random();
        int num1 = random.nextInt();
        int num2 = random.nextInt();
//        client.diff(num1, num2);
//        client.div(num1, num2);
//        client.add(num1, num2);
//        client.mutiple(num1, num2);
        System.out.println(num1);
        System.out.println(num2);
        int diff;
        int add;

        ExecutorService exeDiff = Executors.newFixedThreadPool(1); 
        exeDiff.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Service.Client client1 = pool.getResource();
                    System.out.println(client1.add(num1, num2));
                } catch (TException ex) {
                    Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });   
        ExecutorService exeAdd = Executors.newFixedThreadPool(1); 
        exeAdd.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Service.Client client2 = pool.getResource();
                    System.out.println(client2.diff(num1,num2));
                } catch (TException ex) {
                    Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
