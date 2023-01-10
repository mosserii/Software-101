package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;
import java.util.stream.Collectors;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{



	public HashMapHistogram<T> freq1;
	List<T> keysSorted;

	public HashMapHistogramIterator(HashMapHistogram<T> freq) {
		this.freq1 = freq;
		Set<T> freq1ItemsSet= freq1.getItemsSet();
		keysSorted = new ArrayList<>(freq1ItemsSet);
		keysSorted.sort(new HashMapHistogramComperator());
	}



	public class HashMapHistogramComperator implements Comparator<T>{


		@Override
		public int compare(T o1, T o2) {//TODO what happens if equal
			int val1 = freq1.getCountForItem(o1);
			int val2 = freq1.getCountForItem(o2);

			int comparing = -(Integer.compare(val1,val2));

			if (comparing == 0)
				return o1.compareTo(o2);

			return comparing;
		}


	}

	private int index;



	@Override
	public boolean hasNext() {
		return index < keysSorted.size();
	}

	@Override
	public T next() {
		return keysSorted.get(index++);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}


	//Collections.sort(lst,new FractionalKnapSackComperator());
	//return lst.iterator();


}
