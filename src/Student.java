
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Student {
    
    int Id;
    String FirstName;
    String LastName;
    String Email;
    String ContactNo;
    String Address;
    
    public void ViewAllCourse(){
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT c.Name Course, t.FirstName||' '||t.LastName Teacher FROM Course c JOIN Teacher t ON c.TeacherId = t.Id";
        
        try{
            DBConnect dbcon = new DBConnect();
            connection = dbcon.GetConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            System.out.println("Course\t\tTeacher");
            System.out.println("------\t\t------");
            
            while(resultSet.next()){
                String course = resultSet.getString("Course").toString();
                String teacher = resultSet.getString("Teacher").toString();
                
                System.out.println(course+"\t\t"+teacher);
                
            }
           
                
            
        }catch(Exception ex){
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
        
    }
    public void ChoosingACourse(){
        
    }
    public void QueryingCourseByTeacherName(){
        
    }
    public void QueryingSelectedCourse(){
        
    }
    public void QueryingScores(){
        
    }
}
