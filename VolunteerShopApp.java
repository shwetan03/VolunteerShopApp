package com.volunteershop;

import javax.swing.*;
import java.util.ArrayList;

public class VolunteerShopApp
{
    public static OpenScreen openScreen;
    public static void main(String[] args)
    {
        //Opens up the main screen
        openScreen = new OpenScreen();
        openScreen.setVisible(true);
    }
}
