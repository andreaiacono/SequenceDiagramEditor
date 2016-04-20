package sde.gui;

import sde.gui.components.MessageComponent;
import sde.utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class Main extends JFrame implements ActionListener, KeyListener {

    static final long serialVersionUID = 0;
    private JMenuItem saveMenuItem;
    private boolean isCtrlPressed;
    private String currentFileName;
    private JLabel statusLabel;
    private JTextArea diagramText;
    private SequenceDiagramPanel diagramPanel;
    private String actualDiagram;

    public Main() throws Exception {

        super(Constants.SDE);
        setSize(800, 600);
        SwingUtils.centerFrame(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            // creates the menu bar
            JMenuBar menuBar = new JMenuBar();

            // creates the file menu
            JMenu fileMenu = createMenu("File", KeyEvent.VK_F);
            fileMenu.add(createMenuItem("New", KeyEvent.VK_N, e -> newFile(), true));
            fileMenu.add(createMenuItem("Open", KeyEvent.VK_O, e -> openFile(), true));
            JMenu recentlyOpened = createMenu("Recently Opened", KeyEvent.VK_R);
            addRecentlyOpenedFilesMenuItem(recentlyOpened);
            fileMenu.add(recentlyOpened);
            fileMenu.addSeparator();
            saveMenuItem = createMenuItem("Save", KeyEvent.VK_S, e -> save(), false);
            fileMenu.add(saveMenuItem);
            fileMenu.add(createMenuItem("Save as", KeyEvent.VK_V, e -> saveAs(), false));
            fileMenu.addSeparator();
            fileMenu.add(createMenuItem("Exit", KeyEvent.VK_X, e -> System.exit(0), true));
            menuBar.add(fileMenu);

            // creates the tools menu
            JMenu toolsMenu = createMenu("Tools", KeyEvent.VK_T);
            toolsMenu.add(createMenuItem("Export as PNG", KeyEvent.VK_X, e -> exportAsPng(), false));
            toolsMenu.addSeparator();
            toolsMenu.add(createMenuItem("Option", KeyEvent.VK_O, e -> {
                PreferencesForm preferencesForm = new PreferencesForm(Main.this);
                preferencesForm.setVisible(true);
            }, false));
            menuBar.add(toolsMenu);

            setJMenuBar(menuBar);

            diagramPanel = new SequenceDiagramPanel(new SequenceDiagramDrawing(), this);
            JScrollPane jspTop = new JScrollPane(diagramPanel);
            jspTop.getVerticalScrollBar().setUnitIncrement(16);
            diagramPanel.setPreferredSize(new Dimension(2000, 5000));
            diagramPanel.addKeyListener(this);

            diagramText = new JTextArea();
            diagramText.addKeyListener(this);

            JScrollPane jspBottom = new JScrollPane(diagramText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            jspBottom.setWheelScrollingEnabled(true);

            getContentPane().setLayout(new BorderLayout());

            // creates the divider for the two panes
            JSplitPane spDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jspTop, jspBottom);
            spDivider.setDividerLocation(400);
            spDivider.setOneTouchExpandable(true);
            getContentPane().add(spDivider, BorderLayout.CENTER);

            statusLabel = new JLabel(" Ready");
            getContentPane().add("South", statusLabel);
            this.setFocusable(true);
            loadOptions();
            setVisible(true);
        }
        catch (Exception e) {
            SwingUtils.showFormError(e);
        }

    }

    public static void main(String[] args) throws Exception {

        // sets antialiasing
        System.setProperty("swing.aatext", "true");

        // Show tool tips after one quarter of a second
        ToolTipManager.sharedInstance().setInitialDelay(250);

        // tries to set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            // ok, just tried..
        }

        try {
            Main main = new Main();
            if (args.length > 0) {
                main.loadDiagram(args[0]);
            }
            main.setVisible(true);
        }
        catch (Exception e) {
            SwingUtils.showFormError(e);
        }
    }

    private JMenuItem createMenuItem(String label, int mnemonics, ActionListener listener, boolean addThisListener) {
        JMenuItem item = new JMenuItem(label);
        item.setMnemonic(mnemonics);
        item.addActionListener(listener);
        if (addThisListener) {
            item.addActionListener(this);
        }
        return item;
    }

    private JMenu createMenu(String label, int mnemonics) {
        JMenu menu = new JMenu(label);
        menu.setMnemonic(mnemonics);
        return menu;
    }

    private void exportAsPng() {

        try {
            JFileChooser fc = new JFileChooser();
            int userResponse = fc.showSaveDialog(Main.this);

            // if the user pressed "cancel" skips all
            if (userResponse != JFileChooser.APPROVE_OPTION) {
                return;
            }

            String exportFilename = fc.getSelectedFile().getAbsolutePath();
            if (!exportFilename.toLowerCase().endsWith(".png")) {
                exportFilename += ".png";
            }

            // sets the cursor
            setCursor(Constants.CURSOR_WAIT);
            diagramPanel.repaint();
            diagramPanel.invalidate();
            Rectangle drawingRect = diagramPanel.getSequenceDiagramDrawing().getDrawedRectangle();
            BufferedImage bi = new BufferedImage(diagramPanel.getWidth(), diagramPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            diagramPanel.paint(bi.getGraphics());

            // gets only the part of image with the diagram
            BufferedImage bi2 = bi.getSubimage(drawingRect.x, drawingRect.y, drawingRect.width, drawingRect.height);

            ImageIO.write(bi2, "png", new File(exportFilename));
            setCursor(Constants.CURSOR_DEFAULT);
        }
        catch (Exception ex) {
            setCursor(Constants.CURSOR_DEFAULT);
            SwingUtils.showFormError(ex);
        }
    }

    private void loadOptions() {

        Preferences p = Preferences.userRoot();
        UserPreferences.FONT_NAME = p.get(Constants.PREFERENCES_FONT_NAME, UserPreferences.FONT_NAME);
        UserPreferences.FONT_SIZE = Integer.parseInt(p.get(Constants.PREFERENCES_FONT_SIZE, "" + 12));
        UserPreferences.ENTITY_DISTANCE = Integer.parseInt(p.get(Constants.PREFERENCES_ENTITY_DISTANCE, "" + 150));
        UserPreferences.MESSAGES_DISTANCE = Integer.parseInt(p.get(Constants.PREFERENCES_MESSAGES_DISTANCE, "" + 75));
        UserPreferences.ENTITY_WIDTH = Integer.parseInt(p.get(Constants.PREFERENCES_ENTITY_WIDTH, "" + 100));
        UserPreferences.ENTITY_HEIGHT = Integer.parseInt(p.get(Constants.PREFERENCES_ENTITY_HEIGHT, "" + 30));
        UserPreferences.DIAGRAM_FONT = new Font(UserPreferences.FONT_NAME, Font.PLAIN, UserPreferences.FONT_SIZE);
        UserPreferences.PADDING = Integer.parseInt(p.get(Constants.PREFERENCES_PADDING, "" + 25));
        UserPreferences.NOTES_DISTANCE = Integer.parseInt(p.get(Constants.PREFERENCES_NOTES_DISTANCE, "" + 30));
        UserPreferences.ARROW_SIZE = Integer.parseInt(p.get(Constants.PREFERENCES_ARROW_SIZE, "" + 10));
        UserPreferences.WORKING_THREAD_WIDTH = Integer.parseInt(p.get(Constants.PREFERENCES_WORKING_THREAD_WIDTH, "" + 8));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void loadDiagram(String fileName) throws Exception {

        diagramText.setText(FileUtils.readTextFile(fileName));
        actualDiagram = diagramText.getText();
        String i = null;
        if (i.indexOf("i") == 0) {
            i = "";
        }
        // save this file as a recently opened file
        Preferences p = Preferences.userRoot();
        String newOpenedFiles = p.get(Constants.RECENT_FILES_KEY, "") + Constants.FILENAME_SEPARATOR + fileName;
        p.put(Constants.RECENT_FILES_KEY, newOpenedFiles);

        diagramText.setCaretPosition(0);
        setCurrentFileName(fileName);
        setTitle(String.format("%s [%s]", Constants.SDE, fileName.substring(fileName.lastIndexOf("/") + 1)));
        updateDiagramDrawing();
    }

    // updates the diagram and prints a status (ok or error) message on status bar
    public void updateDiagramDrawing() {
        updateStatusBar(diagramPanel.updateDrawing(diagramText.getText()));
    }

    public void updateStatusBar(String message) {

        if (message != null) {
            statusLabel.setText(message);
            diagramPanel.repaint();
        }
    }

    public void highlightMessageRow(Optional<MessageComponent> messageComponent) {

        int position = diagramText.getText().indexOf(
                messageComponent
                        .map(mc -> mc.getMessage().getRow() + Constants.DEFINITIONS_SEPARATOR + mc.getMessage().getFrom().getId())
                        .orElse("NOT FOUND MESSAGE"));
        if (position > 0) {
            diagramText.setCaretPosition(position);
        }
    }

    public void removeHighlights(JTextComponent textComp) {

        Highlighter highlighter = textComp.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();

        for (int i = 0; i < highlights.length; i++) {
            highlighter.removeHighlight(highlights[i]);
        }
    }

    private void saveDiagram(String fileName) throws Exception {

        FileUtils.writeTextFile(fileName, diagramText.getText());
        updateStatusBar("File [" + fileName + "] saved.");
    }

    public void setCurrentFileName(String currentFileName) {

        this.currentFileName = currentFileName;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        saveMenuItem.setEnabled(true);
        if (ke.isControlDown()) {
            isCtrlPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        invalidate();
        isCtrlPressed = false;
        updateDiagramDrawing();
    }

    public boolean isCtrlPressed() {
        return isCtrlPressed;
    }

    private void newFile() {
        if (goOnWithModifiedDiagram()) {
            diagramText.setText("");
            repaint();
        }
    }

    private void openFile() {

        if (goOnWithModifiedDiagram()) {

            Preferences p = Preferences.userRoot();

            JFileChooser fc = new JFileChooser();
            String lastUsedDirectory = p.get(Constants.PREFERENCES_LAST_USED_DIRECTORY, "");
            fc.setCurrentDirectory(new File(lastUsedDirectory));
            fc.setFileFilter(new GenericFileFilter("sd", Constants.SDE + " Files"));
            int userResponse = fc.showOpenDialog(Main.this);

            // if the user pressed "ok"
            if (userResponse != JFileChooser.APPROVE_OPTION) {
                return;
            }

            try {
                loadDiagram(fc.getSelectedFile().getAbsolutePath());
                p.put(Constants.PREFERENCES_LAST_USED_DIRECTORY, fc.getSelectedFile().getPath());
            }
            catch (Exception ex) {
                SwingUtils.showFormError(ex);
            }
        }
    }

    private boolean goOnWithModifiedDiagram() {

        if (!actualDiagram.equals(diagramText.getText())) {

            int userResponse = JOptionPane.showConfirmDialog(
                    null,
                    "This diagram was been modified. Do you want to save it?",
                    "Save Diagram",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (userResponse == JOptionPane.CANCEL_OPTION) {
                return false;
            }

            if (userResponse == JOptionPane.YES_OPTION) {
                try {
                    saveDiagram(currentFileName);
                    return true;
                }
                catch (Exception ex) {
                    SwingUtils.showFormError(ex);
                    return false;
                }
            }
        }

        return true;
    }

    private void addRecentlyOpenedFilesMenuItem(JMenu recentMenu) {

        // gets the recent files from the preferences
        // on unix it's stored in $HOME/.java/.userPrefs/prefs.xml
        Preferences p = Preferences.userRoot();
        String filenames = p.get(Constants.RECENT_FILES_KEY, "");
        Set<String> recentFiles = Arrays.stream(filenames.split(Constants.FILENAME_SEPARATOR_FOR_REGEX)).collect(Collectors.toSet());

        for (String filename : recentFiles) {

            recentMenu.add(createMenuItem(filename, 0, e -> {
                try {
                    loadDiagram(e.getActionCommand());
                }
                catch (Exception ex) {
                    SwingUtils.showFormError(ex);
                }
            }, false));
        }
    }

    private void saveAs() {

        try {
            JFileChooser fc = new JFileChooser();
            int userResponse = fc.showSaveDialog(Main.this);

            // if the user pressed "ok"
            if (userResponse == JFileChooser.APPROVE_OPTION) {

                currentFileName = fc.getSelectedFile().getPath();
                if (!currentFileName.toLowerCase().endsWith(".sd")) {
                    currentFileName += ".sd";
                }

                File f = new File(currentFileName);
                if (f.exists()) {
                    if (JOptionPane.showConfirmDialog(
                            null,
                            "The file " + currentFileName + " already exists. Do you want to overwrite it?",
                            "Save Diagram",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
            }
            else {
                return;
            }
            saveDiagram(currentFileName);
        }
        catch (Exception ex) {
            SwingUtils.showFormError(ex);
        }
    }

    private void save() {
        try {
            saveDiagram(currentFileName);
        }
        catch (Exception ex) {
            SwingUtils.showFormError(ex);
        }
    }

}
