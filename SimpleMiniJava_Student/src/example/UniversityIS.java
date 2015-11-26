package example;

public class UniversityIS {
	
	public Student[] students = new Student[10];
	private int i = 0;
	
	public Student createStudent(Data data) {
		Student student = new Student();
		student.initialize(data.getDetails());
		students[i++] = student;
		return student;
	}
}