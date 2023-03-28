package com.volunteershop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;

public class TutorMatch extends JFrame
{
    private JPanel panel7;
    private JLabel lblNameLabel;
    private JLabel lblSubjectsLabel;
    private JLabel lblInstructions;
    private JButton btnHomePage;
    private JButton btnTutorConnect;
    private JButton btnNext;
    private JButton btnBack;
    private JLabel lblErrorMessage;
    private Connection con;
    private User user;
    private ArrayList<Tutor> tutors;
    private int page = 0;
    private ArrayList<Tutor> availableTutors;

    public TutorMatch(Connection con, User user, ArrayList<Tutor> tutors)
    {
        this.con = con;
        this.user = user;
        this.tutors = tutors;

        // Creates the window for the opening screen, sets its size, location, and title
        setContentPane(panel7);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(200,100);
        setTitle("VolunteerShop");

        // Creates an ArrayList of all Tutors that a user has not connected with
        ArrayList<Tutor> newTutors = new ArrayList<Tutor>();
        availableTutors = newTutors;

        for (int i = 0; i < tutors.size(); i++)
        {
            availableTutors.add(tutors.get(i));
        }

        for (int i = 0; i < tutors.size(); i++)
        {
            if (tutors.get(i).getUserID() > 0)
            {
                availableTutors.remove(tutors.get(i));
            }
        }

        // Shows the user's only tutor with no back or next buttons
        if (availableTutors.size() == 1) {
            btnBack.setVisible(false);
            btnNext.setVisible(false);
            lblNameLabel.setText("Name: " + availableTutors.get(0).getName());
            lblSubjectsLabel.setText("Subject(s): " + availableTutors.get(0).getSubjects());
        }
        // Allows the user to click through their tutors
        else if (availableTutors.size() > 0) {
            btnBack.setVisible(false);
            lblNameLabel.setText("Name: " + availableTutors.get(0).getName());
            lblSubjectsLabel.setText("Subject(s): " + availableTutors.get(0).getSubjects());
        }
        // If the user hasn't connected with any tutors, an error message is displayed
        else {
            btnTutorConnect.setVisible(false);
            lblErrorMessage.setText("No tutors available currently");
        }

        // Allows user to connect with a tutor
        btnTutorConnect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    // Updates the tutor's record in the Tutors table in the database with the user's id
                    Statement st = (Statement) con.createStatement();
                    st.executeUpdate("UPDATE public.\"Tutors\" SET student_id = '" + user.getId() + "' WHERE id = "+ availableTutors.get(page).getId());
                    // Sets the tutor's Tutor object's UserID field to the user's id
                    for (int i = 0; i < tutors.size(); i++)
                    {
                        if (tutors.get(i) == availableTutors.get(page)) {
                                tutors.get(i).setUserID(user.getId());
                        }
                    }
                } catch (Exception err) {
                    System.out.println("SQL Error");
                    err.printStackTrace();
                }
            }
        });

        // Allows the user to view the next tutor
        btnNext.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Makes sure there is more than 1 available tutor
                if (availableTutors.size() > 1)
                {
                    page++;
                    // Decides if the back button is necessary
                    if ((page - 1) >= 0)
                    {
                        btnBack.setVisible(true);
                    }

                    // Displays a tutor's information
                    lblNameLabel.setText("Name: " + availableTutors.get(page).getName());
                    lblSubjectsLabel.setText("Subject(s): " + availableTutors.get(page).getSubjects());

                    // Decides if the next button is necessary
                    if ((page + 1) < availableTutors.size())
                    {
                        btnNext.setVisible(true);
                    } else
                    {
                        btnNext.setVisible(false);
                    }
                }
            }
        });

        // Allows the user to view previous tutors
        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Makes sure there is more than 1 available tutor
                if (availableTutors.size() > 0)
                {
                    page--;
                    // Decides if the back button is necessary
                    if ((page - 1) >= 0)
                    {
                        btnBack.setVisible(true);
                    } else
                    {
                        btnBack.setVisible(false);
                    }
                    // Displays a tutor's information
                    lblNameLabel.setText("Name: " + availableTutors.get(page).getName());
                    lblSubjectsLabel.setText("Subject(s): " + availableTutors.get(page).getSubjects());
                    // Decides if the next button is necessary
                    if ((page + 1) < availableTutors.size())
                    {
                        btnNext.setVisible(true);
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

    public JPanel getPanel7()
    {
        return panel7;
    }
}
