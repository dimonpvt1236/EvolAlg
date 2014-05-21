/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolalg;

import chromosome.Chromosome;
import chromosome.Population;
import utils.GeneticAlgorithm;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EvolAlg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //<editor-fold desc="пример для логгера">
       /* try {
         LogManager.getLogManager().readConfiguration(
         EvolAlg.class.getResourceAsStream("/logging.properties"));
         } catch (IOException e) {
         System.err.println("Could not setup logger configuration: " + e.toString());
         }*/

        //</editor-fold>
        //<editor-fold desc="Пример для чтения ввода из консоли">
        /*    
         BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
         String number1 = null;
         try {
         number1 = in.readLine();
         } catch (IOException ioex) {
         System.out.println(ioex.toString());
         }*/
        //</editor-fold>
        //<editor-fold desc="Примеры для кроссинговера и мутации (лабы 2-3)">
        /**
         * примеры для кроссинговера и мутации
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
        
         System.out.print(res.toString());
        
        
         Population pop = new Population();
        
         pop.genFullPopulation(8).addShotgun(5);
         System.out.println(pop.toString()+"\n________");
         Population newpop = pop.SelectionWheelFortune();
        
         System.out.println(newpop.toString());
        
         //Chromosome xrom = new Chromosome().setData("abcdefgh");
         //Chromosome xrom2 = new Chromosome().setData("gabecdfh");
         Chromosome xrom = new Chromosome().setData("abcdefghij");
         Chromosome xrom2 = new Chromosome().setData("eciadhjbfg");
         System.out.println("Parent");
         System.out.println(xrom.toString());
         System.out.println(xrom2.toString());
         int point[];
         //point = xrom.OK_PointThree(xrom2);
         //point = xrom.OK_SerialOnePoint(xrom2);
         point = xrom.OK_PartialLinkedOnePoint(xrom2);
         System.out.println("Children");
         System.out.println(xrom.toString(point));
         System.out.println(xrom2.toString(point));
       
         Object[] m1 = {1,2,3,4,5,6,7,8,9,10};
         Object[] m2 = {5,3,9,1,4,8,10,2,6,7};
         Chromosome xrom = new Chromosome().setData(m1);
         Chromosome xrom2 = new Chromosome().setData(m2);
         int[] m = {0,1,1,1,0,0,0,1,0};
         System.out.println("Parent");
         System.out.println(xrom.toString());
         System.out.println(xrom2.toString());
         //xrom.OK_Universal(xrom2, m);
         xrom.OK_cicle(xrom2);
         // System.out.println("Mask");
         // for(int i=0; i<9;i++) {
         //     System.out.print(m[i]+" ");
         //  }
         System.out.println();
         System.out.println("Children");
         System.out.println(xrom.toString());
         System.out.println(xrom2.toString());
        
         Chromosome xrom = new Chromosome().setData("0001110101");
         System.out.println("FibanacciTwoPoint");
         System.out.println("Parent");
         System.out.println(xrom.toString());
         int point[];
         point = xrom.MT_FibanacciTwoPoint();
         System.out.println("Children");
         System.out.println(xrom.toString(point));
        
         Chromosome xrom2 = new Chromosome().setData("0101010001");
         System.out.println("GoldTwoPoint");
         System.out.println("Parent");
         System.out.println(xrom2.toString());
         int point2[];
         point2 = xrom2.MT_GoldTwoPoint();
         System.out.println("Children");
         System.out.println(xrom2.toString(point2));
        
         Chromosome xrom2 = new Chromosome().setData("0101010001");
         System.out.println("Inversion");
         System.out.println("Parent");
         System.out.println(xrom2.toString());
         xrom2.Inversion();
         System.out.println("Children");
         System.out.println(xrom2.toString());
        
         Chromosome xrom1 = new Chromosome().setData("0101010001");
         System.out.println("Inversion Two Point");
         System.out.println("Parent");
         System.out.println(xrom1.toString());
         int point2[];
         point2 = xrom1.INV_PointTwo();
         System.out.println("Children");
         System.out.println(xrom1.toString(point2));
        
         Chromosome xrom1 = new Chromosome().setData("abcdefgh");
         Chromosome xrom2 = new Chromosome().setData("qwrtuiop");
         System.out.println("Parent");
         System.out.println(xrom1.toString());
         System.out.println(xrom2.toString());
         int point2[];
         point2 = xrom1.Translocation(xrom2);
         System.out.println("Children");
         System.out.println(xrom1.toString(point2));
         System.out.println(xrom2.toString(point2));
         */
        //</editor-fold>
        //<editor-fold desc="Отладка для шкальной селекции">
       /* Population p = new Population().genFullPopulation(10);

        double[] a1 = {.5, .2}, //шкала групп
                a2 = {.5, .3, .01}; // коэффициент попадания из группы
        System.out.println(p.getLength() + "\n-----------");
        Population p2 = p.SelectionByScale(a1, a2);
        System.out.println(p2.getLength());
           */
        
        GeneticAlgorithm.SimpleGA(50, 70, 20);
        //</editor-fold>
    }

}
