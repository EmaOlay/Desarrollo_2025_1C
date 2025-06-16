#!/bin/bash

# cuento del 1 al 10
contador=1
while [ $contador -le 10 ]; do
    echo "Contador: $contador"
    contador=$((contador + 1))
done
# cuento del 10 al 1
contador=10
while [ $contador -ge 1 ]; do
    echo "Contador: $contador"
    contador=$((contador - 1))
done
# cuento del 1 al 10 con break
contador=1
while [ $contador -le 10 ]; do
    echo "Contador: $contador"
    if [ $contador -eq 5 ]; then
        echo "Rompo el bucle"
        break
    fi
    contador=$((contador + 1))
done
# cuento del 1 al 10 con continue
contador=1
while [ $contador -le 10 ]; do
    if [ $contador -eq 5 ]; then
        echo "Salto el 5"
        contador=$((contador + 1))
        continue
    fi
    echo "Contador: $contador"
    contador=$((contador + 1))
done