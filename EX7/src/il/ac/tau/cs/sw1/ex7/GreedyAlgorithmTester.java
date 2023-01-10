package il.ac.tau.cs.sw1.ex7;
import java.util.*;

import static java.util.Arrays.*;

public class GreedyAlgorithmTester {
    public static void main(String[] args){
        coinsTest();
        fksTest();
        mstTest();
    }


    public static void coinsTest(){
        System.out.println((new Coins(1)).greedyAlgorithm().equals(asList(1)));
        System.out.println((new Coins(19)).greedyAlgorithm().equals(asList(10,5,2,2)));
        System.out.println((new Coins(30)).greedyAlgorithm().equals(asList(10,10,10)));
        System.out.println((new Coins(36)).greedyAlgorithm().equals(asList(10,10,10,5,1)));
    }
    public static void fksTest(){
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(10,60);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(20,100);
        FractionalKnapSack.Item s3 = new FractionalKnapSack.Item(30,120);
        FractionalKnapSack s = new FractionalKnapSack(50, asList(s1,s3,s2));
        System.out.println(s.greedyAlgorithm().toString().equals("[{weight=10.0, value=60.0}, {weight=20.0, value=100.0}, {weight=20.0, value=120.0}]"));
    }
    public static void mstTest(){
        Graph.Edge s1 = new Graph.Edge(0,1,1);//
        Graph.Edge s2 = new Graph.Edge(1,2,4);
        Graph.Edge s3 = new Graph.Edge(2,3,1);//
        Graph.Edge s4 = new Graph.Edge(2,4,3);//
        Graph.Edge s5 = new Graph.Edge(3,4,3);
        Graph.Edge s6 = new Graph.Edge(0,4,4);//
        Graph s = new Graph(4, asList(s1,s2,s3,s4,s5,s6));//5 nodes , 6 edges
        List<Graph.Edge> lst = s.greedyAlgorithm();
        List<Graph.Edge> sol1 = asList(s1,s3,s5,s6);
        List<Graph.Edge> sol2 = asList(s1,s3,s4,s2);
        List<Graph.Edge> sol3 = asList(s1,s3,s4,s6);
        List<Graph.Edge> sol4 = asList(s1,s3,s5,s2);
        if (lst.equals(sol3))
            System.out.println("true");
        else if (lst.equals(sol1) || lst.equals(sol2) || lst.equals(sol4))
            System.out.println("false (This is an MST, but not in the right order)");
        else
            System.out.println("false");
    }
}
