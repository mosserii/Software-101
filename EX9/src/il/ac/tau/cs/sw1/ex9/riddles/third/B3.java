package il.ac.tau.cs.sw1.ex9.riddles.third;

public class B3 extends A3{//todo PERFECt

    String s;

    public B3(String s) {
        super(s);
        this.s = s;
    }


    @Override
    public void foo(String s) throws Exception {
        if (s.equals(this.s))
            throw new B3(s);
    }

    public String getMessage(){
        return s;
    }
}