package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public abstract class myAbstractCrewMember implements CrewMember {


    private int age;

    private String name;

    private int yearsInService;

    public myAbstractCrewMember(int age, int yearsInService, String name) {
        super();
        this.name = name;
        this.age = age;
        this.yearsInService = yearsInService;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getYearsInService() {
        return age;
    }

    public void setYearsInService(int yearsInService) {
        this.yearsInService = yearsInService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        myAbstractCrewMember that = (myAbstractCrewMember) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}





