#!/bin/bash


# Crear un script de nombre script.sh en bash cuyo objetivo sea la creación y eliminación de usuarios, con
# las siguientes características
# 1) El script puede tomar dos parámetros. El primer parámetro puede ser crear o eliminar.
# 2) Si se seleccionó crear, el segundo parámetro es la cantidad de usuarios a crear.
# 3) Si se seleccionó eliminar, no toma el segundo parámetro y siempre es un único usuario.
# 4) Debe validar que cada comando ejecutado sea correcto.
# 5) el script debe contener funciones
# Registrar en un archivo log la actividad del script (usuarios creados, eliminado, errores)
# Generación de contraseñas aleatorias
# Confirmar cada acción realizada

# archivo de log
log_file="script.log"

# Funcion de logeo
function log{
    
    echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" >> "$log_file"
}

function crear_usuarios {
    for ((i=1; i<=$2; i++)); do
        username="user$i"
        password=$(tr -dc A-Za-z0-9 </dev/urandom | head -c 13; echo)
        useradd -m -p "$password" "$username"
        if [ $? -eq 0 ]; then
            echo "Usuario $username creado con éxito."
            log "Usuario $username creado con contraseña ${password}."
        else
            echo "Error al crear el usuario ${username}."
            log "Error al crear el usuario ${username}."
        fi
    done
}

# Borro usuario no admin al azar
function eliminar_usuario {
    # obtengo los usuarios generados por crear_usuarios
    usuario=$(cat /etc/passwd | grep ^user | cut -d: -f1)
    userdel -r "$usuario"
    if [ $? -eq 0 ]; then
        echo "Usuario $usuario eliminado con éxito."
        log "Usuario $usuario eliminado."
    else
        echo "Error al eliminar el usuario $usuario."
        log "Error al eliminar el usuario $usuario."
    fi
}

#Empieza el script

#valido cantidad de argumentos
if [ "$#" -lt 1 ] || [ "$#" -gt 2 ]; then
    echo "Uso: $0 {crear|eliminar} [cantidad]"
    exit -1
fi
# Pregunto si quiere crear
if [ "$1" == "crear" ]; then
    if [ -z "$2" ] || ! [[ "$2" =~ ^[0-9]+$ ]]; then
        echo "Debe especificar una cantidad válida de usuarios a crear."
        log "Error: Cantidad de usuarios no válida."
        exit 1
    fi
    # El $1 devuelta es medio innecesario pero lo dejo por conveniencia
    crear_usuarios "$1" "$2"
elif [ "$1" == "eliminar" ]; then
    eliminar_usuario "$1"
else
    echo "Acción no reconocida. Use 'crear' o 'eliminar'."
    log "Error: Acción no reconocida."
    exit -1
fi