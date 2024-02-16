import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertA {
    public static void main(String[] args) {
        // database URL
        final String DATABASE_URL = "jdbc:mysql://localhost/schematest";
        Connection connection = null;
        PreparedStatement pstat = null;
        String firstname = "Steve";
        String address = "Jobs";
        int i = 0;
        try {
            // establish connection to database

            connection = DriverManager.getConnection(DATABASE_URL, "root", "GolfZc@r58");

            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(Name) FROM customer";
            // Executing the query
            ResultSet rs = stmt.executeQuery(query);
            // Retrieving the result
            rs.next();
            int count = rs.getInt(1);
            count = count + 1;

            // create Prepared Statement for inserting data into table
            pstat = connection.prepareStatement("INSERT INTO customer (ID, Name, Address) VALUES (?,?,?)");
            pstat.setInt(1, count);
            pstat.setString(2, firstname);
            pstat.setString(3, address);
            // insert data into table
            i = pstat.executeUpdate();
            System.out.println(i + " record successfully added to the table .");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                pstat.close();
                connection.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    } // end main
} // end class
