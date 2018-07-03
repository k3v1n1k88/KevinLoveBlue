

import CreateServer.HelperCreateServer;
import CreateServer.Type;
import Service2.Calculator;
import Service2.CalculatorException;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.server.TServer;
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
public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static int requestFromClient = 0;
    TServer mServer = null;
    
    CalculatorServiceHandler handler = new CalculatorServiceHandler();
    Type.Server mTypeServer = Type.Server.TNONBLOCKING;
    Type.Transport mTypeTransport = Type.Transport.TFRAMED;
    Type.Protocol mTypeProtocol = Type.Protocol.TBINARY;
    int mPort = 9090;

    public Server(int port){
        mPort = port;
    }
    public Server(Type.Server typeServer,Type.Transport typeTransport,Type.Protocol typeProtocol,int port) throws TApplicationException, TTransportException, TProtocolException {
        mTypeServer = typeServer;
        mTypeProtocol = typeProtocol;
        mTypeTransport = typeTransport;
        mPort = port;
    }

    public TServer getServer() {
        return mServer;
    }

    public void beginServer() throws TTransportException, TApplicationException, TProtocolException{
        Service2.Calculator.Processor processor = new Service2.Calculator.Processor(handler);
        HelperCreateServer helper = new HelperCreateServer( processor,
                                                            mTypeServer,
                                                            mTypeTransport,
                                                            mTypeProtocol,
                                                            mPort);
        mServer = helper.getServer();
        if (mServer == null) {   
            LOGGER.error("Creating server failed");
            throw new TApplicationException("Server is not created");
        }
        System.out.println("Server starting...");
        LOGGER.info("Server starting...");
        mServer.serve(); 
    }

    /**
     * This class implement services we provide
    */
    private class CalculatorServiceHandler implements Calculator.Iface {

        @Override
        public int add(int num1, int num2) throws TException {
            increaseNumsOfRequest();
            System.out.println("add");
            //LOGGER.info("Perform adding");
            int result = 0;
            try {
                result = Math.addExact(num1, num2);
            } catch (Exception e) {
                CalculatorException myex = new CalculatorException();
                myex.what = e.toString();
                myex.what="Adding error";
                myex.err = Service2.Error.OVERFLOW;
                LOGGER.error("Error when calculate: overflow");
                throw myex;
                //throw new CalculatorException();
            }
            return result;
        }

        @Override
        public int subtract(int num1, int num2) throws TException{
            increaseNumsOfRequest();
            System.out.println("sub");
            Server.requestFromClient++;
            //LOGGER.info("Perform subtracting");
            int result = 0;
            try {
                result = Math.subtractExact(num1, num2);
            } catch (Exception e) {
                CalculatorException myex = new CalculatorException();
                myex.what = e.toString();
                myex.err = Service2.Error.UNKNOWN;
                throw myex;
//                throw new CalculatorException();
            }
            return result;
        }

        @Override
        public int div(int num1, int num2) throws TException {
            increaseNumsOfRequest();
            //LOGGER.info("Perform dividing");
            System.out.println("div");
            Server.requestFromClient++;
            int result = 0;
            try {
                result = Math.floorDiv(num1, num2);
            } catch (Exception e) {
                CalculatorException myex = new CalculatorException();
                myex.what = e.toString();
                myex.err = Service2.Error.DEVIDE_BY_ZERO;
                LOGGER.error("Error when calculate: devide by zero");
                throw myex;
//                throw new CalculatorException();
            }
            
            return result;
        }

        @Override
        public int mutiple(int num1, int num2) throws TException{
            increaseNumsOfRequest();
            //LOGGER.info("Perform mutiple");
            System.out.println("add");
            int result = 0;
            try {
                result = Math.multiplyExact(num1, num2);
            } catch (Exception e) {
                CalculatorException myex = new CalculatorException();
                myex.what = e.toString();
                myex.err = Service2.Error.OVERFLOW;
                LOGGER.error("Error when calculate: overflow");
                throw myex;
//                throw new CalculatorException();
            }
            return result;
        }

        @Override
        public String ping() throws TException {
            return "pong";
        }
    }
    public static int getNumsOfRequest(){
        return Server.requestFromClient;
    }
    synchronized public static void setNumsOfRequest(int num){
        Server.requestFromClient = num;
    }
    synchronized public static void increaseNumsOfRequest(){
        Server.requestFromClient++;
    }
}
