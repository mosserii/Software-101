package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear(Collection<Spaceship> fleet) {
		List<String> sortedDesc = new ArrayList<>();
		List<myAbstractSpaceShip> fleet1 = new ArrayList<>();

		for (Spaceship s : fleet)
			fleet1.add((myAbstractSpaceShip) s);

		Collections.sort(fleet1, new mySpaceshipCompartor());

		for (myAbstractSpaceShip s1 : fleet1)
			sortedDesc.add(s1.toString());




		return sortedDesc;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String, Integer> instancemap = new HashMap<>();

		for (Spaceship s : fleet){
			String simpleClass = s.getClass().getSimpleName();
			try {
				instancemap.put(s.getClass().getSimpleName(), instancemap.get(s.getClass().getSimpleName()) + 1);
			}
			catch (NullPointerException e) {
				instancemap.put(s.getClass().getSimpleName(),1);
			}

		}

			return instancemap;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost(Collection<Spaceship> fleet) {
		int totMaintenance = 0;
		for (Spaceship s : fleet)
			totMaintenance += s.getAnnualMaintenanceCost();

		return totMaintenance;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {//TODO CHECK

		Set<String> weaponNames = new HashSet<>();

		for (Spaceship s : fleet) {
			if (myComatSpaceShip.class.isAssignableFrom(s.getClass())) {
				List<Weapon> sWeapons = ((myComatSpaceShip) s).weapons;
				for (Weapon w : sWeapons)
					weaponNames.add(w.getName());
			}
		}

		return weaponNames;

	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {

		int numOfMembers = 0;
		for (Spaceship s : fleet)
			numOfMembers += s.getCrewMembers().size();

		return numOfMembers;

	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships.
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {

		float sumOfAge = 0.0F;
		float numOfOfficers = 0.0F;

		for (Spaceship s : fleet) {
			Set<? extends CrewMember> crewMembers1 = s.getCrewMembers();
			for (CrewMember c : crewMembers1){
				if (c.getClass().getSimpleName().equals("Officer")) {
					numOfOfficers++;
					sumOfAge += c.getAge();
				}
			}

		}

		return sumOfAge /numOfOfficers;
	}

	public static class CrewMemberCompartor implements Comparator<CrewMember> {


		@Override
		public int compare(CrewMember o1, CrewMember o2) {
			o1 = (Officer) o1;
			o2 = (Officer) o2;
			int res = ((Officer) o1).getRank().compareTo(((Officer) o2).getRank());
			if (res == 0)
				return o1.getName().compareTo(o2.getName());
			return res;
		}
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {

		Map<Officer, Spaceship> rankingOfOfficers = new HashMap<>();//every officer - best of s


		for (Spaceship s : fleet) {

			Set<? extends CrewMember> members = s.getCrewMembers();
			TreeSet<CrewMember> members1 = new TreeSet<>(new CrewMemberCompartor());


			for (CrewMember crewMember : members) {
				if (crewMember.getClass().getSimpleName().equals("Officer")) {
					members1.add(crewMember);
				}
			}
			if (!members1.isEmpty())
				rankingOfOfficers.put((Officer) members1.first(),s);
		}

		return rankingOfOfficers;

	}


	public static class RankHistogramCompartor implements Comparator<Map.Entry<OfficerRank, Integer>> {


		@Override
		public int compare(Map.Entry<OfficerRank, Integer> o1, Map.Entry<OfficerRank, Integer> o2) {
			if (Integer.compare(o1.getValue(), o2.getValue()) != 0)
				return Integer.compare(o1.getValue(), o2.getValue());
			return o1.getKey().compareTo(o2.getKey());


		}
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {

		Map<OfficerRank, Integer> rankHistogram = new HashMap<>();
///stream.filter
		for (Spaceship s : fleet) {
			Set<? extends CrewMember> crewMembers1 = s.getCrewMembers();//member of every ship
			for (CrewMember c : crewMembers1) {
				if (c.getClass().getSimpleName().equals("Officer")) {
					OfficerRank rank1 = ((Officer) c).getRank();
					try {
						rankHistogram.put(rank1, rankHistogram.get(rank1) + 1);
					}
					catch (NullPointerException e){
						rankHistogram.put(rank1, 1);
					}
				}
			}
		}

		List<Map.Entry<OfficerRank, Integer>> res = new ArrayList<>(rankHistogram.entrySet());
		Collections.sort(res, new RankHistogramCompartor());
		return res;

	}
}
