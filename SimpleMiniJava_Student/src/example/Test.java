package example;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Data d = new Data();
		d.setDetails("Homer Simpson");
		
		UniversityIS uni = new UniversityIS();
		uni.createStudent(d);
		uni.createStudent(d);
		
		System.out.println(uni.students[0].name);
		System.out.println(uni.students[1].name);
	}

}
