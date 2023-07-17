package sad.rule.creator.javacup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

/** This class serves as the main driver for the JavaCup system.
 *  It accepts user options and coordinates overall control flow.
 *  The main flow of control includes the following activities: 
 *  <ul>
 *    <li> Parse user supplied arguments and options.
 *    <li> Open output files.
 *    <li> Parse the specification from standard input.
 *    <li> Check for unused terminals, non-terminals, and productions.
 *    <li> Build the state machine, tables, etc.
 *    <li> Output the generated code.
 *    <li> Close output files.
 *    <li> Print a summary if requested.
 *  </ul>
 *
 *  Options to the main program include: <dl>
 *   <dt> -package name  
 *   <dd> specify package generated classes go in [default none]
 *   <dt> -parser name   
 *   <dd> specify parser class name [default "parser"]
 *   <dt> -symbols name  
 *   <dd> specify name for symbol constant class [default "sym"]
 *   <dt> -interface
 *   <dd> emit symbol constant <i>interface</i>, rather than class
 *   <dt> -nonterms      
 *   <dd> put non terminals in symbol constant class
 *   <dt> -expect #      
 *   <dd> number of conflicts expected/allowed [default 0]
 *   <dt> -compact_red   
 *   <dd> compact tables by defaulting to most frequent reduce
 *   <dt> -nowarn        
 *   <dd> don't warn about useless productions, etc.
 *   <dt> -nosummary     
 *   <dd> don't print the usual summary of parse states, etc.
 *   <dt> -progress      
 *   <dd> print messages to indicate progress of the system
 *   <dt> -time          
 *   <dd> print time usage summary
 *   <dt> -dump_grammar  
 *   <dd> produce a dump of the symbols and grammar
 *   <dt> -dump_states   
 *   <dd> produce a dump of parse state machine
 *   <dt> -dump_tables   
 *   <dd> produce a dump of the parse tables
 *   <dt> -dump          
 *   <dd> produce a dump of all of the above
 *   <dt> -debug         
 *   <dd> turn on debugging messages within JavaCup 
 *   <dt> -nopositions
 *   <dd> don't generate the positions code
 *   <dt> -noscanner
 *   <dd> don't refer to java_cup.runtime.Scanner in the parser
 *        (for compatibility with old runtimes)
 *   <dt> -version
 *   <dd> print version information for JavaCUP and halt.
 *   </dl>
 *
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */

public class Main {

	/*-------------------------*/
	/* Options set by the user */
	/*-------------------------*/
	/** User option -- do we print progress messages. */
	protected static boolean print_progress = true;

	/** User option -- do we produce a dump of the state machine */
	protected static boolean opt_dump_states = false;
	/** User option -- do we produce a dump of the parse tables */
	protected static boolean opt_dump_tables = false;
	/** User option -- do we produce a dump of the grammar */
	protected static boolean opt_dump_grammar = false;
	/** User option -- do we show timing information as a part of the summary */
	protected static boolean opt_show_timing = false;
	/** User option -- do we run produce extra debugging messages */
	protected static boolean opt_do_debug = false;
	/** User option -- do we compact tables by making most common reduce the 
	 default action */
	protected static boolean opt_compact_red = false;
	/** User option -- should we include non terminal symbol numbers in the 
	 symbol constant class. */
	protected static boolean include_non_terms = false;
	/** User option -- do not print a summary. */
	protected static boolean no_summary = false;
	/** User option -- number of conflicts to expect */
	protected static int expect_conflicts = 0;
	/* frankf added this 6/18/96 */
	/** User option -- should generator generate code for left/right values? */
	protected static boolean lr_values = true;

	/** User option -- should symbols be put in a class or an interface? [CSA]*/
	protected static boolean sym_interface = false;

	/** User option -- should generator suppress references to
	 *  java_cup.runtime.Scanner for compatibility with old runtimes? */
	protected static boolean suppress_scanner = false;

	/*----------------------------------------------------------------------*/
	/* Timing data (not all of these time intervals are mutually exclusive) */
	/*----------------------------------------------------------------------*/
	/** Timing data -- when did we start */
	protected static long start_time = 0;

