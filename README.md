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

## What is this repository for?

* Ptidej 
* https://wiki.ptidej.net/

## Prerequisites

- Get Maven version 3
- Use Java 17 or mor recent (compiled and tested with Java 22)

## How do I get set up?

To build the whole project, use : 
```bash
mvn clean
mvn validate
mvn install
```

- The `mvn validate` command is used to install 3rd party JARs like `cfparse`.
- The `mvn install` command is used to compile, package, and install all modules.

After executing these commands, you get in particular an executable :
```bash
java -jar "DeMIMA UI Viewer Standalone Swing/target/demima-ui-viewer-swing-1.0.0-jar-with-dependencies.jar"
```

This jar launches a Swing GUI that lets you interact with the Ptidej tool.

## Troubleshooting

Currently, the whole project requires some sub-projects to be compiled towards bytecode for Java 1.4. This requirement is described into the appropriate `pom.xml` files. The whole projects and some sub-projects also require specific `--add-exports` and `--add-opens` arguments to the JVM, which are already set appropriately in the corresponding `pom.xml` files for the tests. These areguments are:

```--add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED```

### To be done

In no particular order of importance:
- Migrate the use of the `cfparse` library to the `bcel` [library](https://mvnrepository.com/artifact/org.apache.bcel/bcel)
  - Use `util.lang.CFParseBCELConvertor`?
- Find an alternative to using the `com.sun.tools.javac` library, which is internal to the JDK.
- Change the encoding from **windows-1252** to **UTF-8**
- Refactoring the code to make full use of Java 17
- Remove compilation warnings
- Clean test outputs
- Fix `PADL Creator C++ (Eclipse)`
- Add tests to `Creator MSE`
- Add tests to `PADL Generator PageRank`
- Fix JPG export from the menu Export SVG in `...Swing`
- Simplify and update "About" in `...Swing`
- Maven-ise Caffeine
- Add GitHub Actions to compile/test the whole project

## Contribution guidelines

* Writing tests
* Code review
* Other guidelines

## Who do I talk to?

- Repo. admin: info@ptidej.net
- Wiki documentation: https://wiki.ptidej.net
