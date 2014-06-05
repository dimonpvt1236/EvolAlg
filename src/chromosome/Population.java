/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chromosome;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Population implements Cloneable {

    private List<Chromosome> data;
    private int length;
    private int chromoLength;
    private double sumCF;
    private double maxCF, minCF;
    private String file;
    private int[][] graph;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;

        File f = new File(file);
        if (f.length() == 0 && graph == null) {
            try {
                //generate file with new graph
                f.createNewFile();
                StringBuilder str = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String number;
                System.out.println("Файл не найден. Он будет создан с новым графом. \nВведите размерность генерируемого графа");
                number = in.readLine();
                int n = Integer.valueOf(number);
                graph = new int[n][n];
                str.append(number).append("\n");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        str.append(Math.round(Math.random())).append(' ');
                    }
                    str.append('\n');
                }

                FileWriter wrt = new FileWriter(f);
                wrt.write("");
                wrt.append(str);
                wrt.flush();
            } catch (IOException ex) {
                Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (graph == null && f.length() > 0) {
            try {
                try (Scanner in = new Scanner(f)) {
                    StringBuilder filedata = new StringBuilder();
                    int n = Integer.valueOf(in.nextLine());
                    for (int i = n; i != 0; i--) {
                        filedata.append(in.nextLine()).append("\n");
                    }
                    String[] rows = filedata.toString().split(" \n");
                    graph = new int[rows.length][rows.length];
                    int i = 0;
                    for (String s : rows) {
                        String[] subrows = s.split(" ");
                        int j = 0;
                        for (String sr : subrows) {
                            int pret = Integer.valueOf(sr);
                            graph[i][j] = (pret < 0) ? 0 : pret;
                            j++;
                        }
                        i++;
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.chromoLength = graph.length;
    }

    public double getSumCF() {
        return sumCF;
    }

    public double getMaxCF() {
        return maxCF;
    }

    public double getMinCF() {
        return minCF;
    }

    public double getCF_mid() {
        return CF_mid;
    }
    private double CF_mid;

    public Population() {
        data = new ArrayList<>();
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

    /**
     * Расширяет популяцию за счет другой популяции, дубликаты включены не будут
     *
     * @param p2 Другая популяция
     * @return Первая, расширенная популяция
     */
    public Population adjustPopulation(Population p2) {
        for (Chromosome c : p2.data) {
            if (!this.data.contains(c)) {
                this.data.add(c);
            }
        }
        return this;
    }

    /**
     * Расширяет популяцию за счет другой популяции, дубликаты будут добавлены
     *
     * @param p2 Другая популяция
     * @return Расширенная популяция
     */
    public Population adjustPopulationD(Population p2) {
        for (Chromosome c : p2.data) {
            this.data.add(c);
        }
        return this;
    }

    @Override
    public Population clone() throws CloneNotSupportedException {
        Population obj = (Population) super.clone();
        return obj;
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

    public String toStringWithCFs() {
        String str = "";
        for (Chromosome c : data) {
            str += c.toString() + " = " + c.getCF() + "\n";
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

    public Population genShotgun(int chrLen, float percentage) {
        chromoLength = chrLen;
        int initlength = (int) Math.pow(2, chromoLength);
        data = new ArrayList<>();

        for (int i = 0; i < initlength; i++) {
            if (Math.random() * 100 < percentage) {
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
        }
        length = data.size();
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
            sumCF += c.FunctionValue(graph);
            if (maxCF < c.getCF()) {
                maxCF = c.getCF();
            }
            if (minCF > c.getCF()) {
                minCF = c.getCF();
            }
        }
        CF_mid = sumCF / data.size();
    }

    public Population SelectionWheelFortune(int count) {
        if (sumCF == 0) {
            calculateAllCF();

        }

        double[] P = new double[data.size()];
        double[] N = new double[data.size()];
        int[] Nreal = new int[data.size()];
        int NrealSum = 0;
        for (int i = 0; i < data.size(); i++) {
            P[i] = data.get(i).FunctionValue(graph) / sumCF;
            N[i] = data.get(i).FunctionValue(graph) / CF_mid;
            Nreal[i] = (int) Math.round(N[i]);
            NrealSum += Nreal[i];
        }
        if (count != 0) {
            NrealSum = count;
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
     * Сортирует популяцию
     *
     * @param byMax Если по возрастанию - true, иначе false
     */
    public void Sort(boolean byMax) {
        Comparator c = (Comparator) (Object o1, Object o2) -> {
            int r;
            if (((Chromosome) o1).FunctionValue(graph) > ((Chromosome) o2).FunctionValue(graph)) {
                r = 1;
            } else if (((Chromosome) o1).FunctionValue(graph) == ((Chromosome) o2).FunctionValue(graph)) {
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
