package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    double cap1;
    List<Item> lst;
    List<Item> copy = new ArrayList<>();
    int r = 0;


    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class FractionalKnapSackComperator implements Comparator<FractionalKnapSack.Item>{

        @Override
        public int compare(Item o1, Item o2) {
            return -Double.compare(o1.value/ o1.weight , o2.value/ o2.weight);
        }
    }



    public static class Item {//Inner class
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }


        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {
        Collections.sort(lst,new FractionalKnapSackComperator());
        return lst.iterator();
    }



    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {


        Item copyElement = new Item(0.0,0.0);
        copyElement.value = element.value;
        copyElement.weight = element.weight;
        copy.add(copyElement);

        double cap = (double)capacity;
        for (int j = 0; j<candidates_lst.size(); j++)
            cap -= candidates_lst.get(j).weight;
        cap -= element.weight;

       if (cap < 0){
           copyElement.weight = cap + element.weight;
            if (copyElement.weight <= 0)
                return false;
        }
        return true;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
            candidates_lst.add(copy.get(r));
            r++;
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
        double cap = (double)capacity;
        for (Item objet: candidates_lst)
            cap -= objet.weight;
        //lst = copy;
        return cap >= 0;
    }

}
