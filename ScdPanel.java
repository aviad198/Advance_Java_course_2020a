import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/*
 * A panel to implement the schedule, has a schedule panel with date panels
 * and a text panel with a button to enter plans to schedule
 */

public class ScdPanel extends JPanel implements ActionListener {
	Calendar preMonth =  Calendar.getInstance();
	Calendar month = Calendar.getInstance();
	Calendar aftMonth =  Calendar.getInstance();
	JPanel textArea, scdArea;
	Map<Date,String > myPlans = new HashMap<>();
	ArrayList <DatePanel> disDays = new ArrayList<DatePanel>();
	JButton enter;
	JTextArea textPlan;
	DatePanel tempDate;
	Color temp, pnColor = new Color (153,204,255), mColor = new Color (0,102,204), selectColor =new Color (204,255,255);
	final int SHOW_DATES = 43;

	public ScdPanel () {
		setLayout(new BorderLayout());

		//Panel for textinput and panel for the Schedule 
		textArea = new JPanel();
		scdArea = new JPanel();

		//layout of Schedule - 7 col for days in week. row for day name and 6 rows for days in month
		scdArea.setLayout(new GridLayout(7, 7,10,10));
		scdArea.setBackground(Color.LIGHT_GRAY);

		//Area to enter plans to Schedule
		textArea = new JPanel();
		textArea.setLayout(new FlowLayout());

		enter = new JButton("Enter");
		enter.addActionListener(this);

		JLabel enterPlan = new JLabel("Enter your plan in the box:");

		//Add a scroller with text area
		textPlan = new JTextArea("Type plan here...",3,15);
		textPlan.setEditable(false);
		JScrollPane scroll = new JScrollPane(textPlan);

		//add button and text area at bottom
		textArea.add(enterPlan);
		textArea.add(scroll);
		textArea.add(enter);
		add(textArea, BorderLayout.SOUTH);

		//Initialize previous and next months
		initializeMonth();
		//add the name of days at top
		addDayName();

		//Add Panels for all dates to panel and to aArraylist of panels
		for (int i =1; i<SHOW_DATES;i++) { 
			DatePanel date = new DatePanel();
			disDays.add(date);
			disDays.get(i-1).addMouseListener(new Listener());
			disDays.get(i-1).plans.addMouseListener(new Listener());
			scdArea.add(disDays.get(i-1));
		}
		add(scdArea, BorderLayout.CENTER);

	}
	//"paints" the Schedule
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		//restart day to the first day of the month
		month.set(Calendar.DAY_OF_MONTH,1);
		//get the day the month starts at
		int startDay = month.get(Calendar.DAY_OF_WEEK);
		//counters for  and next month
		int j =1;
		int k =1;

