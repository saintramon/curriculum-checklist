/**
 * @author Jasmin, Ramon Emmiel
 * @author Domingo, Diamond Darrel
 * @author Rabang, Gebreyl Isaac
 * @author Baltazar, Rudyard Lans
 * @author Urbiztondo, Karl Jasper
 * @author Bumanglag, AU
 *
 * CURRICULUM CHECKLIST
 *
 * This program allows a Computer Science student to monitor their progress throughout their degree program
 * by providing a checklist of required courses and electives.
 */

import java.lang.*;
import java.nio.file.*;
import java.util.*;
import java.io.*;

public class CurriculumChecklist {
    private final Scanner userInput = new Scanner(System.in);
    private Scanner filePointer;
    private final String sourceFile = "BSCSCurriculumData1.txt";

    /**
     * This is the main method where the calling of the run() method is done
     * @param args
     */
    public static void main(String[] args) {
        CurriculumChecklist curriculumChecklist;
        try {
            curriculumChecklist = new CurriculumChecklist();
            curriculumChecklist.run();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File is not Found!");
            System.out.println(fileNotFoundException.getMessage());
        } catch (Exception generalException) {
            generalException.printStackTrace();
        }
    }

    /**
     * The run exception is the executable method that runs the whole logic of the program
     * It asks the user if they are an old or new student that is using this program.
     * @throws Exception
     */
    public void run() throws Exception {

        System.out.println("*STUDENT TYPE*");
        System.out.println("<1> NEW (I do not have my own personal record)");
        System.out.println("<2> OLD (I have my own personal record)");
        int studentType = Integer.parseInt(userInput.nextLine());

        switch (studentType){
            case 1:
                newStudent();
                break;
            case 2:
                oldStudent();
                break;
        }

    }


