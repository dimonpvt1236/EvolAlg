/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import chromosome.Chromosome;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Search {

    public static int LinearSearch(Object[] a, Object key) {
        int r = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return r;
    }

    public static Chromosome LinearSearch(List<Chromosome> data, double key) {
        for (Chromosome a : data) {
            if ((a.getCF() - key) <= 0.000000000001) {
                return a;
            }
        }
        return null;
    }
}
