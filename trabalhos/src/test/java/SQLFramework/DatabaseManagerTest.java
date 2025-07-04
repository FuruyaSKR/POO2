package SQLFramework;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import SQLFramework.DatabaseController.*;
import SQLFramework.InjectionDependency.*;
import SQLFramework.InjectionDependency.MySQL.MySQLDialect;

class DatabaseManagerTest {

    @Test
    void verifyEqualsSQLScript() {
        DbType dbType = DbType.MYSQL;
        SQLDialect dialect = new MySQLDialect();
        ConnectionConfig cfg = new ConnectionConfig(
                "localhost", 3306, "root", "root123",
                dialect, dbType, "");
        DatabaseManager manager = new DatabaseManager();
        manager.setConnection(cfg);

        Database db = buildTemplateDatabase(manager);
        String fullScript = manager.generateScript(db);

        String expectedScript = """
                CREATE DATABASE IF NOT EXISTS templatebd;
                CREATE TABLE Users (id INT, name VARCHAR(255), PRIMARY KEY(id));""";

        assertEquals(expectedScript, fullScript);
    }

    private static Database buildTemplateDatabase(DatabaseManager manager) {
        Database db = manager.createDatabase("templatebd");

        Table users = new Table("Users");
        users.addField(new Field("id", "INT", true));
        users.addField(new Field("name", "VARCHAR(255)", false));
        manager.addTable(db, users);
        return db;
    }
}