package hw7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserReader {

	public static List<User> getUsers(String fileName, String separator) {
		String line = "";
		InputStream is = UserReader.class.getResourceAsStream("/res/" + fileName);
		List<User> userList = null;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			userList = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] user = line.split(separator);
				userList.add(new User(Integer.parseInt(user[0]), user[1], user[2], user[3], user[4]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;
	}
}
