package com.uhuiapp.harness.utils.db;

import com.uhuiapp.harness.utils.QAContext;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by zhaoxiong on 2018/1/29.
 */
public class DatabaseConnectionFactory {
    private static final String driverClassName = QAContext.qAconfig.getDatabaseDriver();
    private static final String uri = QAContext.qAconfig.getDatabaseUri();
    private static final String user = QAContext.qAconfig.getDatabaseUser();
    private static final String password = QAContext.qAconfig.getDatabasePassword();

    public static Connection getDatabaseConnection() throws Exception{

        // 1. Register the Driver to the jbdc.driver java property
        PoolConnectionFactory.registerJDBCDriver(driverClassName);

        // 2. Create the Connection Factory (DriverManagerConnectionFactory)
        ConnectionFactory connectionFactory = PoolConnectionFactory.getConnFactory(uri, user, password);

        // 3. Instantiate the Factory of Pooled Objects
        PoolableConnectionFactory poolfactory = new PoolableConnectionFactory(connectionFactory, null);

        // 4. Create the Pool with the PoolableConnection objects
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolfactory);

        // 5. Set the objectPool to enforces the association (prevent bugs)
        poolfactory.setPool(connectionPool);

        // 6. Get the Driver of the pool and register them
        PoolingDriver dbcpDriver = PoolConnectionFactory.getDBCPDriver();
        dbcpDriver.registerPool("dbcp-harness", connectionPool);

        return  DriverManager.getConnection("jdbc:apache:commons:dbcp:dbcp-harness");
    }
}
