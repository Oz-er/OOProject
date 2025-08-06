package View;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SwitchButton extends Component {

    private Timer timer;
    private float location;
    private boolean selected = true;  // ✅ Initially ON
    private boolean mouseOver;
    private float speed = .2f;
    private List<EventSwitchSelected>events;

    public SwitchButton() {
        setPreferredSize(new Dimension(60, 30));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        events = new ArrayList<>();

        // Initial toggle position
        location = getWidth() - getHeight() + 2;

        // Animation timer
        timer = new Timer(0, ae -> {
            int endLocation = selected ? getWidth() - getHeight() + 2 : 2;
            if (selected && location < endLocation) {
                location += speed;
                repaint();
            } else if (!selected && location > endLocation) {
                location -= speed;
                repaint();
            } else {
                timer.stop();
                location = endLocation;
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me) && mouseOver) {
                    selected = !selected;
                    timer.start();
                    runEvent();
                }
            }
        });

        SwingUtilities.invokeLater(() -> {
            location = getWidth() - getHeight() + 2;
            repaint();
        });
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        location = selected ? getWidth() - getHeight() + 2 : 2;
        repaint();
        runEvent();
    }

    public void addEventSelected(EventSwitchSelected event) {
        events.add(event);
    }

    private void runEvent() {
        for (EventSwitchSelected event : events) {
            event.onSelected(selected);
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();

        // Track background
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(0, 0, width, height, height, height);

        // Toggle background color based on state
        g2.setColor(selected ? new Color(0,128,0) : new Color(211, 47, 47)); // ✅ green/red
        g2.fillRoundRect(0, 0, width, height, height, height);

        // Toggle circle
        g2.setColor(getForeground());
        g2.fillOval((int) location, 2, height - 4, height - 4);

        // Text
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        if (selected) {
            g2.drawString("ON", 8, height / 2 + 5);
        } else {
            g2.drawString("OFF", height + 5, height / 2 + 5);
        }
    }
}