package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class myAbstractSpaceShip implements Spaceship{



    protected String name;
    protected int commissionYear;
    protected float maximalSpeed;
    protected int firePower;
    protected Set<? extends CrewMember> crewMembers;
    protected int annualMaintenanceCost;


    public myAbstractSpaceShip(String name, int commissionYear, float maximalSpeed, int firePower, Set<CrewMember> crewMembers, int annualMaintenanceCost) {
        this.name = name;
        this.commissionYear = commissionYear;
        this.maximalSpeed = maximalSpeed;
        this.firePower = firePower;
        this.crewMembers = crewMembers;
        this.annualMaintenanceCost = annualMaintenanceCost;
    }

    public myAbstractSpaceShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers) {
        this.name = name;
        this.commissionYear = commissionYear;
        this.maximalSpeed = maximalSpeed;
        this.crewMembers = crewMembers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommissionYear() {
        return commissionYear;
    }

    public void setCommissionYear(int commissionYear) {
        this.commissionYear = commissionYear;
    }

    public float getMaximalSpeed() {
        return maximalSpeed;
    }

    public void setMaximalSpeed(float maximalSpeed) {
        this.maximalSpeed = maximalSpeed;
    }

    public int getFirePower() {//TODO - every spaceship has 10 firepower.

        return 10;//TODO CHECK
    }

    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    public Set<CrewMember> getCrewMembers() {
        return (Set<CrewMember>) crewMembers;//TODO CHECK
    }

    public void setCrewMembers(Set<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }


    public int getAnnualMaintenanceCost() {
        return annualMaintenanceCost;
    }

    public void setAnnualMaintenanceCost(int annualMaintenanceCost) {
        this.annualMaintenanceCost = annualMaintenanceCost;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        myAbstractSpaceShip that = (myAbstractSpaceShip) other;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {

        return    this.getClass().getSimpleName()
                + "\n\tName=" + this.getName()
                + "\n\tCommissionYear=" + this.getCommissionYear()
                + "\n\tMaximalSpeed=" + this.getMaximalSpeed()
                + "\n\tFirePower=" + this.getFirePower()
                + "\n\tCrewMembers=" + this.getCrewMembers().size()
                + "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
    }
}
