
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*********************************************************
 * Written by: Simon Cicek                               *
 * Last changed: 2012-04-20                              *
 *                                                       *
 * A list implementing the interface: List with nodes as *
 * the underlying datastructure.                         *    
 *********************************************************/

public class NodeList<E> implements List<E>
{
    // The inner class representing a node
    protected class Node<E> 
    {
        E element;
        Node next;
        
        protected Node(E e)
        {
            this.element = e;
            this.next =  null;
        }
    }
    
    // Required variables
    Node first;
    Node last;
    Comparator comp;
    int size;
    
    public NodeList()
    {
        first = null;
        last = null;
        comp = new DefaultComparator();
        size = 0;
    }
    
    public NodeList(Comparator c)
    {
        first = null;
        last = null;
        comp = c;
        size = 0;        
    }
    
    @Override
    public void clear() 
    {
        Node currentNode = first;
        Node nextNode = currentNode.next;
        first = null;
        last = null;
        
        // Helps the Garbage Collector by removing references
        while(nextNode != null)
        {
            currentNode.next = null;
            currentNode = nextNode;
            nextNode = nextNode.next;
        }
        size = 0;
    }

    @Override
    public int size() 
    {
        return size;
    }

    @Override
    public boolean isEmpty() 
    {
        return first == null;
    }

    @Override
    public int indexOf(E element) 
    {
        int index;
        
        // For slight speed improvement, 
        // we check if the element to be found is the first/last 
        if(comp.compare(element, first.element) == 0)
            return index = 0;
        else if(comp.compare(element, last.element) == 0)
            return index = size() - 1;
        else
        {
            Node current = first;
            index = 0;
            // Search the entire list for the element
            while(comp.compare(element, current.element) != 0 && current.next != null)
            {
                current = current.next;
                index++;
            }
            
            // If the element was not found, return -1
            if(comp.compare(element, current.element) != 0)
                return -1;
            else
                return index;
        }
    }
    
    @Override
    public void add(int index, E element) 
    {
        // Ensure that the index is within the bounds of the list
        if(index < 0 || index > size())
            throw new IndexOutOfBoundsException("Index is less or greater than the bounds!");

        // Replace the first element in the list
        if(index == 0)
        {
            Node newNode = new Node(element);
            Node tmp = first;
            first = newNode;
            first.next = tmp;
        }
        // Replace the last element in the list
        else if (index >= this.size() - 1)
        {
            Node tmp = last;
            last = new Node(element);
            tmp.next = last;
        }
        // Add the element at the defined index
        else
        {
            Node replacingNode = nodeAt(index);
            Node replacementNode = new Node(element);
            nodeAt(index - 1).next = replacementNode;
            replacementNode.next = replacingNode;
        }
        size++;
    }

    @Override
    public void add(E element) 
    {
        // In case there are no elements in the list 
        if(this.isEmpty())
        {
            first = new Node(element);
            last  = first;
        }
        // Add the element to the end of the list
        else
        {
            Node tmp = last;
            last = new Node(element);
            tmp.next = last;
        }
        size++;
    }
    
    @Override
    public void addAll(List<E> l)
    {
        Iterator i = l.iterator();
        
        while(i.hasNext())
            this.add((E) i.next());
    }

    @Override
    public E get(int index) 
    {
        Node n = nodeAt(index);
        if(n == null)
            return null;
        else
            return (E) n.element;
    }

    @Override
    public void remove(E element) 
    {
        // If the element to be removed is not in the list, do nothing
        if(indexOf(element) == -1)
            return;
        
        // Check if the element to be removed is the first in the list
        if(element == first.element)
        {
            if(last == first)
                last = null;
            
            Node tmp = first.next;
            first.next = null;
            first.element = null;
            first = tmp;
        }
        // Check if the element to be removed is the last in the list
        else if(element == last.element)
        {
            last.element = null;
            last = nodeAt(size() - 2);
            last.next = null;
        }
        // Remove the element in a proper way
        else
        {
            Node pre = nodeAt(indexOf(element) - 1);
            Node current = nodeAt(indexOf(element));
            Node next = nodeAt(indexOf(element) + 1);
            current.element = null;
            current.next = null;
            pre.next = next;
        }
        size--;
    }
    
    @Override
    public void remove(int index) 
    {
        // Ensure that the index is within the bounds of the list
        if(index < 0 || index > this.size() - 1)
            return;
        
        // Check if the element to be removed is the first in the list
        if(index == 0)
        {
            if(first == last)
                last = null;
            
            Node tmp = first.next;
            first.element = null;
            first.next = null;
            first = tmp;
        }
        // Check if the element to be removed is the last in the list
        else if(index == size() - 1)
        {
            last.element = null;
            last = nodeAt(size() - 2);
            last.next= null;
        }
        // Remove the element in a proper way
        else
        {
            Node pre = nodeAt(index - 1);
            Node current = nodeAt(index);
            Node next = nodeAt(index + 1);
            current.next = null;
            current.element = null;
            pre.next = next;
        }
        size--;
    }
    
    @Override
    public boolean contains(E element)
    {
        boolean found = false;
        
        Iterator itr = this.iterator();
        // Iterate over the list
        while(itr.hasNext())
        {
            // In case the element was found, stop searching
            if(comp.compare((E)itr.next(),element) == 0)
            {
                found = true;
                break;
            }
        }
        return found;        
    }
    
    public Node nodeAt(int index)
    {
        // Ensure that the index is within the bounds of the list
        if(index < 0 || index > this.size() - 1)
            return null;
        
        Node tmp = first;
        int i = 0;
        // Iterate over the list
        while(i != index)
        {
            if(tmp != null)
                tmp = tmp.next;
            i++;
        }
        return tmp;
    }

    // Change the comparator used to compare objects
    public void setComparator (Comparator c)
    {
        // Set the comparator to default
        if(c == null)
            this.comp = new DefaultComparator();
        else
            this.comp = c;
    }
    
    protected class DefaultComparator implements Comparator
    {
        @Override
        public int compare(Object o1, Object o2) 
        {
            try
            {
                Comparable c1 = (Comparable) o1;
                Comparable c2 = (Comparable) o2;
                return c1.compareTo(c2);
            }
            catch(Exception e)
            {
                throw new ClassCastException("Objects could not be compared!");
            }
        }
    }
    
    public Iterator<E> iterator ()
    {
        return this.new SetIterator ();
    }
    
    protected class SetIterator implements Iterator<E>
    {
        protected Node    nextNode;
	protected Node    lastReturnedNode;
        
        public SetIterator ()
        {
            nextNode = first;
            lastReturnedNode = null;
        }

        public boolean hasNext ()
        {
            return nextNode != null;
        }

	public E next () throws NoSuchElementException
        {
            // If we have reached the end of the list, alert the user
            if (!this.hasNext ())
                throw new NoSuchElementException ("No more elements");

            // Return the next element and move forward in the list
            E element = (E) nextNode.element;
            lastReturnedNode = nextNode;
            nextNode = nextNode.next;
            return element;

        }

        // Removes the element we just passed
        public void remove () throws IllegalStateException
        {
            // If we have not yet iterated or in case of repeated subsequent calls
            // alert the user of its error
            if (lastReturnedNode == null)
                throw new IllegalStateException ("No element to be removed");

            // Use the lists remove function to remove an element
            NodeList.this.remove ((E) lastReturnedNode.element);
            // Enable handling of repeated subsequent calls to this function 
            lastReturnedNode = null;
        }
    }
}