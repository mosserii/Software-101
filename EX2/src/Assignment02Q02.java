public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;

		int n = Integer.parseInt(args[0]);
		double even = 0.0;
		double odd = 0.0;
		for (int j = 0; j < n ; j += 2){ // even indexes
			even = even + (1.0/(2*j+1));
		}
		for (int j = 1; j < n ; j += 2) { // odd indexes
			odd = odd + (1.0/(2*j+1));
		}


		piEstimation = 4 * (even - odd);
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}

}


