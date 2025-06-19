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

# archivo de log duro en el mismo directorio que el script
log_file="script.log"

# Funcion de logeo recibe un unico argumento que es el mensaje a loggear
function log(){
    if [ $# != 1 ]; then
        echo "Error: La función log requiere un mensaje como argumento."
        exit -1
    fi
    echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" >> "$log_file"
}


function crear_usuarios() {
    # en principio siempre empieza en 1 consultar...
    for ((i=1; i<=$2; i++)); do
        # El usuario siempre sera user+numero, para facilitar la busqueda luego en eliminar_usuario
        username="user$i"
        # Para la contrase;a genero una cadena aleatoria de 13 caracteres alfanuméricos
        # en el kernel de linux /dev/urandom es un generador de números aleatorios
        # uso tr para filtrar unicamente caracteres alfanuméricos
        password=$(tr -dc A-Za-z0-9 </dev/urandom | head -c 13; echo)
        useradd -m -p "$password" "$username"
        # Valido si user add termino exitosamente
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
function eliminar_usuario() {
    # obtengo los usuarios generados por crear_usuarios
    # busco el usuario en /etc/passwd le abro un pipe a grep para filtrar los usuarios
    # que siguen el patron userN, luego uso cut para quedarme solo con el nombre de usuario
    # aclarando el delimitador ':' 
    # y por ultimo uso head para quedarme con el primero que aparezca
    usuario=$(cat /etc/passwd | grep ^user | cut -d: -f1 | head -n 1)
    # Valido si el string de usuario tiene al menos un caracter
    if [ ${#usuario} -lt 1 ]; then
        echo "No hay usuarios para eliminar."
        log "Error: No hay usuarios para eliminar."
        exit -3
    fi
    # userdel -r elimina el usuario y su directorio home
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

#valido cantidad de argumentos menos de 1 o mas de 2 tiro error
# Consulta... loggeo el mal uso?
if [ "$#" -lt 1 ] || [ "$#" -gt 2 ]; then
    echo "Uso: $0 {crear|eliminar} [cantidad]"
    exit -1
fi
# Si los argumentos estan bien en cantidad, me guardo la accion a realizar
accion="$1"
# Pregunto si quiere crear
if [ "${accion}" == "crear" ]; then
    # Pregunto si es 0 o no es un numero
    if [ -z "$2" ] || ! [[ "$2" =~ ^[0-9]+$ ]]; then
        echo "Debe especificar una cantidad válida de usuarios a crear."
        log "Error: Cantidad de usuarios no válida."
        exit -1
    fi
    #El ${accion} devuelta es medio innecesario pero lo dejo por conveniencia
    crear_usuarios "${accion}" "$2"
elif [ "${accion}" == "eliminar" ]; then
    eliminar_usuario
else
    echo "Acción no reconocida. Use 'crear' o 'eliminar'."
    log "Error: Acción no reconocida."
    exit -1
fi