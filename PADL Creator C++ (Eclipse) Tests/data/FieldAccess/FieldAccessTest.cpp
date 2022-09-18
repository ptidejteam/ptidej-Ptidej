/*
 * FieldAccessTest.cpp
 *
 *  Created on: 2013-07-15
 *      Author: Yann
 */

#include <cstdio>
#include <iostream>
#include "FieldAccessTest.h"
using namespace std;

int main() {
	setvbuf(stdout, NULL, _IONBF, 0);
	setvbuf(stderr, NULL, _IONBF, 0);

	FieldAccessTest c = FieldAccessTest();
	string str = "Hello";
	c.setString(str);
	cout << c.getString() << endl;
	cout << "Hello world!" << endl;
	cerr << "Hello world!" << endl;
}
