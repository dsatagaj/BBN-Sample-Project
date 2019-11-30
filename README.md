# BBN-Sample-Project
This coding project was created as a sample for BBN Technologies (a subsidiary of Raytheon).
Please note that the in-depth solution is under the folder called: BBN_Tech_Sample_Project
Please note that the MVP solution is under the folder called: BBN_Tech_Sample_Project_MVP

# Notes for the in-depth Solution
The in-depth solution has the operability to take user inputs or an input file. This is designed such that if the user has
a pre-written file but wishes to make another query they can do that. Also, please note that in the current input file configuration, 
there is no ability to specify if days will be missed due to holidays or other reasons. To compensate for this, the "Add a No Meeting" 
day feature can be used to specify days that will not be met on prior to running the input file.

# Input Files (e.g. input.csv)
Please note that the input files should be in the following format:\
#input.csv\
#start, end, day of week\
2018-05-02, 2018-12-31, Wednesday\
2019-01-01, 2019-12-31, Thursday\
...

# Sources used: 
# To get up to speed on calendar class
  https://www.geeksforgeeks.org/calendar-class-in-java-with-examples/
# Working with checking meeting dates
  https://knowm.org/get-day-of-week-from-date-object-in-java/ 
# Working with Simple Date Format
  https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
  
  

