
import java.util.Iterator;

/***************************
 * Written by: Simon Cicek
 * Last changed: 2012-03-28
 ***************************/

public class Main 
{
    public static void main(String[] args)
    {
        List<String> list = new NodeList();
        List<String> list2 = new NodeSortedList();
        list.add("F");
        list.add("D");
        list.add("C");
        list.add("A");
        list.add(3,"B");
        System.out.println("NodeList:");
        for(int i = 0; i < list.size(); i++)
            System.out.println("Index: " + i +  "  Element: " + list.get(i));
 
        System.out.println("---------------------");
        
        list2.addAll(list);        
        list2.add(4, "E");
        list2.remove("F");
        list2.remove(4);
        System.out.println("NodeSortedList:");
        for(int i = 0; i < list2.size(); i++)
            System.out.println("Index: " + i +  "  Element: " + list2.get(i));
    }
}
