/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolalg;

import chromosome.*;
import java.io.*;
import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class EvolAlg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //in: chromo length
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String number1 = null;
        try {
            number1 = in.readLine();
        } catch (IOException ioex) {
            System.out.println(ioex.toString());
        }
        Population p = new Population();
        p.setChromoLength(Integer.valueOf(number1));

        p.genFullPopulation(Integer.valueOf(number1));
        System.out.println(p.toString());
        System.out.println("===========");
        
        p.genFullPopulation(Integer.valueOf(number1));
        p.addFocus(10, 30, 50f);
        System.out.println(p.toString());
        System.out.println("===========");
        
        p.genFullPopulation(Integer.valueOf(number1));
        p.addShotgun(50f);
        System.out.println(p.toString());
        System.out.println("===========");
       
    }

}
