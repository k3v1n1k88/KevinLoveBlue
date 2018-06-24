/**
 *  				Copyright 2015 Jiang Wei
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package pool.connection;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pool.config.ThriftConnectionPoolConfig.TProtocolType;
import pool.config.ThriftConnectionPoolConfig.TTransportType;
import pool.exception.ThriftConnectionPoolException;

/**
 * 默认实现的thrift客户端对象
 * 
 * @author jiangwei (ydswcy513@gmail.com)
 * @version V1.0
 */
public class DefaultThriftConnection<T extends TServiceClient> implements ThriftConnection<T> {
	private static final Logger logger = LoggerFactory.getLogger(DefaultThriftConnection.class);

	/**
	 * 服务器地址
	 */
	private String host;
	/**
	 * 服务器端口
	 */
	private int port;
	/**
	 * 连接超时时间
	 */
	private int connectionTimeOut;
	/**
	 * thrift管道类型
	 */
	private TProtocolType tProtocolType;

	/**
	 * thrift连接对象
	 */
	private TTransport transport;

        private TTransportType tTransportType;
	/**
	 * thrift客户端对象
	 */
	private T client;

	/**
	 * 客户端类对象
	 */
	private Class<? extends TServiceClient> clientClass;

	public DefaultThriftConnection(String host, int port, int connectionTimeOut, TProtocolType tProtocolType, TTransportType tTransportType,
			Class<? extends TServiceClient> clientClass) throws ThriftConnectionPoolException{
		this.host = host;
		this.port = port;
		this.connectionTimeOut = connectionTimeOut;
		this.tProtocolType = tProtocolType;
		this.clientClass = clientClass;
                this.tTransportType = tTransportType;
		// 创建连接
		createConnection();
	}

	/**
	 * 创建原始连接的方法
	 * 
	 * @throws ThriftConnectionPoolException
	 *             创建连接出现问题时抛出该异常
	 */
	@SuppressWarnings("unchecked")
	private void createConnection() throws ThriftConnectionPoolException {
		try {
                        transport = createTransport();
//			transport = new TSocket(host, port, connectionTimeOut);
			transport.open();
			TProtocol protocol = createTProtocol(transport);
			// 反射实例化客户端对象
			Constructor<? extends TServiceClient> clientConstructor = clientClass.getConstructor(TProtocol.class);
			client = (T) clientConstructor.newInstance(protocol);
			if (logger.isDebugEnabled()) {
				logger.debug("Create new connection successful:" + host + " port：" + port);
			}
		} catch (Exception e) {
			throw new ThriftConnectionPoolException("Cannot connect to server：" + host + " port：" + port);
		}
	}

	/**
	 * 根据配置创建thrift管道的方法
	 * 
	 */
	private TProtocol createTProtocol(TTransport transport) {
            TProtocol protocol = null;
            switch(tProtocolType){
                case BINARY:
                    protocol = new TBinaryProtocol(transport);
                    break;
                case JSON:
                    protocol = new TJSONProtocol(transport);
                    break;
                case COMPACT:
                    protocol = new TSimpleJSONProtocol(transport);
                    break;
                case SIMPLEJSON:
                    protocol = new TCompactProtocol(transport);
                    break;
                default:
                    throw new IllegalStateException("Unsupport pipe type：" + tProtocolType);
            }
            return protocol;
//		if (tProtocolType == TProtocolType.BINARY) {
//			return new TBinaryProtocol(transport);
//		} else if (tProtocolType == TProtocolType.JSON) {
//			return new TJSONProtocol(transport);
//		}
//		throw new IllegalStateException("暂不支持的管道类型：" + tProtocolType);
	}
        private TTransport createTransport(){
            TTransport transport = new TSocket(host,port,connectionTimeOut);
            switch(tTransportType){
                case FASTFRAMED:
                    transport = new TFastFramedTransport(transport);
                    break;
                case FRAMED:
                    transport = new TFramedTransport(transport);
                    break;
                case SOCKET:
                    break;
                default:
                    throw new IllegalStateException("Unsupport type of transport: "+tTransportType);
//                case HTTPCLIEN:
//                    break;
//                case MEMORYBUFFER:
//                    break;
//                case FILE:
//                    break;
                
            }
            return transport;
        }
	/*
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if (transport != null) {
			transport.close();
		}
	}

	/*
	 * @see com.wmz7year.thrift.pool.connection.ThriftConnection#getClient()
	 */
	@Override
	public T getClient() {
		return client;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K extends TServiceClient> K getClient(String serviceName, Class<K> clazz) {
		return (K) getClient();
	}

	/*
	 * @see com.wmz7year.thrift.pool.connection.ThriftConnection#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return !transport.isOpen();
	}

    /*
     * @see com.wmz7year.thrift.pool.connection.ThriftConnection#setAvailable(boolean)
     */
    @Override
    public void setAvailable(boolean available) {
        throw new UnsupportedOperationException();
    }

}
