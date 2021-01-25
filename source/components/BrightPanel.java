package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrightPanel extends JPanel {

    JLabel 				lbl_bright_value;
    JTextField			txt_bright_value;
    JButton				btn_setBright;

    private Bulb			bulb_instance;

    public BrightPanel(Bulb bulbObj) {
        /* this.lbl_bright_value 	= new JLabel("Bulb brightness :");
        this.txt_bright_value 	= new JTextField("10", 5);
        this.btn_setBright 		= new JButton("Set Brightness");

        this.setLayout(new FlowLayout());
        this.add(lbl_bright_value);
        this.add(txt_bright_value);
        this.add(btn_setBright); */

        this.bulb_instance = bulbObj;

        //setButtonActions();
    }

    public void setBulbInstance(Bulb bulbInstance) {
        this.bulb_instance = bulbInstance;
    }

    public String getBulbBrightValue() {
        return txt_bright_value.getText();
    }

    /* private void setButtonActions() {

        this.btn_setBright.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int brightvalue = Integer.parseInt(txt_bright_value.getText());
                bulb_instance.setBulbBright(brightvalue);

            }
        });
    } */
}