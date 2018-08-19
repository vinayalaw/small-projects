/*
   Filename   : JosephusList.cpp
   Author     : Vinayak Desai
   Class      : CS362
   Assignment : Josephus List
   Description: With sample size of N objects and executing
                every k'th person in the list, find survivor
                and understand algorithm's complexity
*/

/************************************************************/
//System includes
#include <cstdlib>
#include <iostream>

/************************************************************/
//local includes
#include "List.hpp"

/************************************************************/
//Using declarations
using std::cin;
using std::cout;
using std::endl;
using std::string;

/************************************************************/
//Function prototypes/Global vars/Typedefs
int traversalCount = 0;

/************************************************************/
//Function implementations

/*
   Analysis: The action counted is total link traversals
             within an N sized list and k counts per
             execution
             
             T(N,k)= ((k/N)-1) + (k*(N-1)) + (N-1)
             
             1st term is times header is reached inside FOR loop
             where if it is some small decimal number, it is 
             negligible
             
             2nd term is total normal traversals in FOR loop 
             which means k*(number of executions)
             
             3rd term is number of decrements outside FOR loop
             
             O(N)= N * k = N
             
*/
template <typename T>
List<T>
execute (List<T> & circle, size_t k)
{
  List<T> executed;
  auto it = circle.end ();
  while (circle.size () > 1)
  {
    for (size_t i = 0; i < k; ++i)
    {
      ++it;
      ++traversalCount;
      if (it == circle.end ())
      {
        ++it;
        ++traversalCount;
      }
    }
    ListNode<T> * victim = it.m_nodePtr;
    executed.push_back (victim->data);
    delete victim;
    --it;
    ++traversalCount;
    //holder data was 0 and it was crash
    ListIterator<T> holder(it.m_nodePtr->next);
    circle.erase (holder);
  }
  return executed;
}

/************************************************************/

int
main (int argc, char *argv[])
{
  size_t N, k;
  cout << "N ==> ";
  cin >> N;
  cout << "k ==> ";
  cin >> k;
  cout << endl;

  List<unsigned> A;
  for (unsigned i = 1; i <= N; ++i)
  {
    A.push_back (i);
  }

  List<unsigned> order = execute (A, k);
  cout << "Execution order: ";
  for(auto i = order.begin (); i != order.end (); ++i)
  {
    cout << *i;
    if (i != --order.end ())
    {
      cout << ", ";
    }
  }
  cout << "survivor: " << *A.begin();
  
  return EXIT_SUCCESS;
}



/************************************************************/