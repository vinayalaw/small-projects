/*
  Filename   : BSTDriver.cc
  Author     : Vinayak Desai
  Course     : CSCI 362-01
  Assignment : BST container
  Description: Test methods of the SearchTree class
*/

/************************************************************/
// System includes

#include <cstdlib>
#include <iostream>
#include <string>
#include <iterator>
#include <sstream>
#include <cassert>

/************************************************************/
// Local includes

#include "SearchTree.hpp"

/************************************************************/
// Using declarations

using std::cin;
using std::cout;
using std::endl;
using std::ostream_iterator;
using std::string;
using std::ostringstream;

/************************************************************/
// Function prototypes/global vars/typedefs

void
printTestResult (const string& test,
		 const string& expected,
		 const ostringstream& actual);

/************************************************************/

int
main (int argc, char *argv[])
{
  ostringstream output;
  
  SearchTree<int> A;
  output << A;
  printTestResult ("no-arg ctor", "[ ]", output);
  
  output.str ("");
  A.insert(5);
  A.insert(3);
  A.insert(7);
  A.insert(1);
  A.insert(4);
  A.insert(6);
  A.insert(9);
  A.insert(2);
  A.insert(8);
  A.insert(10);
  /*A should look like:
          5
      3       7
   1    4   6   9
    2          8 10
  */

  output.str ("");
  output << A.empty ();
  printTestResult ("empty", "0", output);

  output.str ("");
  output << A.size ();
  printTestResult ("size", "10", output);

  output.str ("");
  output << *A.find (7);
  printTestResult ("find", "7", output);

  output.str ("");
  output << *A.begin ();
  printTestResult ("begin", "1", output);

  output.str ("");
  output << * (A.end ());
  printTestResult ("end", "0", output);
  
  output.str ("");
  auto iter = A.begin ();
  iter++;
  output << *iter;
  printTestResult ("iterator++", "2", output);

  output.str ("");
  iter = A.end ();
  iter--;
  output << *iter;
  printTestResult ("iterator--", "10", output);
  
  output.str ("");
  output << A;
  printTestResult ("insert", "[ 5 | 3 7 | 1 4 6 9 | - 2 - - - - 8 10 ]", output);

  output.str ("");
  SearchTree<int> B (A);
  output << B;
  printTestResult ("copy ctor", "[ 5 | 3 7 | 1 4 6 9 | - 2 - - - - 8 10 ]", output);

  output.str ("");
  output << A.erase (10);
  printTestResult ("erase leaf", "1", output);

  output.str ("");
  A.erase (1);
  output << A;
  printTestResult ("erase right child", "[ 5 | 3 7 | 2 4 6 9 | - - - - - - 8 - ]", output);

  output.str ("");
  A.erase (9);
  output << A;
  printTestResult ("erase left child", "[ 5 | 3 7 | 2 4 6 8 ]", output);

  output.str ("");
  A.erase (7);
  output << A;
  printTestResult ("erase two children", "[ 5 | 3 8 | 2 4 6 - ]", output);

  

  return EXIT_SUCCESS;
}

/************************************************************/

void
printTestResult (const string& test,
		 const string& expected,
		 const ostringstream& actual)
{
  cout << "Test: " << test << endl;
  cout << "==========================" << endl;
  cout << "Expected: " << expected << endl;
  cout << "Actual  : " << actual.str () << endl;
  cout << "==========================" << endl << endl;

  // Ensure the two results are the same
  //assert (expected == actual.str ());
}
