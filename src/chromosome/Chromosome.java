/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Search;
import utils.Variant;

/**
 *
 * @author Admin
 */
public class Chromosome extends Object {

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

    public int BinToDec() {
        int result = 0;
        for (int i = 0; i < this.length; i++) {
            int x = (int) Integer.valueOf(data[i].toString());
            result += (int) (x * Math.pow((double) 2, (double) this.length - 1 - i));
        }
        return result;
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
    public int[] OK_PointTwo(Chromosome chr2) {
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
    public int[] OK_PointThree(Chromosome chr2) {
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
    public int[] OK_SerialOnePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        // позиции гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = (int) (Math.random() * (length - 1));

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            if (i <= point[0]) {
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
    public int[] OK_serialTwoPoint(Chromosome chr2) {
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
            if (i <= point[0] || i > point[1]) {
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
     * частично-соответствующий одноточечный оператор кроссинговера</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точка разрыва
     */
    public int[] OK_PartialLinkedOnePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        // позиция гена, после которого точка разрыва
        int[] point = new int[1];
        point[0] = (int) (Math.random() * (length - 1));

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];
        for (int i = point[0] + 1; i < data.length; i++) {
            //left part ==>
            res1[i] = chr2.data[i];
            res2[i] = data[i];

        }
        for (int i = 0; i < point[0] + 1; i++) {
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
    public int[] OK_PartialLinkedTwoPoint(Chromosome chr2) {
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
    public int[] OK_cicle(Chromosome chr2) {

        //TODO: make algorithm
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        // позиции гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = (int) (Math.random() * (length - 1));

        Object[] res1 = new Object[data.length];
        Object[] res2 = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            if (i <= point[0]) {
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
    public int[] OK_Universal(Chromosome chr2) {
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
    public void OK_Universal(Chromosome chr2, int[] mask) {

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
        cf = BinToDec();
        return cf;
    }

    /**
     * <p>
     * Жадный оператор кроссинговера
     * </p>
     *
     * @param c Массив хромосом-предков типа Chromosome
     * @param table Матрица смежности графа для данного надора хромосом
     * @param alphabet Алфавит, использующийся в хромосомах в виде массива
     * (обяхательно упорядоченный так же как и столбцы/строки в матрице
     * смежности)
     * @param isMax Максимизируется ли функция; true - максимизация, false -
     * минимизация
     * @return Хромосома-потомок
     */
    public static Chromosome OK_greedy(Chromosome[] c, int[][] table, Object[] alphabet, boolean isMax) {
        if (c.length < 1) {
            return null;
        }
        int c_len = c[0].length;
        for (Chromosome ce : c) {
            if (ce.length != c_len) {
                System.err.println("Chromosomes' lengths is different");
                return null;
            }
        }
        int sel_chromo = (int) (Math.random() * c.length);
        int point = (int) (Math.random() * (c_len - 1));

        System.out.println("selected: " + sel_chromo + " - " + c[sel_chromo].toString(point));

        Chromosome result = new Chromosome(c_len);

        List<Variant> vars = new ArrayList<>();

        result.data = new Object[c_len];
        result.data[0] = c[sel_chromo].data[point];

        int prev_add_index = 0;
        for (int a = 0; a < alphabet.length; a++) {
            if (alphabet[a] == result.data[0]) {
                prev_add_index = a;
            }
        }

        for (int a = 1; a < c_len; a++) {
            //находим варианты и их стоимости 
            for (int i = 0; i < table[prev_add_index].length; i++) {
                int cur_table_value = table[prev_add_index][i];
                if (cur_table_value > 0) {
                    vars.add(new Variant(alphabet[i], cur_table_value));
                }
            }
            //отсеиваем недопустимые варианты
            ArrayList<Variant> vars_cpy = new ArrayList<>(vars);
            for (Variant v : vars_cpy) {
                boolean var_ok = false;
                for (int counter = 0; counter < c.length; counter++) {
                    Object[] s = {result.data[a - 1], v.value};
                    if (c[counter].findSequence(s)) {
                        var_ok = true;
                        break;
                    }
                }
                if (Search.LinearSearch(result.data, v.value) != -1) {
                    var_ok = false;
                }
                if (!var_ok) {
                    vars.remove(v);
                }

            }

            //выбираем мин/макс стоимость, добавляем значение
            Object sel_value = null;
            int sel_weight;
            if (isMax) {
                sel_weight = -1;
                for (Variant v : vars) {
                    if (v.weight > sel_weight) {
                        sel_weight = v.weight;
                        sel_value = v.value;
                    }
                }
            } else {
                sel_weight = Integer.MAX_VALUE;
                for (Variant v : vars) {
                    if (v.weight < sel_weight) {
                        sel_weight = v.weight;
                        sel_value = v.value;
                    }
                }
            }
            if (sel_value == null) {
                for (Object o : alphabet) {
                    if (Search.LinearSearch(result.data, o) == -1) {
                        sel_value = o;
                    }
                }
            }
            result.data[a] = sel_value;
            prev_add_index = Search.LinearSearch(alphabet, sel_value);
            vars.clear();
        }
        //обнуляем список вариантов
        //повторяем пока не заполним хромосому

        return result;
    }

    /**
     * <p>
     * одноточечный оператор кроссинговера на основе чисел фибоначи</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точка разрыва
     */
    public int[] OK_FibonacciOnePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        // позиция гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = Math.round((float) (length - 1) * 2 / 5);

        for (int i = point[0] + 1; i < length; i++) {
            //swap
            Object d = data[i];
            data[i] = chr2.data[i];
            chr2.data[i] = d;
        }
        return point;
    }

    /**
     * <p>
     * двухточечный оператор кроссинговера на основе чисел фибоначи</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Массив точек разрыва
     */
    public int[] OK_FibonacciTwoPoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }

        // позиции гена, после которой точка разрыва
        int point[] = new int[2];
        point[0] = Math.round((float) (length - 1) * 2 / 5);
        point[1] = Math.round((float) (length - 1) * 3 / 5);

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
     * трехточечный оператор кроссинговера на основе чисел фибоначи</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Массив точек разрыва
     */
    public int[] OK_FibonacciThreePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }

        // позиции гена, после которой точка разрыва
        int point[] = new int[3];
        point[0] = Math.round((float) (length - 1) * 2 / 5);
        point[1] = Math.round((float) (length - 1) * 3 / 5);
        point[2] = Math.round((float) (length - 1) - (float) ((length - 1) * 3 / 5) + ((float) (length - 1) * 2 / 5));
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
     * одноточечный оператор кроссинговера на основе золотого сечения</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Точка разрыва
     */
    public int[] OK_GoldOnePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        // позиция гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = Math.round((float) (length - 1) - (float) (length - 1) / 1.618f);

        for (int i = point[0] + 1; i < length; i++) {
            //swap
            Object d = data[i];
            data[i] = chr2.data[i];
            chr2.data[i] = d;
        }
        return point;
    }

    /**
     * <p>
     * двухточечный оператор кроссинговера на основе золотого сечения</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Массив точек разрыва
     */
    public int[] OK_GoldTwoPoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }

        // позиции гена, после которой точка разрыва
        int point[] = new int[2];
        point[0] = Math.round((float) (length - 1) - (float) (length - 1) / 1.618f);
        point[1] = Math.round((float) (length - 1) / 1.618f);

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
     * трехточечный оператор кроссинговера на основе чисел фибоначи</p>
     * <p>
     * Используется отноительно текущей хромосомы this и
     * хромосомой-аргументом.</p>
     *
     * @param chr2 вторая хромосома для кроссинговера
     * @return Массив точек разрыва
     */
    public int[] OK_GoldThreePoint(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }

        // позиции гена, после которой точка разрыва
        int point[] = new int[3];
        point[0] = Math.round((float) (length - 1) - (float) (length - 1) / 1.618f);
        point[1] = Math.round((float) (length - 1) / 1.618f);
        point[2] = Math.round(((float) (length - 1) / 1.618f) + (float) (length - 1 - ((float) (length - 1) / 1.618f)) / 1.618f);
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
     * оператор точечной мутации</p>
     * <p>
     * Используется отноительно текущей хромосомы this. Применима только к
     * бинарным хромосомам.</p>
     *
     * @return Точка разрыва
     */
    public int[] MT_Pointer() {
        // позиции гена, который инвертируется
        int[] point = new int[1];
        point[0] = (int) (Math.random() * (length - 1));

        //инвентирование
        if (data[point[0]].equals('0')) {
            data[point[0]] = '1';
        } else {
            data[point[0]] = '0';
        }
        return point;
    }

    /**
     * <p>
     * одноточечный оператор мутации на основе золотого сечения</p>
     * <p>
     * Используется отноительно текущей хромосомы this.</p>
     *
     * @return Точка разрыва
     */
    public int[] MT_GoldOnePoint() {
        // позиция гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = Math.round((float) (length - 1) - (float) (length - 1) / 1.618f);

        //swap меняются гены стоящие слева и справа точки разрыва
        Object d = data[point[0]];
        data[point[0]] = data[point[0] + 1];
        data[point[0] + 1] = d;

        return point;
    }

    /**
     * <p>
     * двухточечный оператор мутации на основе золотого сечения</p>
     * <p>
     * Используется отноительно текущей хромосомы this.</p>
     *
     * @return Точка разрыва
     */
    public int[] MT_GoldTwoPoint() {
        // позиции гена, после которой точка разрыва
        int point[] = new int[2];
        point[0] = Math.round((float) (length - 1) - (float) (length - 1) / 1.618f);
        point[1] = Math.round((float) (length - 1) / 1.618f);

        //swap меняются гены стоящие справа точки разрыва
        Object d = data[point[0] + 1];
        data[point[0] + 1] = data[point[1] + 1];
        data[point[1] + 1] = d;

        return point;
    }

    /**
     * <p>
     * одноточечный оператор мутации</p>
     * <p>
     * Используется отноительно текущей хромосомы this.</p>
     *
     * @return Точка разрыва
     */
    public int[] MT_PointOne() {
        // позиция гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = (int) (Math.random() * (length - 1));

        //swap меняются гены стоящие слева и справа точки разрыва
        Object d = data[point[0]];
        data[point[0]] = data[point[0] + 1];
        data[point[0] + 1] = d;

        return point;
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

    /**
     * <p>
     * одноточечный оператор мутации на основе чисил фибоначи</p>
     * <p>
     * Используется отноительно текущей хромосомы this.</p>
     *
     * @return Точка разрыва
     */
    public int[] MT_FibanacciOnePoint() {
        // позиция гена, после которой точка разрыва
        int[] point = new int[1];
        point[0] = (int) Math.round((float) (length - 1) * 2 / 5);

        //swap меняются гены стоящие слева и справа точки разрыва
        Object d = data[point[0]];
        data[point[0]] = data[point[0] + 1];
        data[point[0] + 1] = d;

        return point;
    }

    /**
     * <p>
     * двухточечный оператор мутации на основе чисил фибоначи</p>
     * <p>
     * Используется отноительно текущей хромосомы this.</p>
     *
     * @return Точка разрыва
     */
    public int[] MT_FibanacciTwoPoint() {
        // позиции гена, после которой точка разрыва
        int point[] = new int[2];
        point[0] = Math.round((float) (length - 1) * 2 / 5);
        point[1] = Math.round((float) (length - 1) * 3 / 5);

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

    /**
     * <p>
     * оператор инверсии</p>
     * <p>
     * Используется отноительно текущей хромосомы this. Полностью инвертирует
     * хромосому. </p>
     *
     */
    public void Inversion() {

        for (int i = 0; i < (int) data.length / 2; i++) {
            Object d = data[i];
            data[i] = data[data.length - i - 1];
            data[data.length - i - 1] = d;
        }
    }

    /**
     * <p>
     * оператор инверсии одноточечный</p>
     * <p>
     * Используется отноительно текущей хромосомы this. Случайным образом
     * получается точка разрыва за которой часть хромосомы инвертируется. </p>
     *
     * @return Точки разрыва
     */
    public int[] INV_PointOne() {
        // получение точки разрыва
        int point[] = new int[1];
        point[0] = (int) (Math.random() * (length - 1));
        // расчет центрального элемента вокруг которого происходит раворот части хромосомы
        int center = ((data.length - point[0] - 1) / 2 + point[0] + 1);
        //System.out.println(center);
        for (int i = point[0] + 1; i < center; i++) {
          //  System.out.println(i);
            //  System.out.println(data.length-i+point[0]);
            // swap
            Object d = data[i];
            data[i] = data[data.length - i + point[0]];
            data[data.length - i + point[0]] = d;
        }

        return point;
    }

    /**
     * <p>
     * оператор инверсии двухточечный</p>
     * <p>
     * Используется отноительно текущей хромосомы this. Случайным образом
     * получаются две точки разрыва между которыми часть хромосомы
     * инвертируется. </p>
     *
     * @return Точки разрыва
     */
    public int[] INV_PointTwo() {
        // получение точки разрыва
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

        return INV_PointTwo(point);
    }

    /**
     * <p>
     * оператор инверсии двухточечный</p>
     * <p>
     * Используется отноительно текущей хромосомы this. Передаются две точки
     * разрыва между которыми часть хромосомы инвертируется. </p>
     *
     * @param point точки разрыва
     * @return Точки разрыва
     */
    public int[] INV_PointTwo(int[] point) {
        // расчет центрального элемента вокруг которого происходит раворот части хромосомы
        int center = ((point[1] - point[0] - 1) / 2 + point[0] + 1);
        //System.out.println(center);
        for (int i = point[0] + 1; i <= center; i++) {
            if (i == (int) (point[1] - i + point[0] + 1)) {
                continue;
            }
            //System.out.println(i);
            //System.out.println(point[1]-i+point[0]+1);
            // swap
            Object d = data[i];
            data[i] = data[point[1] - i + point[0] + 1];
            data[point[1] - i + point[0] + 1] = d;
        }

        return point;
    }

    /**
     * <p>
     * оператор транслокации</p>
     * <p>
     * Используется отноительно текущей хромосомы this и хромосомой-аргументом.
     * Случаным образом получается точка разрыва, после чего проводится обмен
     * правыми частями хромосом и их инвертация.</p>
     *
     * @param chr2 вторая хромосома
     * @return Точки разрыва
     */
    public int[] Translocation(Chromosome chr2) {
        if (this.getLength() != chr2.getLength()) {
            return null;
        }
        int point[];
        int point2[] = new int[2];

        point = this.OK_PointOne(chr2);
        point2[0] = point[0];
        point2[1] = length - 1;
        this.INV_PointTwo(point2);
        chr2.INV_PointTwo(point2);

        return point;
    }

    public boolean findSequence(Object[] sequence) {
        if (data.length < sequence.length) {
            return false;
        }
        for (int i = 0; i < data.length - sequence.length + 1; i++) {
            boolean equal = true;
            for (int k = i; k < sequence.length + i && equal; k++) {
                if (data[k] != sequence[k - i]) {
                    equal = false;
                }
            }
            if (equal) {
                return true;
            }
        }
        return false;
    }
}
