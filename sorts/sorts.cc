/*
   FileName    : sorts.cc
   Author(s)   : Vinayak Desai, Harry Hawkes
   Course      : CSCI 362-01
   Description : Practice implementation of merge-sort,
                 quick-sort, and shell-sort
   Run-Times   :
   N:       20,000,000   40,000,000   80,000,000
   =============================================
   Merge       5161.50      10183.9     20852.6
   Quick       1059.05      2149.09     4334.76
   Shell           .xx          .xx         .xx
   std         798.325      1592.12     3243.84

   Comparisons :
   N:       20,000,000   40,000,000   80,000,000
   =============================================
   Merge     506445567    1052891135  2185782271
   Quick     363636369    744545061   1546435769
   Shell           .xx          .xx          .xx

   Analysis: The program's merge sort was much slower than the
             quick and std sorts. Merge was consistently five
             times slower in all cases indicating similar
             complexities but different scalars.  Our shell sort's
             complexity is kind of high, and because of that,
             we estimated that the time it would take for it to do
             20 mil elements would be at least 3 hours. Std sort
             was faster than all of our sorts, but quicksort was
             close behind it. This means that the std sort went
             with a quicksort for the big numbers. The Comparisons
             with a shell sort were significantly higher. For
             example N = 666 quick did 7057 compares, merge did
             6967 compares, and shell did 85487 compares.
             This just shows that shell will complete, but at
             a slower rate.

*/

/************************************************************/
//System Inlcudes
#include <iostream>
#include <cstdlib>
#include <vector>
#include <random>
#include <algorithm>

/************************************************************/
//Local includes
#include "Timer.hpp"

/************************************************************/
//Using declarations
using std::cout;
using std::cin;
using std::endl;
using std::vector;
using std::sort;
using std::mt19937;
using std::random_device;

/************************************************************/
//Function ProtoTypes/typedefs/global vars
size_t
mergeSort (vector<int>& v);

size_t
mergeHelper(vector<int>& v, size_t first, size_t last);

size_t
outPlace_merge (vector<int>& v, size_t first, size_t last);

size_t
quickSort (vector<int>& v);

size_t
quickHelper (vector<int>& v, size_t left, size_t right);

size_t
insertionSort (vector<int>& v, size_t begin, size_t end);

size_t
shellSort (vector<int>& v);

/************************************************************/
int
main (int argc, char* argv[])
{
  size_t N;
  cout << "N ==> ";
  cin >> N;
  vector<int> mergeVec(N);
  random_device rd;
  mt19937 mt_gen (0);
  for (int& elem: mergeVec)
  {
    elem = mt_gen()%999;
  }

  vector<int> quickVec (mergeVec);
  vector<int> shellVec (mergeVec);
  vector<int> stdVec (mergeVec);

  Timer<> t;
  size_t mergenum = mergeSort (mergeVec);
  t.stop ();
  cout << "Merge time: " << t.getElapsedMs () << " ms" << endl;
  cout << "Merge compares: " << mergenum << endl;

  t.start ();
  size_t quicknum = quickSort (quickVec);
  t.stop ();
  cout << "Quick time: " << t.getElapsedMs () << " ms" << endl;
  cout << "Quick compares: " << quicknum << endl;

  t.start ();
  sort (stdVec.begin (), stdVec.end ());
  t.stop ();
  cout << "std   time:" << t.getElapsedMs () << " ms" << endl;

  t.start ();
  size_t shellnum = shellSort (shellVec);
  t.stop ();
  cout << "Shell time: " << t.getElapsedMs () << " ms" << endl;
  cout << "Shell compares: " << shellnum << endl;

  bool mergeOK = (stdVec == mergeVec);
  bool quickOK = (stdVec == quickVec);
  bool shellOK = (stdVec == shellVec);

  cout << "Merge ok? " << mergeOK<< endl;
  cout << "Quick ok? " << quickOK<< endl;
  cout << "Shell ok? " << shellOK<< endl;

}

/************************************************************/
//Function Implementations

// Perform a merge sort on 'v'
// Return the number of comparisons performed
size_t
mergeSort (vector<int>& v){
  return mergeHelper (v, 0, v.size ());
}

