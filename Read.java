
//Displaying the contents of the Authors table .
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Read {
    public static void main(String args[]) {
        // DATABASE_URL
        final String DATABASE_URL = "jdbc:mysql://localhost/schematest";
        Connection connection = null;
        PreparedStatement pstat = null;
        ResultSet resultSet = null;
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "GolfZc@r58");
            // create Prepared Statement for querying data in the table
            pstat = connection.prepareStatement("SELECT ID, Name, Address FROM customer");

            // query data in the table
            resultSet = pstat.executeQuery();
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            // System.out.println( numberOfColumns);
            System.out.println("customer Table of schematest Database:\n");
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.print(metaData.getColumnName(i) + "\t");
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.print(resultSet.getObject(i) + "\t");
                System.out.println();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
                pstat.close();
                connection.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    } // end main
} // end class
