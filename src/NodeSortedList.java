/*****************************************************************************
 * Written by: Simon Cicek                                                   *
 * Last changed: 2012-04-20                                                  *
 *                                                                           *
 * A subclass to "NodeList", implementing the interface "List" but enforcing *
 * a sorted list. Thus most functions do not differ from the superclass.     *
 * The main difference is that the function 'add' must ensure that           *
 * the list remains sorted.                                                  * 
 *****************************************************************************/

public class NodeSortedList<E> extends NodeList<E> implements List<E>
{    
    @Override
    public void add(int index, E element) 
    {
        // Ensure that the index is within the bounds of the list
        if(index < 0 || index > size())
            throw new IndexOutOfBoundsException("Index is less or greater than the bounds!");
         
        
        // Replace the first element in the list
        if(index == 0 && comp.compare(element, nodeAt(1).element) <= 0)
        {
            Node tmp = first;
            first = new Node(element);
            first.next = tmp;
        }
        // Replace the last element in the list
        else if (index == this.size() - 1 && 
                 comp.compare(element, nodeAt(this.size - 2).element) >= 0 &&
                 comp.compare(element, last.element) <= 0)
        {
            Node pre = nodeAt(this.size() - 2);
            Node newNode = new Node(element);
            pre.next = newNode;
            newNode.next = last;
        }
        // Add an element to the end of the list
        else if(index == this.size() && comp.compare(element, last.element) >= 0)
        {
            Node newNode = new Node(element);
            Node pre = last;
            pre.next = newNode;
            last = newNode;
        }
        // Ensure that the element to be added does not cause a disorder
        else if(comp.compare(element, nodeAt(index - 1).element) < 0 ||
                comp.compare(element, nodeAt(index + 1).element) > 0)
            return;
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
        else
        {
            // In case the element to be added is smaller than everything in the list
            if(comp.compare(element, first.element) < 0)
            {
                Node tmp = first;
                first = new Node(element);
                first.next = tmp;
            }
            // In case the element to be added is larger than everything in the list
            else if (comp.compare(element, last.element) > 0 )
            {
                Node tmp = last;
                last = new Node(element);
                tmp.next = last;
            }
            // Find the proper place for the element to be added
            else
            {
                Node current = first;
                // Iterate over all the elements to find the proper place
                while(current.next != null && comp.compare(element, current.next.element) > 0)
                    current = current.next;
                
                Node tmp = new Node(element);
                tmp.next = current.next;
                current.next = tmp;
            }
        }
        size++;
    }
}
