package interview_project;
import java.io.*;
import java.text.*;
import java.util.*;


public class Meeting_Tracker {
	static String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	static Vector<Calendar> noMtgDates = new Vector<Calendar>();
	static String mtg_day = "Wed";
	static int index_mtg_day = 4; //index based on DAY_OF_WEEK (e.g. Sun. is 1, Mon. is 2 etc.)
	static Calendar cal = Calendar.getInstance();
	static Calendar other_date = Calendar.getInstance();
	static Calendar other_date_2 = Calendar.getInstance();
	static SimpleDateFormat dateformat = new SimpleDateFormat("EEE, d MMM yyyy");
	static Scanner scan = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		int temp = 0, option_tracker = 0, whilechecker = 0, tempchecker = 0;
		int number_of_options = 10;
		String filename;
		Meeting_Tracker x = new Meeting_Tracker();
		
		System.out.println("Welcome to this portion of the Calendar/Scheduling Application.");
		System.out.println("Please note that the application factors in how many meetings will be missed due to user-specified holidays.");
		System.out.println("To view this project on github, please see https://github.com/dsatagaj/BBN-Sample-Project.");
		System.out.println("To give feedback to David Satagaj, the creator, please email drsatagaj@liberty.edu\n\n");
		System.out.println(System.getProperty("user.dir"));
		while (whilechecker == 0)
		{
			printOptions();
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(x.scan.nextLine());
					option_tracker = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
			tempchecker = 0;
			while(option_tracker < 0 || option_tracker > number_of_options) {
				System.out.println("Invalid Option Selected, Please Try Again");
				try {
					temp = Integer.parseInt(x.scan.nextLine());
					option_tracker = temp;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}				
			}
			switch(option_tracker) {
			case 1 : //option for getting meetings to the end of the year (12/31/yyyy)
				System.out.println("There are " + x.mtgstoEndofYear() + " meetings until the end of the year.");
				break;
			case 2 : //option for getting meetings between today and a definable date
				System.out.println("There are " + x.mtgstoDefinableDate() + " meetings between \ntoday and " + x.other_date.getTime() + ".");
				break;
			case 3 : //option for getting meetings between two dates
				System.out.println("There are " + x.mtgstoTwoDefDates() + " meetings between \n" + 
						x.other_date.getTime() + " and " + x.other_date_2.getTime() + ".");
				break;
			case 4 : //option for changing meeting day
				if(x.changeMtgDay() == 1)
					System.out.println("Meeting Day changed successfully to " + x.getMtgDay());
				else
					System.out.println("Error changing meeting day, please try again.");
				break;
			case 5 : //option for printing out current meeting day
				System.out.println("The current meeting day is: " + x.getMtgDay() + ".");
				break;
			case 6 : //option for adding no meeting dates
				x.addNoMeetingDate();
				break;
			case 7 : //option for removing no meeting dates
				x.removeNoMeetingDate();
				break;
			case 8 : //option for printing no meeting dates
				printNoMeetingDates();
				break;
			case 9 : //option for reading input from file
				System.out.println("Please enter the name of the input file.");
				filename = scan.nextLine();
				try {
					readFromInputFile(filename);
				}
				catch(IOException e){
					System.out.println("Cannot find file. Please check that the file is in the correct folder or specify the path.");
				}
				break;	
			default:
				System.out.println("Thank you for using this program.");
				whilechecker = -1;
			}
			
			
		}

	}

	public static void readFromInputFile(String filename) throws IOException {
		boolean filechecker = false;
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String cell;
		int numMtgs;
		Vector<String> data = new Vector<String>();
		
		
		filechecker = true;
		if(filechecker) {
			while((cell = reader.readLine()) != null) {
				if(!cell.contains("#")) {
					String[] temp = cell.split(",");
					for(int i = 0; i < temp.length; i++) {
						temp[i] = temp[i].replace(" ", "");
						data.add(temp[i]);
					}
				}
			}
		}
		for(int i = 0; i < data.size() - 1; i = i + 3) {
			if(data.get(i).contains("-") && data.get(i+1).contains("-") && data.get(i+2).contains("day")) {
				String[] day1 = data.get(i).split("-");
				String[] day2 = data.get(i+1).split("-");
				String dow = data.get(i+2).substring(0,3);
				setOtherDate(Integer.parseInt(day1[0]), Integer.parseInt(day1[1]), Integer.parseInt(day1[2]));
				setOtherDate(Integer.parseInt(day2[0]), Integer.parseInt(day2[1]), Integer.parseInt(day2[2]));
				changeMtgDay(dow);
				numMtgs = mtgstoTwoDefDates(other_date, other_date_2);
				System.out.println("The number of meetings between " + dateformat.format(other_date.getTime()) + 
						" and " + dateformat.format(other_date_2.getTime()) + " is " + numMtgs);
				
			}
			else {
				System.out.println("Error parsing file, please check file syntax.");
			}
			
		}
		reader.close();
	}
	
	public static void addNoMeetingDate()
	{
		int temp = 0, tempchecker = 0, year = 0, month = 0, date = 0;
		Calendar tempcal = Calendar.getInstance();
		
		//user inputs
		System.out.println("The current date is " + cal.getTime() + ". Please choose a date after this one.");
		System.out.println("Which date will a meeting not be held?");
		System.out.print("Please enter the year of the No Meeting Date: ");
		while(tempchecker == 0) {
			try {
				temp = Integer.parseInt(scan.nextLine());
				year = temp;
				tempchecker = 1;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
		while(year < cal.get(Calendar.YEAR) || year > cal.get(Calendar.YEAR) + 2 ) { //arbitrarily limited to up to 2 years in the future
			System.out.println("Invalid year entered, please try again. (Please note this works up to two years in the future.)");
			tempchecker = 0;
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(scan.nextLine());
					year = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
		}
		System.out.print("Please enter the month of the No Meeting Date: ");
		tempchecker = 0;
		while(tempchecker == 0) {
			try {
				temp = Integer.parseInt(scan.nextLine());
				month = temp;
				tempchecker = 1;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
		while((month < cal.get(Calendar.MONTH) && year == cal.get(Calendar.YEAR)) || month > 12) { 
			System.out.println("Invalid month entered, please try again. "
					+ "(Please note date being entered must be further in the future to today)");
			tempchecker = 0;
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(scan.nextLine());
					month = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
		}
		System.out.print("Please enter the date of the No Meeting Date: ");
		tempchecker = 0;
		while(tempchecker == 0) {
			try {
				temp = Integer.parseInt(scan.nextLine());
				date = temp;
				tempchecker = 1;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
		while( (date < cal.get(Calendar.DATE) && year == cal.get(Calendar.YEAR) && month == cal.get(Calendar.MONTH) + 1) || date > 31) { 
			System.out.println("Invalid date entered, please try again. "
					+ "(Please note date being entered must be further in the future to today)");
			tempchecker = 0;
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(scan.nextLine());
					date = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
		}
		//set temp date so it can be added to vector noMtgDates
		tempcal.set(year, month - 1, date);
		noMtgDates.add(tempcal);
	}
	
	public static void removeNoMeetingDate()
	{
		int tempchecker = 0, temp, todelete = 0;
		Calendar tempcal;
		if(noMtgDates.size() == 0) {
			System.out.println("There are currently no dates in the No Meeting Date list.");
		}
		else {
			System.out.println("The current No Meeting Dates are:");
			printNoMeetingDates();
			System.out.println("Please enter the index (number) of the No Meeting Date you wish to delete.");
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(scan.nextLine());
					todelete = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
			while(todelete > noMtgDates.size() || todelete < 0)
			{
				System.out.println("An invalid number was entered. Please try again.");
				try {
					temp = Integer.parseInt(scan.nextLine());
					todelete = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
			tempcal = noMtgDates.get(todelete - 1);
			System.out.println("The date that is being deleted is " + dateformat.format(tempcal.getTime()));
			noMtgDates.remove(todelete - 1);
		}
	}
	
	public static void printNoMeetingDates()
	{
		int ret = 0;
		Calendar temp;
		if(noMtgDates.size() == 0) {
			System.out.println("There are currently zero No Meeting Dates.");
			ret = 1;
		}
		if(ret == 0) {
			System.out.println("The current No Meeting Dates are: ");
			for(int i = 0; i < noMtgDates.size(); i++) {
				temp = noMtgDates.get(i);
				System.out.println((i + 1) + ". " + dateformat.format(temp.getTime()));
			}
		}
	}
	
	public static void printOptions() {
		System.out.println("\nMeeting Calculation Options:");
		System.out.println("Please Enter the Number of the Option You'd like to Select.\n");
		System.out.println("\t~1. Calculate Meetings to the End of the Year");
		System.out.println("\t~2. Calculate Meetings from Now until a Date of Your Choice");
		System.out.println("\t~3. Calculate Meetings between two Dates");
		System.out.println("\t~4. Change Meeting Date");
		System.out.println("\t~5. Display Meeting Day");
		System.out.println("\t~6. Add a No Meeting Day");
		System.out.println("\t~7. Remove No Meeting Days");
		System.out.println("\t~8. Print Current No Meeting Days");
		System.out.println("\t~9. Parse an Input file (format: input.csv)");
		System.out.println("\t~10. Exit Program");
	}
	
	public static int mtgstoEndofYear()
	{
		//Set other date to Dec. 31 of current year
		setOtherDate(cal.get(Calendar.YEAR), 11, 31);
		//necessary variables
		int index = 0; //index of day of the week
		int remaining_mtgs; //remaining meetings before end of year
		
		//find ms between current day and end of year
		long start = cal.getTimeInMillis(); //today's date in ms
		long end = other_date.getTimeInMillis(); //Dec. 31's date in ms
		long ms_to_days = 1000*60*60*24; //ms * sec * min * hrs
		long days_to_eoy = (end-start)/ms_to_days; //days to end of year
		String strDate = dateformat.format(cal.getTime()); //current day of the week
		strDate = strDate.substring(0,3); //just the day of the week
		
		//sets index to be the index of the current day (begins at 0)
		for(int i = 0; i < days.length; i++) {
			if(days[i].contains(strDate)) {
				index = i;
			}
		}
		
		//moves to day of next meeting
		while(index != getMtgDayInt()) { //checks index against index of meeting day
			days_to_eoy --; //changes amount of days to end of year
			index++; //keeps changing index if it does not equal index of meeting day
			if(index == 7) //rolls index over if meeting day is prior to current day of week
				index = 0;
		}
		//remaining meetings is equal to 1 plus remaining days divided by 7
		remaining_mtgs = 1 + ((int)days_to_eoy/7);
		
		return remaining_mtgs - checkNoMtgDate(cal, other_date);
	}
	
	public static int mtgstoDefinableDate() {
		//variable definitions
		int temp = 0, tempchecker = 0, year = 0, month = 0, date = 0;
		int index = 0; //index of day of the week
		int remaining_mtgs; //remaining meetings before end of year
		
		//user inputs
		System.out.println("The current date is " + dateformat.format(cal.getTime()) + ". Please choose a date after this one.");
		System.out.print("Please enter the year to read until: ");
		while(tempchecker == 0) {
			try {
				temp = Integer.parseInt(scan.nextLine());
				year = temp;
				tempchecker = 1;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
		while(year < cal.get(Calendar.YEAR) || year > cal.get(Calendar.YEAR) + 2 ) { //arbitrarily limited to up to 2 years in the future
			System.out.println("Invalid year entered, please try again. (Please note this works up to two years in the future.)");
			tempchecker = 0;
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(scan.nextLine());
					year = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
		}
		System.out.print("Please enter the month to read until: ");
		tempchecker = 0;
		while(tempchecker == 0) {
			try {
				temp = Integer.parseInt(scan.nextLine());
				month = temp;
				tempchecker = 1;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
		while((month < cal.get(Calendar.MONTH) && year == cal.get(Calendar.YEAR)) || month > 12) { 
			System.out.println("Invalid month entered, please try again. "
					+ "(Please note date being entered must be further in the future to today)");
			tempchecker = 0;
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(scan.nextLine());
					month = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
		}
		System.out.print("Please enter the date to read until: ");
		tempchecker = 0;
		while(tempchecker == 0) {
			try {
				temp = Integer.parseInt(scan.nextLine());
				date = temp;
				tempchecker = 1;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
		while( (date < cal.get(Calendar.DATE) && year == cal.get(Calendar.YEAR) && month == cal.get(Calendar.MONTH) + 1) || date > 31) { 
			System.out.println("Invalid date entered, please try again. "
					+ "(Please note date being entered must be further in the future to today)");
			tempchecker = 0;
			while(tempchecker == 0) {
				try {
					temp = Integer.parseInt(scan.nextLine());
					date = temp;
					tempchecker = 1;
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter an integer.");
				}
			}
		}
		//set other date
		setOtherDate(year, month - 1, date);
		
		
		//find ms between current day and other date
		long start = cal.getTimeInMillis(); //today's date in ms
		long end = other_date.getTimeInMillis(); //other date in ms
		long ms_to_days = 1000*60*60*24; //ms * sec * min * hrs
		long days_to_other_date = (end-start)/ms_to_days; //days to other date
		String strDate = dateformat.format(cal.getTime()); //current day of the week
		strDate = strDate.substring(0,3); //just the day of the week
		
		//sets index to be the index of the current day (begins at 0)
		for(int i = 0; i < days.length; i++) {
			if(days[i].contains(strDate)) {
				index = i;
			}
		}
		
		//moves to day of next meeting
		while(index != getMtgDayInt()) { //checks index against index of meeting day
			days_to_other_date --; //changes amount of days to other date
			index++; //keeps changing index if it does not equal index of meeting day
			if(index == 7) //rolls index over if meeting day is prior to current day of week
				index = 0;
		}
		//remaining meetings is equal to 1 plus remaining days divided by 7
		remaining_mtgs = 1 + ((int)days_to_other_date/7);
		
		return remaining_mtgs - checkNoMtgDate(cal, other_date);
	}
	
	public static int mtgstoTwoDefDates() {
		//variable definitions
				int year = 0, month = 0, date = 0;
				int year1 = 0, month1 = 0, date1 = 0;
				int tempchecker = 0, temp = 0;
				Calendar tempcal;
				int index = 0; //index of day of the week
				int remaining_mtgs; //remaining meetings before end of year
				
				//user inputs
				System.out.println("You are entering two dates to find the number of meetings between them.");
				System.out.println("\nFor the first date to read between:\n");	
				System.out.print("Please enter the year to read until: ");
				tempchecker = 0;
				while(tempchecker == 0) {
					try {
						temp = Integer.parseInt(scan.nextLine());
						year = temp;
						tempchecker = 1;
					}
					catch(NumberFormatException e) {
						System.out.println("Please enter an integer.");
					}
				}
				while(year < cal.get(Calendar.YEAR) || year > cal.get(Calendar.YEAR) + 2 ) { //arbitrarily limited to up to 2 years in the future
					System.out.println("Invalid year entered, please try again. (Please note this works up to two years in the future.)");
					tempchecker = 0;
					while(tempchecker == 0) {
						try {
							temp = Integer.parseInt(scan.nextLine());
							year = temp;
							tempchecker = 1;
						}
						catch(NumberFormatException e) {
							System.out.println("Please enter an integer.");
						}
					}
				}
				System.out.print("Please enter the month to read until: ");
				tempchecker = 0;
				while(tempchecker == 0) {
					try {
						temp = Integer.parseInt(scan.nextLine());
						month = temp;
						tempchecker = 1;
					}
					catch(NumberFormatException e) {
						System.out.println("Please enter an integer.");
					}
				}
				while((month < cal.get(Calendar.MONTH) && year == cal.get(Calendar.YEAR)) || month > 12) { 
					System.out.println("Invalid month entered, please try again. "
							+ "(Please note date being entered must be further in the future to today)");
					tempchecker = 0;
					while(tempchecker == 0) {
						try {
							temp = Integer.parseInt(scan.nextLine());
							month = temp;
							tempchecker = 1;
						}
						catch(NumberFormatException e) {
							System.out.println("Please enter an integer.");
						}
					}
				}
				System.out.print("Please enter the date to read until: ");
				tempchecker = 0;
				while(tempchecker == 0) {
					try {
						temp = Integer.parseInt(scan.nextLine());
						date = temp;
						tempchecker = 1;
					}
					catch(NumberFormatException e) {
						System.out.println("Please enter an integer.");
					}
				}
				while( (date < cal.get(Calendar.DATE) && year == cal.get(Calendar.YEAR) && month == cal.get(Calendar.MONTH) + 1) || date > 31) { 
					System.out.println("Invalid date entered, please try again. "
							+ "(Please note date being entered must be further in the future to today)");
					tempchecker = 0;
					while(tempchecker == 0) {
						try {
							temp = Integer.parseInt(scan.nextLine());
							date = temp;
							tempchecker = 1;
						}
						catch(NumberFormatException e) {
							System.out.println("Please enter an integer.");
						}
					}
				}
				//set other date
				setOtherDate(year, month - 1, date);
				
				System.out.println("\nFor the second date to read between:\n");
				System.out.print("Please enter the year to read until: ");
				tempchecker = 0;
				while(tempchecker == 0) {
					try {
						temp = Integer.parseInt(scan.nextLine());
						year1 = temp;
						tempchecker = 1;
					}
					catch(NumberFormatException e) {
						System.out.println("Please enter an integer.");
					}
				}
				while(year1 < cal.get(Calendar.YEAR) || year1 > cal.get(Calendar.YEAR) + 2 ) { //arbitrarily limited to up to 2 years in the future
					System.out.println("Invalid year entered, please try again. (Please note this works up to two years in the future.)");
					tempchecker = 0;
					while(tempchecker == 0) {
						try {
							temp = Integer.parseInt(scan.nextLine());
							year1 = temp;
							tempchecker = 1;
						}
						catch(NumberFormatException e) {
							System.out.println("Please enter an integer.");
						}
					}
				}
				System.out.print("Please enter the month to read until: ");
				tempchecker = 0;
				while(tempchecker == 0) {
					try {
						temp = Integer.parseInt(scan.nextLine());
						month1 = temp;
						tempchecker = 1;
					}
					catch(NumberFormatException e) {
						System.out.println("Please enter an integer.");
					}
				}
				while((month1 < cal.get(Calendar.MONTH) && year1 == cal.get(Calendar.YEAR)) || month1 > 12) { 
					System.out.println("Invalid month entered, please try again. "
							+ "(Please note date being entered must be further in the future to today)");
					tempchecker = 0;
					while(tempchecker == 0) {
						try {
							temp = Integer.parseInt(scan.nextLine());
							month1 = temp;
							tempchecker = 1;
						}
						catch(NumberFormatException e) {
							System.out.println("Please enter an integer.");
						}
					}
				}
				System.out.print("Please enter the date to read until: ");
				tempchecker = 0;
				while(tempchecker == 0) {
					try {
						temp = Integer.parseInt(scan.nextLine());
						date1 = temp;
						tempchecker = 1;
					}
					catch(NumberFormatException e) {
						System.out.println("Please enter an integer.");
					}
				}
				while( (date1 < cal.get(Calendar.DATE) && year1 == cal.get(Calendar.YEAR) && month1 == cal.get(Calendar.MONTH) + 1) || date1 > 31) { 
					System.out.println("Invalid date entered, please try again. "
							+ "(Please note date being entered must be further in the future to today)");
					tempchecker = 0;
					while(tempchecker == 0) {
						try {
							temp = Integer.parseInt(scan.nextLine());
							date1 = temp;
							tempchecker = 1;
						}
						catch(NumberFormatException e) {
							System.out.println("Please enter an integer.");
						}
					}
				}
				//set other date
				setOtherDate2(year1, month1 - 1, date1);
				
				if(other_date.getTimeInMillis() > other_date_2.getTimeInMillis()) {
					tempcal = other_date_2;
					other_date_2 = other_date;
					other_date = tempcal;
				}
				//find ms between current day and other date
				long start = other_date.getTimeInMillis(); //date 1's date in ms
				long end = other_date_2.getTimeInMillis(); //date 2's date in ms
				long ms_to_days = 1000*60*60*24; //ms * sec * min * hrs
				long days_to_other_date = java.lang.Math.abs(end - start)/ms_to_days; //days to other date
				String strDate = dateformat.format(other_date.getTime()); //current day of the week
				strDate = strDate.substring(0,3); //just the day of the week
				//sets index to be the index of the current day (begins at 0)
				for(int i = 0; i < days.length; i++) {
					if(days[i].contains(strDate)) {
						index = i;
					}
				}
				
				//moves to day of next meeting
				while(index != getMtgDayInt() && days_to_other_date > 0) { //checks index against index of meeting day
					days_to_other_date --; //changes amount of days to other date
					index++; //keeps changing index if it does not equal index of meeting day
					if(index == 7) //rolls index over if meeting day is prior to current day of week
						index = 0;
				}
				//remaining meetings is equal to 1 plus remaining days divided by 7
				if(days_to_other_date > 7)
					remaining_mtgs = 1 + ((int)days_to_other_date/7);
				else
					remaining_mtgs = 0 + ((int)days_to_other_date/7);
				
				return remaining_mtgs - checkNoMtgDate(other_date, other_date_2);
	}
	
	public static int mtgstoTwoDefDates(Calendar d1, Calendar d2) {
		//variable definitions
		Calendar tempcal;
		int index = 0; //index of day of the week
		int remaining_mtgs; //remaining meetings before end of year
		
				
		//set other date
		setOtherDate(d1.get(Calendar.YEAR), d1.get(Calendar.MONTH), d1.get(Calendar.DATE));				
		//set other date 2
		setOtherDate2(d2.get(Calendar.YEAR), d2.get(Calendar.MONTH), d2.get(Calendar.DATE));
				
		if(other_date.getTimeInMillis() > other_date_2.getTimeInMillis()) {
			tempcal = other_date_2;
			other_date_2 = other_date;
			other_date = tempcal;
		}
		//find ms between current day and other date
		long start = other_date.getTimeInMillis(); //date 1's date in ms
		long end = other_date_2.getTimeInMillis(); //date 2's date in ms
		long ms_to_days = 1000*60*60*24; //ms * sec * min * hrs
		long days_to_other_date = java.lang.Math.abs(end - start)/ms_to_days; //days to other date
		String strDate = dateformat.format(other_date.getTime()); //current day of the week
		strDate = strDate.substring(0,3); //just the day of the week
		//sets index to be the index of the current day (begins at 0)
		for(int i = 0; i < days.length; i++) {
			if(days[i].contains(strDate)) {
				index = i;
			}
		}
				
		//moves to day of next meeting
		while(index != getMtgDayInt() && days_to_other_date > 0) { //checks index against index of meeting day
			days_to_other_date --; //changes amount of days to other date
			index++; //keeps changing index if it does not equal index of meeting day
			if(index == 7) //rolls index over if meeting day is prior to current day of week
				index = 0;
		}
		//remaining meetings is equal to 1 plus remaining days divided by 7
		if(days_to_other_date > 7)
			remaining_mtgs = 1 + ((int)days_to_other_date/7);
		else
			remaining_mtgs = 0 + ((int)days_to_other_date/7);
		
		return remaining_mtgs - checkNoMtgDate(other_date, other_date_2);
	}
	
	public static void setOtherDate(int year, int month, int date) {
		other_date.set(year, month, date);
	}
	
	public static void setOtherDate2(int year, int month, int date) {
		other_date_2.set(year, month, date);
	}

	public static int changeMtgDay() {
		System.out.println("Please enter a new meeting day using the index value.");
		printDays();
		System.out.println(" 1.   2.   3.   4.   5.   6.   7.");
		int temp = 0, tempchecker = 0;
		while(tempchecker == 0) {
			try {
				temp = Integer.parseInt(scan.nextLine());
				tempchecker = 1;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
			if(temp < 1 || temp > 7) {
				tempchecker = 0;
				System.out.println("Please enter an index between 1 & 7.");
				printDays();
				System.out.println(" 1.   2.   3.   4.   5.   6.   7.");
			}
		}
		for (int i = 0; i <= days.length - 1; i++)
		{
			if(temp-1 == i)
			{
				mtg_day = days[i];
				setIndexMtgDay(temp);
				return 1;
			}
		}
		System.out.println("An invalid day was entered. Please try again.");
		return -1;
	}

	public static void changeMtgDay(String newDay) {
		switch(newDay) {
		case "Sun":
			setIndexMtgDay(1);
			mtg_day = newDay;
			break;
		case "sun":
			setIndexMtgDay(1);
			mtg_day = newDay;
			break;
		case "Mon":
			setIndexMtgDay(2);
			mtg_day = newDay;
			break;
		case "mon":
			setIndexMtgDay(2);
			mtg_day = newDay;
			break;
		case "Tue":
			setIndexMtgDay(3);
			mtg_day = newDay;
			break;
		case "tue":
			setIndexMtgDay(3);
			mtg_day = newDay;
			break;
		case "Wed":
			setIndexMtgDay(4);
			mtg_day = newDay;
			break;
		case "wed":
			setIndexMtgDay(4);
			mtg_day = newDay;
			break;
		case "Thu":
			setIndexMtgDay(5);
			mtg_day = newDay;
			break;
		case "thu":
			setIndexMtgDay(5);
			mtg_day = newDay;
			break;
		case "Fri":
			setIndexMtgDay(6);
			mtg_day = newDay;
			break;
		case "fri":
			setIndexMtgDay(6);
			mtg_day = newDay;
			break;
		case "Sat":
			setIndexMtgDay(7);
			mtg_day = newDay;
			break;
		case "sat":
			setIndexMtgDay(7);
			mtg_day = newDay;
			break;
		default:
			System.out.println("An Invalid day was entered, please check syntax.");
		}
	}
	
	public static void setIndexMtgDay(int x) {
		if(x <= 7 && x >= 1) {
			index_mtg_day = x;
		}
		else
			System.out.println("Invalid index entered. Please try again.");
	}
	
	public static String getMtgDay() {
		return mtg_day;
	}
	
	public static int getMtgDayInt() {
		return index_mtg_day;
	}
	
	public static void printDays() {
		System.out.println("Possible Days:");
		for(int i = 0; i < days.length - 1; i++) {
			System.out.print(days[i] + ", ");
		}
		System.out.println(days[days.length - 1]);
	}

	public static int checkNoMtgDate(Calendar cal1, Calendar cal2)
	{
		int subtractmtgs = 0;
		Calendar temp;
		long date1ms = cal1.getTimeInMillis();
		long date2ms = cal2.getTimeInMillis();
		long tempms;
		if(date1ms > date2ms)
		{
			temp = cal1;
			cal1 = cal2;
			cal2 = temp;
		}
		for(int i = 0; i < noMtgDates.size(); i++) {
			temp = noMtgDates.get(i);
			tempms = temp.getTimeInMillis();
			if((tempms >= date1ms && tempms <= date2ms) && (temp.get(Calendar.DAY_OF_WEEK) == getMtgDayInt()) )
			{
				subtractmtgs++;
			}
		}
		System.out.println("The total number of meetings missed between the two given dates will be: " + subtractmtgs + ".");
		return subtractmtgs;
	}
	
}
