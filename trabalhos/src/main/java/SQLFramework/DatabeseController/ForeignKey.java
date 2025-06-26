package SQLFramework.DatabeseController;

import SQLFramework.InjectionDependency.*;

public class ForeignKey {
    private String column;
    private String referencedTable;
    private String referencedColumn;

    public ForeignKey(String column, String referencedTable, String referencedColumn) {
        this.column = column;
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
    }

    public String toSQL(SQLDialect dialect) {
        return dialect.foreignKeyDefinition(this);
    }

    public String getColumn() {
        return column;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public String getReferencedColumn() {
        return referencedColumn;
    }
}
