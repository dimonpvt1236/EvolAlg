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
 * @author Admin
 */
public class GeneticAlgorithm {

    /**
     * <p>
     * Простой генетический алгоритм Дэвиса
     * </p>
     *
     * @param t Время работы алгоритма
     * @param p_k Вероятность выполнения оператора кроссинговера
     * @param p_m Вероятность выполнения оператора мутации
     * @param filename Имя файла с графом
     * @return Хромосома - лучшее решение
     */
    public static Chromosome SimpleGA_Devica(long t, int p_k, int p_m, String filename) {
        Population p2;
        Population p = new Population();
        p.setFile(filename);
        System.out.println("File reading ends");
        p.genShotgun(p.getChromoLength(), 40);
        System.out.println("Population generations ends");
        System.out.println("Before:\nLength: " + p.getLength());
        p.calculateAllCF();
        System.out.println("MaxCF " + p.getMaxCF());
        System.out.println("MinCF " + p.getMinCF());
        System.out.println("MidCF " + p.getCF_mid() + "\n-----------");

        long start = new Date().getTime();
        long end = start;

        do {

            int c_k = 0;
            List<Chromosome> newpop = new ArrayList<>();
            for (Chromosome c : p.getData()) {
                int pk = (int) (Math.random() * 100);
                if (pk <= p_k) {
                    Chromosome xrom = new Chromosome();
                    Chromosome xrom2 = new Chromosome();
                    xrom.setData(c.getData());
                    int k = (int) (Math.random() * p.getLength());
                    xrom2.setData(p.getData().get(k).getData());
                    xrom.OK_PointOne(xrom2);

                    newpop.add(xrom);
                    newpop.add(xrom2);
                    c_k++;
                }
            }

            int c_m = 0;
            for (Chromosome c : newpop) {
                int pm = (int) (Math.random() * 100);
                if (pm <= p_m) {
                    c.MT_PointTwo();
                    c_m++;
                }
            }

            p2 = new Population(newpop);
            p.adjustPopulationD(p2);
            p.calculateAllCF();
            p = p.SelectionWheelFortune(40);
            p.setFile(filename);
            p.calculateAllCF();
            System.out.println("After:\nLenght " + p.getLength());
            System.out.println("Kol OK " + c_k);
            System.out.println("Kol MT " + c_m);
            System.out.println("MaxCF " + p.getMaxCF());
            System.out.println("MinCF " + p.getMinCF());
            System.out.println("MidCF " + p.getCF_mid() + "\n========");
            end = new Date().getTime();
        } while ((end - start) <= t);

        return Search.LinearSearch(p.getData(), p.getMaxCF());
    }
}
