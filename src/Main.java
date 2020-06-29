import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Main {
    
    private static final Teacher _currentTeacher = new Teacher();
    private static final Student _currentStudent = new Student();
    
    public static void main(String[] args) {
       Home();
    }
    
    private static void Home(){
        
        System.out.println("Login As....\n\n1. Teacher\n2. Student\n");
        System.out.print("Please Select One (1/2) : ");
        
        Scanner scanner = new Scanner(System.in);
        int c = 0;
        c = Integer.parseInt(scanner.nextLine());
        
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
        
        System.out.println("\nTeacher's Login Panel\n");
        System.out.print("Enter Username : ");
        username = scanner.nextLine();
        System.out.print("Enter Password : ");
        password = scanner.nextLine();
        
        if(IsTeacherLoginSuccess(username, password)){
            System.out.println("Login Succesfully\n");
            TeacherDashboard();
        }
        else{
            System.out.println("Username or Password is incorrect");
        }
        
    }
    private static void StudentLogin(){
        
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("\nStudent Login Panel\n");
        System.out.print("Enter Username : ");
        username = scanner.nextLine();
        System.out.print("Enter Password : ");
        password = scanner.nextLine();
        
        if(IsStudentLoginSuccess(username, password)){
            System.out.println("Login Succesfully\n");
            StudentDashboard();
        }
        else{
            System.out.println("Username or Password is incorrect");
        }
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
            
            if(resultSet.next()){
                isFound = true;
                _currentStudent.id = Integer.parseInt(resultSet.getString("Id").toString());
            }
                
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return isFound;
    }
    private static void TeacherDashboard(){
        
        System.out.println("\nTeacher Dashboard\n");
        System.out.println("1. Starting a Course");
        System.out.println("2. Giving Score to Student");
        System.out.println("3. Log Out\n");
        System.out.print("Choose an Option : ");
        
        Scanner scanner = new Scanner(System.in);
        int c = Integer.parseInt(scanner.nextLine());
        
        switch(c){
            case 1:
                StartingNewCourse();
                TeacherDashboard();
                break;
            
            case 2:
                GivingScore();
                TeacherDashboard();
                break;
                
            case 3:
                _currentTeacher.Id = 0;
                System.out.println("Log Out Succesfully");
                Home();
                break;
        }
    }
    private static void StudentDashboard(){
        
        System.out.println("\nStudent Dashboard\n");
        System.out.println("1. Choose Course");
        System.out.println("2. View Selected Courses");
        System.out.println("3. Search Course By Teacher Name");
        System.out.println("4. View Scores");
        System.out.println("5. Log Out");
        System.out.print("Choose an Option : ");
        
        Scanner scanner = new Scanner(System.in);
        int c = Integer.parseInt(scanner.nextLine());
        
        switch(c){
            case 1:
                ChooseCourse();
                StudentDashboard();
                break;
                
            case 2:
                ViewSelectedCourses();
                StudentDashboard();
                break;
            
            case 3:
                SearchCourse();
                StudentDashboard();
                break;
                
            case 4:
                ViewScores();
                StudentDashboard();
                break;
            case 5:
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
            System.out.println("Something went wrong");
    }
    private static void GivingScore(){
        Scanner scanner = new Scanner(System.in);
        
        int teacherId = _currentTeacher.Id;
        int courseId = 0;
        int studentId = 0;
        int score = 0;
        
        System.out.println("\nGiving Score to the Student\n");
        List<CourseViewModel> courses = new LinkedList<>();
        Teacher t = new Teacher();
        courses = t.SearchCourseByTeacherId(teacherId);
        
        System.out.println("Available Courses");
        System.out.println("SL\tCourse Name\t\tCourse ID\tTeacher");
        System.out.println("--\t-----------\t\t---------\t-------");
        
        Iterator<CourseViewModel> cit = courses.iterator();
        int sl=1;
        while(cit.hasNext()){
            CourseViewModel cvm = cit.next();
            System.out.println(sl++ +"\t"+cvm.courseName+"\t\t\t"+cvm.id+"\t\t"+cvm.teacherName);
        }
        
        System.out.print("\nEnter Course ID : ");
        courseId = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Available Courses");
        System.out.println("SL\tStudent Id\tStudent Name\t\tEmail");
        System.out.println("--\t----------\t------------\t\t-------");
        
        List<Student> students = new LinkedList<>();
        students = t.SearchStudentByCourseId(courseId);
        
        Iterator<Student> stdit = students.iterator();
        int s=1;
        while(stdit.hasNext()){
            Student std = stdit.next();
            System.out.println(s++ +"\t"+std.id+"\t\t"+std.firstName+" "+std.lastName+"\t\t"+std.email);
        }
        
        System.out.print("\nEnter Student ID : ");
        studentId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Score : ");
        score = Integer.parseInt(scanner.nextLine());
        
        if(t.GivingScore(teacherId, studentId, courseId, score)){
            System.out.println("\nSuccess.....");
            System.out.println("\nPress Enter to Continue.....");
            scanner.nextLine();
        }
            
    }
    
    private static void ChooseCourse(){
        Scanner scanner = new Scanner(System.in);
        List<CourseViewModel> courses = new LinkedList<>();
        int courseId = 0;
        int studentId = _currentStudent.id;
        
        Course c = new Course();
        courses = c.GetAllCourses();
        
        
        System.out.println("\nStarting a new course\n");
        System.out.println("Available Courses");
        System.out.println("SL\tCourse Name\t\tCourse ID\tTeacher");
        System.out.println("--\t-----------\t\t---------\t-------");
        
        Iterator<CourseViewModel> it = courses.iterator();
        int sl=1;
        while(it.hasNext()){
            CourseViewModel cvm = it.next();
            System.out.println(sl++ +"\t"+cvm.courseName+"\t\t\t"+cvm.id+"\t\t"+cvm.teacherName);
        }
        
        System.out.print("Choose Course ID : ");
        courseId = Integer.parseInt(scanner.nextLine());
        
        Student s = new Student();
        boolean isAdded = s.ChoosingACourse(studentId, courseId);
        
        if(isAdded){
            System.out.println("Choosing Course Completed"); 
            StudentDashboard();
        }
        else
            System.out.println("Something went wrong");
    }
    private static void SearchCourse(){
        Scanner scanner = new Scanner(System.in);
        List<CourseViewModel> courses = new LinkedList<>();
                
        Student s = new Student();
        
        System.out.println("\nSearch Course by Teacher Name\n");
        System.out.print("Enter Teacher Name : ");
        String teacherName = scanner.nextLine();
        courses = s.SearchCourseByTeacherName(teacherName);
        System.out.println("Available Courses");
        System.out.println("SL\tCourse Name\t\tCourse ID\tTeacher");
        System.out.println("--\t-----------\t\t---------\t-------");
        
        Iterator<CourseViewModel> it = courses.iterator();
        int sl=1;
        while(it.hasNext()){
            CourseViewModel cvm = it.next();
            System.out.println(sl++ +"\t"+cvm.courseName+"\t\t\t"+cvm.id+"\t\t"+cvm.teacherName);
        }
        
        System.out.print("\nPress Enter to continue.......");
        scanner.nextLine();
    }   
    private static void ViewScores(){
        Scanner scanner = new Scanner(System.in);
        List<ScoreViewModel> scores = new LinkedList<>();
        int studentId = _currentStudent.id;
        
        Student s = new Student();
        scores = s.QueryingScores(studentId);
        
        
        System.out.println("\nCourses Score\n");
        System.out.println("SL\tCourse Name\t\tCourse ID\tTeacher\t\t\tScore");
        System.out.println("--\t-----------\t\t---------\t-------\t\t\t-----");
        
        Iterator<ScoreViewModel> it = scores.iterator();
        int sl=1;
        while(it.hasNext()){
            ScoreViewModel svm = it.next();
            System.out.println(sl++ +"\t"+svm.courseName+"\t\t\t"+svm.id+"\t\t"+svm.teacherName+"\t\t"+svm.score);
        }
        
        System.out.print("\nPress Enter to Continue...");
        scanner.nextLine();
    }
    
    private static void ViewSelectedCourses(){
        Scanner scanner = new Scanner(System.in);
        List<CourseViewModel> courses = new LinkedList<>();
        int studentId = _currentStudent.id;
        
        Student s = new Student();
        courses = s.QueryingSelectedCourse(studentId);
        
        
        System.out.println("\nSelected Courses\n");
        System.out.println("SL\tCourse Name\t\tCourse ID\tTeacher");
        System.out.println("--\t-----------\t\t---------\t-------");
        
        Iterator<CourseViewModel> it = courses.iterator();
        int sl=1;
        while(it.hasNext()){
            CourseViewModel cvm = it.next();
            System.out.println(sl++ +"\t"+cvm.courseName+"\t\t\t"+cvm.id+"\t\t"+cvm.teacherName);
        }
        
        System.out.print("\nPress Enter to Continue...");
        scanner.nextLine();
    }
}
