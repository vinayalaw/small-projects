 /*
  Filename   : SearchTree.hpp
  Author     : Gary M. Zoppetti
  Course     : CSCI 362-01
  Description: Binary search tree class,
  a basis for implementing associative containers.
*/

//errors yet to be resolved: seg-faults for certain erase
//cases and printLevelOrder

/************************************************************/
// Macro guard

#ifndef SEARCHTREE_HPP
#define SEARCHTREE_HPP

/************************************************************/
// System includes

#include <iostream>
#include <algorithm>
#include <iterator>
#include <queue>
#include <initializer_list>

/************************************************************/
// Local includes

/************************************************************/
// Using declarations

using std::ostream;
using std::ostream_iterator;
using std::queue;
using std::initializer_list;
using std::cout;
using std::endl;

/************************************************************/

template<typename T>
struct Node
{
  typedef Node* NodePtr;

  Node (const T& d = T ())
   : data (d), left (nullptr), right (nullptr), parent (nullptr)
  {
  }

  Node (const T& d, NodePtr l, NodePtr r, NodePtr p)
   : data (d), left (l), right (r), parent (p)
  {
  }

  //**

  T        data;
  NodePtr  left;
  NodePtr  right;
  NodePtr  parent;
};

/************************************************************/

// Forward declaration
template<typename T>
class SearchTree;

/************************************************************/

template<typename T>
struct TreeIterator
{
  friend class SearchTree<T>;

  typedef TreeIterator<T>  Self;
  typedef Node<T>*         NodePtr;
  typedef const Node<T>*   ConstNodePtr;

  typedef T                value_type;
  typedef const T*         pointer;
  typedef const T&         reference;

  TreeIterator ()
    : m_nodePtr ()
  { }

  explicit
  TreeIterator (ConstNodePtr n)
    : m_nodePtr (n)
  { }

  reference
  operator* () const
  {
    return m_nodePtr->data;
  }

  // Return address of node's data member
  pointer
  operator-> () const
  {
    return &*this;
  }

  // Pre
  Self&
  operator++ ()
  {
    m_nodePtr = increment (m_nodePtr);
    return *this;
  }

  // Post
  Self
  operator++ (int)
  {
    Self temp (*this);
    m_nodePtr = increment (m_nodePtr);
    return temp;
  }

  // Pre
  Self&
  operator-- ()
  {
    m_nodePtr = decrement (m_nodePtr);
    return *this;
  }

  // Post
  Self
  operator-- (int)
  {
    Self temp (*this);
    m_nodePtr = decrement (m_nodePtr);
    return temp;
  }

  bool
  operator== (const Self& i) const
  {
    return m_nodePtr == i.m_nodePtr;
  }

  bool
  operator!= (const Self& i) const
  {
    return m_nodePtr != i.m_nodePtr;
  }

private:

  static ConstNodePtr
  increment (ConstNodePtr n)
  {
    auto ptr = n;
    //get minimum in right subtree
    if(ptr->right != nullptr)
    {
      ptr = ptr->right;
      while(ptr->left != nullptr)
      {
        ptr = ptr->left;
      }
    }
    //must travel up tree
    else
    {
      ConstNodePtr prevPtr = ptr;
      ptr = ptr->parent;
      while (true)
      {
        if(ptr == nullptr || ptr->left == prevPtr)
        {
          break;
        }
      }
    }
    return ptr;
  }

  static ConstNodePtr
  decrement (ConstNodePtr n)
  {
    auto ptr = n;
    //get maximum in left subtree
    if(ptr->left != nullptr)
    {
      ptr = ptr->left;
      while(ptr->right != nullptr)
      {
        ptr = ptr->right;
      }
    }
    //must travel up tree
    else
    {
      ConstNodePtr prevPtr = ptr;
      ptr = ptr->parent;
      while (true)
      {
        if(ptr == nullptr || ptr->right == prevPtr)
        {
          break;
        }
      }
    }
    return ptr;
  }

private:

  ConstNodePtr m_nodePtr;
};

