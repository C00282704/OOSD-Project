import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    public static void main(String[] args) {
        // database URL
        final String DATABASE_URL = "jdbc:mysql://localhost/schematest";
        String firstname = "Lisa";
        String address = "Power";
        Connection connection = null;
        PreparedStatement pstat = null;
        int i = 0;
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "GolfZc@r58");
            // create Prepared Statement for updating data in the table
            pstat = connection.prepareStatement("UPDATE customer SET Name=? WHERE Address=?");
            pstat.setString(1, firstname);
            pstat.setString(2, address);
            // Update data in the table
            i = pstat.executeUpdate();
            System.out.println(i + " record successfully updated in the table .");
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
