package hw7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BuildStudDB {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rset = null;
	private PreparedStatement pStmt = null;
	private Map<String, Integer> genresMap = new LinkedHashMap<>();
	private List<Movie> movieList = MovieReader.getMovies("movies.dat", "::", "\\|");
	private List<Rating> ratingList = RatingReader.getRatings("ratings.dat", "::");
	private List<User> userList = UserReader.getUsers("users.dat", "::");

	public static void main(String[] args) {
		BuildStudDB hw7 = new BuildStudDB();
		hw7.connect("mledezm1", "cdm1535695");

		try {
			hw7.processUsers();
			hw7.processMovies();

			hw7.closeAll();
		} catch (SQLException e) {
			System.out.println("SQL ERROR: " + e);
		}
	}

	private void processUsers() throws SQLException {

		String tableName = "users";
		try {
			String dropString = "DROP TABLE " + tableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.print("Building new " + tableName + " table...\n\n");
		String createString = "CREATE TABLE " + tableName + " (user_id NUMBER(4,0) NOT NULL PRIMARY KEY, "
				+ "gender VARCHAR2(10) NOT NULL, age NUMBER(3,0) NOT NULL, occupation NUMBER(2,0) NOT NULL, "
				+ "zip_code VARCHAR2(10) NOT NULL)";
		stmt.executeUpdate(createString);

		System.out.print("Inserting rows in User table...\n\n");
		String insertString = String.format(
				"INSERT INTO %s (user_id, gender, age, occupation, zip_code) VALUES (?, ?, ?, ?, ?)", tableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);
		for (User user : userList) {
			pStmt.setInt(1, user.getUserId());
			pStmt.setString(2, user.getGender());
			pStmt.setInt(3, user.getAge());
			pStmt.setInt(4, user.getOccupation());
			pStmt.setString(5, user.getZipCode());
			pStmt.executeUpdate();
			if (userList.indexOf(user) >= 20)
				break;
		}

		rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		while (rset.next())
			System.out.println(rset.getString("user_id") + " : " + rset.getString("zip_code"));
	}

	private void processMovies() throws SQLException {
		String tableName = "Titles";
		try {
			String dropString = "DROP TABLE " + tableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.print("Building new " + tableName + " table...\n\n");
		String createString = "CREATE TABLE " + tableName + " (movie_id NUMBER(4,0) NOT NULL PRIMARY KEY, "
				+ "title VARCHAR2(150) NOT NULL)";
		stmt.executeUpdate(createString);

		System.out.print("Inserting rows in Movies table...\n\n");
		String insertString = String.format("INSERT INTO %s (movie_id, title) VALUES (?, ?)", tableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);
		for (Movie movie : movieList) {
			pStmt.setInt(1, movie.getMovieId());
			pStmt.setString(2, movie.getTitle());
			pStmt.executeUpdate();
			if (movieList.indexOf(movie) >= 20)
				break;
		}

		ResultSet rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		while (rset.next())
			System.out.println(rset.getString("movie_id") + " : " + rset.getString("title"));

		processGenres();
		processMovieGenres();
	}

	private void processGenres() throws SQLException {
		genresMap.put("Action", genresMap.size() + 1);
		genresMap.put("Adventure", genresMap.size() + 1);
		genresMap.put("Animation", genresMap.size() + 1);
		genresMap.put("Children's", genresMap.size() + 1);
		genresMap.put("Comedy", genresMap.size() + 1);
		genresMap.put("Crime", genresMap.size() + 1);
		genresMap.put("Documentary", genresMap.size() + 1);
		genresMap.put("Drama", genresMap.size() + 1);
		genresMap.put("Fantasy", genresMap.size() + 1);
		genresMap.put("Film-Noir", genresMap.size() + 1);
		genresMap.put("Horror", genresMap.size() + 1);
		genresMap.put("Musical", genresMap.size() + 1);
		genresMap.put("Mystery", genresMap.size() + 1);
		genresMap.put("Romance", genresMap.size() + 1);
		genresMap.put("Sci-Fi", genresMap.size() + 1);
		genresMap.put("Thriller", genresMap.size() + 1);
		genresMap.put("War", genresMap.size() + 1);
		genresMap.put("Western", genresMap.size() + 1);

		String tableName = "Genres";

		try {
			String dropString = "DROP TABLE " + tableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.print("Building new " + tableName + " table...\n\n");
		String createString = "CREATE TABLE " + tableName + " (genre_id NUMBER(2,0) NOT NULL PRIMARY KEY, "
				+ "name VARCHAR2(25) NOT NULL, CONSTRAINT name_unique UNIQUE (name))";
		stmt.executeUpdate(createString);

		System.out.print("Inserting rows in Genres table...\n\n");
		String insertString = String.format("INSERT INTO %s (genre_id, name) VALUES (?, ?)", tableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);
		for (String genre : genresMap.keySet()) {
			pStmt.setInt(1, genresMap.get(genre));
			pStmt.setString(2, genre);
			pStmt.executeUpdate();
		}

		rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		while (rset.next())
			System.out.println(rset.getString("genre_id") + " : " + rset.getString("name"));
	}

	private void processMovieGenres() throws SQLException {
		String tableName = "GenresPerMovie";

		try {
			String dropString = "DROP TABLE " + tableName + " CASCADE CONSTRAINTS";
			stmt.executeUpdate(dropString);
		} catch (SQLException se) {
			/* do nothing (table doesn't exist) */
		}

		System.out.print("Building new " + tableName + " table...\n\n");
		String createString = "CREATE TABLE " + tableName + " (movie_id NUMBER(4,0) NOT NULL, "
				+ "genre_id NUMBER(2,0) NOT NULL, CONSTRAINT pk_gpm PRIMARY KEY (movie_id, genre_id), "
				+ "CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES Titles(movie_id), "
				+ "CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES Genres(genre_id))";
		stmt.executeUpdate(createString);

		System.out.print("Inserting rows in " + tableName + " table...\n\n");
		String insertString = String.format("INSERT INTO %s (movie_id, genre_id) VALUES (?, ?)", tableName);
		PreparedStatement pStmt = conn.prepareStatement(insertString);
		for (Movie movie : movieList) {
			String[] genres = movie.getGenres();
			System.out.print("");
			for (int i = 0; i < movie.getGenres().length; i++) {
				String genre = genres[i];
				// System.out.println(genre);

				if (genresMap.containsKey(genre)) {
					pStmt.setInt(1, movie.getMovieId());
					pStmt.setInt(2, genresMap.get(genre));
					pStmt.executeUpdate();
				} else
					System.err.println("Genre not recognized: " + genre);
			}
			if (movieList.indexOf(movie) >= 20)
				break;
		}

		ResultSet rset = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ROWNUM <= 10");
		while (rset.next())
			System.out.println(rset.getString("movie_id") + " : " + rset.getString("genre_id"));
	}

	private void connect(String userName, String password) {
		System.out.print("\nLoading JDBC driver...\n\n");
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		}

		try {
			System.out.print("Connecting to DEF database...\n\n");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@140.192.30.237:1521:def", "mledezm1", "cdm1535695");
			System.out.println("Connected to database DEF...");
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
			System.exit(1);
		}
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
