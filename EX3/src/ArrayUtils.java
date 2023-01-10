import java.util.Arrays;


public class ArrayUtils {

	public static int[][] transposeMatrix(int[][] m) {
		int[][] too = {};
		int[][] empty = {{}};
		if (Arrays.deepEquals(m, empty) || Arrays.deepEquals(m, too))
				return m;

		int row = m.length;
		int column = (m[0]).length;
		if (column == 0){
			return m;}
		int[][] matrix = new int[column][row]; // void matrix to be
		for (int i = 0; i < column; i++){
			for (int j = 0; j<row; j++){
				matrix[i][j] = m[j][i];
			}
		}
		return matrix;
	}

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {
		if ((direction != 'L') && (direction != 'R')){
			return array;}
		int[] arrayHelper = new int[array.length];
		move = move % (array.length);// check if its working for negative numbers
		if (move<0){
			if (direction == 'R')
				direction = 'L';
			else
				direction = 'R';
			move = move*-1;
		}
		if (direction == 'R'){
		for (int i = 0; i < move; i++) {
			arrayHelper[(i + 1) % array.length] = array[i];
		}
		}
		if (direction == 'L') {
			for (int i = 0; i < move; i++) {
				if (i == 0) {
					arrayHelper[array.length - 1] = array[i];
				} else {
					arrayHelper[i - 1] = array[i];
				}
			}
		}
		array = arrayHelper;
		return array;

	}



	public static int alternateSum(int[] array) {
		int[] empty = {};
		if (Arrays.equals(array, empty))
			return 0;
		int n = array.length;
		int maxTotal = array[0];
		int midSum;
		int rowMaxSum;

		for (int i=0; i < n; i++) {
			rowMaxSum = array[i];
			midSum = array[i];
			for (int j = i+1; j < n; j++) {

				if ((j-i) % 2 == 1) {
					midSum -= array[j];
					if (midSum > rowMaxSum)
						rowMaxSum = midSum;

				} else {
					midSum += array[j];
					if (midSum > rowMaxSum)
						rowMaxSum = midSum;
				}
			}
			if (maxTotal < rowMaxSum)
				maxTotal = rowMaxSum;
		}
			return maxTotal;
	}

	public static int[][] matMulti(int[][] matA, int[][] matB){
		int n = matA.length;
		int row = n;
		int column = n;
		int[][] matC = new int[n][n];
		for (int q = 0; q<n; q++){
			for (int l = 0; l<n; l++){
				for (int w = 0; w<n; w++){
					matC[q][l] += matA[q][w] * matB[w][l];
				}
			}

		}
		return matC;
	}


	public static int findPath(int[][] m, int i, int j, int k) {

		int[][] mCopy = new int[m.length][m.length];

		for (int z = 1; z<k; z++){
			mCopy = matMulti(m,m);
		}

		if (mCopy[i][j] != 0)
			return 1;
		return 0;
	}

}
