#include <iostream>
#include "lbvector.h"

using std::cin;
using std::cout;
using std::string;
using std::endl;

int main ()
{
//   cout << "creating char vector" << endl;
   const int lowerV = -14;
   const int upperV = 10;
   const char initV = 'o';
   int i;
   lbvector<char> v (lowerV, upperV, initV);
//   cout << "char vector created and initialized" << endl;

   if (v.lower() != lowerV) {
      cout << "Failed construction lower." << endl;
   }
   if (v.upper() != upperV) {
      cout << "Failed construction upper." << endl;
   }
//   cout << "checking initialization" << endl;
   for (i = lowerV; i <= upperV; i++) {
      if (v[i] != initV) {
         cout << "Failed initialization test." << endl;
      }
   }
   
   v[lowerV] = 'c';
   v[upperV] = 'l';

   cout << "Output should look like coooooool ==> ";

   for (i = lowerV; i <= upperV; i++) {
      cout << v[i];
   }
   cout << endl;

   cout << "Trying an index one higher than upper bound of " << upperV << endl;
   cout << v[upperV+1] << endl;

   cout << "Trying an index one lower than lower bound of " << lowerV << endl;
   cout << v[lowerV-1] << endl;

   cout << "If your program got here instead of giving an "
        << "assertion error," << endl
        << "it failed the bounds checking test" << endl;

   return EXIT_SUCCESS;
}
