#include <cstdlib>
#include <vector>
#include <iostream>

using std::cin;
using std::cout;
using std::endl;
using std::vector;
using std::string;

void printVector(string label, vector<double> & v);
double dotProduct(vector<double> v1, vector<double> v2);
void fillVectors(vector<double> & v1, vector<double> & v2, int vSize);

int
main() {
  int length;
  cout << "Enter vector length: ";
  cin >> length;
  if (length > 100 || length < 0)
  {
    cout << "length invalid";
    return 1;
  }
  vector <double> vectorOne(length, 0);
  vector <double> vectorTwo(length, 0);
  fillVectors(vectorOne, vectorTwo, length);
  printVector("v1", vectorOne);
  printVector("v2", vectorTwo);
  cout << dotProduct(vectorOne, vectorTwo);
  return 0;
}

//displays vector
void
printVector (string label, vector<double> & v)
{
  cout << label << ": " ;
  for(size_t i = 0; i < v.size(); ++i)
  {
    cout << " [" << i << "] ";
    printf("%.1f  ", i, v[i]);
  }
  cout << endl;
}

//calculates dot product of the vectors
double
dotProduct (vector<double> v1, vector<double> v2)
{
  double dotProduct=0;
  for (size_t i = 0; i < v1.size(); ++i)
  {
    dotProduct += (v1[i] * v2[i]);
  }
  return dotProduct;
}

//fills vectors while input is valid
void
fillVectors (vector<double> & v1, vector<double> & v2, int vSize)
{
  int count = 0;
  double num;
  while (cin >> num)
  {
    if(count < vSize)
    {
      v1[count] = num;
    }
    else if (count > vSize - 1 && count < vSize*2){
      v2[count - vSize]=num;
    }
    ++count;
  }
}
