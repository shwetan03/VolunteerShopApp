package com.volunteershop;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CreateAccount extends JFrame
{
    private JButton btnSubmit;
    private JTextField txtFieldFirstName;
    private JTextField txtFieldLastName;
    private JTextField txtFieldEmail;
    private JTextField txtFieldPassword;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblInstructions;
    private JPanel Panel3;
    private JLabel lblErrorMessage;
    private JButton btnBack;
    private Connection con;


    public CreateAccount(Connection con) throws HeadlessException
    {
        this.con = con;

        // Creates the window for the opening screen, sets its size, location, and title
        setContentPane(Panel3);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(200,100);
        setTitle("VolunteerShop");

        // Allows the user to submit their information to create an account
        btnSubmit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (isUserInfoValid()) {
                    createNewUser();
                    close();
                    // If an account is created (all information inputted is valid)
                    // The user is taken to the sign in page
                    Signin signin = new Signin(con);
                    signin.setVisible(true);
                } else {
                    lblErrorMessage.setText("One or more fields contain errors");
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

    public boolean UserExists()
    {
        boolean userExists = false;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from public.\"Users\";");

            // Checks if the email and password inputted are linked to an existing user
            while (rs.next())
            {
                if (txtFieldEmail.getText().equals(rs.getString("email")) && txtFieldPassword.getText().equals(rs.getString("password"))) {
                    userExists = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("SQL Error");
            e.printStackTrace();
        }

        return userExists;
    }

    public boolean isUserInfoValid(){
        boolean UserInfoValid;
        if (txtFieldFirstName.getText().equals("") || txtFieldLastName.getText().equals("") ||
                txtFieldEmail.getText().equals("") || !txtFieldEmail.getText().contains("@") ||
                !txtFieldEmail.getText().contains(".") || txtFieldPassword.getText().equals("")
                || UserExists()) {
            UserInfoValid = false;
        } else {
            UserInfoValid = true;
        }
        return UserInfoValid;
    }

    // Creates a user based on the inputted information
    public void createNewUser(){
        try {
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO public.\"Users\" (firstname, lastname, email, password)" + "VALUES ('" +
                    txtFieldFirstName.getText() + "', '" + txtFieldLastName.getText()+ "', '" + txtFieldEmail.getText()
                    + "', '" +  txtFieldPassword.getText() + "')");
        } catch (Exception e) {
            System.err.println("SQL Exception");
            System.err.println(e.getMessage());
        }
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
