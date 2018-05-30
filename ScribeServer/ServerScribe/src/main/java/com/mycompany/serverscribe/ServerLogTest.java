/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverscribe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author root
 */
public class ServerLogTest {

    private static final Log log = LogFactory.getLog(ServerLogTestHandler.class);

    enum ServerType {
        TABSTRACTNONBLOCKING,
        TSIMPLE,
        TTHREADPOOL
    }

    enum ServerTransportType {
        TNONBLOCKING,
        TSERVER
    }

    //define port 
    private static final Integer port1 = 9090;
    private static final Integer port2 = 9091;
    private static final Integer port3 = 9092;

    //server
    private static TServer server1 = null;
    private static TServer server2 = null;
    private static TServer server3 = null;

    //what what what
    private static ServerLogTestHandler handler;
    private static k3v1n1k88.TestLogScribe.LogTest.Processor processor;

    public static void main(String[] args) throws TTransportException {

        //Init processor
        handler = new ServerLogTestHandler();
        processor = new k3v1n1k88.TestLogScribe.LogTest.Processor(handler);

        //Get server
        try {
            server1 = createServer(ServerType.TTHREADPOOL, ServerTransportType.TSERVER, processor, port1);
//            server2 = createServer(ServerType.TSIMPLE, ServerTransportType.TSERVER, processor, port2);
//            server3 = createServer(ServerType.TSIMPLE, ServerTransportType.TSERVER, processor, port3);
            System.out.println("Server listening");
            server1.serve();
//            server2.serve();
//            server3.serve();
//            TServerTransport serverTransport = new TServerSocket(9090);
//            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
//
//            System.out.println("Starting the simple server...");
//            server.serve();
        } catch (TTransportException eTransport) {
            log.error("Cannot open tranpost");
        }
    }

    public static TServer createServer(ServerType typeServer,
            ServerTransportType typeTransport,
            k3v1n1k88.TestLogScribe.LogTest.Processor processor,
            Integer port) throws TTransportException {

        TServerSocket socket = new TServerSocket(port);
        TServerTransport transport = null;
        TServer server = null;

        switch (typeTransport) {
            case TSERVER:
                transport = socket;
                break;
            case TNONBLOCKING:
                //Do somgthing blabla
                break;
            default:
                throw new TTransportException("This type of transport is not support now");
        }
        switch (typeServer) {
            case TABSTRACTNONBLOCKING:
                //Not avaible, you can write anything here
                break;
            case TSIMPLE:
                server = new TSimpleServer(new TServer.Args(transport).processor(processor));
                break;
            case TTHREADPOOL:
                server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
                break;
        }
        return server;
    }

}
