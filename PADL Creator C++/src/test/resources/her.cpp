class Forme
{
  public :
    Forme() {
      a = 1;
    } 
    int a;
    void presentation() const {cout<<"je suis une forme";}
};

class Forme1 {
public:
  Forme1(int i){
    z = i;
  }
  int z = 0;
};

class Rectangle : public Forme, public Forme2
{
  public :
    int r = 0;
    void presentation() const
    { Forme::presentation(); cout<<" et un rectangle";}
};