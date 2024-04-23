import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Menu's


public class Menu extends JFrame implements ActionListener {
    private JButton customerBTN;
    private JButton invoiceBTN;
    private JButton orderBTN;
    private JButton productsBTN;
    private JFrame frame;
    private JPanel buttonJPanel;

    private Icon banner;

    public static void main(String[] args) {
        new Menu();
    } // end main

    public Menu() {

        getContentPane().setBackground(Color.decode("#eed57a"));

        frame = new JFrame();
        
        // NORTH
        banner = new ImageIcon(getClass().getResource("gun2.png"));
        JLabel image = new JLabel(banner);
        frame.add(image, BorderLayout.NORTH);

        // CENTER
        buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new GridLayout(0, 1));
        buttonJPanel.setBackground(Color.decode("#eed57a"));

        customerBTN = new JButton("Customer");
        customerBTN.addActionListener(this);
        buttonJPanel.add(customerBTN);

        invoiceBTN = new JButton("Invoice");
        invoiceBTN.addActionListener(this);
        buttonJPanel.add(invoiceBTN);

        orderBTN = new JButton("Order");
        orderBTN.addActionListener(this);
        buttonJPanel.add(orderBTN);

        productsBTN = new JButton("Products");
        productsBTN.addActionListener(this);
        buttonJPanel.add(productsBTN);

        buttonJPanel.setBorder(BorderFactory.createEmptyBorder(100, 500, 200, 500));
        buttonJPanel.setLayout(new GridLayout(4, 0, 5, 5));

        frame.add(buttonJPanel);
        frame.add(buttonJPanel, BorderLayout.CENTER);

        //FRAME CREATION
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("UPDATE");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    } // end constructor

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == customerBTN) {
            frame.dispose();
            new customerMenu();
        } else if (event.getSource() == invoiceBTN) {
            frame.dispose();
        } else if (event.getSource() == orderBTN) {
            frame.dispose();
        } else if (event.getSource() == productsBTN) {
            frame.dispose();
        } else {
            // Input Error Handling
            System.out.println("NOT WORKING!");
        }

    }
}// end class
