package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;

public class mySpaceshipCompartor implements Comparator<myAbstractSpaceShip> {//TODO


    @Override
    public int compare(myAbstractSpaceShip o1, myAbstractSpaceShip o2) {

        int firePowerComparison = -Integer.compare(o1.getFirePower() , o2.getFirePower());
        if (firePowerComparison != 0)
            return firePowerComparison;

        int commissionedYearComparison = -Integer.compare(o1.getCommissionYear(), o2.getCommissionYear());
        if (commissionedYearComparison != 0)
            return commissionedYearComparison;

        else
            return o1.getName().compareTo(o2.getName());//by name


    }
}
