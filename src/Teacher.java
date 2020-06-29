import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Teacher {
    
    int Id;
    String FirstName;
    String LastName;
    String Email;
    String ContactNo;
    String Address;
    
    public boolean StartACourse(int teacherId, String courseName){
        
        boolean isAdded = false;
        Connection connection = null;
        Statement statement = null;
        String query = "INSERT INTO Course (Id, TeacherId, Name) VALUES(1,"+teacherId+", '"+courseName+"')";
        
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            
            statement = connection.createStatement();
            int f = statement.executeUpdate(query);
            
            if(f>0){
                isAdded = true;
            }
                
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return isAdded;
    }
    
    public boolean GivingScore(int teacherId, int studentId, int courseId, int score){
        boolean isAdded = false;
        Connection connection = null;
        Statement statement = null;
        String query = "INSERT INTO Score (TeacherId, StudentId, CourseId, Score) VALUES("+teacherId+", "+studentId+","+courseId+","+score+")";
        
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            
            statement = connection.createStatement();
            int f = statement.executeUpdate(query);
            
            if(f>0){
                isAdded = true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return isAdded;
    }
    
    public List<CourseViewModel> SearchCourseByTeacherId(int teacherid){
        List<CourseViewModel> courses = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT c.Id CourseId, c.Name Course, t.FirstName||' '||t.LastName  Teacher FROM Course  c JOIN Teacher  t ON c.TeacherId = t.Id\n" +
"WHERE c.TeacherId=(SELECT Id FROM Teacher WHERE Id = "+teacherid+") ";
        
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()){
                
                CourseViewModel courseVM = new CourseViewModel();
                
                courseVM.id = Integer.parseInt(resultSet.getString("courseId"));
                courseVM.courseName = resultSet.getString("Course");
                courseVM.teacherName = resultSet.getString("Teacher");
                
                courses.add(courseVM);
            }
            
        }catch(NumberFormatException | SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                resultSet.close();
                statement.close();
                connection.close();
                
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return courses;
    }
    
    public List<Student> SearchStudentByCourseId(int courseId){
        List<Student> students = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT st.Id Id, st.FirstName FirstName, st.LastName LastName, st.Email Email \n" +
                        "FROM SelectedCourse  sc JOIN Student  st\n" +
                        "ON sc.StudentId = st.Id \n" +
                        "WHERE sc.CourseId = 2";
        
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()){
                
                Student s = new Student();
                
                s.id = Integer.parseInt(resultSet.getString("Id"));
                s.firstName = resultSet.getString("FirstName");
                s.lastName = resultSet.getString("LastName");
                s.email = resultSet.getString("Email");
                
                students.add(s);
            }
            
        }catch(NumberFormatException | SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                resultSet.close();
                statement.close();
                connection.close();
                
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return students;
    }
}
