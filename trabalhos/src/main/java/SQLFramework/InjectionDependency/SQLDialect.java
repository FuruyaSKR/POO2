package SQLFramework.InjectionDependency;

import SQLFramework.DatabeseController.*;

public interface SQLDialect {
    String createDatabaseSQL(Database db);

    String createTableSQL(Table table);

    String fieldDefinition(Field field);

    String foreignKeyDefinition(ForeignKey fk);
}