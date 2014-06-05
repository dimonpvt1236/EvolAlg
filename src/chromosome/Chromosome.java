/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chromosome;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class Chromosome extends Object implements Cloneable {

    private int length;
    private double cf;

    private Object[] data;

    public Chromosome() {
    }

    public Chromosome(int l) {
        length = l;
    }

    public int getLength() {
        return length;
    }

    public double getCF() {
        return cf;
    }

    public void setCF(double cf) {
        this.cf = cf;
    }

    public Chromosome setLength(int l) {
        length = l;
        return this;
    }

    public Chromosome setData(Object[] d) {
        data = d;
        length = d.length;
        return this;
    }

    public Chromosome setData(String d) {
        Object[] o = new Object[d.length()];
        for (int i = 0; i < d.length(); i++) {
            o[i] = d.charAt(i);
        }
        data = o;
        length = data.length;
        return this;
    }

    public Object[] getData() {
        return data;
    }

    @Override
    public Chromosome clone() throws CloneNotSupportedException {
        Chromosome obj = (Chromosome) super.clone();
        return obj;
    }

    @Override
    public String toString() {
        String str = "";
        for (Object data1 : data) {
            str += data1 + " ";
        }
        return str;
    }

    public String toString(int crossPoint) {
        String str = "";

        for (int i = 0; i < data.length; i++) {

            str += data[i] + " ";
            if (i == crossPoint) {
                str += "| ";
            }

        }
        return str;
    }

    public String toString(int[] crossPoints) {
        String str = "";

        for (int i = 0; i < data.length; i++) {

            str += data[i] + " ";
            for (int j = 0; j < crossPoints.length; j++) {
                if (crossPoints[j] == i) {
                    str += "| ";
                    break;
                }
            }

        }
        return str;
    }

    @Override
    public boolean equals(Object c) {
        return c.getClass() == Chromosome.class
                && c.hashCode() == this.hashCode();

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.length;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.cf) ^ (Double.doubleToLongBits(this.cf) >>> 32));
        hash = 71 * hash + Arrays.deepHashCode(this.data);
        return hash;
    }

    /**
     * <p>
     * одноточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точка разрыва
     */
    public int[] OK_PointOne(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }

        // позиция гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = (int) (Math.random() * (length - 1));

        for (int i = point[0] + 1; i < length; i++) {
            //swap
            Object d = data[i];
            data[i] = chr2.data[i];
            chr2.data[i] = d;
        }
        return point;
    }

    public double FunctionValue(int[][] graph) {

        cf = 0;
        //расчет целевой функции
        int sum = 0;
        for (Object item : data) {
            if ((int) item >= 1) {
                sum++;
            }
        }
        if (sum == 0) {
            cf = 0.0;
            return cf;
        }
        if (sum == 1) {
            cf = 1.0;
            return cf;
        }

        Set<Integer> click = new HashSet<>();
        for (int i = 0; i < data.length; i++) {
            if ((int) data[i] >= 1) {
                click.add(i);
            }
        }

        Set<Integer> click_cpy = new HashSet<>(click);
        boolean incorrect = false;
        for (int i : click) {
            if (!incorrect) {
                for (int j : click_cpy) {
                    //try{
                    if (!(i == j || graph[i][j] > 0)) {
                        incorrect = true;
                        break;
                    }
                   //}catch(NullPointerException e){System.err.println(i+" "+j+" "+incorrect+" "+cf+" "+Arrays.toString(graph));}

                }
            }
        }

        if (!incorrect) {
            for (Object o : data) {
                cf += (int) o;
            }
        } else {
            cf = 0;
        }

        return cf;
    }

    /**
     * <p>
     * двухточечный оператор мутации</p>
     * <p>
     * Используется отноительно текущей хромосомы this.</p>
     *
     * @return Точка разрыва
     */
    public int[] MT_PointTwo() {
        // позиции гена, после которой точка разрыва
        int point[] = new int[2];
        point[0] = (int) (Math.random() * (length - 1));
        do {
            point[1] = (int) (Math.random() * (length - 1));
        } while (point[0] == point[1]);

        // Упорядочиваем точки
        if (point[1] < point[0]) {
            int s = point[0];
            point[0] = point[1];
            point[1] = s;
        }
        //swap меняются гены стоящие справа точки разрыва
        Object d = data[point[0] + 1];
        data[point[0] + 1] = data[point[1] + 1];
        data[point[1] + 1] = d;

        return point;
    }

}
