package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.HashSet;

public class Officer extends CrewWoman {
	private OfficerRank rank;


	public Officer(String name, int age, int yearsInService, OfficerRank rank) {
		super(age, yearsInService, name);
		this.rank = rank;
	}

	public OfficerRank getRank() {
		return rank;
	}

	public void setRank(OfficerRank rank) {
		this.rank = rank;
	}


}
