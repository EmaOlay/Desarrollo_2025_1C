import java.util.Scanner;

public class FactorialRecursivo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        do {
            if (num<0) System.out.println("Numero Incorrecto!");
            System.out.print("Introduci un numero: ");
            num = sc.nextInt();
        }while (num<0);

        sc.close();

        System.out.println("El factorial de " + num + " es: " + factorial(num));
    }

    public static int factorial(int num) {
        return (num == 0) ? 1 : num * factorial(num - 1);
    }
}