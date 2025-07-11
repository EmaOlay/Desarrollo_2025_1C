package model;

public interface Dictionary {
    void add(int k, int v);
    void remove(int k, int v);
    Set getKeys(); 
    int getValue(int k);
}