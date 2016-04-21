package sde.utils;

import sde.gui.ErrorForm;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SwingUtils {

    public static void drawMultilineString(Graphics g2d, String text, int x, int y) {
        if (text != null) {
            String[] textRows = text.split("\\\\n");
            int rowY = y;
            for (String row : textRows) {
                g2d.drawString(row, x, rowY);
                rowY += UserPreferences.FONT_SIZE * 1.2;
            }
        }
    }

    public static void drawAlignedText(Graphics2D g, int y, int x, boolean isStartingLeft, String text) {

        if (isStartingLeft) {
            g.drawString(text, x + UserPreferences.ARROW_SIZE * 2, y);
        }
        else {
            int width = SwingUtils.getStringWidth(g, UserPreferences.DIAGRAM_FONT, text);
            g.drawString(text, x - UserPreferences.ARROW_SIZE * 2 - width, y);
        }
    }

    public static void fillComboBox(JComboBox<String> comboBox, String[] values, boolean isToClean, String firstElement) {

        if (comboBox == null) {
            return;
        }

        if (isToClean) {
            comboBox.removeAllItems();
        }

        if (firstElement != null) {
            comboBox.addItem(firstElement);
        }

        Arrays.stream(values).forEach(val -> comboBox.addItem(val));
    }

    public static void centerFrame(JFrame frame) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }

    public static void centerFrame(JDialog jd) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jd.getSize();
        jd.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }

    public static void showFormError(Exception ex) {

        new ErrorForm(ex).setVisible(true);
    }

    public static int getStringWidth(Graphics g, Font font, String text) {

        FontMetrics fm = g.getFontMetrics(font);
        return (int) fm.getStringBounds(text, g).getWidth();
    }

    public static int getStringHeight(Graphics g, Font font, String text) {

        if (font == null || g == null || text == null) {
            return -1;
        }

        FontMetrics fm = g.getFontMetrics(font);

        if (!text.contains(("\\n"))) {
            return (int) fm.getStringBounds(text, g).getHeight();
        }

        return Arrays
                .stream(text.split("\\\\n"))
                .mapToInt(row -> (int) fm.getStringBounds(text, g).getHeight())
                .reduce(Integer::sum).getAsInt();
    }
}
