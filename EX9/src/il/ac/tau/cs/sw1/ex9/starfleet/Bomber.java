package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends myComatSpaceShip {


	protected int numberOfTechnicians;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		this.numberOfTechnicians = numberOfTechnicians;
	}

	public int getNumberOfTechnicians() {
		return numberOfTechnicians;
	}

	@Override
	public int getAnnualMaintenanceCost() {

		int annualCostBasisBomber = 5000;
		int totalFireForceCostBomber = 0;


		totalFireForceCostBomber = (int) ((1 - (0.1 * numberOfTechnicians)) * super.getAnnualMaintenanceCost());//TODO CHECK OF CASTTOINT
		return annualCostBasisBomber + totalFireForceCostBomber;
	}


	@Override
	public String toString() {
		return super.toString()
				+ "\n\tNumberOfTechnicians=" + this.getNumberOfTechnicians();
	}
}