		//loop throw all days in schedule
		for (int i =1; i<SHOW_DATES;i++) { 
			//Erase prev plan from panel
			disDays.get(i-1).setPlans("");
			//The previous month
			if (i < startDay) {
				//set Background of prev/next month
				disDays.get(i-1).setBackground(pnColor);
				//set current date (the num of days in prev month - start day + 1 + this number of day in scd)
				preMonth.set(Calendar.DAY_OF_MONTH, preMonth.getActualMaximum(Calendar.DAY_OF_MONTH)-startDay+1+i);
				//set the panel date
				disDays.get(i-1).setDate(preMonth);
				//if there are plans in hashmap for this date add them
				if (myPlans.containsKey(preMonth.getTime())) {
					disDays.get(i-1).setPlans(myPlans.get(preMonth.getTime()));
				}

			}
			//This month
			//if i is smaller then number of days in month + the day we started from -1 
			else if(i <= month.getActualMaximum(Calendar.DAY_OF_MONTH)+startDay-1) {
				//set Background of month
				disDays.get(i-1).setBackground(mColor);
				//set current date (the num of days in prev month - start day + 1 + this number of day in scd)
				month.set(Calendar.DAY_OF_MONTH,k);
				//set the panel date
				disDays.get(i-1).setDate(month);
				//if there are plans in hashmap for this date add them
				if (myPlans.containsKey(month.getTime())) {
					disDays.get(i-1).setPlans(myPlans.get(month.getTime()));
				}
				k++;


			}

			else {
				//set Background of prev/next month
				disDays.get(i-1).setBackground(pnColor);
				//set current date (the num of days in prev month - start day + 1 + this number of day in scd)
				aftMonth.set(Calendar.DAY_OF_MONTH, j);
				//set the panel date
				disDays.get(i-1).setDate(aftMonth);
				//if there are plans in hashmap for this date add them
				if (myPlans.containsKey(aftMonth.getTime())) {
					disDays.get(i-1).setPlans(myPlans.get(aftMonth.getTime()));
				}
				j++;

			}

		}
	}

	//init the prev and next months
	private void initializeMonth() {
		//init the prev month - if first month init to last month of last year
		int lastMonth = month.get(Calendar.MONTH) >0 ? month.get(Calendar.MONTH)-1 : 11;
		preMonth.set(month.get(Calendar.YEAR),lastMonth,1);
		if (lastMonth == 11)
			preMonth.set(month.get(Calendar.YEAR)-1,lastMonth,1);
		//init the next month - if last month init to first month of next year
		int nextMonth = month.get(Calendar.MONTH) <11 ? month.get(Calendar.MONTH)+1 : 1;
		aftMonth.set(month.get(Calendar.YEAR),nextMonth,1);
		if (nextMonth == 1)
			aftMonth.set(month.get(Calendar.YEAR)+1,lastMonth,1);
	}

	//adds name of days to panel
	private void addDayName() {
		String colname[] = { "Sunday", "Monday", "Tuesday", "wednesday", "Thursday", "Friday", "Saturday" };
		for (int i = 0; i < 7; i++) {
			JLabel day = new JLabel(colname[i]);
			day.setHorizontalAlignment(JLabel.CENTER);
			scdArea.add(day);}
	}

	//gets the year and month from the spinners
	public  void setPanel(Calendar c) {
		month = c;
		repaint();
	}






	//when month clicked - add plans to this month
	private class Listener extends MouseAdapter {


		//if source is textpan (not the panel) send panel
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof DatePanel)
				selectPanel((DatePanel) e.getSource());
			else if (e.getSource() instanceof JTextPane){
				JTextPane tempPane = (JTextPane) e.getSource();
				selectPanel((DatePanel) tempPane.getParent());
			}

		}
		//select the clicked panel and add text to it
		private void selectPanel(DatePanel datePanel) {
			//stop entering text
			textPlan.setEditable(false);
			//empty text panel
			textPlan.setText("");
			//if double click - cancel selection
			if(tempDate == datePanel) {
				tempDate.setBackground(temp);
				textPlan.setText("Enter your plan here");
				tempDate =null;}
			else if (datePanel!=tempDate) {
				//remove selection (color) of prev panel
				if (tempDate != null)
					tempDate.setBackground(temp);
				//gets a pointer to the panel
				tempDate = datePanel;
				//change selected date color
				temp = tempDate.getBackground();
				tempDate.setBackground(selectColor);
				//if there is already plans for this date - keep them and add the new plan
				if (myPlans.containsKey(tempDate.getDate())) {
					textPlan.setText(myPlans.get(tempDate.getDate()));
				}
				//Allow to enter text
				textPlan.setEditable(true);}			
		}
	}

	@Override
	//when button pressed
	public void actionPerformed(ActionEvent e) {
		if (textPlan.isEditable()) {
		//stop entering text
		textPlan.setEditable(false);	
		//change back background color
		myPlans.put(tempDate.getDate(),textPlan.getText());
		//init text area
		textPlan.setText("Enter your plan here");

		repaint();}
	}





}
