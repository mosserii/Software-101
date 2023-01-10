package il.ac.tau.cs.sw1.ex9.riddles.forth;


import java.util.Iterator;

public class B4  implements Iterator<String> {

    private String[] strings;
    private int k;
    private int i = -1;

    public B4(String[] strings, int k) {
        this.strings = strings;
        this.k = k;
    }


    @Override
    public boolean hasNext() {

        return i < (k*strings.length);
    }

    @Override
    public String next() {
        i++;
        return strings[i % strings.length];
    }
}
