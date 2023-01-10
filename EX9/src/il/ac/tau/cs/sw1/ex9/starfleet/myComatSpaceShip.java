package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class myComatSpaceShip extends myAbstractSpaceShip {


    protected List<Weapon> weapons;


    public myComatSpaceShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons) {
        super(name, commissionYear, maximalSpeed, crewMembers);
        this.weapons = weapons;
    }

    @Override
    public int getFirePower() {
        int totalFireForce = 0;

        for (Weapon wp : weapons)
            totalFireForce += wp.getFirePower();

        return super.getFirePower() + totalFireForce;
    }

    public List<Weapon> getWeapon() {
        return this.weapons;
    }

    public int getAnnualMaintenanceCost() {
        int totalFireForceCost = 0;

        for (Weapon wp : weapons)
            totalFireForceCost += wp.getAnnualMaintenanceCost();

        return totalFireForceCost;
    }

    @Override
    public String toString() {
        return   super.toString()
                + "\n\tWeaponArray=" + this.getWeapon().toString();
    }
}
