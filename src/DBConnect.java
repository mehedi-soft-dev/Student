import java.sql.*;

public class DBConnect {
    
    Connection connection = null;
    
    public Connection GetConnection(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","12345");
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}
