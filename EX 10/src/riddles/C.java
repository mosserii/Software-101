package riddles;

import java.util.Objects;

public class C extends B {
	
	private int i;
	private int j;

	public C(int i, int j) {
		super(i,j);
		
	}


	@Override
	public int compareTo(A other) {
		int res = Integer.compare(this.i, other.i);
		if (res == 0)
			return Integer.compare(this.j, other.j);
		return res;
	}



}