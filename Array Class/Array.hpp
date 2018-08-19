/*
  Filename   : Array.hpp
  Author     : Gary M. Zoppetti
  Modifier   :Vinayak Desai
  Course     : CSCI 362-01
  Assignment : N/A
  Description: Array class, our implementation of a list ADT
                 with random access and dynamic resizing.

		 With templated class we often include the
		 implementation of the class in the header
		 file. In these cases we use the extension
		 ".hpp".
*/

/************************************************************/
// Macro guard to prevent multiple inclusions

#ifndef ARRAY_H
#define ARRAY_H

/************************************************************/
// System includes

#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <iterator>

/************************************************************/
// Local includes

/************************************************************/
// Using declarations

using std::fill;
using std::copy;
using std::copy_backward;
using std::ostream;
using std::ptrdiff_t;
using std::distance;
using std::cout;
using std::endl;

/************************************************************/

template <typename T>
class Array
{
public:
  //*****************************************************
  // DO NOT MODIFY THIS SECTION OF typedef's
  // Some standard typedef's
  typedef T          value_type;
  // Iterators are just pointers to objects of type T
  typedef T*         iterator;
  typedef const T*   const_iterator;

  typedef T&         reference;
  typedef const T&   const_reference;
  typedef size_t     size_type;
  typedef ptrdiff_t  difference_type;
  //*****************************************************

  // Initialize an empty Array
  Array ()
    : m_size (0), m_capacity (0), m_array (new T [m_capacity])
  {

  }

  // Initialize an Array of size "pSize", with each element
  //   set to "value"
  explicit Array (size_t pSize, const T& value = T ())
    : m_size (pSize), m_capacity (m_size), m_array (new T [m_capacity])
  {
    fill (m_array, m_array + m_size, value);
  }

  // Initialize an Array from the range [first, last)
  Array (const_iterator first, const_iterator last)
    : m_size (distance (first, last)), m_capacity (distance (first, last)), m_array (new T [m_capacity])
  {
    copy (first, last, m_array);
  }

  // Initialize this object from "a"
  Array (const Array& a)
    : m_size (a.size ()), m_capacity (a.capacity ()), m_array (new T [m_capacity])
  {
    copy (a.begin (), a.end (), m_array);
  }

  // Release allocated memory
  ~Array ()
  {
    delete [] m_array;
    cout << "Array has been deleted" << endl;
  }

  // Assign "a" to this object
  //   Watch for self-assignment
  Array&
  operator= (const Array& a)
  {
    resize (a.capacity (), 0);
    /*if (m_capacity < a.capacity ())
    {
      reserve (a.capacity ());
    }
    else
    {
      delete [] m_array;
      m_capacity = a.capacity ();
      m_array= new T[a.capacity ()];
    }*/
    copy (a.begin (), a.end (), m_array);
    return *this;
  }

  // Return the size
  size_t
  size () const
  {
    return m_size;
  }

  // Return the capacity
  size_t
  capacity () const
  {
    return m_capacity;
  }

  // Return the element at position "index"
  T&
  operator[] (size_t index)
  {
    return * (m_array + index);
  }

  const T&
  operator[] (size_t index) const
  {
    return * (m_array + index);
  }

  // Insert an element at the back
  void
  push_back (const T& item)
  {
    insert (end (), item);
  }

  // Erase the element at the back
  void
  pop_back ()
  {
    erase (end () - 1);
  }

  // Reserve capacity for "space" elements
  // "space" must be  > capacity
  //   if not, leave the capacity unchanged
  // "size" is unchanged
  void
  reserve (size_t space)
  {
    if (space > m_capacity)
    {
      T * data= new T [space];
      copy (m_array, end (), data);
      delete [] m_array;
      m_array = data;
      m_capacity = space;
    }
  }

  // Change the size to be "newSize"
  // If "newSize" is less than "size",
  //   erase the last elements
  // If "newSize" is more than "size",
  //   insert "value"-s at the end
  void
  resize (size_t newSize, const T& value = T ())
  {
    if (m_size == newSize)
    {
      return;
    }
    //reduction case
    else if (newSize < m_size)
    {
      for (auto i = m_array + newSize; i != end (); )
      {
        erase (i);
      }
    }
    //expansion case
    else
    {
      reserve (newSize);
      fill (m_array + m_size, m_array + newSize, value);
    }
    m_size = newSize;
  }

  // Insert "item" before "pos", and return iterator pointing to "item"
  // If the capacity is insufficient, double it
  //   If the capacity is 0, increase it to 1
  // NOTE: If a reallocation occurs, "pos" will be invalidated!
  iterator
  insert (iterator pos, const T& item)
  {
    //store distance in case reserve is used
    auto arrPos = distance (m_array, pos);
    if (m_capacity == 0)
    {
      reserve (1);
    }
    else if (m_size == m_capacity)
    {
      reserve (m_capacity * 2);
    }
    if(pos != end())
    {
      //shift values right;
      for (auto i = end (); i > pos; --i)
      {
        *i = * (i - 1);
      }
    }
    * (m_array + arrPos) = item;
    ++m_size;
    return pos;
  }

  // Remove element at "pos", and return iterator
  //   referencing next element
  iterator
  erase  (iterator pos)
  {
    if (pos != end() - 1)
    {
      //shift values left
      for (auto i = pos; i < end () - 1; ++i)
      {
        *i = * (i + 1);
      }
    }
    --m_size;
    return pos;
  }

  // Return iterator pointing to first element
  iterator
  begin ()
  {
    return m_array;
  }

  const_iterator
  begin () const
  {
    return m_array;
  }

  // Return iterator pointing one beyond the last element
  iterator
  end ()
  {
    return (m_array + m_size);
  }

  const_iterator
  end () const
  {
    return (m_array + m_size);
  }

private:
  size_t m_size;
  size_t m_capacity;
  T*     m_array;
};

/************************************************************************/
// Free functions associated with the class

// Output operator
// Allows us to do "cout << a;", where "a" is an Array
template <typename T>
ostream&
operator<< (ostream& output, const Array<T>& a)
{
  output << "[ ";
  for (const auto& elem : a)
  {
    output << elem << " ";
  }
  output << "]";

  return output;
}

#endif

/************************************************************************/
