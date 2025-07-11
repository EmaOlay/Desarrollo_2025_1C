package model;

import java.util.Random; // Supongo que esta premitido para la seleccion aleatoria de valores

public class DictionaryEntry {
    private int key;
    private int[] values;
    private int currentValuesCount; 
    private int maxAllowedValues;

    public DictionaryEntry(int key, int initialValue, int maxAllowedValues) {
        this.key = key;
        this.maxAllowedValues = maxAllowedValues;

        this.values = new int[maxAllowedValues];
        this.values[0] = initialValue;
        this.currentValuesCount = 1;
    }

    public int getKey() {
        return key;
    }

    public int getRandomValue() {
        if (currentValuesCount == 0) {
            throw new IllegalStateException("No values associated with this key.");
        }
        if (currentValuesCount == 1) {
            return values[0];
        } else {
            Random rand = new Random();
            return values[rand.nextInt(currentValuesCount)];
        }
    }

    // Preconditions: none
    // Complexity: O(1)
    public boolean addValue(int value) {
        if (currentValuesCount < maxAllowedValues) {
            this.values[currentValuesCount++] = value;
            return true;
        }
        return false; // Cannot add more values
    }

    // Preconditions: none
    // Complexity: O(1)
    public int getCurrentValuesCount() {
        return currentValuesCount;
    }

    // Preconditions: none
    // Complexity: O(1)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DictionaryEntry that = (DictionaryEntry) obj;
        return key == that.key; // Only compare keys for equality
    }

    // Preconditions: none
    // Complexity: O(1)
    @Override
    public int hashCode() {
        return Integer.hashCode(key);
    }
}