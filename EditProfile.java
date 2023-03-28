package com.volunteershop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;

public class EditProfile extends JFrame
{
    private JPanel panel5;
    private JLabel lblName;
    private JLabel lblEmailLabel;
    private JLabel lblEmail;
    private JLabel lblPasswordLabel;
    private JLabel lblPassword;
    private JButton btnEditEmail;
    private JButton btnEditPassword;
    private JTextField txtFieldEmail;
    private JTextField txtFieldPassword;
    private JLabel lblErrorMessage;
    private JButton btnHomePage;
    private User user;
    private Connection con;
    private ArrayList<Tutor> tutors;

    public EditProfile(Connection con, User user, ArrayList<Tutor> tutors) {
        this.user = user;
        this.con = con;
        this.tutors = tutors;

        // Creates the window for the opening screen, sets its size, location, and title
        setContentPane(panel5);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(200,100);
        setTitle("VolunteerShop");

        // Displays the user's current email
        lblEmail.setText(user.getEmail());

        txtFieldEmail.setText("Enter new email");
        txtFieldPassword.setText("Enter new password");

        btnEditEmail.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Checks if the new email is the same as the old email and if the new email is valid
                if (txtFieldEmail.getText().equals(user.getEmail())) {
                    lblErrorMessage.setText("New email is the same as current email. Please try again.");
                } else if (!txtFieldEmail.getText().equals("") && txtFieldEmail.getText().contains("@") && txtFieldEmail.getText().contains(".")) {
                    // Updates the User object and the SQL database with the new email
                    user.setEmail(txtFieldEmail.getText());
                    try {
                        Statement st = (Statement) con.createStatement();
                        st.executeUpdate("UPDATE public.\"Users\" SET email = '" + txtFieldEmail.getText() + "' WHERE ID = "+ user.getId());
                    } catch (Exception err) {
                        System.out.println("error");
                        err.printStackTrace();
                    }
                    lblErrorMessage.setText("Email changed");
                } else {
                    lblErrorMessage.setText("Invalid email");
                }
            }
        });


        btnEditPassword.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Checks if the new password is the same as the old password and if the new password is valid
                if (txtFieldPassword.getText().equals(user.getPassword())) {
                    lblErrorMessage.setText("New password is the same as current password. Please try again.");
                } else if (!txtFieldPassword.getText().equals("")) {
                    // Updates the User object and the SQL database with the new password
                    user.setPassword(txtFieldPassword.getText());
                    try {
                        Statement st = (Statement) con.createStatement();
                        st.executeUpdate("UPDATE public.\"Users\" SET password = '" + txtFieldPassword.getText() + "' WHERE id = "+ user.getId());
                    } catch (Exception err) {
                        System.out.println("error");
                        err.printStackTrace();
                    }
                    lblErrorMessage.setText("Password changed");
                } else {
                    lblErrorMessage.setText("Invalid password");
                }
            }
        });

        //Allows the user to return to the home page
        btnHomePage.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
                Home home = new Home(con, user, tutors);
                home.setVisible(true);
            }
        });
    }

    // Allows the window to be closed when another JFrame is opened
    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

    public JPanel getPanel5()
    {
        return panel5;
    }
}
