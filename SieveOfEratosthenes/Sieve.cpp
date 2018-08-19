/*
  FileName   : Sieve.cpp
  Author     : Vinayak Desai
  Course     : CSCI 362
  Assignment : Sieve of Eratosthenes
  Description: finding prime numbers from 2 to N
               using vector and set implementation

  RUNTIMES (ms):
  N:       1,000,000   2,000,000   4,000,000    
  ==========================================
  Set        2272.04     4840.99    10276.00
  Vector      208.31      354.32      618.22
  
  times collected from one run each so accuracy may vary
*/

/************************************************************/
// System includes

#include <iostream>
#include <string>
#include <cstdlib>
#include <set>
#include <vector>

/************************************************************/
// Local includes
#include "Timer.hpp"

/************************************************************/
// Using declarations

using std::cout;
using std::cerr;
using std::endl;
using std::string;
using std::stoul;
using std::set;
using std::vector;

/************************************************************/
// Function prototypes/global vars/typedefs
set<unsigned>
sieveSet (unsigned n);

set<unsigned>
sieveVector (unsigned n);

/************************************************************/

int
main (int argc, char* argv[])
{
  string conType (argv[1]);
  string arg2 (argv[2]);
  unsigned N = stoul(arg2);
  set<unsigned> primes;
  Timer<> t;
  if (conType == "set")
  {
    primes = sieveSet (N);
  }
  else
  {
    primes = sieveVector (N);
  }
  t.stop ();

  //outputs here
  cout << "Pi[" << N << "] = " << primes.size () << " (using a " 
   << conType << ")" << endl;
  cout << "Time: " << t.getElapsedMs () << " ms"<<endl;
}
/************************************************************/
//Function Implementations

set<unsigned>
sieveSet (unsigned n)
{
  set<unsigned> pi;
  for(unsigned i=2; i <= n; ++i)
  {
    pi.insert(i);
  }
  for(auto elem: pi)
  {
    unsigned p = elem;
    for (unsigned k = p; p*k <= n; ++k)
    {
      pi.erase(p*k);
    }
  }
  return pi;
}

set<unsigned>
sieveVector (unsigned n)
{
  set<unsigned> pi;
  vector<bool> v (n-1, true);
  for (unsigned i = 0; i < n; ++i)
  {
    if(v[i])
    {
      auto p = i + 2;
      for (auto k = p; p*k <= n; ++k)
      {
        v[ (p*k) - 2] = false;
      }
    }
  }
  //inserts to return set
  for (unsigned i = 2; i <= n; ++i)
  {
    if (v[i-2])
    {
      pi.insert (i);
    }
  }
  return pi;
}