    /**
     * The newStudent() method enables a new user to create their own academic record to monitor their degree checklist.
     *
     * 1. Create an ArrayList called courseList and put the data of the course list file into it.
     * 2. Get the basic information of the student by calling studentInfo()
     * 3. Create the personal record of the new student by using its IDNumber as an identification
     * 4. Show the user the main menu of the program
     * @throws FileNotFoundException
     */
    public void newStudent() throws FileNotFoundException{
        int courseElements = countLines(sourceFile);

        ArrayList<Course> courseList = new ArrayList<Course>(courseElements);
        courseList = readData(sourceFile);

        String[] studentInfo = studentInfo();
        String IDNumber = studentInfo[0];
        String currentYear = studentInfo[1];
        String currentTerm = studentInfo[2];

        String generatedFile = generatePersonalRecord(IDNumber);
        int menuChoice = 999;

        while (menuChoice != 0){
            showMenu();
            menuChoice = Integer.parseInt(userInput.nextLine());
            switch (menuChoice){
                case 1:
                    showPersonalRecord(courseList, IDNumber);
                    break;
                case 2:
                    editPersonalRecord(courseList);
                    break;
                case 3:
                    showCurrentTakenCourses(courseList, IDNumber, currentYear,currentTerm);
                    break;
                case 4:
                    showFinishedCourses(courseList, IDNumber, currentYear, currentTerm);
                    break;
                case 5:
                    showCurriculumPerTerm(courseList);
                    break;
                case 0:
                    writeToPersonalRecord(courseList, generatedFile);
                    System.out.println("Thank you for using this checklist. Goodluck on your Academic Journey!");
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * The oldStudent() method allows a returning user to manipulate and monitor their degree journey such as adding a grade, editing a course, and such.
     *
     * 1. Create an arrayList to put the original course list datafile inside it.
     * 2. Ask the user what is their IDNumber to identify their old files
     * 3. Create another arrayList called studentRecord and put the student's existing personal record file into it.
     * 4. Show the main menu of the program
     *
     * @throws FileNotFoundException
     */
    public void oldStudent() throws FileNotFoundException{
        int courseElements = countLines(sourceFile);

        ArrayList<Course> courseList = new ArrayList<Course>(courseElements);
        courseList = readData(sourceFile);

        ArrayList<Course> studentRecord = new ArrayList<Course>(courseElements);

        System.out.print("ID NUMBER: ");
        String IDNumber = userInput.nextLine();
        String fileName = "Generated Personal Records/"+IDNumber+"PersonalRecord.txt";

        System.out.print("CURRENT YEAR: ");
        String currentYear = userInput.nextLine();

        System.out.print("CURRENT TERM: ");
        String currentTerm = userInput.nextLine();


        try{
            studentRecord = readData(fileName);
        }catch (FileNotFoundException fileNotFoundException){
            System.out.println("The file of "+IDNumber+" cannot be found");
        }

        int menuChoice = 999;

        while (menuChoice != 0){
            showMenu();
            menuChoice = Integer.parseInt(userInput.nextLine());
            switch (menuChoice){
                case 1:
                    showPersonalRecord(studentRecord, IDNumber);
                    break;
                case 2:
                    editPersonalRecord(studentRecord);
                    break;
                case 3:
                    showCurrentTakenCourses(studentRecord,IDNumber, currentYear,currentTerm);
                    break;
                case 4:
                    showFinishedCourses(studentRecord, IDNumber, currentYear, currentTerm);
                    break;
                case 5:
                    showCurriculumPerTerm(studentRecord);
                    break;
                case 0:
                    writeToPersonalRecord(studentRecord, fileName);
                    System.out.println("Thank you for using this checklist. Goodluck on your Academic Journey!");
                    System.exit(0);
                    break;
            }
        }

    }


    /**
     * The writeToPersonalRecord() method allows manipulating and managing a student record. This method writes and edit the personal record
     * file of the current user.
     * @param studentRecord
     * @param fileName
     * @throws FileNotFoundException
     */
    public void writeToPersonalRecord(ArrayList<Course> studentRecord, String fileName) throws FileNotFoundException{
        try{
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < studentRecord.size(); i++){
                writer.write(studentRecord.get(i).toString());
                writer.write(System.lineSeparator());
            }
            writer.close();
        }catch (FileNotFoundException fileNotFoundException){
            System.out.println("File to be edited cannot be found");
        }catch (IOException ioException){
            System.out.println("An error occurred while trying to edit the file.");
        }
    }

    /**
     * The generatePersonalRecord() method allows the new student to create their personal record. The generated text file will then be put into the
     * Generated Personal Records directory.
     * @param IDNumber
     * @return
     */
    public String generatePersonalRecord(String IDNumber){
        String generatedFileName = "Generated Personal Records/"+IDNumber+"PersonalRecord.txt";

        try{
            File newStudentFile = new File(generatedFileName);
            newStudentFile.createNewFile();
        }catch (FileAlreadyExistsException fileAlreadyExistsException){
            System.out.println("Fila name already exists.");
        }catch (Exception e){
            System.out.println("There's an exception occurred while creating the file.");
        }

        return generatedFileName;
    }


    /**
     * The studentInfo() method will ask the user their ID number, current year level, and current term. The method will then display their inputs back for checking.
     * @return
     */
    public String[] studentInfo(){
        String[] studentInfo = new String[3];


        System.out.print("ENTER ID NUMBER: ");
        studentInfo[0] = userInput.nextLine();
        System.out.print("ENTER YEAR LEVEL: ");
        studentInfo[1] = userInput.nextLine();
        System.out.print("ENTER CURRENT TERM: ");
        studentInfo[2] = userInput.nextLine();

        System.out.println("ID NUMBER: " + studentInfo[0]);
        System.out.println("STUDENT YEAR LEVEL: " + studentInfo[1]);
        System.out.println("STUDENT CURRENT TERM: " + studentInfo[2]);

        return studentInfo;
    }


    /**
     * The showMenu() method will print out the whole main menu of the program as an option for the user.
     */
    public void showMenu(){
        System.out.println();
        System.out.println("*MY CHECKLIST MANAGEMENT*");
        System.out.println("<1> Show Complete Personal Record");
        System.out.println("<2> Edit Personal Record");
        System.out.println("<3> Show Courses currently being Taken");
        System.out.println("<4> Show Finished Courses");
        System.out.println("<5> Show BSCS Curriculum");
        System.out.println("<0> Exit");
        System.out.println();
    }


    /**
     * The countLines() method will count the lines of the text file that are passed into it.
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public int countLines(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(sourceFile));
        int lines = 0;

        while (sc.hasNextLine()) {
            sc.nextLine();
            lines++;
        }
        return lines;
    }


    /**
     * The readData() method will read the data of a text file then returns an ArrayList containing the data of the passed text file.
     *
     * 1. Create a new ArrayList courses which will be the storage of the data of the text file
     * 2. Initialize a File called file and pass the file name into it.
     * 3. Inside the try block, Create another filePointer and pass the file into it
     * 4. While text file that are read still has a next Line
     * 5. Split the current line using commas and store into a String[] array called courseData
     * 6. The index 0 data will be stored into yearLevel
     * 7. Index 1 data will be stored into term
     * 8. Index 2 data will be stored into courseNumber
     * 9. Index 3 data will be stored into courseTitle
     * 10. Index 4 data will be stored into courseUnits
     * 11. If the length of courseData is greater than 5, as it contains the other information:
     * 12. Index 5 data will be stored into courseGrade
     * 13. Index 6 will be stored into isCourseTaken
     * 14. Index 7 will be stored into isBeingTaken
     * 15. An object of Course will then be created passing the variables into its parameters
     * 16. The newly created Course object will then be added in the ArrayList courses
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public ArrayList<Course> readData(String fileName) throws FileNotFoundException {
        ArrayList<Course> courses = new ArrayList<Course>();

        String lineOfText = "";
        int lines = 0;
        int index = 0;

        File file = new File(fileName);

        try {
            filePointer = new Scanner(file);
            while (filePointer.hasNextLine()) {
                lines++;

                lineOfText = filePointer.nextLine();
                String[] courseData = lineOfText.split(",");
                int yearLevel = Integer.parseInt(courseData[0].trim());
                int term = Integer.parseInt(courseData[1].trim());
                String courseNumber = courseData[2];
                String courseTitle = courseData[3];
                Double courseUnits = Double.parseDouble(courseData[4].trim());
                if(courseData.length > 5){
                    String courseGrade = courseData[5];
                    boolean isCourseTaken = Boolean.parseBoolean(courseData[6]);
                    boolean isBeingTaken = Boolean.parseBoolean(courseData[7]);
                    Course newCourse = new Course(yearLevel, term, courseNumber, courseTitle, courseUnits, courseGrade, isCourseTaken, isBeingTaken);
                    courses.add(newCourse);
                    index++;
                }else {
                    Course newCourse = new Course(yearLevel, term, courseNumber, courseTitle, courseUnits);
                    courses.add(newCourse);
                }
            }
        }catch (FileNotFoundException fileNotFoundException){
            System.out.println("File not found.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("An Exception occurred while reading the data in line " + lines);

        }
        return courses;
    }


    /**
     * The showPersonalRecord() will show the whole curriculum of Computer Science with Grades as additional information
     * @param personalRecord
     * @param IDNumber
     */
    public void showPersonalRecord(ArrayList<Course> personalRecord, String IDNumber){
        int collegeYears = 4;
        int collegeTerms = 3;

        System.out.println();
        System.out.println("*** PERSONAL RECORD ***");
        for (int year = 1; year <= collegeYears; year++) {
            for (int term = 1; term <= collegeTerms; term++) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("ID NUMBER: " + IDNumber);
                System.out.println("Year = " + numberInWords(year) + " Year" + " Term = " + numberInWords(term) + " Term");
                System.out.println();
                System.out.printf("%-13s%-110s%-10s%-55s%n", "Course No", "Descriptive title", "Units", "Grade");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
                for (int index = 0; index < personalRecord.size(); index++) {
                    if (personalRecord.get(index).getYearLevel() == year && personalRecord.get(index).getTerm() == term)
                        System.out.printf("%-13s%-110s%-10.1f%-55s%n", personalRecord.get(index).getCourseNumber(), personalRecord.get(index).getCourseTitle(), personalRecord.get(index).getCourseUnits(), personalRecord.get(index).getCourseGrade());
                }
                System.out.println();
                System.out.println("Press <ENTER> to proceed to the next term...");
                userInput.nextLine();
            }
        }
    }

    /**
     * The editPersonalRecord() will show a sub menu that will allow the user to edit a grade for a course,
     * add course from current term, and remove course from current term
     * @param personalRecord
     */
    public void editPersonalRecord(ArrayList<Course> personalRecord){
        int userChoice = 999;

        while (userChoice != 0){
            System.out.println("*PERSONAL RECORD MENU*");
            System.out.println("<1> Edit Grade for a Course");
            System.out.println("<2> Add Course from current term");
            System.out.println("<3> Remove Course from current term");
            System.out.println("<0> Back");
            userChoice = Integer.parseInt(userInput.nextLine());

            switch (userChoice){
                case 1:
                    editGrade(personalRecord);
                    break;
                case 2:
                    addCourseCurrentTerm(personalRecord);
                    break;
                case 3:
                    removeCourseCurrentTerm(personalRecord);
            }
        }

    }

    /**
     * The showCurrentTakenCourses() will show the student what are the courses they are currently taking in the current term, the grades of each
     * courses currently being taken will be changed into "Currently Taking"
     * @param personalRecord
     * @param IDNumber
     * @param currentYear
     * @param currentTerm
     */
    public void showCurrentTakenCourses(ArrayList<Course> personalRecord, String IDNumber,String currentYear, String currentTerm){

        System.out.println();
        System.out.println("*** COURSES BEING TAKEN ***");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("ID NUMBER: " + IDNumber);
        System.out.println("Current Year = " + numberInWords(Integer.parseInt(currentYear)) + " Year" + " Current Term = " + numberInWords(Integer.parseInt(currentTerm)) + " Term");
        System.out.println();
        System.out.printf("%-13s%-110s%-10s%-55s%n", "Course No", "Descriptive title", "Units", "Grade");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int index = 0; index < personalRecord.size(); index++) {
            if (personalRecord.get(index).isBeingTaken() == true){
                personalRecord.get(index).setCourseGrade("Currently Taking");
                System.out.printf("%-13s%-110s%-10.1f%-55s%n", personalRecord.get(index).getCourseNumber(), personalRecord.get(index).getCourseTitle(), personalRecord.get(index).getCourseUnits(), personalRecord.get(index).getCourseGrade());
            }

        }
    }

