#!/bin/bash

# imprimo las letras de un array
array=("a" "b" "c" "d" "e")
for letra in "${array[@]}"; do
    echo "Letra: $letra"
done

letras="a2 b2 c2 d2 e2"
# imprimo las letras de un string
for letra in $letras; do
    echo "Letra: $letra"
done

# imprimo letras de un string con un separador
IFS=',' # Defino el separador
letras="a3,b3,c3,d3,e3"
for letra in $letras; do
    echo "Letra: $letra"
done