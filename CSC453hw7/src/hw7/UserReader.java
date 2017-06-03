package hw7;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserReader {

	public List<User> getUsers(String fileName, String cvsSplitBy) {
		String line = "";
		InputStream is = UserReader.class.getResourceAsStream("/res/" + fileName);
		List<User> userList = null;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			userList = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] user = line.split(cvsSplitBy);
				userList.add(new User(user[0], user[1], Integer.parseInt(user[2]), Integer.parseInt(user[3]), user[4]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;
	}
}
