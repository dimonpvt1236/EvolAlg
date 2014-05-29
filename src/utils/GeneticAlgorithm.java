/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;
import chromosome.Chromosome;
import chromosome.Population;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Starik
 */
public class GeneticAlgorithm {
    
    /**
     * <p>
     * Простой генетический алгоритм Голберга
     * </p>
     *
     * @param n Колличество итераций
     * @param p_k Вероятность выполнения оператора кроссинговера
     * @param p_m Вероятность выполнения оператора мутации
     * 
     * @return Хромосома - лучшее решение
     */
    public static Chromosome SimpleGA_Golberga(int n, int p_k, int p_m) {
        Population p2;
        Population p = new Population().genFullPopulation(6);
        p.addShotgun(20);
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
            // выполняем операторы мутации
            int c_m = 0;
            for (Chromosome c : newpop) {
                int pm = (int)(Math.random() *100);
                if (pm<= p_m) {
                    c.MT_PointTwo();
                    c_m++;
                }              
            }
            // добавляем полученых потомков в исходную популяцию не исключая дубликаты
            p2 = new Population(newpop);       
            p.adjustPopulationD(p2);        
            
            p.calculateAllCF();
            System.out.println("Kol OK "+c_k);
            System.out.println("Kol MT "+c_m);
            System.out.println("MaxCF "+p.getMaxCF());
            System.out.println("MinCF "+p.getMinCF());
            System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
        }
        
        //System.out.println(p.toString());
        
        return Search.LinearSearch(p.getData(), p.getMaxCF());
    }
    
    /**
     * <p>
     * Простой генетический алгоритм Голберга
     * </p>
     *  
     * @param p Стартовая популяция
     * @param n Колличество итераций
     * @param p_k Вероятность выполнения оператора кроссинговера
     * @param p_m Вероятность выполнения оператора мутации
     * @param stat Класс статистики, либо null если статистику не нужно вести
     * 
     * @return Хромосома - лучшее решение
     */
    public static Chromosome SimpleGA_Golberga(Population p, int n, int p_k, int p_m, Statistics stat) {
        Population p2;
        p.calculateAllCF();
        if (stat != null) {
            stat.setStartPop(p.getLength());
            stat.setpK(p_k);
            stat.setpM(p_m);
        }
        /*
        System.out.println(p.getLength() + "\n-----------");
        System.out.println("MaxCF "+p.getMaxCF());
        System.out.println("MinCF "+p.getMinCF());
        System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
        */
        int c_k = 0;
        int c_m = 0;
        for (int i=0; i<n; i++) {
            p = p.SelectionWheelFortune(10);
            //System.out.println(p.toString());
            
            // выполнение операторов кроссинговера
            //int c_k =0;
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
            // выполняем операторы мутации
            //int c_m = 0;
            for (Chromosome c : newpop) {
                int pm = (int)(Math.random() *100);
                if (pm<= p_m) {
                    c.MT_PointTwo();
                    c_m++;
                }              
            }
            // добавляем полученых потомков в исходную популяцию не исключая дубликаты
            p2 = new Population(newpop);       
            p.adjustPopulationD(p2);        
            
            p.calculateAllCF();
            
          /*  System.out.println("Kol OK "+c_k);
            System.out.println("Kol MT "+c_m);
            System.out.println("MaxCF "+p.getMaxCF());
            System.out.println("MinCF "+p.getMinCF());
            System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");*/
        }
        
        if (stat != null) {
            stat.setCountK(c_k);
            stat.setCountM(c_m);
            stat.setResult(p.getMaxCF());
        }
        //System.out.println(p.toString());
        
        return Search.LinearSearch(p.getData(), p.getMaxCF());
    }
    
    /**
     * <p>
     * Простой генетический алгоритм Дэвиса
     * </p>
     *
     * @param t Время работы алгоритма
     * @param p_k Вероятность выполнения оператора кроссинговера
     * @param p_m Вероятность выполнения оператора мутации
     * 
     * @return Хромосома - лучшее решение
     */
    public static Chromosome SimpleGA_Devica(long t, int p_k, int p_m) {
        Population p2;
        Population p = new Population().genFullPopulation(6);
        p.addShotgun(20);
        System.out.println(p.getLength() + "\n-----------");
        p.calculateAllCF();
        System.out.println("MaxCF "+p.getMaxCF());
        System.out.println("MinCF "+p.getMinCF());
        System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
        
        long start = new Date().getTime();
        long end = start;
        
        while ((end-start) <= t) {            
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
            // выполняем операторы мутации
            int c_m = 0;
            for (Chromosome c : newpop) {
                int pm = (int)(Math.random() *100);
                if (pm<= p_m) {
                    /*if (c.getCF()<= p.getCF_mid()) {
                        c.MT_Pointer();
                    } else {
                        c.MT_PointTwo();
                    }*/
                    c.MT_PointTwo();
                    c_m++;
                }              
            }
            // добавляем полученых потомков в исходную популяцию не исключая дубликаты
            p2 = new Population(newpop);
            p.adjustPopulationD(p2);
            
            p.calculateAllCF();
            p = p.SelectionWheelFortune(10);
            
            p.calculateAllCF();
            System.out.println("Lenght "+p.getLength());
            System.out.println("Kol OK "+c_k);
            System.out.println("Kol MT "+c_m);
            System.out.println("MaxCF "+p.getMaxCF());
            System.out.println("MinCF "+p.getMinCF());
            System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
            end = new Date().getTime();
        }
        
        //System.out.println(p.toString());
        
        return Search.LinearSearch(p.getData(), p.getMaxCF());
    }
}
