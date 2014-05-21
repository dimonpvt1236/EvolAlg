/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;
import chromosome.Chromosome;
import chromosome.Population;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Starik
 */
public class GeneticAlgorithm {
    
    public static void SimpleGA(int n, int p_k, int p_m) {
        Population p2;
        Population p = new Population().genFullPopulation(4);
        System.out.println(p.getLength() + "\n-----------");
        p.calculateAllCF();
        System.out.println("MaxCF "+p.getMaxCF());
        System.out.println("MinCF "+p.getMinCF());
        System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
        
        for (int i=0; i<n; i++) {
            p = p.SelectionWheelFortune(10);
            //System.out.println(p.toString());
            
            // выполнение операторов кроссинговера
            int c_k =0;
            List<Chromosome> newpop = new ArrayList<>();
            for (Chromosome c : p.getData()) {
                int pk = (int)(Math.random() *100);
                if (pk<= p_k) {
                    Chromosome xrom = new Chromosome();
                    Chromosome xrom2 = new Chromosome();
                    xrom.setData(c.getData());
                    int k = (int)(Math.random() * p.getLength());
                    xrom2.setData(p.getData().get(k).getData());
                    xrom.OK_PointOne(xrom2);
                    
                    newpop.add(xrom);
                    newpop.add(xrom2);
                    c_k++;
                }              
            }
            // добавляем полученых потомков в исходную популяцию не исключая дубликаты
            p2 = new Population(newpop);
            p.adjustPopulationD(p2);
            
            
            // выполняем операторы мутации
            int c_m = 0;
            for (Chromosome c : p.getData()) {
                int pm = (int)(Math.random() *100);
                if (pm<= p_m) {
                    c.MT_PointTwo();
                    c_m++;
                }              
            }
            
            p.calculateAllCF();
            System.out.println("Kol OK "+c_k);
            System.out.println("Kol MT "+c_m);
            System.out.println("MaxCF "+p.getMaxCF());
            System.out.println("MinCF "+p.getMinCF());
            System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
        }
        
        //System.out.println(p.toString());
    }
}
