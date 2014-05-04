/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Objects;

/**
 *
 * @author Admin
 */
public class Variant {
    public int weight;
    public Object value;
    
    public Variant(Object v,int w)
    {
        weight = w;
        value = v;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.weight;
        hash = 97 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Variant other = (Variant) obj;
        if (this.weight != other.weight) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return String.valueOf(value)+" : "+String.valueOf(weight);
    }
}
