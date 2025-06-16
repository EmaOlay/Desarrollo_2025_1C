package Practica1;


import java.util.Scanner;



public class Ejercicio1 {
    public static void main(String[] arg) {
        Scanner sc = new Scanner(System.in);
        float num;
        String color_u;
        System.out.print("Introduci un Radio: ");
        num = sc.nextFloat();
        System.out.print("Introduci un Color: ");
        sc.nextLine();
        color_u = sc.nextLine();
        sc.close();
        circulo mi_circulito = new circulo(num,color_u);
        System.out.println("Cree mi circulo con radio "+mi_circulito.radio+".");
        System.out.println("Mi circulo es de diametro "+mi_circulito.diametro+".");
        mi_circulito.ImprimoColorArea();
    }
}
