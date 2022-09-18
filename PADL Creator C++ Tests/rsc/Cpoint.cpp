//cpoint.cpp
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
//cpoint.h
//Declaration de la classe CPoint
//Méthodes définies dans cpoint.cpp

// Prévient l'exclusion multiple
#ifndef CPOINT_H
#define CPOINT_H

class CPoint {
public:
	CPoint();                          //Constructeur par défaut.
    CPoint( double x, double y );      //Constructeur par valeur.

	double lire_x();                   //Lis la valeur de x
	double lire_y();                   //Lis la valeur de y
	void ecrire( double x, double y ); //Écris les coordonnées
	void afficher();                   //Renvois les coor. arrondies.
	double calculer_origine();         //Calcule distance entre origine
												  //et le point
	void dephasage( CPoint point );    //Fais le dephasage.

private:
	double m_x;   //Coordonnée x
	double m_y;   //Coordonnée y
};

#endif


// Constructeur par défaut.
CPoint::CPoint()
{
	m_x = m_y = 0;
}

// Constructeur par valeur.
CPoint::CPoint( double x, double y )
{
	ecrire( x, y );
}

// Lire la coord. x
double CPoint::lire_x()
{
	return m_x;
}

// Lire la coord. y
double CPoint::lire_y()
{
	return m_y;
}

// Écrire la coord. dans les membres x y
void CPoint::ecrire( double x, double y )
{
	m_x = x;
	m_y = y;
}

// Affiche les coordonnée du point arrondies au centieme
void CPoint::afficher()
{
	printf( "(%.2f,%.2f)", m_x, m_y );
}

// Calcule la distance de l'origine au point
double CPoint::calculer_origine()
{
	 return( sqrt( ( pow( m_x, 2 ) ) + ( pow( m_y, 2 ) ) ) );
}

// deplace le point selon un déphasage d'un autre point
void CPoint::dephasage( CPoint point )
{
	m_x += point.m_x;
	m_y += point.m_y;
}