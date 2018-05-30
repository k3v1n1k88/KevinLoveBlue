/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientteslog;

import java.io.IOException;
import java.util.Scanner;
import k3v1n1k88.TestLogScribe.Item;
import k3v1n1k88.TestLogScribe.LogTest;
import k3v1n1k88.TestLogScribe.Result;
import k3v1n1k88.TestLogScribe.Status;
import k3v1n1k88.TestLogScribe.TPlayer;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author root
 */
public class ClientLogTest {
    static final Integer port = 9090;
    static final String host = "localhost";

    private static void play(LogTest.Client client) throws TException {
        TPlayer player = new TPlayer();
        Scanner inp = new Scanner(System.in);
        System.out.print("Nhap ID: ");
        player.setPlayerID(inp.nextInt());
        player.setCurrentItem(Item.BUA);
        player.setCurrentStatus(Status.Login);
        client.init(player);
        while(true){
            System.out.println("Nhap item: ");
            System.out.println("1. Bua");
            System.out.println("2. Sung");
            System.out.println("3. Gay");
            System.out.println("4. Lay thong tin nhan vat");
            System.out.println("5. Quit");
            int select = inp.nextInt();
            if(select == 4){
                System.out.println(client.getStatusPlayer().toString());
            }
            if(select == 5){
                System.out.println(client.logOut(player.getPlayerID()));
                break;
            }
            System.out.println(client.changeItem(convertIntToItem(select)));
        }
    }

    private static Item convertIntToItem(int itemSelect) {
        switch(itemSelect){
            case 1:
                return Item.BUA;
            case 2:
                return Item.SUNG;
            case 3:
                return Item.GAY;
            default:
                return Item.BUA;
        }
    }
    
    public enum TransportType{
        TSOCKET,
        TFRAMED,
        TFILE,
        TMEMORY,
        TZLIB,
        TNONBLOCKING,
    }
    
    public enum ProtocolType{
        TBinary,
        TCompact,
        TDense,
        TJSON,
        TSIMPLEJSON,
        TDEBUG
    }   

    private ClientLogTest(){}
//    public ClienLogTest(TT){
//        
//    }
    public static void main(String[] args) {
        TTransport transport;
        TProtocol protocol;
        TPlayer player = new TPlayer(001,Item.BUA,Status.Login);
        try {
//            TTransport socket = new TSocket(host,port.intValue());
//            transport = new TFramedTransport(new TSocket(host,port.intValue()));
            transport = new TSocket("localhost",9090);
            transport.open();
            protocol = new TBinaryProtocol(transport);
            LogTest.Client client = new LogTest.Client(protocol);
            play(client);
            transport.close();
     
//            transport = new TSocket("localhost", 9090);
//            transport.open();
//
//            protocol = new TBinaryProtocol(transport);
//            LogTest.Client client = new LogTest.Client(protocol);
//
//
//            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
    static LogTest.Client getClient(TransportType transportType,ProtocolType protocolType) throws IOException, TTransportException, TProtocolException{
        LogTest.Client  client = null;
        TTransport transport = null;
        TProtocol protocol = null;
        TTransport socket = new TSocket(host,port);
        switch(transportType){
            case TSOCKET:
                transport = socket;
                break;
            case TFRAMED:
                transport = new TFramedTransport(socket);
                break;
            case TFILE:
                //Do do do do do
                break;
            case TMEMORY:
                //Alo ha
                break;
            case TZLIB:
                //Nothing here
                break;
            case TNONBLOCKING:
                //Maybe die
                transport = new TNonblockingSocket(host,port);
                break;
            default:
                throw new TTransportException("Transport type is not support");
        }
        transport.open();
        switch(protocolType){
            case TBinary:
                protocol = new TBinaryProtocol(transport);
                break;
            case TCompact:
                protocol = new TCompactProtocol(transport);
                break;
            case TDense:
                //anything here
                break;
            case TJSON:
                //anything here
                break;
            case TSIMPLEJSON:
                //i dont know write here what
                break;
            case TDEBUG:
                //lazy boy
                break;
            default:
                throw new TProtocolException("Protocol is not support");
        }
        client = new LogTest.Client(protocol);
        return client;
    }
}
