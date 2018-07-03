package CreateServer;


import CreateServer.Type;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.server.AbstractNonblockingServer;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author root
 */
public class HelperCreateServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperCreateServer.class);

    private Type.Server mServerType;
    private Type.Transport mTransportType;
    private Type.Protocol mProtocolType;

    private TServer mServer;
    private Integer mPort;

    AbstractServerArgs mArgs;
    TProcessor mProcessor;

    public HelperCreateServer(TProcessor processor, Type.Server serverType, Type.Transport transportType, Type.Protocol protocolType, Integer port) throws TApplicationException, TTransportException, TProtocolException {
        this.mServerType = serverType;
        this.mTransportType = transportType;
        this.mProtocolType = protocolType;
        this.mPort = port;
        this.mProcessor = processor;
        initServer();
    }

    private void prepareArgs() throws TApplicationException, TTransportException, TProtocolException {
        TServerTransport transport = new TServerSocket(mPort);
        switch (mServerType) {
            case TSIMPLE:
                mArgs = new TSimpleServer.Args(transport);
                break;
            case TTHREADPOOL:
                mArgs = new TThreadPoolServer.Args(transport);
                break;
            case TTHREADEDSELECTOR:
                mArgs = new TThreadedSelectorServer.Args((TNonblockingServerTransport) transport);
            case TNONBLOCKING:
                mArgs = new AbstractNonblockingServer.Args((TNonblockingServerTransport) transport);
                break;
            default:
                throw new TApplicationException("Type of server is not support yet");
        }
        this.createTransport();
        this.createProtocol();
        mArgs.processor(mProcessor);
    }

    private void createTransport() throws TTransportException {
        switch (mTransportType) {
            case TFASTFRAMED:
                mArgs.transportFactory(new TFastFramedTransport.Factory());
                break;
            case TFRAMED:
                mArgs.transportFactory(new TFramedTransport.Factory());
                break;
            case TSOCKET:
                break;     
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
        LOGGER.info("Created type of transport: "+mTransportType.toString());
    }

    private void createProtocol() throws TProtocolException {
        switch (mProtocolType) {
            case TBINARY:
                mArgs.protocolFactory(new TBinaryProtocol.Factory());
                break;
            case TCOMPACT:
                mArgs.protocolFactory(new TCompactProtocol.Factory());
                break;
            case TJSON:
                mArgs.protocolFactory(new TJSONProtocol.Factory());
                break;
            case TSIMPLEJSON:
                mArgs.protocolFactory(new TSimpleJSONProtocol.Factory());
                break;
            default:
                LOGGER.error("Create protocol failed because type of protocol "+mProtocolType.toString()+" not support now");
                throw new TProtocolException("Type of protocol is not support");
        }
        LOGGER.info("Created type of protocol: "+mProtocolType.toString());
    }

    public TServer getServer() throws TApplicationException {
        return mServer;
    }

    private void initServer() throws TApplicationException, TTransportException, TProtocolException {
        this.prepareArgs();
        switch (mServerType) {
            case TSIMPLE:
                mServer = new TSimpleServer(mArgs);
                break;
            case TTHREADPOOL:
                mServer = new TThreadPoolServer((TThreadPoolServer.Args) mArgs);
                break;
            case THSHA:
                mServer = new THsHaServer((THsHaServer.Args) mArgs);
                break;
            case TNONBLOCKING:
                mServer = new TNonblockingServer((AbstractNonblockingServer.AbstractNonblockingServerArgs) mArgs);
                break;
            default:
                LOGGER.error("Create protocol failed because type of transport "+mProtocolType.toString()+" not support now");
                throw new TApplicationException("Type of server is not support");
        }
    }
}
