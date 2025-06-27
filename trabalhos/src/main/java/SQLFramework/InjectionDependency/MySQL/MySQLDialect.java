package SQLFramework.InjectionDependency.MySQL;

import java.util.ArrayList;
import java.util.List;

import SQLFramework.DatabaseController.*;
import SQLFramework.InjectionDependency.SQLDialect;

public class MySQLDialect implements SQLDialect {
    @Override
    public String createDatabaseSQL(Database db) {
        return "CREATE DATABASE IF NOT EXISTS " + db.getName();
    }

    @Override
    public String createTableSQL(Table table) {
        List<String> defs = new ArrayList<>();
        List<String> pkCols = new ArrayList<>();

        for (Field f : table.getFields()) {
            defs.add(fieldDefinition(f));

            ForeignKey fk = f.getForeignKey();
            if (fk != null) {
                defs.add(foreignKeyDefinition(fk));
            }

            if (f.isPrimaryKey()) {
                pkCols.add(f.getName());
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(table.getName()).append(" (");
        sb.append(String.join(", ", defs));

        if (!pkCols.isEmpty()) {
            sb.append(", PRIMARY KEY(")
                    .append(String.join(", ", pkCols))
                    .append(")");
        }

        sb.append(")");
        return sb.toString();
    }

    @Override
    public String fieldDefinition(Field field) {
        return field.getName() + " " + field.getType();
    }

    @Override
    public String foreignKeyDefinition(ForeignKey fk) {
        return String.format(
                "FOREIGN KEY (%s) REFERENCES %s(%s)",
                fk.getColumn(), fk.getReferencedTable(), fk.getReferencedColumn());
    }
}