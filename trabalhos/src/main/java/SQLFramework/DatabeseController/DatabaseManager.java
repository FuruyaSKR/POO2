package SQLFramework.DatabeseController;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import SQLFramework.InjectionDependency.*;

public class DatabaseManager {
    private IConnection connection;

    public void setConnection(IConnection conn) {
        this.connection = conn;
    }

    public Database createDatabase(String name) {
        return new Database(name);
    }

    public void dropDatabase(String name) {
        String sql = "DROP DATABASE " + name;
        executeScript(sql);
    }

    public void addTable(Database db, Table table) {
        db.addTable(table);
    }

    public void dropTable(Database db, String name) {
        db.removeTable(name);
    }

    public void addField(Table tbl, Field field) {
        tbl.addField(field);
    }

    public void dropField(Table tbl, String name) {
        tbl.removeField(name);
    }

    public String generateScript(Database db) {
        SQLDialect dialect = connection.getDialect();
        StringBuilder sb = new StringBuilder();
        sb.append(dialect.createDatabaseSQL(db)).append(";");
        for (Table t : db.getTables()) {
            sb.append("\n").append(dialect.createTableSQL(t)).append(";");
        }
        return sb.toString();
    }

    public void executeScript(String script) {
        Connection conn = connection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            for (String sql : script.split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql.trim());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}