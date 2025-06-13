package SQLFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
    private String name;
    private List<Field> fields = new ArrayList<>();
    private List<String> primaryKeys = new ArrayList<>();
    private List<ForeignKey> foreignKeys = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }

    public void addField(Field f) {
        fields.add(f);
    }

    public void removeField(String name) {
        fields.removeIf(f -> f.getName().equals(name));
    }

    public void setPrimaryKeys(List<String> pks) {
        this.primaryKeys = pks;
    }

    public void addForeignKey(ForeignKey fk) {
        foreignKeys.add(fk);
    }

    public String toSQL() {
        String cols = fields.stream()
                .map(Field::toSQL)
                .collect(Collectors.joining(", "));
        String pk = primaryKeys.isEmpty() ? "" : ", PRIMARY KEY(" + String.join(",", primaryKeys) + ")";
        String fks = foreignKeys.stream()
                .map(ForeignKey::toSQL)
                .collect(Collectors.joining(", ", ", ", ""));
        return String.format("CREATE TABLE %s (%s%s%s);", name, cols, pk, fks);
    }

    public String getName() {
        return name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }
}
