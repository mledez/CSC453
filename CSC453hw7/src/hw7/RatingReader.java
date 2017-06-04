package hw7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RatingReader {

	public static List<Rating> getRatings(String fileName, String separator) {
		String line = "";
		InputStream is = RatingReader.class.getResourceAsStream("/res/" + fileName);
		List<Rating> ratingList = null;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			ratingList = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] rating = line.split(separator);
				ratingList.add(new Rating(Integer.parseInt(rating[0]), Integer.parseInt(rating[1]),
						Integer.parseInt(rating[2]), Integer.parseInt(rating[3])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ratingList;
	}
}
