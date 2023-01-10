package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	private HashMap<T,Integer> freq;// = new HashMap<>();//TODO PUBLIC OR NOT and CONSTRUCTOR OR NOT

	public HashMapHistogram(){
		this.freq = new HashMap<T,Integer>();
	}


	@Override
	public Iterator<T> iterator() {//TODO
		return new HashMapHistogramIterator<T>(this);//TODo freq or not, <> or not
	}

	@Override
	public void addItem(T item) {
		if (freq.get(item) == null)
			freq.put(item,1);
		else
			freq.put(item,(freq.get(item))+1);
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {//TODO

			if (freq.get(item) != null)
				freq.put(item, freq.get(item) -1);
			else
				throw new IllegalItemException();
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {//TODO merge, exception
		try {
			if (freq.get(item) == null)
				freq.put(item,k);
			else
				freq.put(item, freq.get(item) + k);
		}
		catch (Exception e) {//TODO
			e.getMessage();
		}
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {//TODO
		try {
			if (k < 0)
				throw new IllegalKValueException(k);

			for (int i = 0; i<k; i++){
				removeItem(item);
			}
		}
		catch (IllegalItemException e2) {
			e2.getMessage();
		}

	}

	@Override
	public int getCountForItem(T item) {
		if (freq.get(item) != null)
			return freq.get(item);
		return 0;
	}

	@Override
	public void addAll(Collection<T> items) {
		for (T item: items) {
			addItem(item);
		}
	}

	@Override
	public void clear() {
		freq = new HashMap<>();
	}

	@Override
	public Set<T> getItemsSet() {
		return freq.keySet();
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {//TODO EXCEPTION
		Set<T> anotha =  anotherHistogram.getItemsSet();
		try {
			for (T anothaKey: anotha)
				this.addItemKTimes(anothaKey , anotherHistogram.getCountForItem(anothaKey));
		}
		catch (IllegalKValueException e) {
		}

	}


	public HashMap<T, Integer> getFreq() {
		return freq;
	}
}
