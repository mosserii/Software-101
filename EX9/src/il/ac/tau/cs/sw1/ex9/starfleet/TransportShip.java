package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip extends myAbstractSpaceShip{

	private int cargoCapacity;
	private int passengerCapacity;


	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers,
						 int cargoCapacity, int passengerCapacity){
		super(name,commissionYear,maximalSpeed,crewMembers);//TODO
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;



	}

	@Override
	public int getAnnualMaintenanceCost() {//OVERRIDE
		int annualCostBasis = 3000;
		return annualCostBasis + (5 * cargoCapacity) + (3 * passengerCapacity);
	}


	public int getCargoCapacity() {
		return cargoCapacity;
	}

	public void setCargoCapacity(int cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}


	public void setPassengerCapacity(int passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}



	//


	@Override
	public String toString() {
		return 	super.toString()
				+ "\n\tCargoCapacity=" + this.getCargoCapacity()
				+ "\n\tPassengerCapacity=" + this.getPassengerCapacity();

	}
}
