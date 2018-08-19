/*
  Filename   : cdInterest.cc
  Author     : Vinayak Desai
  Course     : CSCI362
  Assignment : cdinterest
  Description: Calculate interest earned on a certificate
               of deposit over specified period
*/

/*********************************************/
//System includes
#include <iostream>
#include <cstdlib>
#include <string>
#include <locale.h>
#include <stdio.h>

/*********************************************/
//Local includes

/*********************************************/
//using Declarations
using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::printf;
using std::setlocale;

/*********************************************/
//forward Declarations
void printIntro ();
void printTable (int numRows, double balance, double rate);
void printRow (int rowNum, double balance, double interest);
double calcInterest (double balance, double rate);

/*********************************************/
int
main (int argc, char*argv[])
{
  int years;
  double initBalance, interestRate;

  printIntro ();

  cout << "Please enter the initial balance: ";
  cin >> initBalance;
  cout << "Please enter the interest rate  : ";
  cin >> interestRate;
  cout <<"Please enter the number of years: ";
  cin >> years;
  cout << endl;
  printTable (years, initBalance, interestRate);

  return 0;
}

/*********************************************/
void
printIntro ()
{
  cout << "This program will calculate interest earned" << endl;
  cout << "  on a CD over a period of several years." << endl << endl;
}

/*********************************************/
void
printTable (int numRows,double balance, double rate)
{
  cout << "Year     Balance     Interest     New Balance" << endl;
  cout << "----     -------     --------     -----------" << endl;
  for (int i = 1; i <= numRows; ++i)
  {
    printRow (i, balance, rate);
    balance = balance + calcInterest (balance, rate);
  }
}

/*********************************************/
//
void
printRow (int rowNum, double balance, double rate)
{
  double interestAmount = calcInterest(balance, rate);
  double newBalance = balance + interestAmount;

  setlocale (LC_NUMERIC, "");
  cout << rowNum;
  if (rowNum > 9)
  {
    printf("%'14.2f",balance);
  }
  else
  {
    printf ("%'15.2f", balance);
  }
  printf ("%'13.2f", interestAmount);
  printf ("%'16.2f", newBalance);
  cout << endl;
}

/*********************************************/
//
double
calcInterest (double balance, double rate)
{
  return balance * (rate / 100);
}
