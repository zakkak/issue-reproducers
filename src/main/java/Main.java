public class Main {

	public static void main(String[] args) {
		try {
			@SuppressWarnings({"nullness"})
			sun.security.krb5.Credentials credentials =
				sun.security.krb5.Credentials.acquireTGTFromCache(null, null);
			System.out.println(credentials != null);
		  } catch (Exception ex) {
			System.out.println("FAILED");
		  }
		;
	}
}
