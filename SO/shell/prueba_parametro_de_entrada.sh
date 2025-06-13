#!/bin/bash

# hago un for loop de 1 al parametro de entrada
if [ $# -eq 0 ]; then
    echo "No se ha pasado ningun parametro"
    exit 1
fi
for i in $(seq 1 $1); do
    echo "Contador: $i"
done
# hago un for loop de 1 al parametro de entrada con un paso de 2
for i in $(seq 1 2 $1); do
    echo "Contador: $i"
done