package sde.utils;

import javax.swing.*;
import java.awt.*;

public class UserPreferences {

    // fonts
    public static String FONT_NAME = ((Font) UIManager.get("Label.font")).getName();
    public static int FONT_SIZE = 12;
    public static Font DIAGRAM_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
    public static Font TITLE_FONT = new Font(FONT_NAME, Font.BOLD, (int) (FONT_SIZE * 1.4));
    public static Font ABSTRACT_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);

    // diagram
    public static int ARROW_SIZE = 10;
    public static int MESSAGES_DISTANCE = 60;
    public static int WORKING_THREAD_WIDTH = 8;
    public static int PADDING = 25;
    public static int NOTES_DISTANCE = 25;
    public static int ENTITY_HEIGHT = 40;
    public static int ENTITY_WIDTH = 100;
    public static int ENTITY_DISTANCE = 150;
}
