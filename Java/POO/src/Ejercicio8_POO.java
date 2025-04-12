import java.util.Scanner;

public class Ejercicio8_POO {
    /*
     * 8) Estás desarrollando una herramienta para
     * análisis numérico donde necesitas obtener una propiedad de un número
     * al sumar todos sus dígitos.
     * Este tipo de tarea es útil en ciertas aplicaciones matemáticas
     * como la verificación de la validez de un número.
     * Crea una función que reciba un número entero positivo y
     * calcule la suma de sus dígitos.
     */
    public static void main(String[] arg) {
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.print("Introduci un numero: ");
        num = sc.nextInt();
        sc.close();
        SumoDigitos(num);
    }

    public static int SumoDigitos(int num) {
        int aux = 1;
        int res = 0;
//        int calculito_aux;
        do {
//            calculito_aux =
            res += (int) (num % Math.pow(10, aux))/Math.pow(10,aux-1);
//            System.out.println("Aux = " + aux + " res = " + res);
//            System.out.println("calculito = " + (int) (num % Math.pow(10, aux))/Math.pow(10,aux-1));
            aux++;
        } while ((num % Math.pow(10, aux))/Math.pow(10,aux-1)>1);
        return res;
    }
}