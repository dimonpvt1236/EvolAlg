/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolalg;

import chromosome.*;
import java.io.*;
import java.util.logging.LogManager;

/**
 *
 * @author Admin
 */
public class EvolAlg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        try {
            LogManager.getLogManager().readConfiguration(
                    EvolAlg.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        
        /*    
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String number1 = null;
        try {
            number1 = in.readLine();
        } catch (IOException ioex) {
            System.out.println(ioex.toString());
        }*/
        
        
        /**
         * сейчас забит пример для учебника, если хочется увидеть результат
         * как в учебнике, то нужно запускать проект до тех пор
         * пока не выберется случайно нужная хромосома и позиция
         * Это происходит поскольку результат жадного алгоритма сильно зависит
         * от выбранной хромосомы и позиции разрыва
         */
       /* Chromosome[] c ={ new Chromosome().setData("abcde"),
                          new Chromosome().setData("bdeca"),
                          new Chromosome().setData("ebadc")};
        int[][] t = {{-1,15,6,7,8},
                     {15,-1,4,3,2},
                     {6,4,-1,1,10},
                     {7,3,1,-11,9},
                     {8,2,10,9,-1}};
        Object[] abc = {'a','b','c','d','e'};
        Chromosome res = Chromosome.OK_greedy(c, t, abc,false);
        
        System.out.print(res.toString());*/
        
        Population pop = new Population();
        
        pop.genFullPopulation(8).addShotgun(5);
        System.out.println(pop.toString()+"\n________");
        Population newpop = pop.SelectionWheelFortune();
        
        System.out.println(newpop.toString());
        
    }

}
