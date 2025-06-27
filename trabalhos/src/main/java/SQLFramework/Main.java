package SQLFramework;

import SQLFramework.DatabaseController.*;
import SQLFramework.InjectionDependency.*;
import SQLFramework.InjectionDependency.MySQL.MySQLDialect;
import SQLFramework.InjectionDependency.PostGresql.PostgreSQLDialect;

public class Main {
    public static void main(String[] args) {
        DbType dbType = DbType.MYSQL; // POSTGRES ou MYSQL
        String host = "localhost";
        int port = dbType == DbType.POSTGRES ? 5432 : 3306;
        String user = dbType == DbType.POSTGRES ? "postgres" : "root";
        String pass = "root123";

        String defaultDb = dbType == DbType.POSTGRES ? "postgres" : "";
        SQLDialect dialect = dbType == DbType.POSTGRES
                ? new PostgreSQLDialect()
                : new MySQLDialect();

        ConnectionConfig cfg = new ConnectionConfig(
                host, port, user, pass,
                dialect, dbType,
                defaultDb);
        DatabaseManager manager = new DatabaseManager();
        manager.setConnection(cfg);

        // Cria a base das tabelas
        Database db = buildTemplateDatabase(manager);
        String fullScript = manager.generateScript(db);
        System.out.println(fullScript);

        String[] parts = fullScript.split(";", 2);

        try {
            manager.executeScript(parts[0] + ";");
        } catch (Exception e) {
            System.out.println("Aviso: banco pode já existir → " + e.getMessage());
        }

        String targetDb = dbType == DbType.POSTGRES
                ? db.getName().toLowerCase()
                : db.getName();

        cfg.setConfig(new ConnectionConfig(
                host, port, user, pass,
                dialect, dbType,
                targetDb));
        manager.setConnection(cfg);

        if (parts.length > 1) {
            manager.executeScript(parts[1] + ";");
        }
        System.out.println("Banco '" + db.getName() + "' criado em " + dbType);

        // Insere dados de teste
        String insertScript = ""
                + "INSERT INTO Users (id, name) VALUES (1, 'Alice');"
                + "INSERT INTO Users (id, name) VALUES (2, 'Bob');"
                + "INSERT INTO Profiles (id, description) VALUES (1, 'Admin');"
                + "INSERT INTO Profiles (id, description) VALUES (2, 'User');"
                + "INSERT INTO UserProfile (user_id, profile_id, user_name, profile_description) "
                + "VALUES (1, 1, 'Alice', 'Admin');"
                + "INSERT INTO UserProfile (user_id, profile_id, user_name, profile_description) "
                + "VALUES (2, 2, 'Bob', 'User');";
        manager.executeScript(insertScript);
        System.out.println("Dados de teste inseridos com sucesso!");
    }

    private static Database buildTemplateDatabase(DatabaseManager manager) {
        Database db = manager.createDatabase("templatebd");

        Table users = new Table("Users");
        users.addField(new Field("id", "INT", true));
        users.addField(new Field("name", "VARCHAR(255)", false));
        manager.addTable(db, users);

        Table profiles = new Table("Profiles");
        profiles.addField(new Field("id", "INT", true));
        profiles.addField(new Field("description", "VARCHAR(100)", false));
        manager.addTable(db, profiles);

        Table userProfile = new Table("UserProfile");
        Field userId = new Field("user_id", "INT", true);
        userId.setForeignKey(new ForeignKey("user_id", "Users", "id"));
        Field profileId = new Field("profile_id", "INT", true);
        profileId.setForeignKey(new ForeignKey("profile_id", "Profiles", "id"));
        Field userName = new Field("user_name", "VARCHAR(255)", false);
        Field profileDesc = new Field("profile_description", "VARCHAR(100)", false);
        userProfile.addField(userId);
        userProfile.addField(profileId);
        userProfile.addField(userName);
        userProfile.addField(profileDesc);
        manager.addTable(db, userProfile);

        return db;
    }
}
