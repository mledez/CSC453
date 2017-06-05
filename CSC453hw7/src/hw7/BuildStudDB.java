package hw7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BuildStudDB {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rset = null;
	private PreparedStatement pStmt = null;
	private Map<String, Integer> genreMap = new LinkedHashMap<>();
	private Map<String, String> ageMap = new LinkedHashMap<>();
	private Map<String, String> userGenderMap = new LinkedHashMap<>();
	private Map<String, String> occupationMap = new LinkedHashMap<>();
	private List<Movie> movieList = MovieReader.getMovies("movies.dat", "::", "\\|");
	private List<Rating> ratingList = RatingReader.getRatings("ratings.dat", "::");
	private List<User> userList = UserReader.getUsers("users.dat", "::");
	private int limitRatings = 10000;
	private static String userTableName = "movieUser";
	private static String movieTableName = "movie";
	private static String genreTableName = "genre";
	private static String movieGenreTableName = "movieGenre";
	private static String ratingTableName = "rating";
	private static String occupationTableName = "occupation";
	private static String ageTableName = "age";
	private static String userGenderTableName = "userGender";

	public static void main(String[] args) {
		BuildStudDB hw7 = new BuildStudDB();
		try {
			hw7.connect("mledezm1", "cdm1535695");

			hw7.processAge();
			hw7.processOccupation();
			hw7.processUserGender();
			hw7.processGenre();

			hw7.processUser();
			hw7.processMovie();
			hw7.processMovieGenre();
			hw7.processRating();

			hw7.printTop(5, ageTableName);
			hw7.printTop(5, occupationTableName);
			hw7.printTop(5, userGenderTableName);
			hw7.printTop(5, genreTableName);
			hw7.printTop(5, movieTableName);
			hw7.printTop(5, movieGenreTableName);
			hw7.printTop(5, ratingTableName);

			hw7.closeAll();
		} catch (SQLException e) {
			System.out.println("SQL ERROR: " + e);
		}
	}

	private void processAge() throws SQLException {

		ageMap.put("1", "Under 18");
		ageMap.put("18", "18-24");
		ageMap.put("25", "25-34");
		ageMap.put("35", "35-44");
		ageMap.put("45", "45-49");
		ageMap.put("50", "50-55");
		ageMap.put("56", "56+");

		try {
			String dropString = "DROP TABLE " + ageTableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + ageTableName + "' table...");
		String createString = "CREATE TABLE " + ageTableName + " (age_id VARCHAR2(2) NOT NULL PRIMARY KEY, "
				+ "range VARCHAR2(10) NOT NULL, CONSTRAINT age_unique UNIQUE (range))";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + ageTableName + " table...");
		String insertString = String.format("INSERT INTO %s (age_id, range) VALUES (?, ?)", ageTableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);

		for (String genre : ageMap.keySet()) {
			pStmt.setString(1, genre);
			pStmt.setString(2, ageMap.get(genre));
			// pStmt.executeUpdate();
			pStmt.addBatch();
		}
		pStmt.executeBatch();
		System.out.println("A total of " + ageMap.size() + " rows were added\n");

		// rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(rset.getString("genre_id") + " : " + rset.getString("name"));
	}

	private void processUserGender() throws SQLException {

		userGenderMap.put("M", "Male");
		userGenderMap.put("F", "Female");

		try {
			String dropString = "DROP TABLE " + userGenderTableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + userGenderTableName + "' table...");
		String createString = "CREATE TABLE " + userGenderTableName
				+ " (userGender_id VARCHAR2(2) NOT NULL PRIMARY KEY, "
				+ "userGender VARCHAR2(10) NOT NULL, CONSTRAINT userGender_unique UNIQUE (userGender))";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + userGenderTableName + " table...");
		String insertString = String.format("INSERT INTO %s (userGender_id, userGender) VALUES (?, ?)",
				userGenderTableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);

		for (String userGender : userGenderMap.keySet()) {
			pStmt.setString(1, userGender);
			pStmt.setString(2, userGenderMap.get(userGender));
			// pStmt.executeUpdate();
			pStmt.addBatch();
		}
		pStmt.executeBatch();
		System.out.println("A total of " + userGenderMap.size() + " rows were added\n");

		// rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(rset.getString("genre_id") + " : " + rset.getString("name"));
	}

	private void processOccupation() throws SQLException {
		occupationMap.put("0", "other or not specified");
		occupationMap.put("1", "academic/educator");
		occupationMap.put("2", "artist");
		occupationMap.put("3", "clerical/admin");
		occupationMap.put("4", "college/grad student");
		occupationMap.put("5", "customer service");
		occupationMap.put("6", "doctor/health care");
		occupationMap.put("7", "executive/managerial");
		occupationMap.put("8", "farmer");
		occupationMap.put("9", "homemaker");
		occupationMap.put("10", "K-12 student");
		occupationMap.put("11", "lawyer");
		occupationMap.put("12", "programmer");
		occupationMap.put("13", "retired");
		occupationMap.put("14", "sales/marketing");
		occupationMap.put("15", "scientist");
		occupationMap.put("16", "self-employed");
		occupationMap.put("17", "technician/engineer");
		occupationMap.put("18", "tradesman/craftsman");
		occupationMap.put("19", "unemployed");
		occupationMap.put("20", "writer");

		try {
			String dropString = "DROP TABLE " + occupationTableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + occupationTableName + "' table...");
		String createString = "CREATE TABLE " + occupationTableName + " (occup_id VARCHAR2(2) NOT NULL PRIMARY KEY, "
				+ "occup VARCHAR2(40) NOT NULL, CONSTRAINT occup_unique UNIQUE (occup))";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + occupationTableName + " table...");
		String insertString = String.format("INSERT INTO %s (occup_id, occup) VALUES (?, ?)", occupationTableName);
		pStmt = conn.prepareStatement(insertString);

		for (String occup_id : occupationMap.keySet()) {
			pStmt.setString(1, occup_id);
			pStmt.setString(2, occupationMap.get(occup_id));
			// pStmt.executeUpdate();
			pStmt.addBatch();
		}
		pStmt.executeBatch();
		System.out.println("A total of " + occupationMap.size() + " rows were added\n");

		// rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(rset.getString("genre_id") + " : " + rset.getString("name"));
	}

	private void processGenre() throws SQLException {
		genreMap.put("Action", genreMap.size() + 1);
		genreMap.put("Adventure", genreMap.size() + 1);
		genreMap.put("Animation", genreMap.size() + 1);
		genreMap.put("Children's", genreMap.size() + 1);
		genreMap.put("Comedy", genreMap.size() + 1);
		genreMap.put("Crime", genreMap.size() + 1);
		genreMap.put("Documentary", genreMap.size() + 1);
		genreMap.put("Drama", genreMap.size() + 1);
		genreMap.put("Fantasy", genreMap.size() + 1);
		genreMap.put("Film-Noir", genreMap.size() + 1);
		genreMap.put("Horror", genreMap.size() + 1);
		genreMap.put("Musical", genreMap.size() + 1);
		genreMap.put("Mystery", genreMap.size() + 1);
		genreMap.put("Romance", genreMap.size() + 1);
		genreMap.put("Sci-Fi", genreMap.size() + 1);
		genreMap.put("Thriller", genreMap.size() + 1);
		genreMap.put("War", genreMap.size() + 1);
		genreMap.put("Western", genreMap.size() + 1);

		try {
			String dropString = "DROP TABLE " + genreTableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + genreTableName + "' table...");
		String createString = "CREATE TABLE " + genreTableName + " (genre_id NUMBER(2,0) NOT NULL PRIMARY KEY, "
				+ "name VARCHAR2(25) NOT NULL, CONSTRAINT name_unique UNIQUE (name))";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + genreTableName + " table...");
		String insertString = String.format("INSERT INTO %s (genre_id, name) VALUES (?, ?)", genreTableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);
		int index = 0;
		for (String genre : genreMap.keySet()) {
			index = genreMap.get(genre);
			pStmt.setInt(1, index);
			pStmt.setString(2, genre);
			// pStmt.executeUpdate();
			pStmt.addBatch();

			if (index % 500 == 0 && index != 1)
				System.out.print(String.format("%d rows added\r", index));
		}
		pStmt.executeBatch();
		System.out.println("A total of " + index + " rows were added\n");

		// rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(rset.getString("genre_id") + " : " + rset.getString("name"));
	}

	private void processUser() throws SQLException {
		try {
			String dropString = "DROP TABLE " + userTableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + userTableName + "' table...");
		String createString = "CREATE TABLE " + userTableName + " (user_id NUMBER(4,0) NOT NULL PRIMARY KEY, "
				+ "userGender_id VARCHAR2(10) NOT NULL, age_id VARCHAR(2) NOT NULL, occupation_id VARCHAR(2) NOT NULL, "
				+ "zip_code VARCHAR2(10) NOT NULL, " + "CONSTRAINT fk_age FOREIGN KEY (age_id) REFERENCES "
				+ ageTableName + "(age_id), " + "CONSTRAINT fk_occup FOREIGN KEY (occupation_id) REFERENCES "
				+ occupationTableName + "(occup_id), CONSTRAINT fk_userGender FOREIGN KEY (userGender_id) REFERENCES "
				+ userGenderTableName + "(userGender_id))";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + userTableName + " table...");
		String insertString = String.format(
				"INSERT INTO %s (user_id, userGender_id, age_id, occupation_id, zip_code) VALUES (?, ?, ?, ?, ?)",
				userTableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);
		int index = 0;
		for (User user : userList) {
			pStmt.setInt(1, user.getUserId());
			pStmt.setString(2, user.getGender());
			pStmt.setString(3, user.getAge());
			pStmt.setString(4, user.getOccupation());
			pStmt.setString(5, user.getZipCode());
			pStmt.addBatch();
			// pStmt.executeUpdate();
			index = userList.indexOf(user) + 1;
			if (index % 500 == 0 && index != 1)
				System.out.print(String.format("%d rows added\r", index));
		}
		pStmt.executeBatch();
		System.out.println("A total of " + index + " rows were added\n");
		// rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(rset.getString("user_id") + " : " + rset.getString("zip_code"));
	}

	private void processMovie() throws SQLException {
		try {
			String dropString = "DROP TABLE " + movieTableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + movieTableName + "' table...");
		String createString = "CREATE TABLE " + movieTableName + " (movie_id NUMBER(4,0) NOT NULL PRIMARY KEY, "
				+ "title VARCHAR2(150) NOT NULL)";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + movieTableName + " table...");
		String insertString = String.format("INSERT INTO %s (movie_id, title) VALUES (?, ?)", movieTableName);
		pStmt = conn.prepareStatement(insertString);
		int index = 0;
		for (Movie movie : movieList) {
			pStmt.setInt(1, movie.getMovieId());
			pStmt.setString(2, movie.getTitle());
			pStmt.addBatch();
			// pStmt.executeUpdate();
			// if (movieList.indexOf(movie) >= 20)
			// break;
			index = movieList.indexOf(movie) + 1;
			if (index % 500 == 0 && index != 1)
				System.out.print(String.format("%d rows added\r", index));
		}
		pStmt.executeBatch();
		System.out.println("A total of " + index + " rows were added\n");

		// rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(rset.getString("movie_id") + " : " + rset.getString("title"));
	}

	private void processMovieGenre() throws SQLException {
		try {
			String dropString = "DROP TABLE " + movieGenreTableName;
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + movieGenreTableName + "' table...");
		String createString = "CREATE TABLE " + movieGenreTableName + " (movie_id NUMBER(4,0) NOT NULL, "
				+ "genre_id NUMBER(2,0) NOT NULL, CONSTRAINT pk_gpm PRIMARY KEY (movie_id, genre_id), "
				+ "CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES " + movieTableName + "(movie_id), "
				+ "CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES " + genreTableName + "(genre_id))";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + movieGenreTableName + " table...");
		String insertString = String.format("INSERT INTO %s (movie_id, genre_id) VALUES (?, ?)", movieGenreTableName);
		pStmt = conn.prepareStatement(insertString);
		int index = 0;
		for (Movie movie : movieList) {
			String[] genres = movie.getGenres();
			System.out.print("");
			for (int i = 0; i < movie.getGenres().length; i++) {
				String genre = genres[i];
				if (genreMap.containsKey(genre)) {
					pStmt.setInt(1, movie.getMovieId());
					pStmt.setInt(2, genreMap.get(genre));
					pStmt.addBatch();
					// pStmt.executeUpdate();
					index++;
				} else
					System.err.println("Genre not recognized: " + genre);
			}
			int secondIndex = movieList.indexOf(movie) + 1;
			if (secondIndex % 500 == 0 && secondIndex != 1)
				System.out.print(String.format("%d rows added\r", index));
		}
		pStmt.executeBatch();
		System.out.println("A total of " + index + " rows were added\n");
		// ResultSet rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(rset.getString("movie_id") + " : " + rset.getString("genre_id"));
	}

	private void processRating() throws SQLException {
		try {
			String dropString = "DROP TABLE " + ratingTableName;
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.println("Building new '" + ratingTableName + "' table...");
		String createString = "CREATE TABLE " + ratingTableName + " (user_id NUMBER(4,0) NOT NULL, "
				+ "movie_id NUMBER(4,0) NOT NULL, rating NUMBER(1,0) NOT NULL,"
				+ "CONSTRAINT pk_ratings PRIMARY KEY (user_id, movie_id), "
				+ "CONSTRAINT fk_user2 FOREIGN KEY (user_id) REFERENCES " + userTableName + "(user_id), "
				+ "CONSTRAINT fk_movie2 FOREIGN KEY (movie_id) REFERENCES " + movieTableName + "(movie_id))";
		stmt.executeUpdate(createString);

		System.out.println("Inserting rows in " + ratingTableName + " table...");
		String insertString = String.format("INSERT INTO %s (user_id, movie_id, rating) VALUES (?, ?, ?)",
				ratingTableName);
		pStmt = conn.prepareStatement(insertString);
		int index = 0;
		for (Rating rating : ratingList) {
			pStmt.setInt(1, rating.getUserId());
			pStmt.setInt(2, rating.getMovieId());
			pStmt.setInt(3, rating.getRating());
			// pStmt.executeUpdate();

			pStmt.addBatch();

			index = ratingList.indexOf(rating) + 1;

			if (index % 5000 == 0 && index != 1) {
				pStmt.executeBatch();
				System.out.print(String.format("%d rows added\r", index));
			}

			if (limitRatings != 0 && index == limitRatings)
				break;
		}
		pStmt.executeBatch();
		System.out.println("A total of " + index + " rows were added\n");
		// rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		// while (rset.next())
		// System.out.println(
		// rset.getString("user_id") + " : " + rset.getString("movie_id") + " : " + rset.getString("rating"));
	}

	private void connect(String userName, String password) {
		System.out.println("Loading JDBC driver...");
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		}

		try {
			System.out.println("Connecting to DEF database...");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@140.192.30.237:1521:def", "mledezm1", "cdm1535695");
			System.out.println("Connected to database DEF...\n");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException se) {
			System.out.println(se);
			System.exit(1);
		}
	}

	private void printTop(int numRows, String tableName) throws SQLException {
		rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= " + numRows);
		String report = "";
		Map<Integer, Integer> max = new HashMap<>();
		ResultSetMetaData rsmd = rset.getMetaData();
		int columnCount = rsmd.getColumnCount();
		int currentMax = 0;
		while (rset.next()) {
			for (int j = 1; j <= columnCount; j++) {
				currentMax = Integer.max(rset.getString(j).length(), rsmd.getColumnName(j).length());
				if (currentMax > max.getOrDefault(j, 0))
					max.put(j, currentMax);
			}
		}

		for (int i = 1; i <= columnCount; i++)
			report += String.format("%-" + max.get(i) + "s\t", rsmd.getColumnName(i));

		report += "\n";
		rset.beforeFirst();
		while (rset.next()) {
			for (int j = 1; j <= columnCount; j++) {
				report += String.format("%-" + max.get(j) + "s\t", rset.getString(j));
			}
			report += "\n";
		}
		System.out.println(report);
	}

	private void closeAll() throws SQLException {
		if (rset != null)
			rset.close();
		if (pStmt != null)
			pStmt.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
}
