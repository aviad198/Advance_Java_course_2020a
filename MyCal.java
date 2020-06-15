import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Calendar program - you can move between dates in year and add plans to your dates
 * 
 */




public class MyCal extends JFrame implements ActionListener, ChangeListener{
	JSpinner yearSpin, monthSpin;
	JLabel yearLabel, monthLabel;
	JPanel bttns; 
	JButton left, right;
	ScdPanel MyScd;

	final int START_YEAR = 1900,  END_YEAR = 2100, JUN = 1, DEC =12;

	Calendar c;


	//Calendar frame with panels of scedual and button to move between dates 
	public MyCal() {
		setLayout(new BorderLayout());


		//add buttons panel
		bttns = new JPanel();
		bttns.setLayout(new FlowLayout());
		//left button
		left = new JButton("<");
		left.addActionListener(this);
		bttns.add(left);

		JLabel yearLabel = new JLabel("Year");
		bttns.add(yearLabel);
		//year spinner to go throw years between 1900 to 2100
		yearSpin = new JSpinner(new SpinnerNumberModel(2020,START_YEAR, END_YEAR, 1));
		yearSpin.setPreferredSize(new Dimension(56, 20));
		yearSpin.setEditor(new JSpinner.NumberEditor(yearSpin, "####"));
		yearSpin.addChangeListener(this);
		bttns.add(yearSpin);

		JLabel monthLabel = new JLabel("Month");
		bttns.add(monthLabel);

		//month spinner from jun to dec
		monthSpin = new JSpinner(new SpinnerNumberModel(1,JUN, DEC, 1));
		monthSpin.setPreferredSize(new Dimension(60, 20));
		monthSpin.setName("Month");
		monthSpin.addChangeListener(this);
		bttns.add(monthSpin);

		//right button
		right = new JButton(">");
		right.addActionListener(this);
		bttns.add(right);

		//init c as calendar (this time)
		c = Calendar.getInstance();
		//inti date to 01.01.2020 (spinners default)
		c.set((int)yearSpin.getValue(), (int)monthSpin.getValue()-1,1);

		//new schedule 
		MyScd = new ScdPanel();
		//sets nfirst date
		MyScd.setPanel(c);

		add(bttns, BorderLayout.NORTH);
		add(MyScd, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		setVisible(true);

	}
	//new calendar
	public static void main(String[] args) {
		MyCal cal = new MyCal();


	}

	@Override
	//update cal when spinners date change
	public void stateChanged(ChangeEvent e) {
		c.set((int)yearSpin.getValue(), (int)monthSpin.getValue()-1,1);		
		MyScd.setPanel(c);
		//repaint();
	}

	@Override
	//change spinners with buttons (right/left) and update cal
	public void actionPerformed(ActionEvent e) {
		int year = (int) yearSpin.getValue();
		int month = (int) monthSpin.getValue();
		//if left bttn
		if (e.getSource()==left) {
			//check that not out of date borders and move spinners
			if (month==1 && year != START_YEAR) {
				yearSpin.setValue(year-1);
				monthSpin.setValue(12);
			}
			else if (year != START_YEAR) {
				monthSpin.setValue(month-1);
			}
		}
		//if right bttn
		if (e.getSource()==right) {
			//check that not out of date borders and move spinners
			if (month==12 && year != END_YEAR) {
				yearSpin.setValue(year+1);
				monthSpin.setValue(1);
			}
			else if (year != END_YEAR) {
				monthSpin.setValue(month+1);
			}

		}
		//sets new date
		c.set((int)yearSpin.getValue(), (int)monthSpin.getValue()-1,1);		
		//init cal
		MyScd.setPanel(c);
	}
}


