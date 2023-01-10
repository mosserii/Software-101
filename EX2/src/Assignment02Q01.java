public class Assignment02Q01 {

	public static void main(String[] args) {
		for(String s:args){
			char c = s.charAt(0);
			int ascii = (int) c;
			if (ascii % 2 == 0 && ascii % 3 == 0){
				System.out.println(c);
			}

		}


	}

}
