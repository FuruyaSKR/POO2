package SQLFramework.InjectionDependency.MySQL;

import java.util.List;
import java.util.stream.Collectors;

import SQLFramework.DatabeseController.*;
import SQLFramework.InjectionDependency.SQLDialect;

public class MySQLDialect implements SQLDialect {
    @Override
    public String createDatabaseSQL(Database db) {
        return "CREATE DATABASE IF NOT EXISTS " + db.getName();
    }

    @Override
    public String createTableSQL(Table table) {
        List<String> defs = table.getFields().stream()
                .map(f -> f.toSQL(this))
                .collect(Collectors.toList());
        String cols = String.join(", ", defs);
        return String.format("CREATE TABLE %s (%s)", table.getName(), cols);
    }

    @Override
    public String fieldDefinition(Field field) {
        return field.getName() + " " + field.getType();
    }

    @Override
    public String foreignKeyDefinition(ForeignKey fk) {
        return String.format("FOREIGN KEY (%s) REFERENCES %s(%s)",
                fk.getColumn(), fk.getReferencedTable(), fk.getReferencedColumn());
    }
}