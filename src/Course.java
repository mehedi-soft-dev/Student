import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Course {
    int Id;
    String Code;
    String Name;
    int TeacherId;
    
    public List<CourseViewModel> GetAllCourses(){
        
        List<CourseViewModel> courses = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT c.Id CourseId, c.Name Course, t.FirstName||' '||t.LastName Teacher FROM Course c JOIN Teacher t ON c.TeacherId = t.Id";
        
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
}
