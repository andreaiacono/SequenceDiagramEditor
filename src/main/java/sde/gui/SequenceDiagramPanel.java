package sde.gui;

import sde.gui.components.MessageComponent;
import sde.utils.Constants;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Optional;

public class SequenceDiagramPanel extends JPanel implements MouseWheelListener, MouseInputListener {

    private static final long serialVersionUID = 1L;

    private SequenceDiagramDrawing sequenceDiagramDrawing;
    private Main main;
    private float mf = 1.0f;

    public SequenceDiagramPanel(SequenceDiagramDrawing sequenceDiagramDrawing, Main main) {

        this.sequenceDiagramDrawing = sequenceDiagramDrawing;
        this.main = main;
        setSize(2000, 5000);
        setBackground(Constants.BACKGROUND_COLOR);
        setFocusable(true);

        addMouseWheelListener(this);
        addMouseListener(this);
    }

    public static void main(String[] args) {
        SequenceDiagramPanel p = new SequenceDiagramPanel(new SequenceDiagramDrawing(), null);
        p.setVisible(true);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        // gets the viewport of the website scrollpane
        JViewport jv = (JViewport) getParent();

        // gets the center of the page
        Point vp = jv.getViewPosition();

        int negativizer = e.getWheelRotation() < 0 ? -1 : 1;

        // if it's zooming out or scrolling up
        if (isCtrlPressed()) {

            int msx = e.getX();
            int msy = e.getY();
            mf = mf + negativizer * (mf / Constants.MOUSEWHEEL_ZOOM);

            if (mf == 0) {
                mf = 0.01F;
            }

            // centers the page
            vp.x = (int) (vp.x + negativizer * (msx / Constants.MOUSEWHEEL_ZOOM));
            vp.y = (int) (vp.y + negativizer * (msy / Constants.MOUSEWHEEL_ZOOM));
            vp.x = Math.max(vp.x, 0);
            vp.y = Math.max(vp.y, 0);
            jv.setViewPosition(vp);
        }
        else {
            // scrolls down
            vp.y = (int) (vp.y + negativizer * (Constants.MOUSEWHEEL_SCROLL_Y * mf));
            vp.y = Math.max(vp.y, 0);
            jv.setViewPosition(vp);
        }
        repaint();
    }

    public boolean isCtrlPressed() {
        return main.isCtrlPressed();
    }

    public String updateDrawing(String text) {
        String status = sequenceDiagramDrawing.updateDrawing(text);
        repaint();
        return status;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(mf, mf);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            sequenceDiagramDrawing.draw(g2d);
        }
        catch (Exception e) {
            e.printStackTrace();
            main.updateStatusBar(e.getMessage());
        }
    }

    public SequenceDiagramDrawing getSequenceDiagramDrawing() {
        return sequenceDiagramDrawing;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        Optional<MessageComponent> messageComponent = sequenceDiagramDrawing.getMessageFromCoordinates(e.getX(), e.getY());
        main.highlightMessageRow(messageComponent);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
