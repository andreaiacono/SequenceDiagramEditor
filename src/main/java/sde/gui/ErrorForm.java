package sde.gui;

import sde.utils.IoUtils;
import sde.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorForm extends EscapableDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JTextArea jtaText;
    private JButton jbClose, jbDetails;
    private JLabel jlMessage;
    private JScrollPane pane;
    private String strMessage, strStack;

    public ErrorForm(String strMessage, String strStack) {
        super();
        setWindowTitle("Error Message");
        setModal(true);
        setSize(1000, 100);
        setResizable(true);
        SwingUtils.centerFrame(this);
        this.strMessage = strMessage;
        this.strStack = strStack;
        init();
    }

    public ErrorForm(Exception ex) {
        this(ex.getMessage(), IoUtils.stackTraceToString(ex));
    }

    private void init() {

        if (strMessage == null) strMessage = "NullPointerException";

        jlMessage = new JLabel("An error has occurred: " + strMessage, JLabel.CENTER);
        jlMessage.setIconTextGap(10);
        Icon icon = (Icon) UIManager.getLookAndFeel().getDefaults().get("OptionPane.errorIcon");
        jlMessage.setIcon(icon);
        jlMessage.setPreferredSize(new Dimension(100, 40));

        jtaText = new JTextArea(10, 50);
        pane = new JScrollPane(jtaText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jtaText.setEditable(false);
        pane.setVisible(false);

        // prints the error on the textarea
        jtaText.append("\n\nERROR: " + strMessage);
        jtaText.append("\n\n" + strStack);
        jtaText.setCaretPosition(0);

        jbClose = new JButton("Close");
        jbClose.addActionListener(this);

        jbDetails = new JButton("Show Details");
        jbDetails.addActionListener(this);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        sl.putConstraint(SpringLayout.WEST, jlMessage, 10, SpringLayout.WEST, this.getContentPane());
        sl.putConstraint(SpringLayout.NORTH, jlMessage, 5, SpringLayout.NORTH, this.getContentPane());
        sl.putConstraint(SpringLayout.EAST, jlMessage, -5, SpringLayout.EAST, this.getContentPane());

        sl.putConstraint(SpringLayout.WEST, pane, 5, SpringLayout.WEST, this.getContentPane());
        sl.putConstraint(SpringLayout.NORTH, pane, 5, SpringLayout.SOUTH, jlMessage);
        sl.putConstraint(SpringLayout.EAST, pane, -5, SpringLayout.EAST, this.getContentPane());
        sl.putConstraint(SpringLayout.SOUTH, pane, -5, SpringLayout.NORTH, jbClose);

        sl.putConstraint(SpringLayout.EAST, jbClose, -5, SpringLayout.EAST, this.getContentPane());
        sl.putConstraint(SpringLayout.SOUTH, jbClose, -5, SpringLayout.SOUTH, this.getContentPane());

        sl.putConstraint(SpringLayout.WEST, jbDetails, 5, SpringLayout.WEST, this.getContentPane());
        sl.putConstraint(SpringLayout.SOUTH, jbDetails, -5, SpringLayout.SOUTH, this.getContentPane());

        add(pane);
        add(jbClose);
        add(jbDetails);
        add(jlMessage);
    }

    protected void setWindowTitle(String strTitle) {
        super.setTitle(strTitle);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Close")) {
            this.dispose();
        }
        else if (e.getActionCommand().equals("Show Details")) {
            changeDetails(false);
        }
        else if (e.getActionCommand().equals("Hide Details")) {
            changeDetails(true);
        }
    }

    private void changeDetails(boolean isToHide) {
        int height = isToHide ? 100 : 500;
        String buttonLabel = isToHide ? "Show" : "Hide";
        this.setSize(1000, height);
        pane.setVisible(!pane.isVisible());
        jbDetails.setText(buttonLabel + " Details");
        repaint();
    }
}