/************************************************************/

template<typename T>
class SearchTree
{
  friend class TreeIterator<T>;

public:

  // DO NOT MODIFY typedef's
  typedef T             value_type;
  typedef T*            pointer;
  typedef const T*      const_pointer;
  typedef T&            reference;
  typedef const T&      const_reference;

  typedef TreeIterator<T> iterator;
  typedef TreeIterator<T> const_iterator;

  // Header parent points to root of tree or is nullptr
  //   if the tree is empty
  // Header left points to LARGEST node or is nullptr
  //   if the tree is empty
  // Header right points to SMALLEST node or is nullptr
  //   if the tree is empty
  // size represents the number of elements in the tree
  SearchTree ()
    : m_header (), m_size (0)
  {
  }

  // Copy constructor
  SearchTree (const SearchTree& t)
    : m_header (), m_size (0)
  {
    if (t.m_header.parent != nullptr)
    {
      
      copyTree (t.m_header.parent);
    }
  }

  ~SearchTree ()
  {
    clear ();
  }

  bool
  empty () const
  {
    return (size () == 0);
  }

  size_t
  size () const
  {
    return m_size;
  }

  iterator
  begin ()
  {
    return iterator (m_header.right);
  }

  const_iterator
  begin () const
  {
    return const_iterator (m_header.right);
  }

  iterator
  end ()
  {
    return iterator (&m_header);
  }

  const_iterator
  end () const
  {
    return const_iterator (&m_header);
  }

  iterator
  find (const T& v) const
  {
    // Call private helper method
    ConstNodePtr n = findHelper (v);
    if (n == nullptr)
    {
      // Wasn't found so return end ()
      n = &m_header;
    }
    return iterator (n);
  }

  std::pair<iterator, bool>
  insert (const T& v)
  {
    NodePtr insertedNode = insert (v, m_header.parent, &m_header);
    bool inserted = insertedNode != nullptr;
    if (inserted)
    {
      // Update header right to point to smallest node
      if (m_header.right == nullptr || v < m_header.right->data)
        m_header.right = insertedNode;
      // Update header left to point to largest node
      if (m_header.left == nullptr || v > m_header.left->data)
        m_header.left = insertedNode;
    }
    ++m_size;
    return std::make_pair (iterator (insertedNode), inserted);
  }

  size_t
  erase (const T& v)
  {
    // Update header right to point to smallest node
    if (m_header.right != nullptr && v == m_header.right->data)
      m_header.right = minimum (m_header.parent);
        const_cast<NodePtr> (iterator::increment (m_header.right));
    // Update header left to point to largest node
    if (m_header.left != nullptr && v == m_header.left->data)
      m_header.left = maximum (m_header.parent);
        const_cast<NodePtr> (iterator::decrement (m_header.left));
    bool erased = erase (v, m_header.parent, &m_header);
    
    return erased ? 1 : 0;
  }

  void
  clear ()
  {
    // Delete all nodes and set size to 0
    clear (m_header.parent);
    m_size = 0;
    m_header.left = m_header.right = m_header.parent = nullptr;
  }

  void
  printInOrder (ostream& out) const
  {
    printInOrder (out, m_header.parent);
  }

  void
  printLevelOrder (ostream& out) const
  {
    printLevelOrder (out, m_header.parent);
  }

private:

  typedef struct Node<T> Node;
  typedef Node*          NodePtr;
  typedef const Node*    ConstNodePtr;

  void
  copyTree (NodePtr origNode)
  {
    if (origNode != nullptr)
    {
      insert (origNode->data);
      copyTree (origNode->left);
      copyTree (origNode->right);
    }
  }

  NodePtr
  minimum (NodePtr r) const
  {
    if (r->left == nullptr)
    {
      return r;
    }
    return minimum (r->left);
  }

  NodePtr
  maximum (NodePtr r) const
  {
    if (r->right == nullptr)
    {
      return r;
    }
    return maximum(r->right);
  }

