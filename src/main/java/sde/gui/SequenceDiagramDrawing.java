package sde.gui;

import sde.diagram.Entity;
import sde.diagram.Message;
import sde.diagram.Parser;
import sde.diagram.SequenceDiagram;
import sde.gui.components.EntityComponent;
import sde.gui.components.MessageComponent;
import sde.utils.Constants;
import sde.utils.SwingUtils;
import sde.utils.UserPreferences;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SequenceDiagramDrawing {

    private SequenceDiagram sequenceDiagram = null;
    private List<EntityComponent> entityComponents = new ArrayList<>();
    private List<MessageComponent> messageComponents = new ArrayList<>();
    private int maxRow;
    private int entitiesStartY;

    public String updateDrawing(String diagram) {

        String result = "Ready";
        try {
            sequenceDiagram = Parser.parseDatafile(diagram);
            createGuiComponents();
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    private void createGuiComponents() {

        messageComponents.clear();
        entityComponents.clear();

        int entityCounter = 0;
        maxRow = 0;
        for (Entity entity : sequenceDiagram.getEntities()) {

            EntityComponent entityComponent = new EntityComponent(entity, UserPreferences.PADDING + (UserPreferences.ENTITY_DISTANCE + UserPreferences.ENTITY_WIDTH) * entityCounter);
            entityComponents.add(entityComponent);
            entityCounter++;
        }
        for (Message message : sequenceDiagram.getMessages()) {

            MessageComponent messageComponent = new MessageComponent(
                    message,
                    getEntityComponent(message.getFrom()),
                    getEntityComponent(message.getTo().orElse(Constants.EMPTY_ENTITY))
            );
            messageComponents.add(messageComponent);
            entityCounter++;
            if (maxRow < message.getRow()) {
                maxRow = message.getRow();
            }
        }
    }

    private EntityComponent getEntityComponent(Entity entity) {
        if (entity == null) return Constants.EMPTY_ENTITY_COMPONENT;
        return entityComponents
                .stream()
                .filter(e -> e.getEntity().getId().equals(entity.getId()))
                .findFirst()
                .orElse(Constants.EMPTY_ENTITY_COMPONENT);
    }


    // draws the diagram on the SequenceDiagramPanel component
    public void draw(Graphics2D g2d) {

        if (sequenceDiagram != null) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(UserPreferences.TITLE_FONT);
            SwingUtils.drawMultilineString(g2d, sequenceDiagram.getTitle(), UserPreferences.PADDING, UserPreferences.PADDING);
            g2d.setFont(UserPreferences.ABSTRACT_FONT);
            SwingUtils.drawMultilineString(g2d, sequenceDiagram.getAbstractText(), UserPreferences.PADDING, UserPreferences.PADDING + UserPreferences.FONT_SIZE * 2);
            g2d.setFont(UserPreferences.DIAGRAM_FONT);

            entitiesStartY = SwingUtils.getStringHeight(g2d, UserPreferences.TITLE_FONT, sequenceDiagram.getTitle()) +
                    SwingUtils.getStringHeight(g2d, UserPreferences.ABSTRACT_FONT, sequenceDiagram.getAbstractText()) +
                    UserPreferences.FONT_SIZE * 2;

            for (EntityComponent entity : entityComponents) {
                entity.setStartY(entitiesStartY);
                entity.paint(g2d);
                Point point = entity.getLineStartPoint();
                g2d.drawLine(point.x, point.y, point.x, point.y + (maxRow + 2) * UserPreferences.MESSAGES_DISTANCE);
            }

            for (MessageComponent messageComponent : messageComponents) {
                messageComponent.setStartY(entitiesStartY + UserPreferences.ENTITY_HEIGHT * 2);
                messageComponent.paint(g2d);
            }
        }
    }


    public Rectangle getDrawedRectangle() {

        Rectangle rectangle = new Rectangle(
                0,
                0,
                UserPreferences.PADDING + (sequenceDiagram.getEntitiesNumber() + 1) * (UserPreferences.ENTITY_WIDTH + UserPreferences.ENTITY_DISTANCE),
                entitiesStartY + UserPreferences.ENTITY_HEIGHT * 2 + (maxRow + 2) * UserPreferences.MESSAGES_DISTANCE
        );

        return rectangle;
    }

    public Optional<MessageComponent> getMessageFromCoordinates(int x, int y) {
        return messageComponents
                .stream()
                .filter(message -> message.getMessageArea().contains(x, y))
                .findFirst();
    }
}
