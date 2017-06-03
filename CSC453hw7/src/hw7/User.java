package hw7;
public class User {
	private String userId;
	private String gender;
	private int age;
	private int occupation;
	private String zipCode;

	public User(String userId, String gender, int age, int occupation, String zipCode) {
		super();
		this.userId = userId;
		this.gender = gender;
		this.age = age;
		this.occupation = occupation;
		this.zipCode = zipCode;
	}

	public String getUserId() {
		return userId;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public int getOccupation() {
		return occupation;
	}

	public String getZipCode() {
		return zipCode;
	}
}