size_t
mergeHelper(vector<int>& v, size_t first, size_t last)
{
  if (last - first > 1)
  {
    size_t mid = first + (last - first) / 2;
    //recursive calls followed by out-of-place merge
    size_t mergeOne = mergeHelper (v, first, mid);
    size_t mergeTwo = mergeHelper (v, mid, last);
    size_t ooMerge = outPlace_merge (v, first, last);
    return (mergeOne + mergeTwo + ooMerge);
  }
  return 0;
}

size_t
outPlace_merge (vector<int>& v, size_t first, size_t last)
{
  vector<int> temp;
  size_t mid = first + (last - first) / 2;
  size_t i = first;
  size_t j = mid;
  size_t count = 0;
  //pushing values by increasing magnitude to temp
  while (j < last && i < mid)
  {
    if (v[i] < v[j])
    {
      temp.push_back (v[i]);
      ++i;
    }
    else
    {
      temp.push_back (v[j]);
      ++j;
    }
    ++count;
  }

  //case where j meets last
  if (j == last)
  {
    ++count;
    for (auto ii = i; ii < mid; ++ii)
    {
      ++count;
      temp.push_back (v[ii]);
    }
  }
  //case where i meets mid
  else
  {
    ++count;
    for (auto ii = j; ii < last; ++ii)
    {
      ++count;
      temp.push_back (v[ii]);
    }
  }
  //overwriting original
  for(size_t l = first; l < last; ++l)
  {
    v[l] = temp[l - first];
  }
  return count;
}

// Perform a quicksort on 'v'
// Return the number of comparisons performed
size_t
quickSort (vector<int>& v)
{
  return quickHelper (v, 0, v.size () - 1);
}

size_t
quickHelper (vector<int>& v, size_t left, size_t right)
{
  if (v.size () > 20)
  {
    size_t count = 0;
    if (left >= right)
    {
      return count;
    }
    //partition step here
    size_t mid = (left + right) / 2;
    //find median of left, mid, and right
    if (v[left] >= v[right])
    {
      auto t = v[left];
      v[left] = v[right];
      v[right] = t;
    }
    if (v[mid] <= v[left])
    {
      auto t = v[left];
      v[left] = v[mid];
      v[mid] = t;
    }
    if (v[mid] > v[right])
    {
      auto t = v[mid];
      v[mid] = v[right];
      v[right] = t;
    }
    count+=3;
    //median should be located at v[mid]
    auto pivot = v[mid];
    size_t i = left;
    size_t j = right;
    //re-order so only values < v[pivot] are on left
    //key comparisons are between i, j, and pivot
    while (i <= j)
    {
      ++count;
      while (v[i] < pivot)
      {
        ++count;
        ++i;
      }
      while (v[j] > pivot)
      {
        ++count;
        --j;
      }
      //swap where i and j stopped
      if (i <= j)
      {
        auto temp = v[i];
        v[i] = v[j];
        v[j] = temp;
        ++i;
        --j;
      }
    }
    //recursive calls to quickHelper
    count += quickHelper (v, left, j);
    count += quickHelper (v, i, right);
    return count;
  }
  //small array cutoff is size 20, use insertion sort in that case
  else
  {
    return insertionSort (v, 0 , v.size ());
  }
}

// Perform an insertion sort [begin, end)
// Return the number of comparisons performed
size_t
insertionSort (vector<int>& v, size_t begin, size_t end)
{
  size_t count = 0;
  for (size_t i = begin; i < end; ++i)
  {
    int val = v[i];
    size_t index = i;
    while (index>=1 && val < v[index - 1])
    {
      v[index] = v[index-1];
      --index;
      //key comparison is val to v[index - 1]
      ++count;
    }
    v[index] = val;
  }
  return count;
}

// Perform a Shell sort
// Return the number of comparisons performed
size_t
shellSort (vector<int>& v)
{
  vector<size_t> gaps = {1093, 12, 40, 13, 3};
  size_t count = 0;
  for(size_t h: gaps)
  {
    for (size_t i = h; i < v.size (); i = i + h)
    {
      int val = v[i];
      size_t index = i;
      while (index>=h && val < v[index - h])
      {
        v[index] = v[index-h];
        index = index-h;
        //key comparison is val to v[index - h]
        ++count;
      }
      v[index] = val;
    }
  }
  return count + insertionSort (v, 0, v.size());
}
