  /**
   * Implementation of Prototype Method 
   **/
  #include <iostream>
  #include <map.h>
  #include <string>
 
  using namespace std;
 
  enum RECORD_TYPE_en
  {
    CAR,
    BIKE,
    PERSON
  };
 
  /**
   * Record is the Prototype
   */
 
  class Record
  {
    public :
 
      Record() {}
 
      virtual ~Record() {}
 
      virtual Record* clone()=0;
 
      virtual void print()=0;
  };
 
  /**
   * CarRecord is a Concrete Prototype
   */
 
  class CarRecord : public Record
  {
    private:
      string m_carName;
      int m_ID;
 
    public:
      CarRecord(string carName, int ID)
        : Record(), m_carName(carName),
          m_ID(ID)
      {
      }
 
      CarRecord(CarRecord& carRecord)
        : Record()
      {
        m_carName = carRecord.m_carName;
        m_ID = carRecord.m_ID;
      }
 
      ~CarRecord() {}
 
      Record* clone()
      {
        return new CarRecord(*this);
      }
 
      void print()
      {
        cout << "Car Record" << endl
          << "Name  : " << m_carName << endl
          << "Number: " << m_ID << endl << endl;
      }
  };
 
 
  /**
   * BikeRecord is the Concrete Prototype
   */
 
  class BikeRecord : public Record
  {
    private :
      string m_bikeName;
 
      int m_ID;
 
    public :
      BikeRecord(string bikeName, int ID)
        : Record(), m_bikeName(bikeName),
          m_ID(ID)
      {
      }
 
      BikeRecord(BikeRecord& bikeRecord)
        : Record()
      {
        m_bikeName = bikeRecord.m_bikeName;
        m_ID = bikeRecord.m_ID;
      }
 
      ~BikeRecord() {}
 
      Record* clone()
      {
        return new BikeRecord(*this);
      }
 
      void print()
      {
        cout << "Bike Record" << endl
          << "Name  : " << m_bikeName << endl
          << "Number: " << m_ID << endl << endl;
      }
  };
 
 
  /**
   * PersonRecord is the Concrete Prototype
   */
 
  class PersonRecord : public Record
  {
    private :
      string m_personName;
 
      int m_age;
 
    public :
      PersonRecord(string personName, int age)
        : Record(), m_personName(personName),
          m_age(age)
      {
      }
 
      PersonRecord(PersonRecord& personRecord)
        : Record()
      {
        m_personName = personRecord.m_personName;
        m_age = personRecord.m_age;
      }
 
      ~PersonRecord() {}
 
      Record* clone()
      {
        return new PersonRecord(*this);
      }
 
    void print()
    {
      cout << "Person Record" << endl
        << "Name : " << m_personName << endl
        << "Age  : " << m_age << endl << endl ;
    }
  };
 
 
  /**
   * RecordFactory is the client
   */
 
  class RecordFactory
  {
    private :
      map<RECORD_TYPE_en, Record* > m_recordReference;
 
    public :
      RecordFactory()
      {
        m_recordReference[CAR]  = new CarRecord("Ferrari", 5050);
        m_recordReference[BIKE] = new BikeRecord("Yamaha", 2525);
        m_recordReference[PERSON] = new PersonRecord("Tom", 25);
      }
 
      ~RecordFactory()
      {
        delete m_recordReference[CAR];
        delete m_recordReference[BIKE];
        delete m_recordReference[PERSON];
      }
 
      Record* createRecord(RECORD_TYPE_en enType)
      {
        return m_recordReference[enType]->clone();
      }
  };
 
  int main()
  {
    RecordFactory* poRecordFactory = new RecordFactory();
 
    Record* poRecord;
    poRecord = poRecordFactory->createRecord(CAR);
    poRecord->print();
    delete poRecord;
 
    poRecord = poRecordFactory->createRecord(BIKE);
    poRecord->print();
    delete poRecord;
 
    poRecord = poRecordFactory->createRecord(PERSON);
    poRecord->print();
    delete poRecord;
 
    delete poRecordFactory;
    return 0;
  }