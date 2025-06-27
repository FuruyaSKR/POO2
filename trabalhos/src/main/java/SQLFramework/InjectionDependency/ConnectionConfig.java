package SQLFramework.InjectionDependency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig implements IConnection {
    private String host;
    private int port;
    private String user;
    private String pass;
    private SQLDialect dialect;
    private DbType dbType;
    private String databaseName; // novo campo

    public ConnectionConfig(String host, int port, String user, String pass,
            SQLDialect dialect, DbType dbType, String databaseName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.dialect = dialect;
        this.dbType = dbType;
        this.databaseName = databaseName;
    }

    @Override
    public Connection getConnection() {
        String url;
        if (dbType == DbType.POSTGRES) {
            url = String.format(
                    "jdbc:postgresql://%s:%d/%s?ssl=false",
                    host, port, databaseName);
        } else {
            url = String.format(
                    "jdbc:mysql://%s:%d/%s?serverTimezone=UTC&useSSL=false",
                    host, port, databaseName);
        }
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SQLDialect getDialect() {
        return dialect;
    }

    @Override
    public void setConfig(IConnection config) {
        if (config instanceof ConnectionConfig) {
            ConnectionConfig cfg = (ConnectionConfig) config;
            this.host = cfg.host;
            this.port = cfg.port;
            this.user = cfg.user;
            this.pass = cfg.pass;
            this.dialect = cfg.dialect;
            this.dbType = cfg.dbType;
        }
    }
}