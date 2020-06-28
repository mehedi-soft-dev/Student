import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Main {
    
    private static final Teacher _currentTeacher = new Teacher();
    private static final Student _currentStudent = new Student();
    
    public static void main(String[] args) {
       Student s = new Student();
       s.ViewAllCourse();
        //Home();
    }
    
    private static void Home(){
        
        System.out.println("Login As....\n\n1. Teacher\n2. Student\n");
        System.out.print("Please Select One (1/2) : ");
        
        Scanner scanner = new Scanner(System.in);
        int c = Integer.parseInt(scanner.nextLine());
        
        switch(c){
            case 1:
                TeacherLogin();
                break;
                
            case 2:
                StudentLogin();
                break;
                
            default:
                System.out.println("Enter correct Option!!");
                Home();
                break;
        }
    }
    private static void TeacherLogin(){
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("\nTeacher Login Panel\n");
        System.out.print("Enter Username : ");
        username = scanner.nextLine();
        System.out.print("Enter Password : ");
        password = scanner.nextLine();
        
        if(IsTeacherLoginSuccess(username, password)){
            System.out.println("Login Succesfully\n");
            TeachersDashboard();
        }
        else{
            System.out.println("Username or Password is incorrect");
        }
        
    }
    private static void StudentLogin(){
        System.out.println("\nStudent Login Panel\n");
    }
    private static boolean IsTeacherLoginSuccess(String username, String password){
        
        boolean isFound = false;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Teacher WHERE Username = '"+username+"' AND Password = '"+password+"'";
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            if(resultSet.next()){
               isFound = true;
               _currentTeacher.Id = Integer.parseInt(resultSet.getString("Id"));
            }
                
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return isFound;
    }
    private static boolean IsStudentLoginSuccess(String username, String password){
        
        boolean isFound = false;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Student WHERE Username = '"+username+"' AND Password = '"+password+"'";
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            if(resultSet.next())
                isFound = true;
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return isFound;
    }
    private static void TeachersDashboard(){
        
        System.out.println("\nTeacher's Dashboard\n");
        System.out.println("1. Starting a Course");
        System.out.println("2. Giving Score to Student");
        System.out.println("3. Log Out\n");
        System.out.print("Choose an Option : ");
        
        Scanner scanner = new Scanner(System.in);
        int c = Integer.parseInt(scanner.nextLine());
        
        switch(c){
            case 1:
                StartingNewCourse();
                TeachersDashboard();
                break;
            
            case 3:
                _currentTeacher.Id = 0;
                System.out.println("Log Out Succesfully");
                Home();
                break;
        }
    }
    
    private static void StartingNewCourse(){
        Scanner scanner = new Scanner(System.in);
        String courseName = "";
        int teacherId = _currentTeacher.Id;
        
        System.out.println("\nStarting a new course\n");
        System.out.print("Enter Course Name : ");
        courseName = scanner.nextLine();
        
        Teacher t = new Teacher();
        boolean isAdded = t.StartACourse(teacherId, courseName);
        
        if(isAdded)
            System.out.println("Successfully Started a Course");
        else
            System.out.println("Somethin went wrong");
    }
    
}
