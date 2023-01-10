package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends myComatSpaceShip{

	private int CombatAnnualMaintenanceCost = super.getAnnualMaintenanceCost();

	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name,commissionYear,maximalSpeed,crewMembers, weapons);
	}


	@Override
	public int getAnnualMaintenanceCost() {
		int annualCostBasisFighter = 2500;
		//int totalFireForceCost = 0;
		int costForEngines = Math.round((super.getMaximalSpeed()) * 1000);

		//for (Weapon wp : weapons)
		//	totalFireForceCost += wp.getAnnualMaintenanceCost();


		return annualCostBasisFighter + super.getAnnualMaintenanceCost() + costForEngines;
	}

	public int getCombatAnnualMaintenanceCost() {
		return CombatAnnualMaintenanceCost;
	}


}
