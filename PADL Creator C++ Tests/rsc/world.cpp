// File : world.cpp
//  By: Neil West
//    
//
//This source file contains the following classes:
//i.  Team  (declaration and implementation)
//ii. Game  (declaration only)

#include<iostream.h>
#include<string.h>
#define TEST 20
enum continent {Africa, Asia, Australia, Europe, Americas};
int global;
void hallo(int allo) {};
struct teamtype
{
	char country[20];
	int	 continent;
	int  bidon[2][3];
};






//***********************************************************************


class Team {

public:
  Team(){};
  Team(char * , continent );
  Team(char c){};
  ~Team(){};
  ~Team(int c){};

  void hello() {}//cout <<"hello"<<endl;};
  //Accessors
  char* getcountry()
    {return country;}
  
  continent getcontinent()
    {return from;}

  int getwins()
    {return wins;}

  int getlosses()
    {return losses;}

  int getdraws()
    {return draws;}

  //Mutators
  void updateResults(int WLD);     //WLD is either -1(loss), 0(draw), 1(win)
 
protected:
  char country[TEST*4+1];          //e.g. "Egypt", "Brazil", "USA",etc..
  continent from;            //the continent the team represents
  int wins;                  //The number of games won so far
  int losses;                //The number of games lost so far
  int draws ;                //The number of games the team scored a draw so far
  int **test;
  teamtype orange;
};


Team::Team(char* c, continent f){


  strcpy(country, c);
  from    = f;
  wins    = 0;
  losses  = 0;
  draws   = 0;

}

void Team::updateResults(int WLD){
  //WLD would be -1 (the team lost), 1 (the team won), or 0(its a draw)

      if(WLD)
         wins++;
      else
        {
         losses += -WLD;
         draws  += (1+WLD);
        }       

}



//********************************************************************


class Game {

public:
  Game(){};
  Game(Team * FT, Team * ST, int FTGC, int STGC);
  
  Team * getFirstTeam()
    {return FirstTeam;}

  Team * getSecondTeam()
    {return SecondTeam;}

  Team * WhoWon();     //returns pointer to the winning team, and a null pointer
                       //if the game is a draw 
  void print();        //prints game data; First and second team names, and 
                       //number of goals scored by each
  void printFCont();
  void printSCont();

protected:
  Team * FirstTeam;
  Team * SecondTeam;
  int    FirstTeamGoalCount ;
  int    SecondTeamGoalCount;
  teamtype banane;

};


Game::Game(Team * FT, Team * ST, int FTGC, int STGC){
 FirstTeam = FT;
SecondTeam = ST;
FirstTeamGoalCount = FTGC;
SecondTeamGoalCount = STGC;

}

Team *Game::WhoWon()
{
if (FirstTeamGoalCount > SecondTeamGoalCount) return FirstTeam;
if (FirstTeamGoalCount < SecondTeamGoalCount) return SecondTeam;

if (FirstTeamGoalCount == SecondTeamGoalCount) return NULL;
}

void Game::print()
{

cout <<"Team 1: " <<FirstTeam->getcountry() << " From: ";
	printFCont();
cout << " score: " << FirstTeamGoalCount << endl;
cout <<"Team 2: "<<SecondTeam->getcountry()<<" From: ";
	printSCont();
cout << " score: " << SecondTeamGoalCount << endl;



}

void Game::printFCont()
{
continent y;
y = FirstTeam->getcontinent();
switch (y)
	{
	case 0: cout <<"Africa"<<endl;
			break;
	case 1: cout <<"Asia"<<endl;
			break;
	case 2: cout <<"Australia"<<endl;
			break;
	case 3: cout <<"Europe"<<endl;
			break;
	case 4: cout <<"Americas"<<endl;
			break;
	default : cout <<"other"<<endl;
			break;
	}


}

void Game::printSCont()
{
continent y;
y = SecondTeam->getcontinent();
switch (y)
	{
	case 0: cout <<"Africa"<<endl;
			break;
	case 1: cout <<"Asia"<<endl;
			break;
	case 2: cout <<"Australia"<<endl;
			break;
	case 3: cout <<"Europe"<<endl;
			break;
	case 4: cout <<"Americas"<<endl;
			break;
	default : cout <<"other"<<endl;
			break;
	}


}


/**************************************************************

The statements to create the object,
char x[20]; //the country
continent y; //
loop start
//Ask user for input

Team * T = new Team(x, y);
TheTeams[i] = *T;
loop end;


****************************************************************/


