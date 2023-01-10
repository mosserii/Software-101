package il.ac.tau.cs.sw1.ex9.starfleet;

public class Cylon extends myAbstractCrewMember{

	private int modelNumber;

	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		super(age, yearsInService, name);
		this.modelNumber = modelNumber;

	}

	public int getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(int modelNumber) {
		this.modelNumber = modelNumber;
	}
}

