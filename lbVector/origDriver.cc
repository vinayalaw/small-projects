// small driver for the original vector class
// should not work with modified lbvector class
#include <iostream>
#include "lbvector.h"

using std::cin;
using std::cout;
using std::string;
using std::endl;

int main ()
{
   cout << "This driver uses the original lbvector class before modification" << endl;
   cout << "It should not compile cleanly with the modified class" << endl;
   cout << "creating vector" << endl;
   const int howmany = 10;
   const float value = 42;
   lbvector<float> v (howmany, value);
   cout << "vector created" << endl;
   int i;

   if (v.capacity() != howmany) {
      cout << "Failed construction size." << endl;
   }
   if (v[5] != value) {
      cout << "Failed construction initialization." << endl;
   }
   cout << "vector tests complete" << endl;



   cout << "And finally a very normal vector" << endl;
   lbvector<char> a(howmany,'?');
   cout << "Vector should have " << howmany << " elements and it has " << a.capacity( ) << endl;
   cout << "This should be a bunch of question marks: ";
   for (i = 0; i < howmany; i++) {
      cout << a[i];
   }
   cout << endl;

   cout << "End of tests" << endl;

   return EXIT_SUCCESS;
}
