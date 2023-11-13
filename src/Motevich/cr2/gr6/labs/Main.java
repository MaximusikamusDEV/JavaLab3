package Motevich.cr2.gr6.labs;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Double[] coefficients = new Double[args.length];
        int i = 0;

        for(String arg: args)
        {
           coefficients[i] = Double.parseDouble(arg);
           i++;
        }


        MainFrame frame = new MainFrame(coefficients);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);



    }
}