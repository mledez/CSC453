package hw7;

public class User {
	private int userId;
	private String gender;
	private String age;
	private String occupation;
	private String zipCode;

	public User(int userId, String gender, String age, String occupation, String zipCode) {
		super();
		this.userId = userId;
		this.gender = gender;
		this.age = age;
		this.occupation = occupation;
		this.zipCode = zipCode;
	}

	public int getUserId() {
		return userId;
	}

	public String getGender() {
		return gender;
	}

	public String getAge() {
		return age;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getZipCode() {
		return zipCode;
	}
}
