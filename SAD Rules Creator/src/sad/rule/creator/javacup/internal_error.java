package sad.rule.creator.javacup;

/** Exception subclass for reporting internal errors in JavaCup. */
public class internal_error extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Constructor with a message */
	public internal_error(final String msg) {
		super(msg);
	}

	/** Method called to do a forced error exit on an internal error
	 for cases when we can't actually throw the exception.  */
	public void crash() {
		System.err.println("JavaCUP Fatal Internal Error Detected");
		System.err.println(this.getMessage());
		this.printStackTrace();
		System.exit(-1);
	}
}
