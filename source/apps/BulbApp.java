package apps;

import components.BrightPanel;
import components.Bulb;
import components.BulbPanel;

import javax.swing.*;
import java.awt.*;

public class BulbApp extends JFrame {

    // static instance to support singleton
    private static BulbApp	bulbui_instance;

    private BulbPanel bulb_panel;
    private BrightPanel bright_panel;
    private Bulb bulb_instance;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                BulbApp.getBulbUIInstance();
            }
        } );
    }

    /**
     * Public getinstance method to create an instance of the AppFrame class.
     *
     * @return an instance of AppFrame class.
     */
    public static BulbApp getBulbUIInstance() {
        if(bulbui_instance == null) {
            bulbui_instance = new BulbApp();
        }

        return bulbui_instance;
    }

    /**
     * Constructor
     */
    public BulbApp() {
        super();

        bulb_instance = new Bulb(150, 150);

        bulb_panel = new BulbPanel(300, 300, bulb_instance);
        bright_panel = new BrightPanel(bulb_instance);

        add(bright_panel, BorderLayout.NORTH);
        add(bulb_panel, BorderLayout.CENTER);

        setVisible(true);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}