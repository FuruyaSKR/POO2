package SQLFramework;

import java.sql.Connection;

import SQLFramework.DatabeseController.*;
import SQLFramework.InjectionDependency.*;
import SQLFramework.InjectionDependency.MySQL.MySQLDialect;

public class Main {
    public static void main(String[] args) {
        IConnection conn = new IConnection() {
            @Override
            public Connection getConnection() {
                return null; // stub JDBC
            }

            @Override
            public SQLDialect getDialect() {
                return new MySQLDialect();
            }

            @Override
            public void setConfig(IConnection config) {
                // no-op
            }
        };

        DatabaseManager manager = new DatabaseManager();
        manager.setConnection(conn);

        Database db = manager.createDatabase("TemplateDB");

        // Tabela simples Users
        Table users = new Table("Users");
        users.addField(new Field("id", "INT", true));
        users.addField(new Field("name", "VARCHAR(255)", false));
        manager.addTable(db, users);

        // Tabela simples Profiles
        Table profiles = new Table("Profiles");
        profiles.addField(new Field("id", "INT", true));
        profiles.addField(new Field("description", "VARCHAR(100)", false));
        manager.addTable(db, profiles);

        // Tabela associativa UserProfile
        Table userProfile = new Table("UserProfile");
        Field userId = new Field("user_id", "INT", true);
        userId.setForeignKey(new ForeignKey("user_id", "Users", "id"));
        Field profileId = new Field("profile_id", "INT", true);
        profileId.setForeignKey(new ForeignKey("profile_id", "Profiles", "id"));
        userProfile.addField(userId);
        userProfile.addField(profileId);
        manager.addTable(db, userProfile);

        // Gerar e exibir script completo
        String script = manager.generateScript(db);
        System.out.println(script);
    }
}
