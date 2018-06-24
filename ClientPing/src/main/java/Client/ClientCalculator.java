package Client;


import CreateClient.HelperCreateClient;
import CreateClient.Type;
import CreateClient.Type.Operation;
import Service2.CalculatorException;
import java.util.Random;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class for support for actions execute from user And also support for
 * collecting data to support for analyzing some info
 *
 * @version 1.0
 * @author k3v1n1k88
 * @since 10-6-2018
 */
public class ClientCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCalculator.class);
    
    static int numsOfSend = 1;
    static int numsOfGet = 0;

    Service2.Calculator.Client mClient = null;
    TTransport mTransport = null;

    Type.Transport typeTransport = Type.Transport.TFRAMED;
    Type.Protocol typeProtocol = Type.Protocol.TBINARY;
    
    Class<? extends TServiceClient> typeService = Service2.Calculator.Client.class;
    String host = "localhost";
    int port = 9090;

    /**
     * Constructor Initialize client with type of components provides in this
     * class I don not think
     *
     * @throws TTransportException
     * @throws TProtocolException
     */
    public ClientCalculator() throws TTransportException, TProtocolException {
        HelperCreateClient helperClient = new HelperCreateClient(typeService,
                typeTransport,
                typeProtocol,
                host,
                port);
        mClient = (Service2.Calculator.Client) helperClient.getClient();
        mTransport = helperClient.getTransport();
    }

    /**
     * Support for adding two number
     *
     * @param num1
     * @param num2
     * @return result of adding
     * @throws TException when having error when execute calling client failure
     */
    public int add(int num1, int num2) throws TException {
        ClientCalculator.numsOfSend++;
        return mClient.add(num1, num2);
    }

    /**
     * Support for dividing two number
     *
     * @param num1
     * @param num2
     * @return result of dividing
     * @throws TException when having error when execute calling client failure
     */
    public int div(int num1, int num2) throws TException {
        ClientCalculator.numsOfSend++;
        return mClient.div(num1, num2);
    }

    /**
     * Support for subtract two number
     *
     * @param num1
     * @param num2
     * @return result of subtract
     * @throws TException when having error when execute calling client failure
     */
    public int subtract(int num1, int num2) throws TException {
        ClientCalculator.numsOfSend++;
        return mClient.subtract(num1, num2);
    }

    /**
     * Support for multiple two number
     *
     * @param num1
     * @param num2
     * @return result of multipling
     * @throws TException when having error when execute calling client failure
     */
    public int multiple(int num1, int num2) throws TException {
        try {
            ClientCalculator.numsOfSend++;
            return mClient.mutiple(num1, num2);
        } catch (CalculatorException ex) {
            throw new CalculatorException(ex);
        }
    }

    /**
     * Get numbers of sending message to server
     *
     * @return numbers of sending message to server
     */
    synchronized public static int getNumsOfSend() {
        return ClientCalculator.numsOfSend;
    }
    synchronized public static void setNumsOfSend(int num) {
        ClientCalculator.numsOfSend = num;
    }
    synchronized public static void increaseNumsOfSend(){
        ClientCalculator.numsOfSend++;
    }
    synchronized public static int getNumsOfGet() {
        return ClientCalculator.numsOfGet;
    }
    synchronized public static void setNumsOfGet(int num) {
        ClientCalculator.numsOfGet = num;
    }
    synchronized public static void increaseNumsOfGet(){
        ClientCalculator.numsOfGet++;
    }
    
    /**
     * Disconnect to server
     *
     * @throws TTransportException when transport null
     */
    public void disconnect() throws TTransportException {
        if (mTransport != null) {
            mTransport.close();
        } else {
            throw new TTransportException("Transpor init is not success");
        }
    }

    /**
     * Start communicating to server
     *
     * @throws TTransportException if transport null
     */
    public void start() throws TTransportException {
        try {
            checkTransport();
            LOGGER.info("Starting try connect to server...");
            mTransport.open();
            LOGGER.info("Connected to server");
        } catch (TTransportException ex) {
            throw new TTransportException("Connection failure");
        }
    }

    /**
     * Check transport
     *
     * @throws TTransportException
     */
    private void checkTransport() throws TTransportException {
        if (mTransport == null) {
            throw new TTransportException("Transpor init is not success");
        }
    }
}