void main()
{
//teamtype teamx[4];
char name[20];
Team Teamc[4];  // instance of team class declared
Game Gamec[6];	// instance of Game class declared
int fgoal , sgoal;
continent con;
int conInt;
char cntry[20];    //country
char fteam[20] , steam[20];
char match1[20] , match2[20];
int choice;
Team * hiti2;
Team * hiti1;
char teem[20];
Team * inst;
char * p1;
char * p2;
Team * chck1;
Team * chck2;

char comp1  , comp2;


cout <<"List the teams that are playing"<<endl<<endl;

for(int x=0;x<4;x++)
{

	cout <<"What is the team name?"<<endl;
	cin >>name;
   

	cout <<"What continent?"<<endl<<"1.) Africa \t 2.)Asia \t 3.) Australia \t 4.) Europe \t 5.) Americas" <<endl;

	cin>> conInt;

switch (conInt)
	{
	case 1: con=Africa;
		break;
	case 2: con = Asia;
		break;
	case 3: con = Australia;		
		break;
	case 4: con = Europe;
		break;
	case 5: con = Americas;
		break;
	default: break;
	}




 
   Team * T = new Team(name, con);  // creates new data member of type team
   Teamc[x] = *T;   

}

/****************************************************************
Prompts the user for which teams are participating in which game


*****************************************************************/

for (int y=0; y<6; y++)
{
cout <<"Who is the first team of game "<< y+1<<endl;
cin >> fteam;

	
cout <<"Who is the second team of the game"<<endl;
cin >> steam; 


cout << "What was the goal count for the first team?"<<endl;
cin >>fgoal;  // number of goals for first team

cout << "What was the goal count for the second team?"<<endl;
cin >> sgoal; // number of goals for the second team

/**********************************************************
the first and second team are compared with the different
classes to find a match.  Once a match is found, a pointer
hiti will be set to equal it.
**********************************************************/

for (int u=0; u<4; u++)
	{

	strcpy(match1,Teamc[u].getcountry());
	strcpy(match2,fteam);

		if (*match1 ==*match2 ) 
			{
			
			 hiti1 = new Team();
   			 *hiti1 = Teamc[u];

			}
		
	}

for (int u=0;u<4;u++)
	{
	strcpy(match1,Teamc[u].getcountry());
	strcpy(match2,steam);

			
		if (*match1 ==*match2 ) 
			{
			
			 hiti2 = new Team();
   	                 *hiti2 = Teamc[u];
			}
		
	}
/***************************************************
instance of game created with the first and second team as well
as their respective goals.
***************************************************/
 	Game * G = new Game(hiti1, hiti2 , fgoal , sgoal);   
	Gamec[y] = *G;

	Team * inst;
	inst = Gamec[y].getFirstTeam();
	
}
/***************************************************

A menu is displayed and the user has three choices
1.) Display data of all games, where the game involved a specific team as a participant.
  	The user is prompted to enter the desired team's country before the query is 
	performed.
2.) To display data of all games where one or the two participating teams were from a
	specific continent.  The user is prompted to enter the desired continent before
	the query is performed.

****************************************************/
choice = 4;

while (choice !=3)
{
	cout << "Pick a choice from the menu"<<endl;
	cout << "1.) Display all of one team's games"<<endl;
	cout << "2.) Display all of one continent's games"<<endl;
	cout << "3.) Exit"<<endl;
	cin >> choice;
/****************************************************************

This prints out the schedule for a particular team



****************************************************************/

if (choice ==1)
	{
cout <<"What team schedule would you like to see" << endl;
cin >> teem;

int tt;

for (int t=0; t<6; t++)
	{
	tt = t+1;
	chck1 = Gamec[t].getFirstTeam();
	chck2 = Gamec[t].getSecondTeam();
	

p1=chck1->getcountry();
p2=chck2->getcountry();

if ((strcmp(teem , p1)==0)||(strcmp(teem , p2) ==0))  
				//((*teem == *p1)||(*teem == *p2))
	{	
	cout <<"Game "<<tt<<endl;
	Gamec[t].print();
	cout <<endl;
	}

	}



}

/************************************************************************

This prints out games of teams from a particular continent


***********************************************************************/

	if (choice == 2)
	{
	int in , t2;
	
	cout << "What continent would you like to see?"<<endl;
	cout <<"What continent?"<<endl<<"1.) Africa \t 2.)Asia \t 3.) Australia \t 4.) Europe \t 5.) Americas" <<endl;
	cin >> in;
	for (int t =0;t<6;t++)
	{
	t2=t+1;
	chck1 = Gamec[t].getFirstTeam();
	chck2 = Gamec[t].getSecondTeam();
	
	if (chck1->getcontinent() == (in-1))  {
		cout <<"Game "<<t2<<endl;
		Gamec[t].print();
		cout <<endl;
			}
	}
	}


}
}