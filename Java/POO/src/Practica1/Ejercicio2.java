package Practica1;

import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int edad;
        String nombre;
        System.out.print("Introduci una edad: ");
        edad = sc.nextInt();
        System.out.print("Introduci un Nombre: ");
        sc.nextLine();
        nombre = sc.nextLine();
        sc.close();
        Persona MiPersona = new Persona(nombre,edad);
        MiPersona.Presentarse();
    }
}
