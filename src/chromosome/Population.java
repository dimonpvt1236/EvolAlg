/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class Population {

    private List<Chromosome> data;
    private int length;
    private int chromoLength;
    private double sumCF;
    private double maxCF, minCF;
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

    public Population adjustPopulation(Population p2) {
        for (Chromosome c : p2.data) {
            if (!this.data.contains(c)) {
                this.data.add(c);
            }
        }
        return this;
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
        minCF = Double.MAX_VALUE;
        for (Chromosome c : data) {
            sumCF += c.FunctionValue();
            if (maxCF < c.getCF()) {
                maxCF = c.getCF();
            }
            if (minCF > c.getCF()) {
                minCF = c.getCF();
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
     *
     * @param scale Массив процентов разделяющих на группы (0.00<x<1.00) для
     * разбиения на scale.length+1 групп. Т.е. если массив содержит 3 значения.
     * то это разобьет на 4 группы, где первые 3 получат заданные проценты, а
     * последняя то что останется @param percentage Массив процентов
     * вероятностей, размер этого массива на 1 больше чем размер scale @return
     * Новая популяция
     */
    public Population SelectionByScale(double[] scale, double[] percentage) {
        double sum = 0;
        for (double f : scale) {
            sum += f;
        }
        if (sum >= 1) {
            System.err.println("Ошибка шкалы");
            return null;
        }
        if (scale.length != percentage.length - 1) {
            System.err.println("Ошибка несоответствия длин массивов шкалы и вероятностей");
        }

        List newdata = new ArrayList<>();

        List[] groups = new List[scale.length + 1];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new ArrayList<>();
        }

        this.calculateAllCF();

        //разбиваем на группы
        for (Chromosome c : this.data) {
            for (int j = 0; j < scale.length + 1; j++) {
                double P = 0;
                int a = j;
                if (j != scale.length) {
                    while (a >= 0) {
                        P += scale[a--];
                    }
                } else {
                    P = 1.0;
                }
                if ((c.getCF() + minCF) / (maxCF - minCF) <= P) {
                    groups[j].add(c);
                    break;
                }
            }
        }

        for (int i = 0; i < groups.length; i++) {
            List<Chromosome> l = groups[i];
            for (Chromosome c : l) {
                double rand = Math.random();
                if (rand <= percentage[i]) {
                    newdata.add(c);
                }
            }
        }

        return new Population(newdata);
    }

    /**
     * Турнирная селекция
     *
     * @return Новая популяция
     */
    public Population SelectionTournament() {
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
