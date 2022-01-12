import java.sql.DriverManager;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			DriverManager.getConnection("jdbc:sqlite:./temp-db").createStatement();
			System.out.println("SUCCESS");
    	} catch (Throwable t) {
			System.out.println("FAILED");
		}
    }
}