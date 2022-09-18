#include <iostream>
using namespace std;
 
/* Place holder for thread synchronization mutex */
class Mutex
{   /* placeholder for code to create, use, and free a mutex */
};
 
/* Place holder for thread synchronization lock */
class Lock
{   public:
        Lock(Mutex& m) : mutex(m) { /* placeholder code to acquire the mutex */ }
        ~Lock() { /* placeholder code to release the mutex */ }
    private:
        Mutex & mutex;
};
 
class Singleton
{   public:
        static Singleton* GetInstance();
        int a;
        ~Singleton() { cout << "In Dtor" << endl; }
 
    private:
        Singleton(int _a) : a(_a) { cout << "In Ctor" << endl; }
 
 
        static Mutex mutex;
 
        // Not defined, to prevent copying
        Singleton(const Singleton& );
        Singleton& operator =(const Singleton& other);
};
 
Mutex Singleton::mutex;
 
Singleton* Singleton::GetInstance()
{
    Lock lock(mutex);
 
    cout << "Get Inst" << endl;
 
    // Initialized during first access
    static Singleton inst(1);
 
    return &inst;
}
 
int main()
{
    Singleton* singleton = Singleton::GetInstance();
    cout << "The value of the singleton: " << singleton->a << endl;
    return 0;
}