"Parsing C++ is an incredibly difficult problem, in particular C++ is not LR(N) for any N. That means that bottom-up parsing techniques are usually not attempted for parsing C++. The grammar of C++ is ambiguous and this problem is made even worse by the fact that the CDT parser does not have access to type information to help disambiguate. For these reasons the CDT parsers are hand written recursive descent and take advantage of arbitrary lookahead and backtracking techniques. (These features are supported by linking together the tokens that are returned from the scanner).

C and C++ are closely related programming languages, and so the C and C++ parsers both derive from the same base class."
	---Mike Kucera. Based on work by Markus Schorn. 2011. 
	   http://wiki.eclipse.org/CDT/designs/Overview_of_Parsing#The_ASTs

	   
	   
Up-to-date version: http://wiki.ptidej.net/doku.php?id=headless_eclipse&#known_problems



---------------------------------------------------------
Questions to Answer when Programmatically Calling Eclipse
---------------------------------------------------------

When calling Eclipse programmatically, the reason is often to call from a program some plug-in to perform some computations, thus benefiting from Eclipse rich set of plug-ins, and then get an object as results from these computations to be used in the rest of the program. Exchanging objects between Eclipse and the rest of the program means that Eclipse must be told to use the same classes for these objects as the rest of the program. Hence, it must be told to use the same class-loader for these classes or, really, must be instructed to by-pass its class-loading mechanism to directly as the JVM system class-loader, also called application class-loader, instance of ''Launcher$AppClassLoader''.

The different sections of the MANIFEST.MF and build.properties files interact and impact the compiling and running of the plug-in. First, it is important to understand that plug-in relate to other plug-ins and JARs through three questions:
- Does the current plug-in require other plug-ins / JARs to compile?
- Does the current plug-in require other plug-ins / JARs to run?
- Does the current plug-in require access to the classes in other plug-ins / JARs?

Does the current plug-in require other plug-ins / JARs to compile?
------------------------------------------------------------------

If the current plug-in requires other plug-ins / JARs to compile, these plug-ins / JARs must be declared in "Dependencies -> Required Plug-ins" OR "Build -> Extra Classpath Entries". The difference between the two sections is important.

"Dependencies -> Require Plug-ins" only accepts plug-ins, not JARs. Plug-ins listed in this section becomes accessible to the current plug-ins, i.e., their classes are visible to the current plug-in according to their visibility ("Runtime -> Exported Packages"). 

"Build -> Extra Classpath Entries" only accepts JARs. These JARs will be used to compile the plug-ins but are not related to its running.

Does the current plug-in require other plug-ins / JARs to run?
--------------------------------------------------------------

If the current plug-in requires other plug-ins / JARs to run, these plug-ins / JARs must be declared in "Dependencies -> Required Plug-ins" OR "Runtime -> Classpath". The difference between these two sections is also important.

"Dependencies -> Required Plug-ins" only accepts plug-ins, not JARs. Plug-ins listed in this section become accessible to the current plug-ins, i.e., any class belonging to the package listed in their respective "Runtime -> Exported Packages" sections is accessible. They must be available in the runtime configuration ("config.ini") of the Eclipse application in which the current plug-in will run, else the current plug-in will not load and the log file in the workspace".metadata" folder will contain a "ClassNotDefFoundError".

"Runtime -> Classpath" only accepts JARs. These JARs will be "embedded" in the current plug-in and their classes accessible to it. JARs added to this section automatically appear in the "Build -> Binary Build" section and in the "bin.includes" statement of the "build.properties" file.

Does the current plug-in require access to the classes in other plug-ins / JARs?
--------------------------------------------------------------------------------

If the current plug-in requires classes in other plug-ins / JARs, these plug-ins / JARs must be declared in "Dependencies -> Required Plug-ins" OR "Dependencies -> Imported Packages" OR "Runtime -> Classpath". "Dependencies -> Required Plug-ins" and "Runtime -> Classpath" have been discussed above. "Dependencies -> Imported Packages" is used to bypass the Eclipse class-loading mechanism, as explained below.



---------------------------------------------------------------------------
Sharing Class Instances between a Java Application and some Eclipse Plug-in
---------------------------------------------------------------------------

When calling Eclipse programmatically, the reason is often to call from a program some plug-in to perform some computations, thus benefiting from Eclipse rich set of plug-ins, and then get an object as results from these computations to be used in the rest of the program. Exchanging objects between Eclipse and the rest of the program means that Eclipse must be told to use the same classes for these objects as the rest of the program. Hence, it must be told to use the same class-loader for these classes or, really, must be instructed to by-pass its class-loading mechanism to directly as the JVM system class-loader, also called application class-loader, instance of ''Launcher$AppClassLoader''.

In other words: the main interest of programmatically running some Eclipse plug-in from Java application is to benefit from the Eclipse plug-ins and obtain / use some instances of some classes from them. To share these instances, the Java application and the Eclipse plug-ins must share the same classes, i.e., classes loaded using the Java application class-loader, else the JVM would consider that the classes are different, because loaded from different class-loaders.

To instruct Eclipse to by-pass its class-loading mechanism, the plug-in must be configured so that its ''MANIFEST.MF'' file uses the ''Import-Package'' statement to declares which packages will come from "somewhere else", not a particular plug-in. Then, Eclipse must be run with the ''osgi.compatibility.bootdelegation'' property set to ''true'' (see http://wiki.eclipse.org/Equinox_Boot_Delegation#Compatibility). Finally, the configuration file ''Config.ini'' of the headless Eclipse workspace must include the ''org.osgi.framework.system.packages.extra'' statement (see https://felix.apache.org/site/apache-felix-framework-configuration-properties.html#ApacheFelixFrameworkConfigurationProperties-framework) must match one-for-one the list of imported packages in the ''MANIFEST.MF'' file. If there is a discrepancy between the ''MANIFEST.MF'' and ''Config.ini'' files, Eclipse will use its class-loading mechanism and the objects returned by the plug-in and expected by the program will belong to different classes, because loaded by different class-loaders.



--------------------------------
Issues with PDE and Access Rules
--------------------------------

When using the ''Import-Package'' statement and if the plug-in shares some common package with another plug-in, Eclipse gets confused and claims that other plug-in cannot use classes in this package, even if it is exported by all the plug-ins involved. Therefore, it is important to have different packages, which can of defeat the purpose of having plug-in in the first place. It is true, though, that ''Import-Package'' statement, by instructing Eclipse to by-pass its class-loading mechanism, disturb this mechanism.



---------------------------------------------
Plug-ins Referenced in Config.ini but Missing
---------------------------------------------

The "config.ini" file contains, among other things, the locations of the plug-ins (actually, their JAR files or folders) required by an Eclipse application. If one of more plug-ins are missing, i.e., there is a discrepancy between the "config.ini" file and the folder containing the plug-ins JARs and folders, then Eclipse will throw an error and the "configuration area" will contain a log file detailing the missing plug-ins.