    /**
     * The showFinishedCourses() will allow the student to monitor what are their finished courses in their degree program.
     * @param personalRecord
     * @param IDNumber
     * @param currentYear
     * @param currentTerm
     */
    public void showFinishedCourses(ArrayList<Course> personalRecord, String IDNumber, String currentYear, String currentTerm){
        System.out.println();
        System.out.println("*** FINISHED COURSES ***");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("ID NUMBER: " + IDNumber);
        System.out.println("Current Year = " + numberInWords(Integer.parseInt(currentYear)) + " Year" + " Current Term = " + numberInWords(Integer.parseInt(currentTerm)) + " Term");
        System.out.println();
        System.out.printf("%-13s%-110s%-10s%-55s%n", "Course No", "Descriptive title", "Units", "Grade");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int index = 0; index < personalRecord.size(); index++) {
            if (personalRecord.get(index).isCourseTaken() == true){
                System.out.printf("%-13s%-110s%-10.1f%-55s%n", personalRecord.get(index).getCourseNumber(), personalRecord.get(index).getCourseTitle(), personalRecord.get(index).getCourseUnits(), personalRecord.get(index).getCourseGrade());
            }

        }
    }

    /**
     * The showCurriculumPerTerm() will allow the user to view the entire Computer Science curriculum of Saint Louis University
     * @param courseList
     */
    public void showCurriculumPerTerm(ArrayList<Course> courseList){
        int collegeYears = 4;
        int collegeTerms = 3;

        System.out.println();
        System.out.println("*** BACHELOR OF SCIENCE IN COMPUTER SCIENCE CURRICULUM ***");
        for (int year = 1; year <= collegeYears; year++) {
            for (int term = 1; term <= collegeTerms; term++) {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Year = " + numberInWords(year) + " Year" + " Term = " + numberInWords(term) + " Term");
                System.out.println();
                System.out.printf("%-13s%-110s%-10s%n", "Course No", "Descriptive title", "Units");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
                for (int index = 0; index < courseList.size(); index++) {
                    if (courseList.get(index).getYearLevel() == year && courseList.get(index).getTerm() == term)
                        System.out.printf("%-13s%-110s%-10.1f%n",
                                courseList.get(index).getCourseNumber(), courseList.get(index).getCourseTitle(), courseList.get(index).getCourseUnits());
                }
                System.out.println();
                System.out.println("Press <ENTER> to proceed to the next term...");
                userInput.nextLine();
            }
        }
    }

    /**
     * The editGrade() method will allow the user to add a final grade into a course, this course will then be marked as a "finished" course
     * @param personalRecord
     */
    public void editGrade(ArrayList<Course> personalRecord){
        String courseNumber = "";
        String courseGrade = "";
        int userChoice = 99;

        System.out.println();
        System.out.println("*EDIT GRADE FOR A COURSE*");
        System.out.println();
        System.out.println("NOTE: This program is case sensitive. It is required to follow spacing and naming conventions to avoid errors");
        System.out.println("Course Number Examples: CS 123, CS 121, CFE 102");
        System.out.println();
        System.out.print("INPUT COURSE NUMBER: ");
        courseNumber = userInput.nextLine();

        for (int i = 0; i < personalRecord.size(); i++){
            if (courseNumber.equalsIgnoreCase(personalRecord.get(i).getCourseNumber())){
                System.out.print("ENTER GRADE FOR " + personalRecord.get(i).getCourseNumber() + ": ");
                courseGrade = userInput.nextLine();
                personalRecord.get(i).setCourseGrade(courseGrade);
                personalRecord.get(i).setIsCourseTaken(true);
                System.out.println("GRADE EDITING FINISHED");
                System.out.println();
                return;
            }
        }

        System.out.println("Sorry, Course not found");
        return;
    }

    /**
     * The removeCourseCurrentTerm() will allow the user to remove a course they are currently taking in their record
     * @param personalRecord
     */
    public void removeCourseCurrentTerm(ArrayList<Course> personalRecord){
        System.out.println();
        System.out.println("* REMOVE COURSE CURRENTLY BEING TAKEN *");
        System.out.println("NOTE: This program is case sensitive. It is required to follow spacing and naming conventions to avoid errors");
        System.out.println("Course Number Examples: CS 123, CS 121, CFE 102");
        System.out.println();
        System.out.print("INPUT COURSE NUMBER: ");
        String courseNumber = userInput.nextLine();

        for (int i = 0; i < personalRecord.size(); i++){
            if (courseNumber.equalsIgnoreCase(personalRecord.get(i).getCourseNumber())){
                System.out.println("Removed the Course successfully.");
                personalRecord.get(i).setIsBeingTaken(false);
                System.out.println();
                return;
            }
        }
        System.out.println("Sorry, Course not found");
        return;
    }

    /**
     * The addCourseCurrentTerm() will allow the user to add a course they are currently taking in the current term
     * @param personalRecord
     */
    public void addCourseCurrentTerm(ArrayList<Course> personalRecord){
        System.out.println();
        System.out.println("* ADD COURSE CURRENTLY BEING TAKEN *");
        System.out.println("NOTE: This program is case sensitive. It is required to follow spacing and naming conventions to avoid errors");
        System.out.println("Course Number Examples: CS 123, CS 121, CFE 102");
        System.out.println();
        System.out.print("INPUT COURSE NUMBER: ");
        String courseNumber = userInput.nextLine();

        for (int i = 0; i < personalRecord.size(); i++){
            if (courseNumber.equalsIgnoreCase(personalRecord.get(i).getCourseNumber())){
                System.out.println("Added the Course successfully.");
                personalRecord.get(i).setIsBeingTaken(true);
                System.out.println();
                return;
            }
        }

        System.out.println("Sorry, Course not found");
        return;
    }

    /**
     * the numberInWords() method will return the word-equivalent of a number
     * @param number
     * @return
     */
    public String numberInWords(int number){
        String numberInWords = "";
        switch (number){
            case 1:
                numberInWords = "First";
                break;
            case 2:
                numberInWords = "Second";
                break;
            case 3:
                numberInWords = "Third";
                break;
            case 4:
                numberInWords = "Fourth";
                break;
        }

        return numberInWords;
    }
}