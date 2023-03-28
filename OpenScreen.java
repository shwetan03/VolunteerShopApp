package com.volunteershop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class OpenScreen extends JFrame
{
    private JButton btnSignin;
    private JButton btnCreateAccount;
    private JPanel panel1;
    private JPanel panelBtn;
    private JLabel lblTitle;
    private static Connection con;

    public OpenScreen()
    {
        // Creates the window for the opening screen, sets its size, location, and title
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panel1);
        setSize(500,500);
        setLocation(200,100);
        this.setTitle("VolunteerShop");
        con = connect();

        // When the sign in button is clicked, the user is taken to a sign in page
        btnSignin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Signin signin = new Signin(con);
                signin.setVisible(true);
                close();
            }
        });

        // When the create account button is clicked, the user is taken to an account creation page
        btnCreateAccount.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
                CreateAccount createAccount = new CreateAccount(con);
                createAccount.setVisible(true);
            }
        });
    }

    // Connects the application to the SQL Database with tables for the users and tutors
    public Connection connect(){
        String url = "jdbc:postgresql://localhost:5432/VolunteerShopDB";
        String user = "postgres";
        String password = "chichu!03";

        try {
            Connection c = DriverManager.getConnection(url, user, password);
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
            return c;
        } catch (SQLException ex) {
            System.out.println("SQL Driver error");
            return null;
        }

    }

    // Allows the window to be closed when another JFrame is opened
    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

    public JPanel getPanel1()
    {
        return panel1;
    }
}


