import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/*
 * Class implicates a date panel - has a label for day in the month, the date it represents, and the plans of this day
 */
public class DatePanel extends JPanel {
	private Date panDate = new Date();
	private JLabel dayInMonthLabel;
	//private JLabel plans;
	protected JTextPane plans;
	public DatePanel() {
		setLayout(new BorderLayout());
		//init the day in month label
		dayInMonthLabel = new JLabel();
		dayInMonthLabel.setForeground(Color.black);
		dayInMonthLabel.setSize(10, 10);
		add(dayInMonthLabel,BorderLayout.NORTH);
		//init the plan label
		plans = new JTextPane();
		plans.setEditable(false);
		plans.setForeground(Color.black);
		add(plans,BorderLayout.CENTER);
		
	}
	

	//return this panel date (represented) 
	public Date getDate() {
		return panDate;
	}
	//sets the panel date and lable
	public void setDate(Calendar c) {
		panDate = (c.getTime());
		dayInMonthLabel.setText(""+c.get(Calendar.DAY_OF_MONTH));
		
	}
//sets the date plans
	public void setPlans(String dayPlan) {
		plans.setText(dayPlan);
	}
	

}
