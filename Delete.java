import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        int choice;
        int ID = 0;
        String name = "";
        // database URL
        final String DATABASE_URL = "jdbc:mysql://localhost/schematest";
        Connection connection = null;
        PreparedStatement pstat = null;
        int i = 0;

        // Enter username and press Enter
        System.out.println("Do you want to select a user by:");
        System.out.println("Their ID[1] or their Name[2]");
        System.out.println("Input 1 or 2");
        choice = s1.nextInt();

        if (choice == 1) {
            System.out.print("Input their ID:");
            ID = s1.nextInt();
            System.out.println(" ");
        } else if (choice == 2) {
            System.out.print("Input their Name:");
            name = s1.nextLine();
            System.out.println(" ");
        }
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "GolfZc@r58");
            // create Prepared Statement for deleting data from the table
            if (choice == 1) {
                pstat = connection.prepareStatement("Delete From customer Where ID=?");
                pstat.setInt(1, ID);
            } else if (choice == 2) {
                pstat = connection.prepareStatement("Delete From customer Where Name=?");
                pstat.setString(1, name);
            } else {
                pstat = connection.prepareStatement("Delete From customer Where ID=?");
                pstat.setInt(1, 0);
            }
            // delete data from the table
            i = pstat.executeUpdate();
            System.out.println(i + " record(s) successfully removed from the table .");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                pstat.close();
                connection.close();
                s1.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    } // end main
} // end class
