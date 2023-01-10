package il.ac.tau.cs.sw1.ex9.riddles.second;

public class B2 extends A2{//todo perfect

    @Override
    public String foo(String s) {
        return s.toUpperCase();
    }


    public A2 getA(boolean randomBool) {
        if (randomBool)
            return new B2();

        else // randomBool is false
            return new A2();
    }
}
