package com.volunteershop;

public class Tutor
{
    private int id;
    private String name;
    private String email;
    private String subjects;
    private int userID = 0;

    public Tutor(int id, String name, String email, String subjects, int userID)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subjects = subjects;
        this.userID = userID;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getSubjects()
    {
        return subjects;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }
}
