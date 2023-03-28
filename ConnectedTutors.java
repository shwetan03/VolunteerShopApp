package com.volunteershop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class ConnectedTutors extends JFrame
{
    private JPanel panel6;
    private JLabel lblInstructions;
    private JLabel lblErrorMessage;
    private JLabel lblName;
    private JLabel lblSubjects;
    private JLabel lblEmail;
    private JButton btnBack;
    private JButton btnNext;
    private JButton btnHomePage;
    private Connection con;
    private User user;
    private ArrayList<Tutor> tutors;
    private int page = 0;

    public ConnectedTutors(Connection con, User user, ArrayList<Tutor> tutors) throws HeadlessException
    {
        this.con = con;
        this.user = user;
        this.tutors = tutors;

        // Creates the window for the opening screen, sets its size, location, and title
        setContentPane(panel6);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(200,100);
        setTitle("VolunteerShop");

        // Creates an ArrayList of Tutors who the user has connected with
        ArrayList<Tutor> connectedTutors = new ArrayList<Tutor>();
        for (int i = 0; i < tutors.size(); i++)
        {
            if (tutors.get(i).getUserID() == user.getId())
            {
                connectedTutors.add(tutors.get(i));
            }
        }

        // If the user has connected with any tutors, the first tutor's name, subjects they tutor in, and email are displayed
        // If they haven't connected, an error message is shown
        if (connectedTutors.size() > 0) {
            btnBack.setVisible(false);
            lblName.setText("Name: " + connectedTutors.get(0).getName());
            lblSubjects.setText("Subject(s): " + connectedTutors.get(0).getSubjects());
            lblEmail.setText("Email: " + connectedTutors.get(0).getEmail());
        } else {
            lblErrorMessage.setText("You have not connected with any tutors.");
        }

        // Allows the user to view previous tutors
        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Makes sure there is at least one tutor
                if (connectedTutors.size() > 0) {
                    page--;

                    // Checks to see if the back button is necessary
                    if ((page - 1) >= 0) {
                        btnBack.setVisible(true);
                    } else {
                        btnBack.setVisible(false);
                    }

                    // Displays the previous tutor's information
                    lblName.setText("Name: " + connectedTutors.get(page).getName());
                    lblSubjects.setText("Subject(s): " + connectedTutors.get(page).getSubjects());
                    lblEmail.setText("Email: " + connectedTutors.get(page).getEmail());

                    // Checks to see if the next button is necessary
                    if ((page + 1) < connectedTutors.size()) {
                        btnNext.setVisible(true);
                    }
                }
            }
        });

        // Allows the user to view the next tutor
        btnNext.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Makes sure there is at least one tutor
                if (connectedTutors.size() > 1) {
                    page++;

                    // Checks to see if the back button is necessary
                    if ((page - 1) >= 0) {
                        btnBack.setVisible(true);
                    }

                    //Displays the next tutor's information
                    lblName.setText("Name: " + connectedTutors.get(page).getName());
                    lblSubjects.setText("Subject(s): " + connectedTutors.get(page).getSubjects());
                    lblEmail.setText("Email: " + connectedTutors.get(page).getEmail());

                    // Checks to see if the next button is necessary
                    if ((page + 1) < connectedTutors.size()) {
                        btnNext.setVisible(true);
                    } else {
                        btnNext.setVisible(false);
                    }
                }
            }
        });

        // Allows the user to return to the home page
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

    public JPanel getPanel6()
    {
        return panel6;
    }
}
