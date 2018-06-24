package CreateClient;


import MyLogger.LoggingWrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class support for creating client with type of transport, protocol, type service given  
 * 
 * @author k3v1n1k88
 * @version 1.0
 * @since 10.6.2018
 */
public class HelperCreateClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperCreateClient.class);
    private TProtocol mProtocol = null;
    private TTransport mTransport = null;

    private final Type.Protocol mProtocolType;
    private final Type.Transport mTransportType;

    private final String mHostAddress;
    private final Integer mPortHost;

    private final Integer mTimeout;

    private TServiceClient mClient = null;
    Class<? extends TServiceClient> mService;

    /**
     * Constructor
     * @param typeClass Type of Service class you built from thrift
     * @param transportType Type of transport you want create (it's defined in Type class)
     * @param protocolType Type of protocol you want create (it's defined in Type class)
     * @param host Host address you wanna connect
     * @param port Port of host you wanna connect
    */
    public HelperCreateClient(Class<? extends TServiceClient> typeClass,
            Type.Transport transportType,
            Type.Protocol protocolType,
            String host,
            Integer port){
        mTransportType = transportType;
        mProtocolType = protocolType;
        mHostAddress = host;
        mPortHost = port;
        mTimeout = 0;
        mService = typeClass;
    }
    /**
     * Constructor
     * @param typeClass Type of Service class you built from thrift
     * @param transportType Type of transport you want create (it's defined in Type class)
     * @param protocolType Type of protocol you want create (it's defined in Type class)
     * @param host Host address you wanna connect
     * @param port Port of host you wanna connect
     * @param timeout Connection time out
    */
    public HelperCreateClient(Class<? extends TServiceClient> typeClass,
                              Type.Transport transportType,
                              Type.Protocol protocolType,
                              String host,
                              Integer port,
                              Integer timeout){
        mTransportType = transportType;
        mProtocolType = protocolType;
        mHostAddress = host;
        mPortHost = port;
        mTimeout = timeout;
    }
    
    /**
     * This function return client for callable class
     * @return TServiceClient
     * @throws TTransportException
     * @throws TProtocolException 
     */
    public TServiceClient getClient() throws TTransportException, TProtocolException {
        initClient();
        return mClient;
    }

    /**
     * This function support for initializing client
     * @throws TTransportException
     * @throws TProtocolException 
     */
    private void initClient() throws TTransportException, TProtocolException {
        this.createTransport();
        this.createProtocol();
        try {
            try {
                Constructor<? extends TServiceClient> constructor = mService.getConstructor(TProtocol.class);
                mClient = constructor.newInstance(mProtocol);
                LOGGER.info("Init client success");
                //MyLogger.LoggingWrapper.info(this.getClass(), "Init client success");
                
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOGGER.error("Error when create new instance: ", ex);
                //MyLogger.LoggingWrapper.info(this.getClass(), "Init client success");
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            LOGGER.error("Error when create new instance: ", ex);
        }
    }

    /**
     * Create transport according by type of transport provided when initialize
     * @throws TTransportException 
     */
    private void createTransport() throws TTransportException {
        TSocket transport = new TSocket(mHostAddress, mPortHost);
        switch (mTransportType) {
            case TFASTFRAMED:
                mTransport = new TFastFramedTransport.Factory().getTransport(transport);
                break;
            case TFRAMED:
                mTransport = new TFramedTransport.Factory().getTransport(transport);
                break;
            case TSOCKET:
                mTransport = transport;
                break;
//                
//            case TMEMORYBUFFER:
//
//                break;
//            case THTTPCLIEN:
//
//                break;
//                
//            case TFILE:
//                    
//                break;
            default:
                throw new TTransportException("This transport type is not support");
        }
    }

    /**
     * Create protocol according by type of protocol provided when initialize
     * @throws TTransportException
     * @throws TProtocolException 
     */
    private void createProtocol() throws TTransportException, TProtocolException {
        //checkTransportOpen();
        switch (mProtocolType) {
            case TBINARY:
                mProtocol = new TBinaryProtocol.Factory().getProtocol(mTransport);
                break;
            case TCOMPACT:
                mProtocol = new TCompactProtocol.Factory().getProtocol(mTransport);
                break;
            case TJSON:
                mProtocol = new TJSONProtocol.Factory().getProtocol(mTransport);
                break;
//            case TSIMPLEJSON:
//                break;
            default:
                LOGGER.error("Select type of protocol is not support");
                throw new TProtocolException("This type of protocol is not support");
        }
    }
    /**
     * This function return transport for callable class
     * This goal is for callable class can open or close by self
     * @return 
     */
    public TTransport getTransport(){
        return mTransport;
    }
}