	/** Timing data -- when did we end preliminaries */
	protected static long prelim_end = 0;
	/** Timing data -- when did we end parsing */
	protected static long parse_end = 0;
	/** Timing data -- when did we end checking */
	protected static long check_end = 0;
	/** Timing data -- when did we end dumping */
	protected static long dump_end = 0;
	/** Timing data -- when did we end state and table building */
	protected static long build_end = 0;
	/** Timing data -- when did we end nullability calculation */
	protected static long nullability_end = 0;
	/** Timing data -- when did we end first set calculation */
	protected static long first_end = 0;
	/** Timing data -- when did we end state machine construction */
	protected static long machine_end = 0;
	/** Timing data -- when did we end table construction */
	protected static long table_end = 0;
	/** Timing data -- when did we end checking for non-reduced productions */
	protected static long reduce_check_end = 0;
	/** Timing data -- when did we finish emitting code */
	protected static long emit_end = 0;
	/** Timing data -- when were we completely done */
	protected static long final_time = 0;
	/** Input file.  This is a buffered version of System.in. */
	protected static BufferedInputStream input_file;

	/* Additional timing information is also collected in emit */

	/*-----------------------------------------------------------*/
	/*--- Main Program ------------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Output file for the parser class. */
	protected static PrintWriter parser_class_file;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Output file for the symbol constant class. */
	protected static PrintWriter symbol_class_file;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Start state in the overall state machine. */
	protected static lalr_state start_state;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/*-------*/
	/* Files */
	/*-------*/

	/** Resulting parse action table. */
	protected static parse_action_table action_table;

	/** Resulting reduce-goto table. */
	protected static parse_reduce_table reduce_table;

