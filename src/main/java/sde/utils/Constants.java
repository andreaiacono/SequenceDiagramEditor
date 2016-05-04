package sde.utils;

import sde.diagram.Entity;
import sde.gui.components.EntityComponent;

import java.awt.*;

public class Constants {

    public static final String SDE = "Sequence Diagram Editor";
    public static final String ICON_RESOURCE = "/sde.png";

    public static final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
    public static final Cursor CURSOR_WAIT = new Cursor(Cursor.WAIT_CURSOR);

    public static final Entity EMPTY_ENTITY = new Entity("NOT_EXISTING_ID", "NOT_EXISTING_LABEL", Entity.Type.GENERIC);
    public static final EntityComponent EMPTY_ENTITY_COMPONENT = new EntityComponent(EMPTY_ENTITY, -1);

    public static final String FILENAME_SEPARATOR = "|";
    public static final String FILENAME_SEPARATOR_FOR_REGEX = "\\|";

    public static final Color BACKGROUND_COLOR = new Color(200, 200, 200);
    public static final float MOUSEWHEEL_ZOOM = 15f;
    public static final int MOUSEWHEEL_SCROLL_Y = 30;

    // script related
    public static final String COMMENT_LINE = "#";
    public static final String SECTION_DEFINER = "-";
    public static final String DEFINITIONS_SEPARATOR_FOR_REGEX = "\\|";
    public static final String DEFINITIONS_SEPARATOR = "|";
    public static final String ENTITY_SEPARATOR = ":";
    public static final String ENTITY_ID_REGEX = "^[a-zA-Z][a-zA-Z0-9].*";
    public static final String RESPONSE_SEPARATOR = "=>";
    public static final String WORKING_THREAD_ROWS_SEPARATOR = "..";
    public static final String WORKING_THREAD_ROWS_SEPARATOR_REGEX = "\\.\\.";

    // preferences keys
    public static final String RECENT_FILES_KEY = SDE + " - RecentFiles";
    public static final String PREFERENCES_FONT_NAME = "FontName";
    public static final String PREFERENCES_FONT_SIZE = "FontSize";
    public static final String PREFERENCES_ENTITY_DISTANCE = "EntityDistance";
    public static final String PREFERENCES_ENTITY_WIDTH = "EntityWidth";
    public static final String PREFERENCES_ENTITY_HEIGHT = "EntityHeight";
    public static final String PREFERENCES_NOTES_DISTANCE = "NotesDistance";
    public static final String PREFERENCES_MESSAGES_DISTANCE = "MessagesDistance";
    public static final String PREFERENCES_ARROW_SIZE = "ArrowSize";
    public static final String PREFERENCES_PADDING = "Padding";
    public static final String PREFERENCES_WORKING_THREAD_WIDTH = "WorkingThreadWidth";
    public static final String PREFERENCES_LAST_USED_DIRECTORY = "LastUsedDirectory";
}
