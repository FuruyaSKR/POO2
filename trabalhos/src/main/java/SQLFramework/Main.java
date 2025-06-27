package SQLFramework;

import SQLFramework.DatabeseController.*;
import SQLFramework.InjectionDependency.*;
import SQLFramework.InjectionDependency.MySQL.MySQLDialect;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado", e);
        }

        // Configuração genérica MySQL
        DbType dbType = DbType.MYSQL;
        String host = "localhost";
        int port = 3306;
        String user = "root";
        String pass = "root123";
        SQLDialect dialect = new MySQLDialect();

        ConnectionConfig cfg = new ConnectionConfig(
                host, port, user, pass, dialect, dbType, "");
        DatabaseManager manager = new DatabaseManager();
        manager.setConnection(cfg);

        // Define o Database e as tabelas
        Database db = manager.createDatabase("TemplateDB");

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
        Field profileId = new Field("profile_id", "INT", true);
        Field userName = new Field("user_name", "VARCHAR(255)", false);
        Field profileDesc = new Field("profile_description", "VARCHAR(100)", false);

        userId.setForeignKey(new ForeignKey("user_id", "Users", "id"));
        profileId.setForeignKey(new ForeignKey("profile_id", "Profiles", "id"));

        userProfile.addField(userId);
        userProfile.addField(profileId);
        userProfile.addField(userName);
        userProfile.addField(profileDesc);
        manager.addTable(db, userProfile);

        String fullScript = manager.generateScript(db);
        String[] parts = fullScript.split(";", 2);

        // Cria o database
        manager.executeScript(parts[0] + ";");

        // Reconfigura conexão para usar o novo schema
        cfg = new ConnectionConfig(
                host, port, user, pass, dialect, dbType, db.getName());
        manager.setConnection(cfg);

        if (parts.length > 1) {
            manager.executeScript(parts[1]);
        }

        // Insere dados de teste
        String insertScript = ""
                + "INSERT INTO Users    (id, name)                 VALUES (1, 'Alice');"
                + "INSERT INTO Users    (id, name)                 VALUES (2, 'Bob');"
                + "INSERT INTO Profiles (id, description)          VALUES (1, 'Admin');"
                + "INSERT INTO Profiles (id, description)          VALUES (2, 'User');"
                + "INSERT INTO UserProfile (user_id, profile_id, user_name, profile_description) "
                + "VALUES (1, 1, 'Alice', 'Admin');"
                + "INSERT INTO UserProfile (user_id, profile_id, user_name, profile_description) "
                + "VALUES (2, 2, 'Bob',   'User');";
        manager.executeScript(insertScript);

        System.out.printf(
                "Schema '%s' criado e populado com sucesso em %s!%n",
                db.getName(), dbType);
    }
}
