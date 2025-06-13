#!/bin/bash
# Definimos una variable
algo="algo_str"

if [ "${algo}" = "algo_str" ]; then
    echo "evalua igual"
else
    echo "No evalua igual"
fi

# Comprobamos variable numerica
numero=10
if [ "${numero}" -eq 10 ]; then
    echo "evalua igual"
else
    echo "No evalua igual"
fi
# Comprobamos si un archivo existe
if [ -f "/etc/passwd" ]; then
    echo "El fichero /etc/passwd existe"
else
    echo "El fichero /etc/passwd no existe"
fi
# Comprobamos si un directorio existe
if [ -d "/etc" ]; then
    echo "El directorio /etc existe"
else
    echo "El directorio /etc no existe"
fi