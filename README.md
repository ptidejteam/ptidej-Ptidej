# README #

In the Ptidej Team (Pattern Trace Identification, Detection, and Enhancement in Java1), we aim at developing theories, methods, and tools, to evaluate and to improve the quality of object-oriented programs by promoting the use of idioms, design patterns, and architectural patterns. We want to formalise patterns, to identify occurrences of patterns, and to improve the identified occurrences. We also want to evaluate experimentally the impact of patterns on the quality of object-oriented programs. We develop various tools, most notably the Ptidej tool suite and Taupe, to evaluate and to enhance the quality of object-oriented programs, promoting the use of patterns, either at the language-, design-, or architectural-levels.

Since October 10th, 2014, the source code of the Ptidej Tool Suite is open and released under the GNU Public License v2.

Since December 10th, 2004, the runnable versions of the Ptidej Tool Suite are available at http://www.ptidej.net/downloads/tools/ptidejtoolsuite.

### What is this repository for? ###

* Ptidej 
* v5
* http://wiki.ptidej.net/

### How do I get set up? ###

* Summary of set up

Microsoft Windows and Intel x86 processors, because of DLL dependent on Windows for parsing C++ source code.

Any Java virtual machine.

* Configuration

You may use JDK 17 or greater. The projects may depend on a specific JRE System Library name, make sure that your default installed JRE has the same, expected name.

You *must* use Eclipse with the Plug-in Development Environment installed. Most of the projects composing the Ptidej Tool Suite are plug-ins and their Java Build Paths require the PDE plug-in to be set properly by Eclipse before compilation. After downloading the source code, make sure to select "/PADL/META-INF/MANIFEST.MF" and right click on it and then choose "Plug-in Tools -> Update Classpath... -> Select All -> Finish".

In "Window -> Preferences -> Java -> Build Path -> Classpath Variables", add "ECLIPSE_HOME" pointing to the folder of your Eclipse installation, for example "/usr/eclipse/".

In "Window -> Preferences -> Java -> Build Path -> Classpath Variables", add "P2_REPO" pointing to the plugin folder of your Eclipse installation, for example "/usr/eclipse/plugin". (The folder to which you point should contain lots of folders and JAR files, including files called org.eclipse.jdt.core...)

Some projects depend upon JDT core and, thus, reference the Eclipse plug-in "jdt.core". Depending on the version of Eclipse, you must adjust the path of these projects to point to the current version of the plug-in "jdt.core". For example, for the project "Java Parser", you may have to select "/usr/eclipse/plugins/org.eclipse.jdt.core_3.8.3.v20130121-145325.jar" and also add "/usr/eclipse/plugins/org.eclipse.equinox.common_3.6.100.v20120522-1841.jar" and "/usr/eclipse/plugins/org.eclipse.core.runtime_3.8.0.v20120912-155025.jar".

* Dependencies
* How to run tests

Run as JUnit test the class ptidej.test.all.TestAllPtidej

* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin

Yann-Gaël Guéhéneuc at mailto://yann-gael.gueheneuc@concordia.ca

* Other community or team contact

Wiki of the Ptidej Team at http://wiki.ptidej.net