package com.volunteershop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Signin extends JFrame
{
    private JPanel Panel3;
    private JButton btnSubmit;
    private JTextField txtFieldEmail;
    private JPasswordField pswdFieldPassword;
    private JLabel lblInstructions;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblErrorMessage;
    private JButton btnBack;
    private Connection con;
    private ArrayList<Tutor> tutors;

    public Signin(Connection con) throws HeadlessException
    {
        this.con = con;

        // Creates the window for the opening screen, sets its size, location, and title
        getContentPane().add(Panel3);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(200,100);
        setTitle("VolunteerShop");

        btnSubmit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Takes the user to the home page if they have successfully signed in
                if (UserExists() != null) {
                    close();
                    Home home = new Home(con, UserExists(), tutors);
                    home.setVisible(true);
                } else {
                    // Prompts user to re-enter information if the email or password does not match an existing user
                    lblErrorMessage.setText("Invalid email or password. Please try again.");
                }
            }
        });

        // Allows the user to return to the opening screen
        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
                OpenScreen openScreen = new OpenScreen();
                openScreen.setVisible(true);
            }
        });
    }

    public User UserExists(){
        User user = new User();
        try {
            Statement st = (Statement) con.createStatement();
            try
            {
                ResultSet rs = st.executeQuery("SELECT * FROM public.\"Users\"");
                while (rs.next())
                {
                    // Checks if both the email and the password match a user record in the Users table in the database
                    if (txtFieldEmail.getText().equals(rs.getString("email")) && Objects.equals(pswdFieldPassword.getText(), rs.getString("password")))
                    {
                        // Retrieves an ArrayList of all tutors
                        tutors = getAllTutors(con);

                        user = User.createUser(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"),
                                rs.getString("email"), rs.getString("password"));
                        break;
                    } else {
                        user = null;
                    }
                }
            } catch (Exception err) {
                System.out.println("SQL error");
                err.getMessage();
            }
        } catch (Exception e) {
            System.out.println("SQL error");
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<Tutor> getAllTutors(Connection con) {
        ArrayList<Tutor> tutorList = new ArrayList<>();
        try
        {
            Statement stm = (Statement) con.createStatement();
            ResultSet rst = stm.executeQuery("Select * From public.\"Tutors\"");

            while (rst.next())
            {
                // Creates a new Tutor object for each tutor record in the Tutors table
                Tutor tutor = new Tutor(rst.getInt("id"), rst.getString("name"),
                        rst.getString("email"), rst.getString("subjects"), rst.getInt("student_id"));
                tutorList.add(tutor);
            }

        } catch (Exception e){
            System.out.println("SQL error");
            e.getMessage();
        }
        return tutorList;
    }

    // Allows the window to be closed when another JFrame is opened
    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

    public JPanel getPanel3()
    {
        return Panel3;
    }

}
