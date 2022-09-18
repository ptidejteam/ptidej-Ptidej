// stdafx.h : include file for standard system include files,
// or project specific include files that are used frequently, but
// are changed infrequently

#pragma once

#define WIN32_LEAN_AND_MEAN		// Exclude rarely-used stuff from Windows headers
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
//#include <tchar.h>
#include <time.h>
#include <string>
#include <vector>
#include <math.h>

#include <map>
//#include <hash_map>

#include <sstream>

using namespace std;

#define MAX_W 5
#define MAX_ORDER 15000
#define MAX_SIZE 30000
#define MAX_LABELS 5
#define MAX_DEGREE 50
#define MAX_MULTI 5

#define MAX_PERSONS 5
#define MAX_REQUIREMENTS 30

#define CONFIG "config.txt"
#define SEPARATOR ","

//#define DEBUG_GRAPH cout << "YEP!!!!!\n";

typedef struct cell{
	int x;
	int y;
	long int delta;
	long int delta_m;
	int layer;
	//double* address;
} cellule;


typedef struct occurrence{
	int value;
	int count;
}numb;


template<typename T>
bool from_string( const std::string & Str, T & Dest )
{
    // créer un flux à partir de la chaîne donnée
    std::istringstream iss( Str );
    // tenter la conversion vers Dest
    return iss >> Dest != 0;
}

template<typename T>
std::string to_string( const T & Value )
{
    // utiliser un flux de sortie pour créer la chaîne
    std::ostringstream oss;
    // écrire la valeur dans le flux
    oss << Value;
    // renvoyer une string
    return oss.str();
}