	/** Build the (internal) parser from the previously parsed specification.
	 *  This includes:<ul>
	 *    <li> Computing nullability of non-terminals.
	 *    <li> Computing first sets of non-terminals and productions.
	 *    <li> Building the viable prefix recognizer machine.
	 *    <li> Filling in the (internal) parse tables.
	 *    <li> Checking for unreduced productions.
	 *  </ul>
	 */
	protected static void build_parser() throws internal_error {
		/* compute nullability of all non terminals */
		if (Main.opt_do_debug || Main.print_progress) {
			System.out.println("  Computing non-terminal nullability...");
		}
		non_terminal.compute_nullability();

		Main.nullability_end = System.currentTimeMillis();

		/* compute first sets of all non terminals */
		if (Main.opt_do_debug || Main.print_progress) {
			System.out.println("  Computing first sets...");
		}
		non_terminal.compute_first_sets();

		Main.first_end = System.currentTimeMillis();

		/* build the LR viable prefix recognition machine */
		if (Main.opt_do_debug || Main.print_progress) {
			System.out.println("  Building state machine...");
		}
		Main.start_state = lalr_state.build_machine(emit.start_production);

		Main.machine_end = System.currentTimeMillis();

		/* build the LR parser action and reduce-goto tables */
		if (Main.opt_do_debug || Main.print_progress) {
			System.out.println("  Filling in tables...");
		}
		Main.action_table = new parse_action_table();
		Main.reduce_table = new parse_reduce_table();
		for (final Enumeration st = lalr_state.all(); st.hasMoreElements();) {
			final lalr_state lst = (lalr_state) st.nextElement();
			lst.build_table_entries(Main.action_table, Main.reduce_table);
		}

		Main.table_end = System.currentTimeMillis();

		/* check and warn for non-reduced productions */
		if (Main.opt_do_debug || Main.print_progress) {
			System.out.println("  Checking for non-reduced productions...");
		}
		Main.action_table.check_reductions();

		Main.reduce_check_end = System.currentTimeMillis();

		/* if we have more conflicts than we expected issue a message and die */
		if (emit.num_conflicts > Main.expect_conflicts) {
			System.err.println("*** More conflicts encountered than expected "
					+ "-- parser generation aborted");
			lexer.error_count++; // indicate the problem.
			// we'll die on return, after clean up.
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Check for unused symbols.  Unreduced productions get checked when
	 *  tables are created.
	 */
	protected static void check_unused() {
		terminal term;
		non_terminal nt;

		/* check for unused terminals */
		for (final Enumeration t = terminal.all(); t.hasMoreElements();) {
			term = (terminal) t.nextElement();

			/* don't issue a message for EOF */
			if (term == terminal.EOF) {
				continue;
			}

			/* or error */
			if (term == terminal.error) {
				continue;
			}

			/* is this one unused */
			if (term.use_count() == 0) {
				/* count it and warn if we are doing warnings */
				emit.unused_term++;
				if (!emit.nowarn) {
					System.err.println("Warning: Terminal \"" + term.name()
							+ "\" was declared but never used");
					lexer.warning_count++;
				}
			}
		}

		/* check for unused non terminals */
		for (final Enumeration n = non_terminal.all(); n.hasMoreElements();) {
			nt = (non_terminal) n.nextElement();

			/* is this one unused */
			if (nt.use_count() == 0) {
				/* count and warn if we are doing warnings */
				emit.unused_term++;
				if (!emit.nowarn) {
					System.err.println("Warning: Non terminal \"" + nt.name()
							+ "\" was declared but never used");
					lexer.warning_count++;
				}
			}
		}

	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Close various files used by the system. */
	protected static void close_files() throws java.io.IOException {
		if (Main.input_file != null) {
			Main.input_file.close();
		}
		if (Main.parser_class_file != null) {
			Main.parser_class_file.close();
		}
		if (Main.symbol_class_file != null) {
			Main.symbol_class_file.close();
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce a human readable dump of the grammar. */
	public static void dump_grammar() throws internal_error {
		System.out.println("===== Terminals =====");
		for (int tidx = 0, cnt = 0; tidx < terminal.number(); tidx++, cnt++) {
			System.out.print("[" + tidx + "]" + terminal.find(tidx).name()
					+ " ");
			if ((cnt + 1) % 5 == 0) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.println();

		System.out.println("===== Non terminals =====");
		for (int nidx = 0, cnt = 0; nidx < non_terminal.number(); nidx++, cnt++) {
			System.out.print("[" + nidx + "]" + non_terminal.find(nidx).name()
					+ " ");
			if ((cnt + 1) % 5 == 0) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.println();

		System.out.println("===== Productions =====");
		for (int pidx = 0; pidx < production.number(); pidx++) {
			final production prod = production.find(pidx);
			System.out.print("[" + pidx + "] " + prod.lhs().the_symbol().name()
					+ " ::= ");
			for (int i = 0; i < prod.rhs_length(); i++) {
				if (prod.rhs(i).is_action()) {
					System.out.print("{action} ");
				}
				else {
					System.out.print(((symbol_part) prod.rhs(i))
						.the_symbol()
						.name() + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce a (semi-) human readable dump of the complete viable prefix 
	 *  recognition state machine. 
	 */
	public static void dump_machine() {
		final lalr_state ordered[] = new lalr_state[lalr_state.number()];

		/* put the states in sorted order for a nicer display */
		for (final Enumeration s = lalr_state.all(); s.hasMoreElements();) {
			final lalr_state st = (lalr_state) s.nextElement();
			ordered[st.index()] = st;
		}

		System.out.println("===== Viable Prefix Recognizer =====");
		for (int i = 0; i < lalr_state.number(); i++) {
			if (ordered[i] == Main.start_state) {
				System.out.print("START ");
			}
			System.out.println(ordered[i]);
			System.out.println("-------------------");
		}
	}

	/* . . . . . . . . . . . . . . . . . . . . . . . . .*/
	/* . . Internal Results of Generating the Parser . .*/
	/* . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce a (semi-) human readable dumps of the parse tables */
	public static void dump_tables() {
		System.out.println(Main.action_table);
		System.out.println(Main.reduce_table);
	}

	/** Call the emit routines necessary to write out the generated parser. */
	protected static void emit_parser() throws internal_error {
		emit.symbols(
			Main.symbol_class_file,
			Main.include_non_terms,
			Main.sym_interface);
		emit.parser(
			Main.parser_class_file,
			Main.action_table,
			Main.reduce_table,
			Main.start_state.index(),
			emit.start_production,
			Main.opt_compact_red,
			Main.suppress_scanner);
	}

	/** Emit a long summary message to standard error (System.err) which 
	 *  summarizes what was found in the specification, how many states were
	 *  produced, how many conflicts were found, etc.  A detailed timing 
	 *  summary is also produced if it was requested by the user.
	 * @param output_produced did the system get far enough to generate code.
	 */
	protected static void emit_summary(final boolean output_produced) {
		Main.final_time = System.currentTimeMillis();

		if (Main.no_summary) {
			return;
		}

		System.out.println("------- " + version.title_str
				+ " Parser Generation Summary -------");

		/* error and warning count */
		System.out.println("  " + lexer.error_count + " error"
				+ Main.plural(lexer.error_count) + " and "
				+ lexer.warning_count + " warning"
				+ Main.plural(lexer.warning_count));

		/* basic stats */
		System.out.print("  " + terminal.number() + " terminal"
				+ Main.plural(terminal.number()) + ", ");
		System.out.print(non_terminal.number() + " non-terminal"
				+ Main.plural(non_terminal.number()) + ", and ");
		System.out.println(production.number() + " production"
				+ Main.plural(production.number()) + " declared, ");
		System.out.println("  producing " + lalr_state.number()
				+ " unique parse states.");

		/* unused symbols */
		System.out.println("  " + emit.unused_term + " terminal"
				+ Main.plural(emit.unused_term) + " declared but not used.");
		System.out.println("  " + emit.unused_non_term + " non-terminal"
				+ Main.plural(emit.unused_term) + " declared but not used.");

		/* productions that didn't reduce */
		System.out.println("  " + emit.not_reduced + " production"
				+ Main.plural(emit.not_reduced) + " never reduced.");

		/* conflicts */
		System.out.println("  " + emit.num_conflicts + " conflict"
				+ Main.plural(emit.num_conflicts) + " detected" + " ("
				+ Main.expect_conflicts + " expected).");

		/* code location */
		if (output_produced) {
			System.out.println("  Code written to \"" + emit.parser_class_name
					+ ".java\", and \"" + emit.symbol_const_class_name
					+ ".java\".");
		}
		else {
			System.out.println("  No code produced.");
		}

		if (Main.opt_show_timing) {
			Main.show_times();
		}

		System.out
			.println("---------------------------------------------------- ("
					+ version.version_str + ")");
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The main driver for the system. 
	 * @param argv an array of strings containing command line arguments.
	 */
	public static void main(final String argv[]) throws internal_error,
			java.io.IOException, java.lang.Exception {
		boolean did_output = false;

		Main.start_time = System.currentTimeMillis();

		/* process user options and arguments */
		Main.parse_args(argv);

		/* frankf 6/18/96
		 hackish, yes, but works */
		emit.set_lr_values(Main.lr_values);
		/* open output files */
		if (Main.print_progress) {
			System.out.println("Opening files...");
		}
		/* use a buffered version of standard input */
		Main.input_file = new BufferedInputStream(System.in);

		Main.prelim_end = System.currentTimeMillis();

		/* parse spec into internal data structures */
		if (Main.print_progress) {
			System.out.println("Parsing specification from standard input...");
		}
		Main.parse_grammar_spec();

		Main.parse_end = System.currentTimeMillis();

		/* don't proceed unless we are error free */
		if (lexer.error_count == 0) {
			/* check for unused bits */
			if (Main.print_progress) {
				System.out.println("Checking specification...");
			}
			Main.check_unused();

			Main.check_end = System.currentTimeMillis();

			/* build the state machine and parse tables */
			if (Main.print_progress) {
				System.out.println("Building parse tables...");
			}
			Main.build_parser();

			Main.build_end = System.currentTimeMillis();

			/* output the generated code, if # of conflicts permits */
			if (lexer.error_count != 0) {
				// conflicts! don't emit code, don't dump tables.
				Main.opt_dump_tables = false;
			}
			else { // everything's okay, emit parser.
				if (Main.print_progress) {
					System.out.println("Writing parser...");
				}
				Main.open_files();
				Main.emit_parser();
				did_output = true;
			}
		}
		/* fix up the times to make the summary easier */
		Main.emit_end = System.currentTimeMillis();

		/* do requested dumps */
		if (Main.opt_dump_grammar) {
			Main.dump_grammar();
		}
		if (Main.opt_dump_states) {
			Main.dump_machine();
		}
		if (Main.opt_dump_tables) {
			Main.dump_tables();
		}

		Main.dump_end = System.currentTimeMillis();

		/* close input/output files */
		if (Main.print_progress) {
			System.out.println("Closing files...");
		}
		Main.close_files();

		/* produce a summary if desired */
		if (!Main.no_summary) {
			Main.emit_summary(did_output);
		}

		/* If there were errors during the run,
		 * exit with non-zero status (makefile-friendliness). --CSA */
		if (lexer.error_count != 0) {
			System.exit(100);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Open various files used by the system. */
	protected static void open_files() {
		File fil;
		String out_name;

		/* open each of the output files */

		/* parser class */
		out_name = emit.parser_class_name + ".java";
		fil = new File(out_name);
		try {
			Main.parser_class_file =
				new PrintWriter(new BufferedOutputStream(new FileOutputStream(
					fil), 4096));
		}
		catch (final Exception e) {
			System.err.println("Can't open \"" + out_name + "\" for output");
			System.exit(3);
		}

		/* symbol constants class */
		out_name = emit.symbol_const_class_name + ".java";
		fil = new File(out_name);
		try {
			Main.symbol_class_file =
				new PrintWriter(new BufferedOutputStream(new FileOutputStream(
					fil), 4096));
		}
		catch (final Exception e) {
			System.err.println("Can't open \"" + out_name + "\" for output");
			System.exit(4);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Parse command line options and arguments to set various user-option
	 *  flags and variables. 
	 * @param argv the command line arguments to be parsed.
	 */
	protected static void parse_args(final String argv[]) {
		final int len = argv.length;
		int i;

		/* parse the options */
		for (i = 0; i < len; i++) {
			/* try to get the various options */
			if (argv[i].equals("-package")) {
				/* must have an arg */
				if (++i >= len || argv[i].startsWith("-")
						|| argv[i].endsWith(".cup")) {
					Main.usage("-package must have a name argument");
				}

				/* record the name */
				emit.package_name = argv[i];
			}
			else if (argv[i].equals("-parser")) {
				/* must have an arg */
				if (++i >= len || argv[i].startsWith("-")
						|| argv[i].endsWith(".cup")) {
					Main.usage("-parser must have a name argument");
				}

				/* record the name */
				emit.parser_class_name = argv[i];
			}
			else if (argv[i].equals("-symbols")) {
				/* must have an arg */
				if (++i >= len || argv[i].startsWith("-")
						|| argv[i].endsWith(".cup")) {
					Main.usage("-symbols must have a name argument");
				}

				/* record the name */
				emit.symbol_const_class_name = argv[i];
			}
			else if (argv[i].equals("-nonterms")) {
				Main.include_non_terms = true;
			}
			else if (argv[i].equals("-expect")) {
				/* must have an arg */
				if (++i >= len || argv[i].startsWith("-")
						|| argv[i].endsWith(".cup")) {
					Main.usage("-expect must have a name argument");
				}

				/* record the number */
				try {
					Main.expect_conflicts = Integer.parseInt(argv[i]);
				}
				catch (final NumberFormatException e) {
					Main.usage("-expect must be followed by a decimal integer");
				}
			}
			else if (argv[i].equals("-compact_red")) {
				Main.opt_compact_red = true;
			}
			else if (argv[i].equals("-nosummary")) {
				Main.no_summary = true;
			}
			else if (argv[i].equals("-nowarn")) {
				emit.nowarn = true;
			}
			else if (argv[i].equals("-dump_states")) {
				Main.opt_dump_states = true;
			}
			else if (argv[i].equals("-dump_tables")) {
				Main.opt_dump_tables = true;
			}
			else if (argv[i].equals("-progress")) {
				Main.print_progress = true;
			}
			else if (argv[i].equals("-dump_grammar")) {
				Main.opt_dump_grammar = true;
			}
			else if (argv[i].equals("-dump")) {
				Main.opt_dump_states =
					Main.opt_dump_tables = Main.opt_dump_grammar = true;
			}
			else if (argv[i].equals("-time")) {
				Main.opt_show_timing = true;
			}
			else if (argv[i].equals("-debug")) {
				Main.opt_do_debug = true;
			}
			else if (argv[i].equals("-nopositions")) {
				Main.lr_values = false;
			}
			else if (argv[i].equals("-interface")) {
				Main.sym_interface = true;
			}
			else if (argv[i].equals("-noscanner")) {
				Main.suppress_scanner = true;
			}
			else if (argv[i].equals("-version")) {
				System.out.println(version.title_str);
				System.exit(1);
			}
			/* CSA 24-Jul-1999; suggestion by Jean Vaucher */
			else if (!argv[i].startsWith("-") && i == len - 1) {
				/* use input from file. */
				try {
					System.setIn(new FileInputStream(argv[i]));
				}
				catch (final java.io.FileNotFoundException e) {
					Main.usage("Unable to open \"" + argv[i] + "\" for input");
				}
			}
			else {
				Main.usage("Unrecognized option \"" + argv[i] + "\"");
			}
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Parse the grammar specification from standard input.  This produces
	 *  sets of terminal, non-terminals, and productions which can be accessed
	 *  via static variables of the respective classes, as well as the setting
	 *  of various variables (mostly in the emit class) for small user supplied
	 *  items such as the code to scan with.
	 */
	protected static void parse_grammar_spec() throws java.lang.Exception {
		parser parser_obj;

		/* create a parser and parse with it */
		parser_obj = new parser();
		try {
			if (Main.opt_do_debug) {
				parser_obj.debug_parse();
			}
			else {
				parser_obj.parse();
			}
		}
		catch (final Exception e) {
			/* something threw an exception.  catch it and emit a message so we 
			 have a line number to work with, then re-throw it */
			lexer.emit_error("Internal error: Unexpected exception");
			throw e;
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Helper routine to optionally return a plural or non-plural ending. 
	 * @param val the numerical value determining plurality.
	 */
	protected static String plural(final int val) {
		if (val == 1) {
			return "";
		}
		else {
			return "s";
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce the optional timing summary as part of an overall summary. */
	protected static void show_times() {
		final long total_time = Main.final_time - Main.start_time;

		System.out
			.println(". . . . . . . . . . . . . . . . . . . . . . . . . ");
		System.out.println("  Timing Summary");
		System.out.println("    Total time       "
				+ Main.timestr(Main.final_time - Main.start_time, total_time));
		System.out.println("      Startup        "
				+ Main.timestr(Main.prelim_end - Main.start_time, total_time));
		System.out.println("      Parse          "
				+ Main.timestr(Main.parse_end - Main.prelim_end, total_time));
		if (Main.check_end != 0) {
			System.out
				.println("      Checking       "
						+ Main.timestr(
							Main.check_end - Main.parse_end,
							total_time));
		}
		if (Main.check_end != 0 && Main.build_end != 0) {
			System.out
				.println("      Parser Build   "
						+ Main.timestr(
							Main.build_end - Main.check_end,
							total_time));
		}
		if (Main.nullability_end != 0 && Main.check_end != 0) {
			System.out.println("        Nullability  "
					+ Main.timestr(
						Main.nullability_end - Main.check_end,
						total_time));
		}
		if (Main.first_end != 0 && Main.nullability_end != 0) {
			System.out.println("        First sets   "
					+ Main.timestr(
						Main.first_end - Main.nullability_end,
						total_time));
		}
		if (Main.machine_end != 0 && Main.first_end != 0) {
			System.out.println("        State build  "
					+ Main.timestr(
						Main.machine_end - Main.first_end,
						total_time));
		}
		if (Main.table_end != 0 && Main.machine_end != 0) {
			System.out.println("        Table build  "
					+ Main.timestr(
						Main.table_end - Main.machine_end,
						total_time));
		}
		if (Main.reduce_check_end != 0 && Main.table_end != 0) {
			System.out.println("        Checking     "
					+ Main.timestr(
						Main.reduce_check_end - Main.table_end,
						total_time));
		}
		if (Main.emit_end != 0 && Main.build_end != 0) {
			System.out.println("      Code Output    "
					+ Main.timestr(Main.emit_end - Main.build_end, total_time));
		}
		if (emit.symbols_time != 0) {
			System.out.println("        Symbols      "
					+ Main.timestr(emit.symbols_time, total_time));
		}
		if (emit.parser_time != 0) {
			System.out.println("        Parser class "
					+ Main.timestr(emit.parser_time, total_time));
		}
		if (emit.action_code_time != 0) {
			System.out.println("          Actions    "
					+ Main.timestr(emit.action_code_time, total_time));
		}
		if (emit.production_table_time != 0) {
			System.out.println("          Prod table "
					+ Main.timestr(emit.production_table_time, total_time));
		}
		if (emit.action_table_time != 0) {
			System.out.println("          Action tab "
					+ Main.timestr(emit.action_table_time, total_time));
		}
		if (emit.goto_table_time != 0) {
			System.out.println("          Reduce tab "
					+ Main.timestr(emit.goto_table_time, total_time));
		}

		System.out.println("      Dump Output    "
				+ Main.timestr(Main.dump_end - Main.emit_end, total_time));
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Helper routine to format a decimal based display of seconds and
	 *  percentage of total time given counts of milliseconds.   Note: this
	 *  is broken for use with some instances of negative time (since we don't 
	 *  use any negative time here, we let if be for now).
	 * @param time_val   the value being formatted (in ms).
	 * @param total_time total time percentages are calculated against (in ms).
	 */
	protected static String timestr(long time_val, final long total_time) {
		boolean neg;
		long ms = 0;
		long sec = 0;
		long percent10;
		String pad;

		/* work with positives only */
		neg = time_val < 0;
		if (neg) {
			time_val = -time_val;
		}

		/* pull out seconds and ms */
		ms = time_val % 1000;
		sec = time_val / 1000;

		/* construct a pad to blank fill seconds out to 4 places */
		if (sec < 10) {
			pad = "   ";
		}
		else if (sec < 100) {
			pad = "  ";
		}
		else if (sec < 1000) {
			pad = " ";
		}
		else {
			pad = "";
		}

		/* calculate 10 times the percentage of total */
		percent10 = time_val * 1000 / total_time;

		/* build and return the output string */
		return (neg ? "-" : "") + pad + sec + "." + ms % 1000 / 100 + ms % 100
				/ 10 + ms % 10 + "sec" + " (" + percent10 / 10 + "."
				+ percent10 % 10 + "%)";
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Print a "usage message" that described possible command line options, 
	 *  then exit.
	 * @param message a specific error message to preface the usage message by.
	 */
	protected static void usage(final String message) {
		System.out.println();
		System.out.println(message);
		System.out.println();
		System.out
			.println("Usage: "
					+ version.program_name
					+ " [options] [filename]\n"
					+ "  and expects a specification file on standard input if no filename is given.\n"
					+ "  Legal options include:\n"
					+ "    -package name  specify package generated classes go in [default none]\n"
					+ "    -parser name   specify parser class name [default \"parser\"]\n"
					+ "    -symbols name  specify name for symbol constant class [default \"sym\"]\n"
					+ "    -interface     put symbols in an interface, rather than a class\n"
					+ "    -nonterms      put non terminals in symbol constant class\n"
					+ "    -expect #      number of conflicts expected/allowed [default 0]\n"
					+ "    -compact_red   compact tables by defaulting to most frequent reduce\n"
					+ "    -nowarn        don't warn about useless productions, etc.\n"
					+ "    -nosummary     don't print the usual summary of parse states, etc.\n"
					+ "    -nopositions   don't propagate the left and right token position values\n"
					+ "    -noscanner     don't refer to java_cup.runtime.Scanner\n"
					+ "    -progress      print messages to indicate progress of the system\n"
					+ "    -time          print time usage summary\n"
					+ "    -dump_grammar  produce a human readable dump of the symbols and grammar\n"
					+ "    -dump_states   produce a dump of parse state machine\n"
					+ "    -dump_tables   produce a dump of the parse tables\n"
					+ "    -dump          produce a dump of all of the above\n"
					+ "    -version       print the version information for CUP and exit\n");
		System.exit(1);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/
	/** Only constructor is private, so we do not allocate any instances of this
	 class. */
	private Main() {
	}

	/*-----------------------------------------------------------*/

}
