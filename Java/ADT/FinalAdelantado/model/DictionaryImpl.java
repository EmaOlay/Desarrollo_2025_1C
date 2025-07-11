package model;

import enums.UnitaryPredicateEnum; 

public class DictionaryImpl implements Dictionary {
    private DictionaryEntry[] entries;
    private int size;
    private int maxValuesPerKey; 

    private static final int DEFAULT_CAPACITY = 10;

    public DictionaryImpl(int lu) {
        this.maxValuesPerKey = (UnitaryPredicateEnum.getPredicate(lu).getValue()) + 1;
        this.entries = new DictionaryEntry[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(int k, int v) {
        
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == k) {
                if (!entries[i].addValue(v)) {
                    System.out.println("Warning: Maximum values reached for key: " + k + ". Value not added.");
                }
                return;
            }
        }

        if (size == entries.length) {
            resize();
        }
        entries[size] = new DictionaryEntry(k, v, maxValuesPerKey);
        size++;
    }


    @Override
    public void remove(int k, int v) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == k) {
                for (int j = i; j < size - 1; j++) {
                    entries[j] = entries[j + 1];
                }
                entries[size - 1] = null;
                size--;
                return;
            }
        }
    }


    // Preconditions: none
    // Complexity: O(N) to build the set of keys
    @Override
    public Set getKeys() {
        SetImpl keys = new SetImpl();
        for (int i = 0; i < size; i++) {
            keys.add(entries[i].getKey());
        }
        return keys;
    }

    // Preconditions: key exists in the dictionary, otherwise throws IllegalStateException.
    // Complexity: O(N) in worst case to find the key
    @Override
    public int getValue(int k) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == k) {
                return entries[i].getRandomValue();
            }
        }
        throw new IllegalStateException("Key " + k + " not found in dictionary.");
    }

    private void resize() {
        DictionaryEntry[] newEntries = new DictionaryEntry[entries.length * 2];
        for (int i = 0; i < size; i++) {
            newEntries[i] = entries[i];
        }
        entries = newEntries;
    }

    public boolean containsKey(int k) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == k) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }
}