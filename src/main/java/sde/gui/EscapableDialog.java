package sde.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class EscapableDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    protected JRootPane createRootPane() {
        ActionListener actionListener = actionEvent -> setVisible(false);
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane;
    }
}
