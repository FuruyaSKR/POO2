package SQLFramework;

public class ForeignKey {
    private String column;
    private Table referencedTable;
    private String referencedColumn;

    public ForeignKey(String column, Table referencedTable, String referencedColumn) {
        this.column = column;
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
    }

    public String toSQL() {
        return String.format("FOREIGN KEY(%s) REFERENCES %s(%s)", column, referencedTable.getName(), referencedColumn);
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Table getReferencedTable() {
        return referencedTable;
    }

    public void setReferencedTable(Table referencedTable) {
        this.referencedTable = referencedTable;
    }

    public String getReferencedColumn() {
        return referencedColumn;
    }

    public void setReferencedColumn(String referencedColumn) {
        this.referencedColumn = referencedColumn;
    }
}