package businesslogic;

import java.util.Iterator;

public interface ExtendedIterator<T> extends Iterator<T> {
    //return	the	actual	element	and	go	to	the	previous
    T previous();

    //true	if ther	is	a	previous	element
    boolean hasPrevious();

    //It	is	placed	in	the	first	element
    void goFirst();

    // It	is	placed	in	the	last	element
    void goLast();
}
