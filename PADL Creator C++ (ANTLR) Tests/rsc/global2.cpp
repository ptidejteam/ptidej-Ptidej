#include <stdio.h>

#include <conio.h>

#include <string.h>

// recordar declarar primero proc y funciones

void proc1();

// variables globales

float base, altura, area;

void main() {

clrscr();

//capturando datos

printf("dame base: ");scanf("%f",&base);

printf("dame altura: ");scanf("%f",&altura);

//llamando procedimiento

proc1();

}

void proc1(){

// area de operaciones

area = base * altura / 2;

// area de construccion de pagina de salida

printf(" area =%0.2f",area);

getchar();getchar();

}