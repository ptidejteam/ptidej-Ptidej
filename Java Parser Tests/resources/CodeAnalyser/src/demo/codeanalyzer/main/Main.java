package demo.codeanalyzer.main;

import demo.codeanalyzer.processor.CodeAnalyzerController;

/**
 * The main class to verify java files using custom annotation processor. The
 * files to be verified can be supplied to this class as comma-separated
 * argument.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class Main {

    /**
     * The main method accepts the file(s) for verification. Multiple files can
     * be verified by passing the absolute path of the files as comma-separated
     * argument. This method invokes the custom annotation processor to process
     * the annnotations in the supplied file(s). Verification results will be
     * printed on to the console.
     * 
     * @param args
     *            The java source files to be verified.
     */
    public static void main(String[] args) {

        try {

            if (args.length < 1 || args[0] == null ||
                    args[0].trim().length() <= 0 ) {
				System.out.println("Please provide the java source file(s) "
						+ "to be verified as argument");                
                System.out.println("Usage: java Main {<comma separated list of source files>}");
                System.out.println("Exiting from the program");
                System.exit(0);
            } else {
                CodeAnalyzerController controller = new CodeAnalyzerController();
				controller.invokeProcessor(args[0]);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
