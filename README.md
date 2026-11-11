# COMP-3005-A3-Q1-Submission
This repository contains my submission for COMP 3005 Assignment 3 Question 1

Video of me running the application https://youtu.be/Oq7eoJ64ULo


To Run this code:
1. create an SQL database that you will use to test this application
2. Copy the SQL Script into your SQL environment and run the queries using query tool. 


3. Download the a3 java package (make sure you also have working java on your computer)
4. Open the folder in VScode or your preferred environment
5. Go to A3 java package/src/main/java/studentApp/StudentApp.java

6. Update lines 7,8, and 9 in StudentApp.java to reflect the URL of your database, your DB username and your DB password.
   Unfortunately I cannot do this for you as I do not know the name of the database that you will be testing on. 


    private static final String DB_URL = "jdbc:postgresql://**YOUR URL HERE**";
    private static final String DB_USER = "**YOUR USERNAME HERE, usually postgres**"; 
    private static final String DB_PASSWORD = "**YOUR PASSWORD HERE**";

7. Now that you are connected, run studentapp.java and view results in your output terminal. 
