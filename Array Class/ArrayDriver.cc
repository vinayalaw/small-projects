/*
  Filename   : ArrayDriver.cc
  Author     : Gary M. Zoppetti
  Modifier   :Vinayak Desai
  Course     : CSCI 362-01
  Assignment : N/A
  Description: Test methods of the Array class
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

#include "Array.hpp"

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

void
print (Array<int> A);

/************************************************************/

int
main (int argc, char* argv[])
{
  Array<int> A (3);

  // For holding the actual result
  ostringstream output;
  // Put the actual result into the output stream
  output << A;
  printTestResult ("size ctor", "[ 0 0 0 ]", output);

  A.push_back (5);
  A.push_back (10);
  A.push_back (15);
  output.str ("");
  output << A;
  printTestResult ("push_back", "[ 0 0 0 5 10 15 ]", output);

  output.str ("");
  output << A.size ();
  printTestResult ("size", "6", output);

  A.pop_back ();
  A.pop_back ();
  output.str ("");
  output << A;
  printTestResult ("pop_back", "[ 0 0 0 5 ]", output);

  for (int i = 0; i < 10; ++i)
  {
    A.insert (A.begin (), i);
  }
	output.str ("");
  output << A;
  printTestResult("Front inserts", "[ 9 8 7 6 5 4 3 2 1 0 0 0 0 5 ]", output);

  for (Array<int>::iterator i = A.begin (); i != A.end (); )
  {
    i = A.erase (i);
  }
	output.str ("");
  output << A.size ();
  printTestResult("Erase all", "0", output);

  Array<int> B (3, 9);
	output.str ("");
  output << B;
  printTestResult("size value ctor", "[ 9 9 9 ]", output);

  Array<int> C (B.begin (), B.begin () + 2);
	output.str ("");
  output << C;
  printTestResult("copy ctor", "[ 9 9 9 ]", output);

  B = A;
	output.str ("");
  output << B;
  printTestResult("operator=", "[ ]", output);

  for (int i = 0; i < 5; ++i)
	{
		B.insert (B.begin (), i);
	}
	output.str ("");
	output << B;
	printTestResult("insert to empty", "[ 4 3 2 1 0 ]", output);

	/*
  dereferencing iterator-s and const_iterator-s.
 */
  Array<int> D;
	output.str ("");
	output << D;
	printTestResult("no arg ctor", "[ ]", output);

  D.resize (10, 6);
	output.str ("");
	output << D;
	printTestResult("resize bigger", "[ 6 6 6 6 6 6 6 6 6 6 ]", output);

	D.resize (8, 0);
	output.str ("");
	output << D;
	printTestResult("resize smaller", "[ 6 6 6 6 6 6 6 6 ]", output);

	Array<int> E (B.begin () + 1, B.begin () + 3);
	output.str ("");
	output << E;
	printTestResult("range ctor from middle", "[ 3 2 ]", output);

	E.reserve (15);
	output.str ("");
	output << E.capacity ();
	printTestResult("reserve larger cap", "15", output);

	E.reserve (10);
	output.str ("");
	output << E.capacity ();
	printTestResult("reserve smaller cap", "15", output);

	E.~Array();

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
  assert (expected == actual.str ());
}

/************************************************************/

// Copy ctor will get called as A is passed by value
void
print (Array<int> A)
{
  cout << "[ ";
  // Using a for-each loop, which employs iterators
  for (const auto& e : A)
  {
    cout << e << " ";
  }
  cout << "]";
}

/************************************************************/
