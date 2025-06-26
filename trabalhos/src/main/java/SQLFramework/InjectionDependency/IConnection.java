package SQLFramework.InjectionDependency;

import java.sql.Connection;

public interface IConnection {
    Connection getConnection();

    SQLDialect getDialect();

    void setConfig(IConnection config);
}
