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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    /**
     * <p>
     * ГА транспортной задачи
     * </p>
     *
     * @param n Колличество итераций
     * @param p_k Вероятность выполнения оператора кроссинговера
     * @param p_m Вероятность выполнения оператора мутации
     * @param countPop Размер стартовой популяции
     * 
     * @return Хромосома - лучшее решение
     */
    public static Chromosome SimpleGA_Transport(int n, int p_k, int p_m, int countPop) { 
        int[] Price = {8, 11, 7, 5, 2, 3, 5, 7, 10, 4, 6, 10};
        int[] Sklad = {10, 40, 50};
        int[] Customer = {10, 20, 30, 40};
        /*
        int[] Price = {3, 5, 7, 4, 6, 10};
        int[] Sklad = {40, 50};
        int[] Customer = {20, 30, 40};*/
        
        int kolXrom = Sklad.length * Customer.length;
        int prod_temp = 0;
        for (int i=0; i<Sklad.length; i++) {
            prod_temp += Sklad[i];
        }
 
        Population p2, p_result = null;
        Population p = new Population();
        // ввод исзодных данных
        p.setCustomer(Customer);
        p.setPrice(Price);
        p.setSklad(Sklad);
        // формирование начальной популяции
        p.getShotgunPopulation_Vector(countPop, kolXrom, 6);
        //System.out.println(p.toString());
        System.out.println(p.getLength() + "\n-----------");
        p.calculateAllCF();
        System.out.println("MaxCF "+p.getMaxCF());
        System.out.println("MinCF "+p.getMinCF());
        System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
        
 
        for (int i=0; i<n; i++) {
            try {
                // выполнение операторов кроссинговера
                int c_k =0;
                List<Chromosome> newpop = new ArrayList<>();
                for (Chromosome c : p.getData()) {
                    int pk = (int)(Math.random() *100);
                    if (pk<= p_k) {
                        try {
                            Chromosome xrom;// = new Chromosome();
                            Chromosome xrom2;// = new Chromosome();
                            
                           // Cloner cloner=new Cloner();
                            xrom = c.clone();//.setData(c.getData());
                            int k = (int)(Math.random() * p.getLength());
                            xrom2 = p.getData().get(k).clone();//.setData(p.getData().get(k).getData());
                            
                            xrom.OK_PointOne(xrom2);
                            
                            newpop.add(xrom);
                            newpop.add(xrom2);
                            c_k++;
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                // удаление не подходящих решений
                p = p.removeChromosome(prod_temp);
                if (p.getData().size()==0) {
                    break;
                }
                p = p.SelectionWheelFortuneVector(countPop, false);
                p.calculateAllCF();
                
                System.out.println("Kol OK "+c_k);
                System.out.println("Kol MT "+c_m);
                System.out.println("MaxCF "+p.getMaxCF());
                System.out.println("MinCF "+p.getMinCF());
                System.out.println("MidCF "+p.getCF_mid()+ "\n-----------");
                if (p.getMaxCF()-p.getMinCF() <= 0.0001) {
                    //return Search.LinearSearch(p.getData(), p.getMinCF());
                    break;
                }
                
                p_result = p.clone();
                
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
                } 
        }
        if (p.getData().size()>0) {
            return Search.LinearSearch(p.getData(), p.getMinCF());
        } else { 
            return Search.LinearSearch(p_result.getData(), p.getMinCF());
        }
    }
}
