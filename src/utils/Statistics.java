/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Admin
 */
public class Statistics {
    
    private long start;
    private long end;
    private long timeResult;
    private int pK;
    private int pM;
    private int countK;
    private int countM;
    private int startPop;
    private double result;
    private double resultMAX;
    private double resultMID;
    
    @Override
    public String toString() {
        String str = "";
        str += "Time: " +timeResult+ "\n";
        str += "Probability of crossing over: " +pK+ "\n";
        str += "Probability of mutation: " +pM+ "\n";
        str += "Count of crossing over: " +countK+ "\n";
        str += "Count of mutation: " +countM+ "\n";
        str += "Start populary count: " + startPop + "\n";
        str += "Result MAX: " + resultMAX + "\n";
        str += "Result MID: " + resultMID + "\n";
        str += "==========================================\n";
        return str;
    }

    public double getResultMAX() {
        return resultMAX;
    }

    public void setResultMAX(double resultMAX) {
        this.resultMAX = resultMAX;
    }

    public double getResultMID() {
        return resultMID;
    }

    public void setResultMID(double resultMID) {
        this.resultMID = resultMID;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getTimeResult() {
        return timeResult;
    }

    public void setTimeResult(long timeResult) {
        this.timeResult = timeResult;
    }

    public int getpK() {
        return pK;
    }

    public void setpK(int pK) {
        this.pK = pK;
    }

    public int getpM() {
        return pM;
    }

    public void setpM(int pM) {
        this.pM = pM;
    }

    public int getCountK() {
        return countK;
    }

    public void setCountK(int countK) {
        this.countK = countK;
    }

    public int getCountM() {
        return countM;
    }

    public void setCountM(int countM) {
        this.countM = countM;
    }

    public int getStartPop() {
        return startPop;
    }

    public void setStartPop(int startPop) {
        this.startPop = startPop;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
      
}
