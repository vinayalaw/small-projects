#ifndef _LBVECTOR_H
#define _LBVECTOR_H

#include <cstdlib>
#include <cassert>
#include <iostream>

// templated vector class, partially based on Budd,
//                         Classic Data Structures in C++
// written 11/5/93, modified 3/23/94
// changed on 3/9/95 to make all methods inline (defined in class decl)
// changed on 10/14/99 to clean up for vector lab in CS 330
//////////////////////////////////////////////////////////////////////////
// updated 3/2017 by Vinayak Desai to allow lower and upper bounds
//////////////////////////////////////////////////////////////////////////
//
// for a vector of Items use lbvector<Item>,
//                   e.g., lbvector <int> intvector;
//                   note: Item must have a default constructor
//
// constructors:
//   lbvector( )             -- default, vector of size 0 (no entries)
//   lbvector(int size)      -- empty vector made to size
//   lbvector(int lower, int upper)  -- vector made with myZero from lower to upper
//   lbvector(int lower, int upper, Item fillValue) -- same as above but with fillvalue
//   lbvector(const lbvector & vec) -- copy constructor
//
// others:
//   int capacity( )         -- returns capacity of vector
//   void resize(int newSize)-- resizes the vector to newSize elements
//                              (destructive if size reduced)
//   void fill(Item fillValue)  -- vector made to contain only fillValue
//   operator =              -- assignment operator works properly
//   operator []             -- subscripts both const and non-const vectors
//
//
//  examples of use:
//     lbvector<String> slist(11);  // an empty list to hold 11 strings
//     lbvector<String> svlist(10,20, "very");// made with 11 "very"s from index 10 to 20
//     lbvector<int> dlist(-3,3);  // empty list with indeces -3 to 3
//     lbvector<int> ilist2(ilist1);  // ilist2 made to look like ilist1
//
//
//  class invariant:
//  - variable myList points to first element's address
//  - myUpper and myLower are highest and Lowest legal indeces
//  - myZero = myList-myLower where myLower is type int
//  - myZero + myUpper points to end of allocated myList
using namespace std;

template <class Item> class lbvector
{
public:
   // default constructor 0 elements
   // postcondition: vector of zero items constructed
   lbvector( )
   {
      myCapacity = 0;
      myLower = 0;
      myUpper = -1;
      myList = 0;
      myZero = myList;
   }

   // specify size of vector
   // postcondition: vector of size items constructed
   lbvector(int size)
   {
      assert(size >= 0);
      myCapacity = size;
      myList = new Item [size];
      assert(myList != 0);
      myZero = myList;
   }

   // specify my upper and my lower
   // postcondition: constructed vector of  upper-lower+1 items,
   lbvector(int lower, int upper)
   {
      myCapacity = (upper - lower) + 1;
      myLower = lower;
      myUpper = upper;
      myList = new Item [myCapacity];
      myZero = myList - lower;
   }

   //specifies upper and lower bound with fill value
   //postcondition: constructed vector size of upper-lower+1
   //               each item initialized to fillValue
   lbvector(int lower, int upper, Item fillValue)
   {
      myCapacity = (upper - lower) + 1;
      myLower = lower;
      myUpper = upper;
      myList = new Item [myCapacity];
      fill(fillValue);
      myZero = myList - lower;
   }

   // copy constructor
   // used to create a new and complete copy of the object (vec)
   //    as when passing the object by value or returning object
   //    as a result - need a deep copy when using dynamic memory
   // precondition: Item supports assignment
   // postcondition: constructed vector is an exact duplicate of vec
   lbvector(const lbvector<Item> & vec)
   {
      // allocate storage
      myCapacity = vec.myCapacity;
      myList = new Item [myCapacity];
      assert(myList != 0);
      myLower = vec.lower();
      myUpper = vec.upper();
      myZero = myList - myLower;

      // copy elements from vec
      for (int k = 0; k < vec.myCapacity; k++) {
         myList[k] = vec.myList[k];
      }
   }

   // free new'd storage
   // postcondition: dynamically allocated storage freed
   ~lbvector ( )
   {
      delete [] myList;
   }

   // assignment
   //    need a deep copy when using dynamic memory
   // precondition: Item supports assignment
   // postcondition: self is assigned vec
   lbvector & operator = (const lbvector<Item> & vec)
   {
      // don't assign to self!
      if (this != &vec) {
         // out with old list, in with new
         delete [] myList;
         myCapacity = vec.myCapacity;
         myList = new Item [myCapacity];
         assert(myList != 0);
         myLower = vec.lower();
         myUpper = vec.upper();
         myZero = myList - myLower;

         // copy elements from vec
         for (int k=0; k < vec.myCapacity; k++) {
            myList[k] = vec.myList[k];
         }
      }
      return *this;
   }

   // change size dynamically
   // precondition: vector has room for myCapacity entries
   // postcondition: vector has room for newSize entries
   //          the first myCapacity of which are copies of original
   //          unless newSize < myCapacity, then truncated copy occurs
   void resize(int low, int up)
   {
      int newCap = (up - low) + 1;
      Item * newList = new Item[newCap];
      assert(newList != 0);
      Item * newZero = newList - low;
      
      //actual changes are made here
      int highestlow= max(low, myLower);
      int lowestmax= min(up, myUpper);
      for(int i= highestlow; i<=lowestmax; ++i){
        newZero[i]= myZero[i];
      }
      // update instance variables to reflect changes
      delete [] myList;
      myCapacity = newCap;
      myList = newList;
      myZero = newZero;
      myUpper = up;
      myLower = low;
      
   }

   // capacity of vector
   int capacity( ) const
   {
      return myCapacity;
   }

   // upper bound
   int upper( ) const
   {
      return myUpper;
   }

   // lower bound
   int lower( ) const
   {
      return myLower;
   }

   // postcondition: all entries are equal to fillValue
   void fill(Item fillValue)
   {
      for (int k=0; k < myCapacity; k++) {
         myList[k] = fillValue;
      }
   }

   // safe indexing, returning reference
   // precondition: myLower <= index < myUpper
   // postcondition: return index-th item
   // exception: aborts if index is out-of-bounds
   Item & operator [] (int index)
   {
      checkIndex(index);
      return myZero[index];
   }

   // const index
   // safe indexing, returning const reference to avoid modification
   // precondition: myLower <= index < myUpper
   // postcondition: return index-th item
   // exception: aborts if index is out-of-bounds
   const Item & operator [] (int index) const
   {
      checkIndex(index);
      return myZero[index];
   }

private:
   Item * myList; // the array of items
   int myCapacity; // # things in vector (array), 0,1,...,(myCapacity-1)
   Item * myZero; // imaginary starting place of array
   int myLower;
   int myUpper;

   // aborts with appropriate message if index is not in appropriate range
   // use std:: to access names in std namespace
   void checkIndex (int index) const
   {
      if (index < myLower || myUpper < index) {
         std::cerr << "Illegal vector index: " << index
                   << " (max = " << myUpper << "; min = " << myLower << ")"
                   << std::endl;
         assert(index >= myLower);
         assert(index < myUpper);
      }
   }
};

#endif                // _LBVECTOR_H not defined
