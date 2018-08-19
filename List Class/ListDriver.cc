/*
  Filename   : ListDriver.cc
  Author     : Gary M. Zoppetti
  Modifier   : Vinayak Desai
  Course     : CSCI 362-01
  Assignment : N/A
  Description: Test methods of the List class
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

#include "List.hpp"

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
  List<int> A;

  // For holding the actual result
  ostringstream output;
  // Put the actual result into the output stream
  output << A;
  printTestResult ("no-arg ctor", "[ ]", output);

  // Must clear the output stream each time
  output.str ("");
  output << A.size ();
  printTestResult ("size", "0", output);

  List<int> B (3, 8);
  output.str ("");
  output << B;
  printTestResult ("size ctor", "[ 8 8 8 ]", output);

	List<int> C {1, 2, 3};
	output.str ("");
	output << C;
	printTestResult ("init list ctor", "[ 1 2 3 ]", output);

	List<int> D (C);
	output.str ("");
	output << D;
	printTestResult ("copy ctor", "[ 1 2 3 ]", output);

	B = C;
	output.str ("");
	output << B;
	printTestResult ("operator=", "[ 1 2 3 ]", output);

	output.str ("");
	output << std:: boolalpha << C.empty ();
	printTestResult ("empty", "false", output);

	output.str ("");
	output << C.size ();
	printTestResult ("size", "3", output);

	output.str ("");
	output << C.front ();
	printTestResult ("front", "1", output);

	output.str ("");
	output << C.back ();
	printTestResult ("back", "3", output);

	C.push_front (0);
	output.str ("");
	output << C;
	printTestResult ("push_front", "[ 0 1 2 3 ]", output);

	C.pop_front ();
	output.str ("");
	output << C;
	printTestResult ("pop_front", "[ 1 2 3 ]", output);

	C.push_back (4);
	output.str ("");
	output << C;
	printTestResult ("push_back", "[ 1 2 3 4 ]", output);

	C.pop_back ();
	output.str ("");
	output << C;
	printTestResult ("pop_back", "[ 1 2 3 ]", output);

	//check that iterator from begin leads to first node
	ListNode<int> * testBegin = C.begin ().m_nodePtr;
	output.str ("");
	output << testBegin->data;
	printTestResult ("begin", "1", output);
	delete testBegin;

	//check that itereator from end leads to dummy header
	ListNode<int> * testEnd = C.end ().m_nodePtr;
	output.str ("");
	output << testEnd->prev->data;
	printTestResult ("end", "3", output);
  delete testEnd;

  B.insert (B.end (), 4);
	output.str ("");
	output << B;
	printTestResult ("insert", "[ 1 2 3 4 ]", output);
  
  //pop back call erase
  B.pop_back ();
	output.str ("");
	output << B;
	printTestResult ("erase", "[ 1 2 3 ]", output);

  B.erase(B.begin (), B.end ());
	output.str ("");
	output << B;
	printTestResult ("range erase", "[ ]", output);
/*  
  output.str ("");
  output<< "[ ";
  for(auto i = C.begin (); i != C.end (); ++i)
  {
    ListNode<int> * currNode = i.m_nodePtr;
    output << currNode->data << " ";
    delete currNode;
  }
  output << "]";
	printTestResult ("incr iterator", "[ 1 2 3 ]", output);

	output.str ("");
  output<< "[ ";
  auto j = C.end ();
  --j;
  while (j != C.end ())
  {
    ListNode<int> * currNode = j.m_nodePtr;
    output << currNode->data << " ";
    delete currNode
    --j;
  }
  output << "]";
	printTestResult ("decr iterator", "[ 3 2 1 ]", output);
 */
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
