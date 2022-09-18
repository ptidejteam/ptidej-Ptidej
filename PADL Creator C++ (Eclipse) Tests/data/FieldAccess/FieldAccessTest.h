/*
 * FieldAccessTest.h
 *
 *  Created on: 2013-07-15
 *      Author: Yann
 */

#ifndef FIELDACCESSTEST_H_
#define FIELDACCESSTEST_H_

#include <iostream>
#include <string>
using namespace std;

class FieldAccessTest {
public:
	void setString(const string& aString) {
		cout << aString;
		cout << aString.size();
		cout << strlen(aString.c_str());
		some_string = aString;
	}
	string getString() {
		return some_string;
	}
private:
	std::string some_string;
	std::string* some_string2;
	std::string[] some_string3;
};

#endif /* FIELDACCESSTEST_H_ */