  ConstNodePtr
  findHelper (const T& v) const
  {
    // Return a pointer to the node that contains "v"
    NodePtr currNode = m_header.parent;
    while (currNode != nullptr)
    {
      if (v == currNode->data)
      {
        return currNode;
      }
      else if (v < currNode->data)
      {
        currNode = currNode->left;
      }
      else
      {
        currNode = currNode->right;
      }
    }
    //returns nullptr if not found
    return currNode;
  }

  NodePtr
  insert (const T& v, NodePtr& r, NodePtr parent)
  {
    // Insert "v" into the tree rooted at "r"
    // Use "parent" for recursion and setting the parent of the
    //   node containing "v"
    if (m_size == 0)
    {
      m_header.parent = new Node(v, nullptr, nullptr, &m_header);
      return m_header.parent;
    }
    else{
      if (r == nullptr){
        r = new Node (v, nullptr, nullptr, parent);
      }
      else if (v <= r->data){
        return insert (v, r->left, r);
      }
      else if (v > r->data){
        return insert (v, r->right, r);
      }
    }
    return r;
  }

  bool
  erase (const T& v, NodePtr& r, NodePtr parent)
  {
    // Erase "v" from the tree rooted at "r"
    // Use "parent" for recursion and updating the parent of the
    //   node containing "v"
    // Return whether the erase succeeded or not
    if (r == nullptr)
    {
      return false;
    }
    if (v==r->data)
    {
      NodePtr succ = nullptr;
      //case for right subtree
      if (r->right != nullptr)
      {
        succ= r->right;
        while (succ->left != nullptr)
        {
          succ = succ->left;
        }
        if (succ != r->right)
        {
          if (succ->right !=nullptr)
          {
            succ->right->parent = succ->parent;
            succ->parent->left = succ->right;
          }
          
        }
        succ->parent = parent;
        succ->left = r->left;
      }
      // left specific
      else
      {
        succ= r->left;
        if(succ != nullptr)
        {
          succ->parent = parent;
        }
      }
      //general portion of both cases
      if (succ !=nullptr)
      {
        succ->parent = parent;
        if (succ->data > parent->data)
        {
          parent->right = succ;
        }
        else
        {
          parent->left = succ;
        }
      }
      delete r;
      --m_size;
      return true;
    }
    if (v < r->data)
    {
      return erase (v, r->left, r);
    }
    else{
      return erase (v, r->right, r);
    }
  }

  void
  clear (NodePtr r)
  {
    // Delete all nodes in the tree
    if (r!=nullptr)
    {
      clear (r->left);
      clear (r->right);
      delete r;
    }
  }

  void
  printInOrder (ostream& out, NodePtr r) const
  {
    if (r != nullptr)
    {
      printInOrder (out, r->left);
      out << r->data << " ";
      printInOrder (out, r->right);
    }
  }
  //implements queue to print in level order
  void
  printLevelOrder (ostream& out, NodePtr r) const
  {
    if (r == nullptr)
    {
      return;
    }
    queue<NodePtr> q;
    q.push(r);
    while (!q.empty ())
    {
      r=q.front ();
      q.pop ();
      out << r->data << " ";
      if (r->left != nullptr)
      {
        q.push(r->left);
      }
      else
      {
        out << "- ";
      }
      if (r->right != nullptr)
      {
        q.push(r->right);
      }
      else
      {
        out << "- ";
      }
    }
    out << "| ";
  }

private:

  Node   m_header;
  size_t m_size;
};

/************************************************************/

// Output tree as [ e1 e2 e3 ... en ]
//   with '|' to indicate the end of a level, and '-' to indicate
//   a missing node.
// E.g., the tree
//       4
//    2     7
//         6
// should be printed thus: [ 4 | 2 7 | - - 6 - ]
template<typename T>
ostream&
operator<< (ostream& out, const SearchTree<T>& tree)
{
  out << "[ ";
  tree.printLevelOrder (out);
  out << "]";

  return out;
}

/************************************************************/

#endif

/************************************************************/
