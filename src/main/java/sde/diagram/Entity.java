package sde.diagram;

public class Entity {

    private String id;
    private String label;
    private Type type;
    private int row;

    public Entity(String id, String label, Type type) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.row = 0;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", row=" + row +
                '}';
    }

    public enum Type {
        GENERIC, ACTOR, STORAGE
    }
}
