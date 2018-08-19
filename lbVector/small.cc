// program doesn't resize but dies if student isn't using element
// with index zero calculation correctly.
#include <iostream>
#include "lbvector.h"

using std::cin;
using std::cout;
using std::string;
using std::endl;

int main ()
{
   cout << "This version doesn't resize; if your program ends with" << endl;
   cout << "a segmentation fault, it probably means you haven't" << endl;
   cout << "used the pointer to the element with index zero correctly" << endl;
   cout << "-------------------------------------" << endl;
   cout << "creating vector" << endl;
   const int low = 10;
   const int high = 20;
   const float value = 42;
   const float value2 = 17;
   lbvector<float> v (low, high, value);
   cout << "vector created" << endl;
   int i;

   if (v.lower() != low) {
      cout << "Failed construction lower." << endl;
   }
   if (v.upper() != high) {
      cout << "Failed construction upper." << endl;
   }

   cout << "checking initialization" << endl;
   for (i = low; i <= high; i++) {
      if (v[i] != value) {
         cout << "Failed initialization test." << endl;
      }
   }

   cout << "Copy constructor test" << endl;
   lbvector<float> x(v);

   for (i = low; i <= high; i++) {
      if (x[i] != value) {
         cout << "Failed copying values test." << endl;
      }
   }

   cout << "changing values by assignment in initial vector" << endl;
   for (i = low; i <= high; i++) {
      v[i] = i;
   }

   cout << "checking whether vectors have different allocated space" << endl;
   for (i = low; i <= high; i++) {
      if (v[i] == x[i]) {
         cout << "Failed difference test." << endl;
      }
   }

   cout << "-------------------------------------" << endl;
   cout << "Big index test uncovers bugs where you are writing where you shouldn't." << endl;
   cout << "You may get past this but may have trashed some memory you need." << endl;
   cout << "-------------------------------------" << endl;
   int lowBI = -100000000;
   int highBI = lowBI+5;
   lbvector<int> w(lowBI,highBI);

   cout << "Lower bound should be " << lowBI << " and it is: "
        << w.lower( ) << endl;
   cout << "Upper bound should be " << highBI << " and it is: "
        << w.upper( ) << endl;

   for (i=lowBI; i<=highBI; i++) {
      w[i] = i;
   }
   for (i=lowBI; i<=highBI; i++) {
      if (w[i] != i) {
         cout << "Failed Big Index test" << endl;
      }
   }

   cout << "Checking whether previous vectors are okay." << endl;
   for (i = low; i <= high; i++) {
      if (v[i] != i) {
         cout << "failed first vector values okay test" << endl;
      }
   }
   for (i = low; i <= high; i++) {
      if (x[i] != value) {
         cout << "failed second vector values okay test" << endl;
      }
   }

   cout << "Assignment operator test" << endl;
   lbvector<float> y;

   y = v;
   for (i=low; i<high; i++) {
      if (y[i] != i) {
         cout << "Failed assignment operator test" << endl;
      }
   }
   
   cout << "Fill test" << endl;
   y.fill(value2);
   for (i=low; i<high; i++) {
      if (y[i] != value2) {
         cout << "Failed fill test" << endl;
      }
   }

   cout << "Checking values in big index again." << endl;
   for (i=lowBI; i<=highBI; i++) {
      if (w[i] != i) {
         cout << "Failed Big Index test" << endl;
      }
   }

   cout << "And finally a very normal vector with a lower bound of 0" << endl;
   lbvector<char> a(0,10,'?');
   cout << "Lower bound should be 0 and it is: " << a.lower( ) << endl;
   cout << "Upper bound should be 10 and it is: " << a.upper( ) << endl;
   cout << "This should be a bunch of question marks: ";
   for (i=0; i<=10; i++) {
      cout << a[i];
   }
   cout << endl;

   cout << "End of tests" << endl;
   cout << "-------------------------------------" << endl;
   cout << "If you get a segmentation fault here, it usually means " << endl;
   cout << "you overwrote some memory management information." << endl;
   cout << "Check carefully that you never write at myZero[0] unless" << endl;
   cout << "0 is a legal index." << endl;

   return EXIT_SUCCESS;
}
