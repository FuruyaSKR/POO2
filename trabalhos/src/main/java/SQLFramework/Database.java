package SQLFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    private String name;
    private List<Table> tables = new ArrayList<>();

    public Database(String name) {
        this.name = name;
    }

    public void addTable(Table t) {
        tables.add(t);
    }

    public void removeTable(String name) {
        tables.removeIf(t -> t.getName().equals(name));
    }

    public List<Table> getTables() {
        return tables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toSQL() {
        return tables.stream()
                .map(Table::toSQL)
                .collect(Collectors.joining("\n"));
    }
}
