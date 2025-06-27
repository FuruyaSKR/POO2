package SQLFramework.DatabaseController;

import SQLFramework.InjectionDependency.*;

public class Field {
    private String name;
    private String type;
    private boolean primaryKey;
    private ForeignKey foreignKey;

    public Field(String name, String type, boolean primaryKey) {
        this.name = name;
        this.type = type;
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public ForeignKey getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ForeignKey foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String toSQL(SQLDialect dialect) {
        String sql = dialect.fieldDefinition(this);
        if (primaryKey) {
            sql += " PRIMARY KEY";
        }
        if (foreignKey != null) {
            sql += ", " + foreignKey.toSQL(dialect);
        }
        return sql;
    }
}
