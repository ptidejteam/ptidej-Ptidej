# Ptidej

[![License: GPL v2](https://img.shields.io/badge/License-GPL_v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Maven-green?style=for-the-badge&logo=Apache%20Maven)

In the Ptidej Team (Pattern Trace Identification, Detection, and Enhancement in Java), we aim at developing theories, methods, 
and tools, to evaluate and improve the quality of object-oriented programs by promoting the use of idioms, design patterns, 
and architectural patterns. We want to formalise patterns, identify occurrences of patterns, and improve the identified 
occurrences. We also want to evaluate experimentally the impact of patterns on the quality of object-oriented programs. We 
develop various tools, most notably the Ptidej tool suite and Taupe, to evaluate and enhance the quality of object-oriented 
programs, promoting the use of patterns, at the language, design, and architectural levels.

The source code of the Ptidej Tool Suite is open and released under the GNU Public License v2.

## What is it?

* The Ptidej Tool Suite
* https://wiki.ptidej.net/

## What do I need?

- Maven version 3.9.6
- Java 21 (or more recent?)

## How do I set it up?

To build the whole project, use : 
```bash
mvn clean
mvn validate
mvn install
```

- `mvn validate` installs 3rd party JARs, like `cfparse`.
- `mvn install` compiles, tests, packages, and installs all the sub-projects.

After executing these commands, run:
```bash
java -jar "DeMIMA UI Viewer Standalone Swing/target/demima-ui-viewer-swing-1.0.0-jar-with-dependencies.jar"
```

This JAR launches a Swing GUI to interact with the Ptidej Tool Suite.

## Who do I talk to?

- Repo. admin: info@ptidej.net
- Wiki documentation: https://wiki.ptidej.net

## Troubleshooting

Some sub-projects must be compiled towards bytecode for Java 1.4. This requirement is enforced in the appropriate `pom.xml` files. The whole projects and some sub-projects also require specific `--add-exports` and `--add-opens` arguments to the JVM, which are also already set in the corresponding `pom.xml` files. These arguments are:

```--add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED```

## Guidelines

* Writing tests
* Code review
* Other guidelines

### TODO

In some order of importance:
- Migrate the use of the `cfparse` library to the `bcel` [library](https://mvnrepository.com/artifact/org.apache.bcel/bcel)
  - Use `util.lang.CFParseBCELConvertor`?
- Fix tests in `PADL Creator C++ (Eclipse)`
- Add tests to `Creator MSE`
- Add tests to `PADL Generator PageRank`
- Clean test outputs
  - Fix/hide any exceptions
- Maven-ise `Caffeine`
- Change the encoding from **windows-1252** to **UTF-8**
- Refactoring the code to make full use of Java 17
- Remove compilation warnings
- Fix extremely slow and memory-consuming running of Ptidej from JAR
  - `java -jar "DeMIMA UI Viewer Standalone Swing/target/demima-ui-viewer-swing-1.0.0-jar-with-dependencies.jar"`
- Fix JPG export from the menu Export SVG in `...Swing`
- Simplify and update "About" in `...Swing`
- Find an alternative to using the `com.sun.tools.javac` library, which is internal to the JDK.
- Add GitHub Actions to compile/test the whole project
