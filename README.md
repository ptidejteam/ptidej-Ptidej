# Ptidej

[![License: GPL v2](https://img.shields.io/badge/License-GPL_v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

In the Ptidej Team (Pattern Trace Identification, Detection, and Enhancement in Java), we aim at developing theories, methods, 
and tools, to evaluate and to improve the quality of object-oriented programs by promoting the use of idioms, design patterns, 
and architectural patterns. We want to formalise patterns, to identify occurrences of patterns, and to improve the identified 
occurrences. We also want to evaluate experimentally the impact of patterns on the quality of object-oriented programs. We 
develop various tools, most notably the Ptidej tool suite and Taupe, to evaluate and to enhance the quality of object-oriented 
programs, promoting the use of patterns, either at the language-, design-, or architectural-levels.

The source code of the Ptidej Tool Suite is open and released under the GNU Public License v2.

## What is this repository for?

* Ptidej 
* http://wiki.ptidej.net/

## Prerequisites

- Get Maven version 3 - [Installation](https://maven.apache.org/install.html)
- Configure Maven using Java 17 - [Download JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - [Set Java Version of Maven](https://stackoverflow.com/a/19654699)
  - Also compiled and tested with Java 22

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

Currently, the project is not functional because it uses the `cfparse` library, which is only compatible with java version 1.4.

### To be done

- Migrate the use of the `cfparse` library to the [`bcel` library](https://mvnrepository.com/artifact/org.apache.bcel/bcel)
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

## Contribution guidelines

* Writing tests
* Code review
* Other guidelines

## Who do I talk to?

- Repo owner or admin: **Yann-Gaël Guéhéneuc** at *yann-gael.gueheneuc@concordia.ca*
- Other community or team contact: **Wiki** of the Ptidej Team at *http://wiki.ptidej.net*