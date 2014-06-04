/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolalg;

import chromosome.Chromosome;
import chromosome.Population;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.GeneticAlgorithm;
import utils.Statistics;

/**
 *
 * @author Admin
 */
public class EvolAlg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        try {
            File f = new File("graph.txt");
            int n;
            try (Scanner in = new Scanner(f)) {
                n = Integer.valueOf(in.nextLine());
            }
            
            List<Chromosome> bests = new ArrayList<>();
            for (int i=0; i<10; i++) {
                    
                // выполнение ГА
                Chromosome best = GeneticAlgorithm.SimpleGA_Devica(1000, 10+i*8, 20, n);
                  
                if(best!=null)
                    bests.add(best);
            }
          
            Chromosome bestofthebest=new Chromosome();
            bestofthebest.setCF(0);
            for(Chromosome c:bests)if(c!=null && c.getCF()>bestofthebest.getCF())bestofthebest=c;
            
            if(bestofthebest.getData()!=null)System.out.println(bestofthebest.toString()+ " : " + bestofthebest.getCF());
            else System.out.println("null");
            
            } catch (FileNotFoundException ex) {
            Logger.getLogger(EvolAlg.class.getName()).log(Level.SEVERE, null, ex);
        }
            //</editor-fold>
        
    }

}
