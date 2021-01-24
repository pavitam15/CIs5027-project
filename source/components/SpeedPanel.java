package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SpeedPanel extends JPanel{
	
	JLabel 				lbl_speed_value;
	JTextField			txt_speed_value;
	JButton				btn_setSpeed;
	
	private Fan			fan_instance;
			
	public SpeedPanel(Fan fanObj) {
		//this.lbl_speed_value 	= new JLabel("Fan speed (Delay in ms) :");
		//this.txt_speed_value 	= new JTextField("10", 5);
		//this.btn_setSpeed 		= new JButton("Set Speed");
		
		//this.setLayout(new FlowLayout());
		//this.add(lbl_speed_value);
		//this.add(txt_speed_value);
		//this.add(btn_setSpeed);
		
		this.fan_instance = fanObj;
		
		//setButtonActions();
	}
	
	public void setFanInstance(Fan fanInstance) {
		this.fan_instance = fanInstance;
	}

	public String getFanSpeedValue() {
		return null;
	}

	/*private void setButtonActions() {
		
		this.btn_setSpeed.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				float timervalue = Float.parseFloat(t.getData());
				fan_instance.setFanSpeed(timervalue);
				
			}
		});
	}*/
}
