// interface defination for module mytest, Fri Dec 20 17:46:51 2002
#ifndef CLAIREH_mytest
#define CLAIREH_mytest


extern char * mytest_ptidejVersion_void();
extern char * mytest_ptidejReleaseDate_void();
extern char * mytest_ptidejInfo_void();
extern void  mytest_showPtidejLicense_void();

// namespace class for mytest 
class mytestClass: public NameSpace {
public:

property * ptidejVersion;// mytest/"ptidejVersion"
property * ptidejReleaseDate;// mytest/"ptidejReleaseDate"
property * ptidejInfo;// mytest/"ptidejInfo"
property * showPtidejLicense;// mytest/"showPtidejLicense"

// module definition 
 void metaLoad();};

extern mytestClass mytest;

#endif
