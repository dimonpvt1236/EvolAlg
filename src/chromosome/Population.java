/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Population {

    private List<Chromosome> data;
    private int length;
    private int chromoLength;
    private double sumCF;
    private double maxCF;
    private double CF_mid;
  
    
    public Population() {
    }

    public Population(List<Chromosome> d) {
        data = d;
        length = d.size();
        try {
            chromoLength = d.get(0).getLength();
        } catch (Exception ex) {
            ex.toString();
        }
    }

    public Population setLength(int l) {
        length = l;
        return this;
    }

    public int getLength() {
        return length;
    }

    public int getChromoLength() {
        return chromoLength;
    }

    public Population setChromoLength(int chrLen) {
        chromoLength = chrLen;
        return this;
    }

    public Population setData(List<Chromosome> d) {
        data = d;
        return this;
    }

    public List<Chromosome> getData() {
        return data;
    }

    @Override
    public String toString() {
        String str = "";
        for (Chromosome c : data) {
            str += c.toString() + "\n";
        }
        str += "Total: " + data.size() + " chromosome(s)\n";
        return str;
    }

    @Override
    public boolean equals(Object p) {
        return p.hashCode() == this.hashCode() && p.getClass() == Population.class;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.data);
        hash = 83 * hash + this.length;
        hash = 83 * hash + this.chromoLength;
        return hash;
    }

    public Population addChromosome(Chromosome c) {
        data.add(c);
        return this;
    }

    public Population removeChromosome(Chromosome c) {
        data.remove(c);
        return this;
    }

    public Population removeChromosomeByIndex(int i) {
        if (i < data.size() && i >= 0) {
            data.remove(i);
        }
        return this;
    }

    public Population genFullPopulation(int chrLen) {

        chromoLength = chrLen;
        length = (int) Math.pow(2, chromoLength);
        data = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Object[] chr = new Object[chromoLength];

            //setting 0 to all chromosome
            for (int l = 0; l < chromoLength; l++) {
                chr[l] = 0;
            }

            for (int j = 0; j < chromoLength; j++) {
                int x = i;
                int k = chromoLength - 1;
                while (x != 0) {
                    chr[k] = x % 2;
                    x = (int) x / 2;
                    k--;
                }

            }
            data.add(new Chromosome(chromoLength).setData(chr));

        }

        return this;
    }

    public Population addShotgun(float percentage) {

        List<Chromosome> newpop = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (Math.random() * 100 + 1 <= percentage) {
                newpop.add(data.get(i));
            }
        }
        data = newpop;

        length = newpop.size();
        return this;
    }

    public Population addFocus(int focusPoint, int radius, float percentage) {

        int left = focusPoint - radius;
        int right = focusPoint + radius;

        List<Chromosome> newpop = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            double rand = Math.random() * 100 + 1;
            if (rand <= percentage && i <= right && i >= left) {
                newpop.add(data.get(i));
            }
        }

        data = newpop;

        length = newpop.size();
        return this;
    }

    /**
     * <p>
     * Вычислеят значение целевой функции для хромосомы</p>
     * <p>
     * На данным момент возвращает просто десятичное значение двичной хромосомы
     * в послдствии необходимо задать саму функцию значение которой будет
     * высчитываться.</p>
     *
     */
    public void calculateAllCF() {
        sumCF = 0;
        maxCF = 0;
        for (Chromosome c : data) {
            sumCF += c.FunctionValue();
            if (maxCF < c.FunctionValue()) {
                maxCF = c.FunctionValue();
            }
        }
        CF_mid = sumCF / data.size();
    }

    public Population SelectionWheelFortune() {
        if (sumCF == 0) {
            calculateAllCF();
           
        }

        double[] P = new double[data.size()];
        double[] N = new double[data.size()];
        int[] Nreal = new int[data.size()];
        int NrealSum = 0;
        for (int i = 0; i < data.size(); i++) {
            P[i] = data.get(i).FunctionValue() / sumCF;
            N[i] = data.get(i).FunctionValue() / CF_mid;
            Nreal[i] = (int) Math.round(N[i]);
            NrealSum += Nreal[i];
        }
       
        List<Chromosome> newData = new ArrayList<>();
        for (int i = 0; i < NrealSum; i++) {
            double rand = Math.random();
            for (int j = 0; j < data.size(); j++) {
                double prev = 0.;
                int c = j;
                while (c >= 0) {
                    prev += P[c--];
                   
                }

                if (rand < prev) {
                    newData.add(data.get(j));
                    break;
                }

            }
        }

        return new Population(newData);
    }
    
    /**
     * Селекция на основе заданной шкалы
     * @return Новая популяция
     */
    public Population SelectionByScale(){
        List newdata = new ArrayList<>();
        
        return new Population(newdata);
    }
    
    /**
     * Турнирная селекция
     * @return Новая популяция
     */
    public Population SelectionTournament(){
        List newdata = new ArrayList<>();
        
        return new Population(newdata);
    }
    

    /**
     * Сортирует популяцию
     *
     * @param byMax Если по возрастанию - true, иначе false
     */
    public void Sort(boolean byMax) {
        Comparator c = (Comparator) (Object o1, Object o2) -> {
            int r;
            if (((Chromosome) o1).FunctionValue() > ((Chromosome) o2).FunctionValue()) {
                r = 1;
            } else if (((Chromosome) o1).FunctionValue() == ((Chromosome) o2).FunctionValue()) {
                r = 0;
            } else {
                r = -1;
            }

            if (!byMax) {
                r = -r;
            }
            return r;

        };

        data.sort(c);
    }
    
    
}
