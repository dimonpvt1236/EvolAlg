/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chromosome;

import java.util.Arrays;
import utils.Search;

/**
 *
 * @author Admin
 */
public class Chromosome extends Object {

    private int length;

    private Object[] data;

    public Chromosome() {
    }

    public Chromosome(int l) {
        length = l;
    }

    public int getLength() {
        return length;
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
        return this;
    }

    public Object[] getData() {
        return data;
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
        int hash = 7;
        hash = 89 * hash + this.length;
        hash = 89 * hash + Arrays.deepHashCode(this.data);
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
    public int OK_onePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return -1;
        }

        // позиция гена, после которой точка разрыва
        int point = (int) (Math.random() * (length - 1));

        for (int i = point + 1; i < length; i++) {
            //swap
            Object d = data[i];
            data[i] = chr2.data[i];
            chr2.data[i] = d;
        }
        return point;
    }

    /**
     * <p>
     * двухточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Массив точек разрыва
     */
    public int[] OK_twoPoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }

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

        for (int i = point[0] + 1; i <= point[1]; i++) {
            //swap
            Object d = data[i];
            data[i] = chr2.data[i];
            chr2.data[i] = d;
        }
        return point;
    }

    /**
     * <p>
     * трехточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Массив точек разрыва
     */
    public int[] OK_threePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }

        // позиции гена, после которой точка разрыва
        int point[] = new int[3];
        point[0] = (int) (Math.random() * (length - 1));
        do {
            point[1] = (int) (Math.random() * (length - 1));
        } while (point[0] == point[1]);
        do {
            point[2] = (int) (Math.random() * (length - 1));
        } while (point[0] == point[2] || point[1] == point[2]);

        // Упорядочиваем точки
        Arrays.sort(point);

        for (int i = point[0] + 1; i <= point[1]; i++) {
            //swap
            Object d = data[i];
            data[i] = chr2.data[i];
            chr2.data[i] = d;
        }
        for (int i = point[2] + 1; i < data.length; i++) {
            //swap
            Object d = data[i];
            data[i] = chr2.data[i];
            chr2.data[i] = d;
        }
        return point;
    }

    /**
     * <p>
     * упорядочивающий одноточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точка разрыва
     */
    public int OK_serialOnePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return -1;
        }
        // позиции гена, после которой точка разрыва
        int point;
        point = (int) (Math.random() * (length - 1));

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            if (i <= point) {
                res1[i] = data[i];
                res2[i] = chr2.data[i];
            } else {
                for (int j = 0; j < chr2.data.length; j++) {
                    Object obj = chr2.data[j];
                    boolean isIn = false;
                    for (Object o : res1) {
                        if (o == obj) {
                            isIn = true;
                            break;
                        }
                    }
                    if (!isIn) {
                        res1[i] = obj;
                        break;
                    }
                }

                for (int j = 0; j < data.length; j++) {
                    Object obj = data[j];
                    boolean isIn = false;
                    for (Object o : res2) {
                        if (o == obj) {
                            isIn = true;
                            break;
                        }
                    }
                    if (!isIn) {
                        res2[i] = obj;
                        break;
                    }
                }
            }
        }
        data = res1;
        chr2.data = res2;
        return point;
    }

    /**
     * <p>
     * упорядочивающий одноточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точки разрыва
     */
    public int[] OK_serialtwoPoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        // позиции гена, после которой точка разрыва
        int[] point = new int[2];
        point[0] = (int) (Math.random() * (length - 1));
        do {
            point[0] = (int) (Math.random() * (length - 1));
        } while (point[0] == point[1]);
        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            if (i <= point[0] || i>point [1]) {
                res1[i] = data[i];
                res2[i] = chr2.data[i];
            } else{
                for (int j = 0; j < chr2.data.length; j++) {
                    Object obj = chr2.data[j];
                    boolean isIn = false;
                    for (Object o : res1) {
                        if (o == obj) {
                            isIn = true;
                            break;
                        }
                    }
                    if (!isIn) {
                        res1[i] = obj;
                        break;
                    }
                }

                for (int j = 0; j < data.length; j++) {
                    Object obj = data[j];
                    boolean isIn = false;
                    for (Object o : res2) {
                        if (o == obj) {
                            isIn = true;
                            break;
                        }
                    }
                    if (!isIn) {
                        res2[i] = obj;
                        break;
                    }
                }
            }
        }
        data = res1;
        chr2.data = res2;
        return point;
    }

    /**
     * <p>
     * частично-соответствующий одноточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точка разрыва
     */
    public int OK_partialLinkedOnePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return -1;
        }
        // позиция гена, после которого точка разрыва
        int point;
        point = (int) (Math.random() * (length - 1));

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];
        for (int i = point + 1; i < data.length; i++) {
            //left part ==>
            res1[i] = chr2.data[i];
            res2[i] = data[i];

        }
        for (int i = 0; i < point + 1; i++) {
            //right part <==
            //1
            Object curValue1;
            int positionOfValue1 = 0;
            int pos1 = i;
            while (positionOfValue1 != -1) {
                if (pos1 > data.length - 1) {
                    System.err.println("unable to find swap");
                    break;
                }
                curValue1 = data[pos1];
                positionOfValue1 = Search.LinearSearch(res1, curValue1);
                if (positionOfValue1 == -1) {
                    res1[i] = curValue1;
                } else {
                    curValue1 = chr2.data[pos1];
                    positionOfValue1 = Search.LinearSearch(res1, curValue1);
                    if (positionOfValue1 == -1) {
                        res1[i] = curValue1;
                    } else {
                        pos1++;
                    }
                }
            }

            //2
            Object curValue2;
            int positionOfValue2 = 0;
            int pos2 = i;
            while (positionOfValue2 != -1) {
                if (pos2 > data.length - 1) {
                    System.err.println("unable to find swap");
                    break;
                }
                curValue2 = chr2.data[pos2];
                positionOfValue2 = Search.LinearSearch(res2, curValue2);
                if (positionOfValue2 == -1) {
                    res2[i] = curValue2;
                } else {
                    curValue2 = data[pos2];
                    positionOfValue2 = Search.LinearSearch(res2, curValue2);
                    if (positionOfValue2 == -1) {
                        res2[i] = curValue2;
                    } else {
                        pos2++;
                    }
                }
            }

        }

        data = res1;
        chr2.data = res2;
        return point;
    }

    /**
     * <p>
     * частично-соответствующий двухточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точки разрыва
     */
    public int[] OK_partialLinkedTwoPoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        // позиция гена, после которого точка разрыва
        int[] points = new int[2];
        points[0] = (int) (Math.random() * (length - 1));
        do {
            points[1] = (int) (Math.random() * (length - 1));
        } while (points[0] == points[1]);

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];
        for (int i = points[0] + 1; i < points[1]; i++) {
            //middle part >=<
            res1[i] = chr2.data[i];
            res2[i] = data[i];

        }
        for (int i = points[1] + 1; i < data.length; i++) {
            //left part <==
            //1
            Object curValue1;
            int positionOfValue1 = 0;
            int pos1 = i;
            while (positionOfValue1 != -1) {
                if (pos1 > data.length - 1) {
                    System.err.println("unable to find swap");
                    break;
                }
                curValue1 = data[pos1];
                positionOfValue1 = Search.LinearSearch(res1, curValue1);
                if (positionOfValue1 == -1) {
                    res1[i] = curValue1;
                } else {
                    curValue1 = chr2.data[pos1];
                    positionOfValue1 = Search.LinearSearch(res1, curValue1);
                    if (positionOfValue1 == -1) {
                        res1[i] = curValue1;
                    } else {
                        pos1++;
                    }
                }
            }

            //2
            Object curValue2;
            int positionOfValue2 = 0;
            int pos2 = i;
            while (positionOfValue2 != -1) {
                if (pos2 > data.length - 1) {
                    System.err.println("unable to find swap");
                    break;
                }
                curValue2 = chr2.data[pos2];
                positionOfValue2 = Search.LinearSearch(res2, curValue2);
                if (positionOfValue2 == -1) {
                    res2[i] = curValue2;
                } else {
                    curValue2 = data[pos2];
                    positionOfValue2 = Search.LinearSearch(res2, curValue2);
                    if (positionOfValue2 == -1) {
                        res2[i] = curValue2;
                    } else {
                        pos2++;
                    }
                }
            }

        }
        for (int i = 0; i < points[0] + 1; i++) {
            //left part <==
            //1
            Object curValue1;
            int positionOfValue1 = 0;
            int pos1 = i;
            while (positionOfValue1 != -1) {
                if (pos1 > data.length - 1) {
                    System.err.println("unable to find swap");
                    break;
                }
                curValue1 = data[pos1];
                positionOfValue1 = Search.LinearSearch(res1, curValue1);
                if (positionOfValue1 == -1) {
                    res1[i] = curValue1;
                } else {
                    curValue1 = chr2.data[pos1];
                    positionOfValue1 = Search.LinearSearch(res1, curValue1);
                    if (positionOfValue1 == -1) {
                        res1[i] = curValue1;
                    } else {
                        pos1++;
                    }
                }
            }

            //2
            Object curValue2;
            int positionOfValue2 = 0;
            int pos2 = i;
            while (positionOfValue2 != -1) {
                if (pos2 > data.length - 1) {
                    System.err.println("unable to find swap");
                    break;
                }
                curValue2 = chr2.data[pos2];
                positionOfValue2 = Search.LinearSearch(res2, curValue2);
                if (positionOfValue2 == -1) {
                    res2[i] = curValue2;
                } else {
                    curValue2 = data[pos2];
                    positionOfValue2 = Search.LinearSearch(res2, curValue2);
                    if (positionOfValue2 == -1) {
                        res2[i] = curValue2;
                    } else {
                        pos2++;
                    }
                }
            }

        }

        data = res1;
        chr2.data = res2;
        return points;
    }

    /**
     * <p>
     * Циклический оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точка разрыва
     */
    public int OK_cicle(Chromosome chr2) {

        //TODO: make algorithm
        if (this.getLength() != chr2.getLength()) {
            return -1;
        }
        // позиции гена, после которой точка разрыва
        int point;
        point = (int) (Math.random() * (length - 1));

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            if (i <= point) {
                res1[i] = data[i];
                res2[i] = chr2.data[i];
            } else {
                for (int j = 0; j < chr2.data.length; j++) {
                    Object obj = chr2.data[j];
                    boolean isIn = false;
                    for (Object o : res1) {
                        if (o == obj) {
                            isIn = true;
                            break;
                        }
                    }
                    if (!isIn) {
                        res1[i] = obj;
                        break;
                    }
                }

                for (int j = 0; j < data.length; j++) {
                    Object obj = data[j];
                    boolean isIn = false;
                    for (Object o : res2) {
                        if (o == obj) {
                            isIn = true;
                            break;
                        }
                    }
                    if (!isIn) {
                        res2[i] = obj;
                        break;
                    }
                }
            }
        }
        data = res1;
        chr2.data = res2;
        return point;
    }

    /**
     * <p>
     * Универсальный оператор кроссинговера с автоматической генерацией
     * маски</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Маска
     */
    public int[] OK_universal(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            System.err.println("length is different!");
            return null;
        }

        int mask[] = new int[data.length];

        for (int i = 0; i < mask.length; i++) {
            mask[i] = (int) (Math.random() * 2);
        }

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            res1[i] = mask[i] ^ (int) ((char) data[i] - '0');
            res2[i] = mask[i] ^ (int) ((char) chr2.data[i] - '0');
        }

        data = res1;
        chr2.data = res2;
        return mask;
    }

    /**
     * <p>
     * Универсальный оператор кроссинговера с ручным вводом маски генерацией
     * маски</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @param mask маска
     */
    public void OK_universal(Chromosome chr2, int[] mask) {

        //TODO: make algorithm
        if (this.getLength() != chr2.getLength()) {
            System.err.println("length is different!");
            return;
        }
        if (this.getLength() != mask.length) {
            System.err.println("mask length doesn't equal chromosome length");
        }

        for (int i = 0; i < mask.length; i++) {
            mask[i] = (int) (Math.random() * 2);
        }

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            res1[i] = mask[i] ^ Integer.parseInt((String) data[i], 2);
            res2[i] = mask[i] ^ Integer.parseInt((String) chr2.data[i], 2);
        }

        data = res1;
        chr2.data = res2;
    }

    public double FunctionValue() {

        return 0.f;
    }
    
    public static Chromosome OK_greedy(Chromosome[] c,int [][] table){
        if(c.length<1)return null;
        int c_len = c[0].length;
        for(Chromosome ce:c){
            if(c.length!=c_len){System.err.println("Chromosomes' lengths is different");return null;}
        }
        int sel_chromo = (int)(Math.random()*c.length);
        int point = (int)(Math.random()*c[0].length);
        
        System.out.println("selected: "+sel_chromo+" -  "+c[sel_chromo].toString(point));
        
        Chromosome result = new Chromosome(c_len);
        
        
        return result;
    }
    
}
