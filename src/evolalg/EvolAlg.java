/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolalg;

import chromosome.Chromosome;
import chromosome.Population;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.GeneticAlgorithm;
/**
 *
 * @author Admin
 */
public class EvolAlg {

    /**
     * @param args the command line arguments
     * @throws java.lang.CloneNotSupportedException
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        try {
            BufferedReader inK = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите имя файла с графом ");
            System.out.println("Например: \"..\\graphbuilder\\graphs\\sample2.graph\"");
            String filename = inK.readLine();
            File f = new File(filename);
            
            int n=0;
            try (Scanner in = new Scanner(f)) {
                n = Integer.valueOf(in.nextLine());
            }
            Population bests = new Population();
            bests.setFile(filename);
            for (int i=0; i<100; i++) {  
                Chromosome best = GeneticAlgorithm.SimpleGA_Devica(10, 10+i*8, 20, n, filename);
                  
                if(best!=null)
                    bests.addChromosome(best);
            }
          
            Chromosome bestofthebest=new Chromosome();
            bestofthebest.setCF(0);
            bests.calculateAllCF();
            for(Chromosome c:bests.getData())
                if(c.getCF()>bestofthebest.getCF())
                    bestofthebest=c;
            
            if(bestofthebest.getData()!=null)System.out.println("Result:\n"+bestofthebest.toString()+ " : " + bestofthebest.getCF());
            else System.out.println("Results not found");
            
            } catch (FileNotFoundException ex) {
            Logger.getLogger(EvolAlg.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EvolAlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
