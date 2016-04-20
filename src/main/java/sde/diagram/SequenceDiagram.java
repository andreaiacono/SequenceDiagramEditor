package sde.diagram;

import java.util.*;

public class SequenceDiagram {

    private String title;
    private String abstractText;
    private Map<String, Entity> entities;

    // needed for accessing in the right order to entities
    private List<Entity> entitiesList;
    private List<Message> messages;

    public SequenceDiagram() {
        entities = new HashMap<>();
        messages = new ArrayList<>();
        entitiesList = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
        entitiesList.add(entity);
    }

    public Entity getEntity(String label) {
        return entities.get(label);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public int getEntitiesNumber() {
        return entities.size();
    }

    public Collection<Entity> getEntities() {
        return entitiesList;
    }

    public boolean containsEntityId(String id) {
        return entitiesList.stream().anyMatch(e -> e.getId().equals(id));
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    @Override
    public String toString() {
        return "SequenceDiagram{" +
                "title='" + title + '\'' +
                ", abstractText='" + abstractText + '\'' +
                ", entities=" + entities +
                ", messages=" + messages +
                '}';
    }
}
