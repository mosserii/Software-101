package riddles;


import java.util.Objects;

public class A implements Comparable<A> {
	
	protected int i;
	protected int j;

	public A(int i, int j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public int hashCode() {
		return Objects.hash(j);
	}

	@Override
	public boolean equals(Object obj) {// by j value
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		A a = (A) obj;
		return j == a.j;
	}

	@Override
	public int compareTo(A o) {
		if (this.j < o.j)
			return -1;
		else if (this.j > o.j)
			return 1;
		return 0;
	}
	
	
	public String toString() {
		return "("+this.i+" "+this.j+")";}



}
