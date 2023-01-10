package il.ac.tau.cs.sw1.ex7;

import java.util.*;


public interface Greedy<T> {

    /**
     * A selection function, which chooses the best candidate to be added to the solution
     */
    Iterator<T> selection();

    /**
     * A feasibility function, that is used to determine if a candidate can be used to contribute to a solution
     */
    boolean feasibility(List<T> lst, T element);

    /**
     * An assign function, which assigns a value to a solution, or a partial solution
     */
    void assign(List<T> lst, T element);

    /**
     * A solution function, which will indicate when we have discovered a complete solution
     */
    boolean solution(List<T> lst);

    /**
     * The Greedy Algorithm
     */
    default List<T> greedyAlgorithm() {

        List<List<T>> finalRes = new ArrayList<>();

        List<T> lst2 = new ArrayList<T>();
        Iterator<T> it = selection();


        while (it.hasNext()) {
            T elem = it.next();
            if (feasibility(lst2, elem))
                assign(lst2, elem);


            if (solution(lst2)) {
                List<T> lst3 = new ArrayList<T>(lst2);//copy
                finalRes.add(0, lst3);
            }


        }

        return finalRes.get(0);
    }
}
