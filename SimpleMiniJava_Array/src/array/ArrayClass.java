package array;


public class ArrayClass {
	int[] array;
	public int access(int index){
		array = new int[2];
		array[0] = 3;
		array[1] = 4;
		return array[index];
	}

}
