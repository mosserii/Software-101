package riddles;

import java.util.Objects;

public class B extends A{
	
	protected int i;
	protected int j;


	public B(int i, int j) {
		super(i,j);
	
	}


	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), i, j);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		B b = (B) o;
		return i == b.i && j == b.j;
	}
}
