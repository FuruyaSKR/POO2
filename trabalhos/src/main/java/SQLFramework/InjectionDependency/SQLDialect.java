package SQLFramework.InjectionDependency;

import SQLFramework.DatabaseController.*;

public interface SQLDialect {
    String createDatabaseSQL(Database db);

    String createTableSQL(Table table);

    String fieldDefinition(Field field);

    String foreignKeyDefinition(ForeignKey fk);
}