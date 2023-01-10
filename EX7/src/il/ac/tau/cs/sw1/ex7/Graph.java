package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,...,n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }



    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }


    public static class EdgeComparator implements Comparator<Edge>{


        @Override
        public int compare(Edge e1, Edge e2) {
            if (!(e1.weight == e2.weight))//weight not equal
                return Double.compare(e1.weight, e2.weight);

            if (!(e1.node1 == e2.node1))//node1 not equal
                return Integer.compare(e1.node1, e2.node1);

            return Integer.compare(e1.node2, e2.node2);
        }
    }


    @Override
    public Iterator<Edge> selection() {
        Collections.sort(lst,new EdgeComparator());
        return lst.iterator();//TODO check if lst NOT CANDIDATE
    }



    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        int nodeOne = element.node1;
        int nodeTwo = element.node2;
        //Set<Integer> neighbors1 = new HashSet<>();
        //Set<Integer> neighbors2 = new HashSet<>();
        List<Integer> neighbors1 = new ArrayList<>();//SET
        List<Integer> neighbors2 = new ArrayList<>();

        for (Edge e : candidates_lst) {
            if (nodeOne == e.node1)
                neighbors1.add(e.node2);
            if (nodeOne == e.node2)
                neighbors1.add(e.node1);

            if (nodeTwo == e.node1)
                neighbors2.add(e.node2);
            if (nodeTwo == e.node2)
                neighbors2.add(e.node1);
        }
        for (Integer neighbor : neighbors1)
            if (neighbors2.contains(neighbor))
                return false;

        return true;
    }



    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        if (candidates_lst.size() != n)
            return false;




        Set<Integer> nodeSetSol = new HashSet<>();
        for (Edge cand : candidates_lst){//kashir
            nodeSetSol.add(cand.node1);
            nodeSetSol.add(cand.node2);
        }

        return nodeSetSol.size() == n+1;
    }
}
