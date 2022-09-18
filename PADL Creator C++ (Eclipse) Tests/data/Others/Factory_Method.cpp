#include <stdexcept>
#include <iostream>
#include <memory>
 
class Pizza {
public:
    virtual int getPrice() const = 0;
};
 
class HamAndMushroomPizza : public Pizza {
public:
    virtual int getPrice() const { return 850; }
};
 
class DeluxePizza : public Pizza {
public:
    virtual int getPrice() const { return 1050; }
};
 
class HawaiianPizza : public Pizza {
public:
    virtual int getPrice() const { return 1150; }
};
 
class PizzaFactory {
public:
    enum PizzaType {
         HamMushroom,
         Deluxe,
         Hawaiian
    };
 
    static Pizza* createPizza(PizzaType pizzaType) {
        switch (pizzaType) {
            case HamMushroom:
                return new HamAndMushroomPizza();
            case Deluxe:
                return new DeluxePizza();
            case Hawaiian:
                return new HawaiianPizza();
        }
        throw "invalid pizza type.";
    }
};
 
/*
 * Create all available pizzas and print their prices
 */
void pizza_information( PizzaFactory::PizzaType pizzatype )
{
        Pizza* pizza = PizzaFactory::createPizza(pizzatype);
        std::cout << "Price of " << pizzatype << " is " << pizza->getPrice() << std::endl;
        delete pizza;
}
 
int main ()
{
        pizza_information( PizzaFactory::HamMushroom );
        pizza_information( PizzaFactory::Deluxe );
        pizza_information( PizzaFactory::Hawaiian );
}