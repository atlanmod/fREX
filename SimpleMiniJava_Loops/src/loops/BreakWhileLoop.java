package loops;
 
class BreakWhileLoop {
	
	public int whileMethod(){
		int i = 0;
		int j = 5;
		while (i<j) {
			i = i +1;
		}
		return i;
	}
	
  public static void main(String[] args) {
	  System.out.println(new BreakWhileLoop().whileMethod()); //returns 5
  }
}