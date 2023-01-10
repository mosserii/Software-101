package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;

public class StealthCruiser extends Fighter{

	static int numOfStealthCruisers = 0;


	//private static final List<Weapon> wp2 = List.of(new Weapon("Laser Cannons", 10, 100));




	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		numOfStealthCruisers++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name, commissionYear,maximalSpeed,crewMembers,
				Collections.singletonList(new Weapon("Laser Cannons", 10, 100)));
	}

	@Override
	public int getAnnualMaintenanceCost() {// TODO 50 * number of engines in group

		return super.getAnnualMaintenanceCost() + (50*numOfStealthCruisers);
	}

}
