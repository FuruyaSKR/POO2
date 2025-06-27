package SQLFramework.DatabaseController;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Field> fields = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void removeField(String fieldName) {
        fields.removeIf(f -> f.getName().equals(fieldName));
    }

    public List<Field> getFields() {
        return fields;
    }
}
