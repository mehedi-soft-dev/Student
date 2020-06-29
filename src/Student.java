
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Student {
    
    int id;
    String studentId;
    String firstName;
    String lastName;
    String email;
    String contactNo;
    String address;
    
    public boolean ChoosingACourse(int studentId, int CourseId){
        
        boolean isAdded = false;
        Connection connection = null;
        Statement statement = null;
        String query = "INSERT INTO SelectedCourse (StudentId, CourseId) VALUES("+studentId+", "+CourseId+")";
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
    
    public List<CourseViewModel> SearchCourseByTeacherName(String teacherName){
        List<CourseViewModel> courses = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT c.Id CourseId, c.Name Course, t.FirstName||' '||t.LastName  Teacher FROM Course  c JOIN Teacher  t ON c.TeacherId = t.Id\n" +
"WHERE c.TeacherId=(SELECT Id FROM Teacher WHERE FirstName Like '%"+teacherName+"%' OR LastName Like '%"+teacherName+"%')";
        
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
    
    public List<CourseViewModel> QueryingSelectedCourse(int studentId){
        List<CourseViewModel> courses = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT c.Id CourseId, c.Name Course, t.FirstName||' '|| t.LastName Teacher\n" +
                        "FROM SelectedCourse sc \n" +
                        "JOIN Course c ON sc.CourseId = c.Id\n" +
                        "JOIN Teacher t ON c.TeacherId = t.Id\n" +
                        "WHERE sc.StudentId = "+studentId+"";
        
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
    
    public List<ScoreViewModel> QueryingScores(int studentId){
        List<ScoreViewModel> scores = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT c.Id CourseId, c.Name Course, s.FirstName||' '||s.LastName Teacher, score.score Score\n" +
                        "FROM Score score\n" +
                        "JOIN Course c ON score.courseid = c.Id\n" +
                        "JOIN Student s ON score.studentid = s.Id\n" +
                        "WHERE score.studentid = 2";
        
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()){
                
                ScoreViewModel scorevm = new ScoreViewModel();
                
                scorevm.id = Integer.parseInt(resultSet.getString("CourseID"));
                scorevm.courseName = resultSet.getString("Course");
                scorevm.teacherName = resultSet.getString("Teacher");
                scorevm.score = Integer.parseInt(resultSet.getString("Score"));
                
                scores.add(scorevm);
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
        return scores;
    }
    
}
