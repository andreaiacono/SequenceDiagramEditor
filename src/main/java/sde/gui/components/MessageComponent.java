package sde.gui.components;

import sde.diagram.Message;
import sde.utils.Constants;
import sde.utils.SwingUtils;
import sde.utils.UserPreferences;

import java.awt.*;

public class MessageComponent {

    private Message message;
    private EntityComponent from;
    private EntityComponent to;
    private int[] x = new int[3];
    private int[] y = new int[3];
    private int startY;

    public MessageComponent(Message message, EntityComponent from, EntityComponent to) {
        this.message = message;
        this.from = from;
        this.to = to;
        startY = from.getLineStartPoint().y + message.getRow() * UserPreferences.MESSAGES_DISTANCE;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);

        // draws the label for the message towards To
        if (to != Constants.EMPTY_ENTITY_COMPONENT) {
            y[0] = startY;
            y[1] = y[0] - UserPreferences.ARROW_SIZE / 2;
            y[2] = y[0] + UserPreferences.ARROW_SIZE / 2;

            if (from.getLineStartPoint().x > to.getLineStartPoint().x) {
                x[0] = to.getLineStartPoint().x + UserPreferences.WORKING_THREAD_WIDTH - 2;
                x[1] = x[0] + UserPreferences.ARROW_SIZE;
                x[2] = x[1];

                SwingUtils.drawAlignedText(g, startY - UserPreferences.FONT_SIZE / 4, from.getLineStartPoint().x + UserPreferences.WORKING_THREAD_WIDTH, false, message.getLabel());
                g.drawLine(from.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH, startY, to.getLineStartPoint().x + UserPreferences.WORKING_THREAD_WIDTH, startY);
            }
            else {
                x[0] = to.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH + 2;
                x[1] = x[0] - UserPreferences.ARROW_SIZE;
                x[2] = x[1];
                SwingUtils.drawAlignedText(g, startY - UserPreferences.FONT_SIZE / 4, from.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH, true, message.getLabel());
                g.drawLine(from.getLineStartPoint().x + UserPreferences.WORKING_THREAD_WIDTH, startY, to.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH, startY);
            }
            g.fillPolygon(x, y, 3);
        }
        // draws only the label (no To entity)
        else {
            if (message.getLabel() != null) {
                String[] textRows = message.getLabel().split("\\\\n");

                int row_height = startY;
                for (String row : textRows) {
                    SwingUtils.drawAlignedText(
                            g,
                            row_height,
                            (int) (from.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH * 1.2),
                            message.getOrientation() == Message.Orientation.LEFT ? false : true,
                            row);
                    row_height += UserPreferences.FONT_SIZE * 1.2;
                }
            }
        }

        // writes the response back to the requester
        if (message.hasImmediateResponse()) {
            int responseY = (int) (startY + UserPreferences.FONT_SIZE * 1.6);
            y[0] = responseY;
            y[1] = y[0] - UserPreferences.ARROW_SIZE / 2;
            y[2] = y[0] + UserPreferences.ARROW_SIZE / 2;

            if (from.getLineStartPoint().x < to.getLineStartPoint().x) {
                x[0] = from.getLineStartPoint().x + UserPreferences.WORKING_THREAD_WIDTH - 2;
                x[1] = x[0] + UserPreferences.ARROW_SIZE;
                x[2] = x[1];
                SwingUtils.drawAlignedText(g, responseY - UserPreferences.FONT_SIZE / 4, to.getLineStartPoint().x, false, message.getResponseLabel());
                g.drawLine(to.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH, responseY, from.getLineStartPoint().x + UserPreferences.WORKING_THREAD_WIDTH, responseY);

            }
            else {
                x[0] = from.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH + 2;
                x[1] = x[0] - UserPreferences.ARROW_SIZE;
                x[2] = x[1];
                SwingUtils.drawAlignedText(g, responseY - UserPreferences.FONT_SIZE / 4, to.getLineStartPoint().x, true, message.getResponseLabel());
                g.drawLine(to.getLineStartPoint().x + UserPreferences.WORKING_THREAD_WIDTH, responseY, from.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH, responseY);
            }
            g.fillPolygon(x, y, 3);
        }

        // draws a thread occupation
        if (message.getDuration() >= 1) {
            int endThreadY = (message.getDuration() - message.getRow()) * UserPreferences.MESSAGES_DISTANCE;
            if (message.hasImmediateResponse()) {
                endThreadY += UserPreferences.FONT_SIZE * 1.6;
            }
            g.setColor(Color.GRAY);
            g.fillRect(from.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH / 2, startY, UserPreferences.WORKING_THREAD_WIDTH, endThreadY);
            g.setColor(Color.DARK_GRAY);
            g.drawRect(from.getLineStartPoint().x - UserPreferences.WORKING_THREAD_WIDTH / 2, startY, UserPreferences.WORKING_THREAD_WIDTH, endThreadY);
        }
    }

    public Rectangle getMessageArea() {
        if (from.getLineStartPoint().x < to.getLineStartPoint().x) {
            return new Rectangle(from.getLineStartPoint().x, startY, to.getLineStartPoint().x, UserPreferences.MESSAGES_DISTANCE);
        }
        else {
            return new Rectangle(to.getLineStartPoint().x, startY, from.getLineStartPoint().x, UserPreferences.MESSAGES_DISTANCE);
        }
    }

    public void setStartY(int startY) {
        this.startY = startY + (message.getRow() + 1) * UserPreferences.MESSAGES_DISTANCE;
    }

    public Message getMessage() {
        return message;
    }
}
