import java.util.Arrays;

public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfEven = 0;
		int n = -1;
		// Your code goes here
		n = Integer.parseInt(args[0]);
		long[] fibNumbers = new long[n];//saving places for n fib numbers
		fibNumbers[0] = 1;
		fibNumbers[1] = 1;
		for (int i = 2; i < n; i++){
			fibNumbers[i] = (fibNumbers[i-2] + fibNumbers[i-1]);
			if (fibNumbers[i]%2==0){
				numOfEven++;}
		}
		String[] fib = new String[n];//to strings array
		for (int i = 0; i < n; i++){
			fib[i] = String.valueOf(fibNumbers[i]);
		}
		StringBuilder builder = new StringBuilder();//to string so we can print
		for (int i = 0; i < n; i++) {
			builder.append(fib[i]);
			builder.append(" ");
		}
		String text = builder.toString();
		text = text.substring(0,text.length()-1);// without last space








		System.out.println("The first "+ n +" Fibonacci numbers are:");
		System.out.println(text);
		System.out.println("The number of even numbers is: "+numOfEven);

	}

}