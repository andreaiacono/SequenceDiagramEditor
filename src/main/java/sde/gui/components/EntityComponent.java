package sde.gui.components;

import sde.diagram.Entity;
import sde.utils.SwingUtils;
import sde.utils.UserPreferences;

import java.awt.*;

public class EntityComponent {

    private Entity entity;
    private Rectangle rect;

    public EntityComponent(Entity entity, int x) {

        this.entity = entity;
        rect = new Rectangle(x, UserPreferences.PADDING, UserPreferences.ENTITY_WIDTH, UserPreferences.ENTITY_HEIGHT);
    }

    public void paint(Graphics2D g) {

        switch (entity.getType()) {
            case GENERIC:
                g.setColor(Color.WHITE);
                g.fillRoundRect(rect.x, rect.y + rect.height / 4, rect.width, rect.height, rect.height, rect.height);
                g.setColor(Color.DARK_GRAY);
                g.drawRoundRect(rect.x, rect.y + rect.height / 4, rect.width, rect.height, rect.height, rect.height);
                drawLabel(g);
                break;
            case STORAGE:
                g.setColor(Color.GRAY);
                g.fillRect(rect.x, rect.y + rect.height / 4, rect.width, (int) (rect.height * 0.75) + 1);
                g.fillArc(rect.x, (int) (rect.y + rect.height * 0.75), rect.width, rect.height / 2, 0, -180);

                g.setColor(Color.DARK_GRAY);
                g.drawArc(rect.x, (int) (rect.y + rect.height * 0.75), rect.width, rect.height / 2, 0, -180);

                g.drawLine(rect.x, rect.y + rect.height / 4, rect.x, rect.y + rect.height);
                g.drawLine(rect.x + rect.width, rect.y + rect.height / 4, rect.x + rect.width, rect.y + rect.height);

                g.setColor(Color.WHITE);
                g.fillOval(rect.x, rect.y, rect.width, UserPreferences.ENTITY_HEIGHT / 2);
                g.setColor(Color.DARK_GRAY);
                g.drawOval(rect.x, rect.y, rect.width, UserPreferences.ENTITY_HEIGHT / 2);

                // label
                g.setColor(Color.BLACK);
                int textWidth = SwingUtils.getStringWidth(g, UserPreferences.DIAGRAM_FONT, entity.getLabel());
                g.drawString(entity.getLabel(), rect.x + (rect.width - textWidth) / 2, rect.y + rect.height);
                break;

            case ACTOR:

                // head
                g.setColor(Color.WHITE);
                g.fillOval(getLineStartPoint().x - UserPreferences.ENTITY_HEIGHT / 8, rect.y + UserPreferences.ENTITY_HEIGHT / 8,
                        UserPreferences.ENTITY_HEIGHT / 4, UserPreferences.ENTITY_HEIGHT / 4);
                g.setColor(Color.DARK_GRAY);
                g.drawOval(getLineStartPoint().x - UserPreferences.ENTITY_HEIGHT / 8, rect.y + UserPreferences.ENTITY_HEIGHT / 8,
                        UserPreferences.ENTITY_HEIGHT / 4, UserPreferences.ENTITY_HEIGHT / 4);
                g.setColor(Color.DARK_GRAY);

                Point bodyEnd = new Point(getLineStartPoint().x, (int) (rect.y + UserPreferences.ENTITY_HEIGHT * 0.9));

                // body
                g.drawLine(getLineStartPoint().x, (int) (rect.y + UserPreferences.ENTITY_HEIGHT * 0.34), bodyEnd.x,
                        bodyEnd.y);

                // arms
                g.drawLine(
                        getLineStartPoint().x - UserPreferences.ENTITY_HEIGHT / 3,
                        rect.y + UserPreferences.ENTITY_HEIGHT / 2,
                        getLineStartPoint().x + UserPreferences.ENTITY_HEIGHT / 3,
                        rect.y + UserPreferences.ENTITY_HEIGHT / 2);

                // left leg
                g.drawLine(bodyEnd.x, bodyEnd.y, bodyEnd.x - UserPreferences.ENTITY_HEIGHT / 4, bodyEnd.y + UserPreferences.ENTITY_HEIGHT / 3);

                // right leg
                g.drawLine(bodyEnd.x, bodyEnd.y, bodyEnd.x + UserPreferences.ENTITY_HEIGHT / 4, bodyEnd.y + UserPreferences.ENTITY_HEIGHT / 3);

                // label
                g.setColor(Color.BLACK);
                textWidth = SwingUtils.getStringWidth(g, UserPreferences.DIAGRAM_FONT, entity.getLabel());
                g.drawString(entity.getLabel(), rect.x + (rect.width - textWidth) / 2, rect.y - UserPreferences.ENTITY_HEIGHT / 4);
        }
    }

    protected void drawLabel(Graphics2D g) {
        g.setColor(Color.BLACK);
        int textWidth = SwingUtils.getStringWidth(g, UserPreferences.DIAGRAM_FONT, entity.getLabel());
        int textHeight = SwingUtils.getStringHeight(g, UserPreferences.DIAGRAM_FONT, entity.getLabel());
        g.drawString(entity.getLabel(), rect.x + (rect.width - textWidth) / 2, (int) (rect.y + (rect.height + rect.height / 4 - textHeight) * 1.1));
    }

    public Point getLineStartPoint() {
        return new Point(rect.x + rect.width / 2, (int) (rect.y + rect.height * 1.6));
    }

    public Entity getEntity() {
        return entity;
    }

    public void setStartY(int entityStartY) {
        rect.y = UserPreferences.PADDING + entityStartY;
    }
}
