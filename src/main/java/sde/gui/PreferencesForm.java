package sde.gui;

import sde.utils.Constants;
import sde.utils.SwingUtils;
import sde.utils.UserPreferences;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class PreferencesForm extends JDialog implements ActionListener, ChangeListener {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> jcFont;
    private Main main;
    private JSlider fontSizeSlider;
    private JSlider entityDistanceSlider;
    private JSlider messagesDistanceSlider;
    private JSlider entityWidthSlider;
    private JSlider entityHeightSlider;
    private JSlider arrowSizeSlider;
    private JSlider workingThreadWidthSlider;

    public PreferencesForm(Main main) {

        super.setTitle("Preferences");
        this.setModal(false);
        this.main = main;

        init();
    }

    public void init() {

        // sets the size and the location of the window
        setSize(340, 400);
        SwingUtils.centerFrame(this);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        int ww = 80, hh = 25;

        JButton jbOk = new JButton("Close");
        jbOk.addActionListener(this);
        this.getRootPane().setDefaultButton(jbOk);
        add(jbOk);

        sl.putConstraint(SpringLayout.SOUTH, jbOk, -5, SpringLayout.SOUTH, this.getContentPane());
        sl.putConstraint(SpringLayout.EAST, jbOk, -5, SpringLayout.EAST, this.getContentPane());

        Preferences p = Preferences.userRoot();

        JLabel jlName = new JLabel("Font Name: ");
        jlName.setPreferredSize(new Dimension(ww, hh));
        add(jlName);
        sl.putConstraint(SpringLayout.NORTH, jlName, 5, SpringLayout.NORTH, this.getContentPane());
        sl.putConstraint(SpringLayout.WEST, jlName, 5, SpringLayout.WEST, this.getContentPane());

        jcFont = new JComboBox<>();
        SwingUtils.fillComboBox(jcFont, GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(), true, "");
        jcFont.setSelectedItem(p.get(Constants.PREFERENCES_FONT_NAME, "Arial"));
        jcFont.addActionListener(this);
        add(jcFont);
        sl.putConstraint(SpringLayout.NORTH, jcFont, 5, SpringLayout.NORTH, this.getContentPane());
        sl.putConstraint(SpringLayout.WEST, jcFont, 35, SpringLayout.EAST, jlName);
        sl.putConstraint(SpringLayout.EAST, jcFont, -5, SpringLayout.EAST, this.getContentPane());

        JLabel fontSizeLabel = new JLabel("Font size: ");
        add(fontSizeLabel);
        sl.putConstraint(SpringLayout.NORTH, fontSizeLabel, 18, SpringLayout.SOUTH, jcFont);
        sl.putConstraint(SpringLayout.WEST, fontSizeLabel, 5, SpringLayout.WEST, getContentPane());

        fontSizeSlider = new JSlider(5, 50);
        fontSizeSlider.setName(Constants.PREFERENCES_FONT_SIZE);
        fontSizeSlider.addChangeListener(this);
        fontSizeSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_FONT_SIZE, "11")));
        add(fontSizeSlider);

        sl.putConstraint(SpringLayout.NORTH, fontSizeSlider, 0, SpringLayout.SOUTH, jcFont);
        sl.putConstraint(SpringLayout.WEST, fontSizeSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, fontSizeSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel entityDistanceLabel = new JLabel("Entity distance: ");
        add(entityDistanceLabel);
        sl.putConstraint(SpringLayout.NORTH, entityDistanceLabel, 18, SpringLayout.SOUTH, fontSizeLabel);
        sl.putConstraint(SpringLayout.WEST, entityDistanceLabel, 5, SpringLayout.WEST, getContentPane());

        entityDistanceSlider = new JSlider(0, 500);
        entityDistanceSlider.setName(Constants.PREFERENCES_ENTITY_DISTANCE);
        entityDistanceSlider.addChangeListener(this);
        entityDistanceSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_ENTITY_DISTANCE, "150")));

        add(entityDistanceSlider);

        sl.putConstraint(SpringLayout.NORTH, entityDistanceSlider, 0, SpringLayout.SOUTH, fontSizeLabel);
        sl.putConstraint(SpringLayout.WEST, entityDistanceSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, entityDistanceSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel messagesDistanceLabel = new JLabel("Messages distance: ");
        add(messagesDistanceLabel);
        sl.putConstraint(SpringLayout.NORTH, messagesDistanceLabel, 18, SpringLayout.SOUTH, entityDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, messagesDistanceLabel, 5, SpringLayout.WEST, getContentPane());

        messagesDistanceSlider = new JSlider(1, 200);
        messagesDistanceSlider.setName(Constants.PREFERENCES_MESSAGES_DISTANCE);
        messagesDistanceSlider.addChangeListener(this);
        messagesDistanceSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_MESSAGES_DISTANCE, "60")));
        add(messagesDistanceSlider);

        sl.putConstraint(SpringLayout.NORTH, messagesDistanceSlider, 0, SpringLayout.SOUTH, entityDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, messagesDistanceSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, messagesDistanceSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel entityWidthLabel = new JLabel("Entity width: ");
        add(entityWidthLabel);
        sl.putConstraint(SpringLayout.NORTH, entityWidthLabel, 18, SpringLayout.SOUTH, messagesDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, entityWidthLabel, 5, SpringLayout.WEST, getContentPane());

        entityWidthSlider = new JSlider(1, 400);
        entityWidthSlider.setName(Constants.PREFERENCES_ENTITY_WIDTH);
        entityWidthSlider.addChangeListener(this);
        entityWidthSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_ENTITY_WIDTH, "100")));
        add(entityWidthSlider);

        sl.putConstraint(SpringLayout.NORTH, entityWidthSlider, 0, SpringLayout.SOUTH, messagesDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, entityWidthSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, entityWidthSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel entityHeight = new JLabel("Entity height: ");
        add(entityHeight);
        sl.putConstraint(SpringLayout.NORTH, entityHeight, 18, SpringLayout.SOUTH, entityWidthSlider);
        sl.putConstraint(SpringLayout.WEST, entityHeight, 5, SpringLayout.WEST, getContentPane());

        entityHeightSlider = new JSlider(1, 250);
        entityHeightSlider.setName(Constants.PREFERENCES_ENTITY_HEIGHT);
        entityHeightSlider.addChangeListener(this);
        entityHeightSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_ENTITY_HEIGHT, "40")));
        add(entityHeightSlider);

        sl.putConstraint(SpringLayout.NORTH, entityHeightSlider, 0, SpringLayout.SOUTH, entityWidthSlider);
        sl.putConstraint(SpringLayout.WEST, entityHeightSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, entityHeightSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel workingThreadWidthLabel = new JLabel("Working Thread Width: ");
        add(workingThreadWidthLabel);
        sl.putConstraint(SpringLayout.NORTH, workingThreadWidthLabel, 18, SpringLayout.SOUTH, entityHeight);
        sl.putConstraint(SpringLayout.WEST, workingThreadWidthLabel, 5, SpringLayout.WEST, getContentPane());

        workingThreadWidthSlider = new JSlider(1, 30);
        workingThreadWidthSlider.setName(Constants.PREFERENCES_WORKING_THREAD_WIDTH);
        workingThreadWidthSlider.addChangeListener(this);
        workingThreadWidthSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_WORKING_THREAD_WIDTH, "8")));
        add(workingThreadWidthSlider);

        sl.putConstraint(SpringLayout.NORTH, workingThreadWidthSlider, 0, SpringLayout.SOUTH, entityHeight);
        sl.putConstraint(SpringLayout.WEST, workingThreadWidthSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, workingThreadWidthSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel arrowSizeLabel = new JLabel("Arrow Size: ");
        add(arrowSizeLabel);
        sl.putConstraint(SpringLayout.NORTH, arrowSizeLabel, 18, SpringLayout.SOUTH, workingThreadWidthLabel);
        sl.putConstraint(SpringLayout.WEST, arrowSizeLabel, 5, SpringLayout.WEST, getContentPane());

        arrowSizeSlider = new JSlider(3, 40);
        arrowSizeSlider.setName(Constants.PREFERENCES_ARROW_SIZE);
        arrowSizeSlider.addChangeListener(this);
        arrowSizeSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_ARROW_SIZE, "10")));
        add(arrowSizeSlider);

        sl.putConstraint(SpringLayout.NORTH, arrowSizeSlider, 0, SpringLayout.SOUTH, workingThreadWidthLabel);
        sl.putConstraint(SpringLayout.WEST, arrowSizeSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, arrowSizeSlider, -5, SpringLayout.EAST, this.getContentPane());
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals(Constants.PREFERENCES_ENTITY_DISTANCE)) {
            UserPreferences.ENTITY_DISTANCE = slider.getValue();
        }
        else if (slider.getName().equals(Constants.PREFERENCES_MESSAGES_DISTANCE)) {
            UserPreferences.MESSAGES_DISTANCE = slider.getValue();
        }
        else if (slider.getName().equals(UserPreferences.ENTITY_WIDTH)) {
            UserPreferences.ENTITY_WIDTH = slider.getValue();
        }
        else if (slider.getName().equals(Constants.PREFERENCES_WORKING_THREAD_WIDTH)) {
            UserPreferences.WORKING_THREAD_WIDTH = slider.getValue();
        }
        else if (slider.getName().equals(Constants.PREFERENCES_FONT_SIZE)) {
            UserPreferences.DIAGRAM_FONT = new Font((String) jcFont.getSelectedItem(), Font.PLAIN, fontSizeSlider.getValue());
        }
        else if (slider.getName().equals(Constants.PREFERENCES_ARROW_SIZE)) {
            UserPreferences.ARROW_SIZE = slider.getValue();
        }
        main.updateDiagramDrawing();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Close")) {
            Preferences p = Preferences.userRoot();
            p.put(Constants.PREFERENCES_FONT_NAME, (String) jcFont.getSelectedItem());
            p.put(Constants.PREFERENCES_FONT_SIZE, "" + fontSizeSlider.getValue());
            p.put(Constants.PREFERENCES_ENTITY_DISTANCE, "" + entityDistanceSlider.getValue());
            p.put(Constants.PREFERENCES_MESSAGES_DISTANCE, "" + messagesDistanceSlider.getValue());
            p.put(Constants.PREFERENCES_ENTITY_WIDTH, "" + entityWidthSlider.getValue());
            p.put(Constants.PREFERENCES_WORKING_THREAD_WIDTH, "" + workingThreadWidthSlider.getValue());
            p.put(Constants.PREFERENCES_ARROW_SIZE, "" + arrowSizeSlider.getValue());
            this.dispose();
        }
        else if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        }
        else if (e.getActionCommand().equals("comboBoxChanged")) {
            UserPreferences.DIAGRAM_FONT = new Font((String) jcFont.getSelectedItem(), Font.PLAIN, fontSizeSlider.getValue());
            main.updateDiagramDrawing();
        }
    }
}
