package integeraddition;

public class MainClass {
	
	public int sum(){
		 int i = 1;
	     int j = 2; 
	     return i + j; 
	}
	
   public static void main(String[] args) {
	   System.out.println(new MainClass().sum()); //returns 3
   }
}