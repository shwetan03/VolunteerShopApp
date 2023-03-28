package com.volunteershop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class Home extends JFrame
{
    private JPanel panel4;
    private JButton btnEditProfile;
    private JButton btnConnectedTutors;
    private JButton btnTutorMatch;
    private JButton btnLogOut;
    private Connection con;
    private User user;
    private ArrayList<Tutor> tutors;

    public Home(Connection con, User user, ArrayList<Tutor> tutors) {
        this.con = con;
        this.user = user;
        this.tutors = tutors;

        // Creates the window for the opening screen, sets its size, location, and title
        setContentPane(panel4);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(200,100);
        setTitle("VolunteerShop");

        // Links the user to the profile editing page
        btnEditProfile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
                EditProfile editProfile = new EditProfile(con, user, tutors);
                editProfile.setVisible(true);
            }
        });

        // Links the user to the Connected Tutors page
        btnConnectedTutors.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
                ConnectedTutors connectedTutors = new ConnectedTutors(con, user, tutors);
                connectedTutors.setVisible(true);
            }
        });

        // Links the user to the Tutor Match page
        btnTutorMatch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
                TutorMatch tutorMatch = new TutorMatch(con, user, tutors);
                tutorMatch.setVisible(true);
            }
        });

        // Allows the user to log out and return to the opening screen
        btnLogOut.addActionListener(new ActionListener()
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

    // Allows the window to be closed when another JFrame is opened
    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

    public JPanel getPanel4()
    {
        return panel4;
    }
}
