package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BulbPanel extends JPanel implements ActionListener {
    private Bulb bulb;

    Timer timer;
    Color colour;

    //private final static int DEFAULT_DELAY_TIME = 10;

    public BulbPanel(int width, int height, Bulb bulbObj) {
        setPreferredSize(new Dimension(width, height));
        bulb = bulbObj;
        bulb.setBulbJPanel(this);
        //colour = new Color(); //Update our animations every n ms
        //timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 0.25f)); // 50% darker (change to 0.25f for 25% darker)
        bulb.draw(g);
    }

    public Bulb getBulbInstance() {
        return this.bulb;
    }


    public void setBright(int bright) {
        colour.brighter();
        //timer.setInitialDelay(delay * 10);
        //timer.restart();
    }

    /**
     * this function is called, everytime the timer goes off (in this case 3 ms).
     * https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        bulb.update(); //Let the fan move
        repaint(); //Draw the changes
    }
}