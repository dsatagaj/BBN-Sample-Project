package interview_project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;
import java.util.*;


public class Meeting_Tracker_MVP {

	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome to this portion of the Calendar/Scheduling Application.");
		System.out.println("To view this project on github, please see https://github.com/dsatagaj/BBN-Sample-Project.");
		System.out.println("To give feedback to David Satagaj, the creator, please email drsatagaj@liberty.edu\n\n");
		//System.out.println(System.getProperty("user.dir"));
		
		String filename; //input filename
		
		System.out.println("Please enter the name of an input file (format: input.csv)");
		filename = scan.nextLine();

		try {
			meetingTracker(filename);
		}
		catch(IOException e) {
			System.out.println("The file does not exist or is not in the right path. Please address this issue.");
			System.out.println("The program will now exit.");
			System.exit(0);
		}
		
		scan.close();
		System.out.println("Thank you for using this program!");
	
	}	
	
	public static void meetingTracker(String filename) throws IOException {
		//Meeting_Tracker_MVP variables
		Scanner scan = new Scanner(System.in);
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		Calendar tempcal = Calendar.getInstance();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("EEE, d MMM yyyy");
		boolean filechecker = false;
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		filechecker = true; //will not make it to this point if file does not exist
		
		
		String cell;
		int numMtgs, mtgDayIndex, totalDays, tempval;
		int noMtgs = -1;
		Vector<String> data = new Vector<String>();

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
				mtgDayIndex = getMtgDayIndex(dow);
				
				
				d1.set(Integer.parseInt(day1[0]), Integer.parseInt(day1[1]), Integer.parseInt(day1[2]));
				d2.set(Integer.parseInt(day2[0]), Integer.parseInt(day2[1]), Integer.parseInt(day2[2]));
				//if dates are out of order, switch them
				if(d1.getTimeInMillis() > d2.getTimeInMillis()) {
					tempcal = d1;
					d1 = d2;
					d2 = tempcal;
				}
				totalDays = (int) ((d2.getTimeInMillis() - d1.getTimeInMillis())/(1000*60*60*24)); //get total days between the two dates
				System.out.println("The date range is " + dateformat.format(d1.getTime()) + 
						" to " + dateformat.format(d2.getTime()));
				System.out.println("Please enter the number of meetings that will be missed in this timeframe.");
				while(noMtgs < 0) {
					try {
						noMtgs = Integer.parseInt(scan.nextLine());
					}
					catch(NumberFormatException e) {
						System.out.println("Please enter an integer greater than or equal to 0.");
						System.out.println("If unsure, enter 0 to see number of meetings between the two dates.");
					}
				}
				tempval = d1.get(Calendar.DAY_OF_WEEK);
				while(mtgDayIndex != tempval) { //moves to nearest meeting date
					totalDays--;
					tempval++;
					if(tempval == 7)
						tempval = 1;
				}
				
				
				numMtgs = 1 + totalDays/7 - noMtgs; //meetings minus user defined days not meeting
				if(numMtgs < 0)
					numMtgs = 0;
				System.out.println("The total number of meetings in the given timeframe will be: " + numMtgs);
				noMtgs = -1;
			}
			else {
				System.out.println("Error parsing file, please check file syntax.");
			}
			
		}
		reader.close();
		scan.close();
	}
	
	public static int getMtgDayIndex(String day) {
		int index = 0;
		
		switch(day) {
		case "Sun":
			index = 1;
			break;
		case "sun":
			index = 1;
			break;
		case "Mon":
			index = 2;
			break;
		case "mon":
			index = 2;
			break;
		case "Tue":
			index = 3;
			break;
		case "tue":
			index = 3;
			break;
		case "Wed":
			index = 4;
			break;
		case "wed":
			index = 4;
			break;
		case "Thu":
			index = 5;
			break;
		case "thu":
			index = 5;
			break;
		case "Fri":
			index = 6;
			break;
		case "fri":
			index = 6;
			break;
		case "Sat":
			index = 7;
			break;
		case "sat":
			index = 7;
			break;
		default:
			System.out.println("An Invalid day was entered, please check syntax.");
		}
		
		
		return index;
	}
	
}
