
import java.util.Iterator;

/**********************************************************************************
 * Written by: Simon Cicek                                                        *
 * Last changed: 2012-04-20                                                       *
 *                                                                                *
 * The interface defining a proper list (regardless of underlying datastructure). *
 **********************************************************************************/

public interface List<E> 
{
    // Clears the list
    void clear();
    // The size of the list
    int size();
    // Checks if the list is empty
    boolean isEmpty();
    // Finds the index of the given element
    int indexOf(E element);
    // Adds the given element to the list
    void add(E element);
    // Adds the given element to the defined index
    void add(int index, E element);
    // Adds all the elements in the given list
    void addAll(List<E> list);
    // Checks if the given element exists in the list
    boolean contains(E element);
    // Returns the element at the given index
    E get(int index);
    // Removes the given element from the list
    void remove(E element);
    // Removes the element at the given index
    void remove(int index);
    // Returns an iterator, used to traverse the list
    public Iterator<E> iterator ();
}
