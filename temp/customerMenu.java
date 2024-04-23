import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class customerMenu extends JFrame implements ActionListener, ItemListener {
    private JButton createButton, readButton, updateButton, deleteButton, exitButton;

    int count = 0;
    boolean selected = false;

    private JLabel label[];
    private JPanel panel;
    private JFrame frame;
    private JComboBox<String> c1;
    private JButton submitBTN;
    private JPanel panel2;
    private JButton toMenu; 

    private int numberOfColumns;
    private Connection connection;
    private ResultSet resultSet;
    private Statement pstat;
    private Icon banner;

    JTextField inputName;
    JTextField inputAddress;

    final String DATABASE_URL = "jdbc:mysql://localhost/schematest";

    public customerMenu() {
        setTitle("Gun Shop");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gun Shop title
        JLabel titleLabel = new JLabel("Customer Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);

        // Add Gun Shop logo
        ImageIcon logoIcon = new ImageIcon("gun_logo.png");
        JLabel logoLabel = new JLabel(logoIcon, JLabel.CENTER);

        // Create buttons
        createButton = createStyledButton("Add New Customer");
        readButton = createStyledButton("Display Customer Detail");
        updateButton = createStyledButton("Update Customer Details");
        deleteButton = createStyledButton("Delete Customer");
        exitButton = createStyledButton("Main Menu");

        // Add action listeners
        createButton.addActionListener(this);
        readButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Layout components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(logoLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 5, 5));
        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(Color.decode("#eed57a"));

        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBackground(Color.decode("#eed57a"));

        // Add panel to frame
        getContentPane().add(panel);

        setVisible(true);
    }

//STYLED BUTTONS
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

//ACTION PERFORMED
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            // Handle create operation
            addRecordToDatabase("odo", "Ozcar");
        } else if (e.getSource() == readButton) {
            // Handle read operation
            dispose();
            Read();
        } else if (e.getSource() == updateButton) {
            // Handle update operation
            dispose();
            Update();
        } else if (e.getSource() == deleteButton) {
            // Handle delete operation
            JOptionPane.showMessageDialog(this, "Delete operation clicked");
        } else if (e.getSource() == exitButton) {
            // Handle exit operation
            int response = JOptionPane.showConfirmDialog(this, "Go to Main Menu?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                new Menu();
            }
        }
        else if (e.getSource() == toMenu) 
        {
            int response = JOptionPane.showConfirmDialog(frame, "Go to Customer menu?", "Read Exit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                frame.dispose();
                new customerMenu();
            }
        }
        else if (e.getSource() == submitBTN)
        {
            int response = JOptionPane.showConfirmDialog(frame, "Apply Update?", "Update Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) 
                {
                    try
                    {
                        System.out.println("Submitted!");
                        pstat.executeUpdate("Update customer SET Name = '" + inputName.getText() + "', Address = '" + inputAddress.getText() + "' WHERE ID = '"+ label[9].getText()+"'");
                        frame.dispose();
                        Reload("Update");
                    }
                    catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
        }
    }

    
    private void addRecordToDatabase(String name, String address) {
        // Database URL
        final String DATABASE_URL = "jdbc:mysql://localhost/schematest";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "oscar1234")) {
            String query = "INSERT INTO customer (Name, Address) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            int rowsAffected = preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, rowsAffected + " record successfully added to the database.");
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(this, "Error occurred while adding record to the database: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new customerMenu();
    }
public void itemStateChanged(ItemEvent e)
{
    // if the state combobox is changed
    if (e.getSource() == c1) 
    {
        try
        {
            resultSet = pstat.executeQuery("SELECT ID,Name,Address FROM customer WHERE Name='"+c1.getSelectedItem()+"'");
            int k = 9;
            resultSet.next();
            for (int j = 1; j <= numberOfColumns; j++)
            {
                label[k].setText(resultSet.getObject(j) + "\t");
                k++;
            }
        if(selected == true)
        {
            panel.remove(inputAddress);
            panel.remove(inputName);
            panel.remove(submitBTN);
            panel.remove(label[12]);
        }
            panel.setBorder(BorderFactory.createEmptyBorder(50, 200, 100, 200));
            label[12].setText("");
            panel.add(label[12]);
            label[13].setText("New Name: ");
            panel.add(label[13]);
            label[14].setText("New Address: ");
            panel.add(label[14]);
            label[15].setText("");
            panel.add(label[15]);
            inputName = new JTextField();
            panel.add(inputName);
            inputAddress = new JTextField();
            panel.add(inputAddress);
            label[16].setText("");
            panel.add(label[16]);
            submitBTN = new JButton("Submit");
            submitBTN.addActionListener((ActionListener) this);
            panel.add(submitBTN);
            

            selected = true;
            //resultSet.next();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

public void Reload(String c)
    {
        if(c == "Update")
        {
            Update();
        }
    }

//CRUD
    public void Read() {
        try
        {
            frame = new JFrame();
            panel = new JPanel();

            panel.setBorder(BorderFactory.createEmptyBorder(50, 500, 200, 500));
            panel.setLayout(new GridLayout(0, 3, 5, 5));
            panel.setBackground(Color.decode("#eed57a"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "GolfZc@r58");

            // create Prepared Statement for querying data in the table
            PreparedStatement pstat = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = pstat.executeQuery();

            banner = new ImageIcon(getClass().getResource("gun2.png"));
            JLabel image = new JLabel(banner);

    //TABLE
            label = new JLabel[20];
            for(int i = 0; i < 20; i++)
            {
                label[i] = new JLabel();
                label[i].setText("");
                panel.add(label[i]);
            }

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            label[0].setText("");
            label[1].setText("Customer Table");
            Font font = label[1].getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            label[1].setFont(font.deriveFont(attributes));
            label[2].setText("");

            resultSet.next();
            int k = 6;
            for (int i = 1; i <= numberOfColumns; i++)
                {
                    label[i+2].setText(metaData.getColumnName(i) + "\t");
                    Font font2 = label[i+2].getFont();
                    Map attributes2 = font2.getAttributes();
                    attributes2.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    label[i+2].setFont(font.deriveFont(attributes));
                        for (int j = 1; j <= numberOfColumns; j++)
                        {
                            label[k].setText(resultSet.getObject(j) + "\t");
                            k++;
                        }
                    resultSet.next();
                    //numberOfColumns = numberOfColumns+3;
                }

    //PANEL 2
            panel2 = new JPanel();
            toMenu = new JButton("Customer Menu");
            toMenu.addActionListener((ActionListener) this);
            panel2.setBackground(Color.decode("#eed57a"));
            panel2.add(toMenu);

    //FRAME FINAL
            frame.add(image, BorderLayout.NORTH);
            frame.add(panel, BorderLayout.CENTER);
            frame.add(panel2, BorderLayout.SOUTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("UPDATE");
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace(); // Driver not found
        } finally {
            try {
                
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        
    }
    public void Update() {
        try
        {
            frame = new JFrame();
            panel = new JPanel();

            panel.setBorder(BorderFactory.createEmptyBorder(50, 200, 250, 200));
            panel.setLayout(new GridLayout(0, 3, 5, 5));
            panel.setBackground(Color.decode("#eed57a"));

            banner = new ImageIcon(getClass().getResource("gun2.png"));
            JLabel image = new JLabel(banner);

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/schematest", "root", "GolfZc@r58");

            pstat = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = pstat.executeQuery("SELECT ID,Name,Address FROM customer");

    //LABEL Intialisation
            label = new JLabel[20];
            for(int i = 0; i < 20; i++)
            {
                label[i] = new JLabel();
                label[i].setText("");
            }

            ResultSetMetaData metaData = resultSet.getMetaData();
            numberOfColumns = metaData.getColumnCount();
    //label 0
            label[0].setText("");
            panel.add(label[0]);

    //label 1: Title
            label[1].setText("Customer Table");
            panel.add(label[1]);
            Font font = label[1].getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            label[1].setFont(font.deriveFont(attributes));

    //label 2
            label[2].setText("");
            panel.add(label[2]);

    //label 3
            label[3].setText("");
            panel.add(label[3]);

    //label 4: ComboBOX
            int numberOfRows = 0;
            while (numberOfRows < 10) 
            {
                numberOfRows++;
            }
            String[] list = new String[numberOfRows];

            int i = 0;
            while (resultSet.next()) 
            {
                list[i] = resultSet.getString(2); // Store values in the array
                i++;
            }

            c1 = new JComboBox<>(list);
            c1.addItemListener(this);
            panel.add(c1);

    //label 5/6/7/8
            label[5].setText("");
            panel.add(label[5]);
            label[6].setText("");
            panel.add(label[6]);
            label[7].setText("");
            panel.add(label[7]);
            label[8].setText("");
            panel.add(label[8]);

    //Columns
            resultSet.next();
            for (i = 1; i <= numberOfColumns; i++)
                {
                    label[i+5].setText(metaData.getColumnName(i) + "\t");
                    Font font2 = label[i+5].getFont();
                    Map attributes2 = font2.getAttributes();
                    attributes2.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    label[i+5].setFont(font.deriveFont(attributes));
                }

    //label 9/10/11: Customer Info
            label[9].setText("");
            panel.add(label[9]);
            label[10].setText("");
            panel.add(label[10]);
            label[11].setText("");
            panel.add(label[11]);

            panel2 = new JPanel();
            toMenu = new JButton("Customer Menu");
            toMenu.addActionListener((ActionListener) this);
            panel2.setBackground(Color.decode("#eed57a"));
            panel2.add(toMenu);

    //Frame
            frame.add(image, BorderLayout.NORTH);
            frame.add(panel, BorderLayout.CENTER);
            frame.add(panel2, BorderLayout.SOUTH);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Update");
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace(); // Driver not found
        } finally {
            try {
                
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        
    }

}
