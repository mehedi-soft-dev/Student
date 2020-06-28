import java.sql.*;

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
    
    public void GivingScore(){
        
    }
}
