//CSegment.cpp
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "cpoint.h"

//csegment.h
//Declaration de la classe CSegment
//Méthodes définies dans csegment.cpp

// Prévient l'exclusion multiple
#ifndef CSEGMENT_H
#define CSEGMENT_H

class CSegment {
public:
	CSegment();                                  //Constructeur par défaut.
	CSegment( CPoint p1, CPoint p2 );            //Constructeur par valeur.

	void ecrire( CPoint point1, CPoint point2 ); // écrire dans chaque points
	CPoint getp1();                              // lire le premier point
	CPoint getp2();                              // lire le deuxieme point
	void afficher();                             // afficher les coor. des deux points
	void dephasage( CPoint point_dephase );      // déplacer le segment selon un déphasage
	double calculer_longueur();                  // calcule la longueur d'un segment

private:
	CPoint m_point1;
	CPoint m_point2;         // Les 2 points du segment
};

#endif

//Constructeur par défaut
CSegment::CSegment()
{
	m_point2.ecrire( 1, 1 );
}

//Constructeur par valeur
CSegment::CSegment( CPoint p1, CPoint p2 )
{
	ecrire( p1, p2 );
}

// Lire le point 1
CPoint CSegment::getp1()
{
	return m_point1;
}

// Lire le point 2
CPoint CSegment::getp2()
{
	return m_point2;
}

// Écrire les deux points dans les membres point1 et point2
void CSegment::ecrire( CPoint point1, CPoint point2 )
{
	m_point1 = point1;
	m_point2 = point2;
}

// Affiche les coor. des deux points arrondis au centieme
void CSegment::afficher()
{
	m_point1.afficher();
	printf( "-" );
	m_point2.afficher();
}

// Calcule la longueur du segment
double CSegment::calculer_longueur()
{
	 return( sqrt( ( pow( abs( m_point1.lire_x() - m_point2.lire_x() ), 2 ) ) +
						( pow( abs( m_point1.lire_y() - m_point2.lire_y() ), 2 ) ) ) );
}

// deplace le point selon un déphasage d'un autre point
void CSegment::dephasage( CPoint point_d )
{
	m_point1.dephasage( point_d );
	m_point2.dephasage( point_d );
}
