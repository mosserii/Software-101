package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter{

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int annualCostBasisCoViper = 4000;
		int numOfCrewMembers = crewMembers.size();

		return (int) (super.getCombatAnnualMaintenanceCost() + annualCostBasisCoViper
						+ (500*numOfCrewMembers) + (500*maximalSpeed));
	}
}
