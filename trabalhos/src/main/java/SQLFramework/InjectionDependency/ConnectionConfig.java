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

    public ConnectionConfig(String host, int port, String user, String pass, SQLDialect dialect) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.dialect = dialect;
    }

    @Override
    public Connection getConnection() {
        String url = String.format("jdbc:mysql://%s:%d/", host, port);
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar ao banco MySQL", e);
        }
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
        }
    }
}