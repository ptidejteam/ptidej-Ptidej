# Ptidej

[![License: GPL v2](https://img.shields.io/badge/License-GPL_v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)
![Java](https://img.shields.io/badge/Java-orange)
![Apache Maven](https://github.com/ptidejteam/ptidej-Ptidej/actions/workflows/maven.yml/badge.svg)
[![CO₂ Shield](https://img.shields.io/badge/CO₂-C_0.42g-C89806)](https://overbrowsing.com/projects/co2-shield)

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

At minimum
- Java 21 and its JDK
- Maven version 3.9.6
- Eclipse 2024-03 (4.31.0)

Tested with
- Java 24 and its JDK
- Maven version 3.9.9
- Eclipse 2025-09 (4.37.0 M2)

(Be aware that Eclipse only allows previews for the latest JDK that it supports.)

## How do I set it up?

To build the whole project, use: 
```bash
mvn clean
mvn validate
mvn install
```

where:

- `mvn validate` installs 3rd party JARs, like `cfparse` and `db4o`.
- `mvn install` compiles, tests, packages, and installs all the sub-projects.

You could also use the following command to clean your local Maven repository:
`mvn dependency:purge-local-repository -DactTransitively=false -DreResolve=false`.

After executing these commands, run:
```bash
java -jar "DeMIMA UI Viewer Standalone Swing/target/demima-ui-viewer-swing-1.0.0-jar-with-dependencies.jar"
```

This JAR launches a Swing GUI to interact with the Ptidej Tool Suite.

## Who do I talk to?

- Repo. admin: info@ptidej.net
- Wiki documentation: https://wiki.ptidej.net

## Troubleshooting

Some sub-projects require the features previewed in JDK 21 (which may become available in JDK 22). Thus, tests and programs require adding the JVM argument `--enable-preview` to the command line. The whole projects and some sub-projects also require specific `--add-exports` and `--add-opens` arguments to the JVM, which are also already set in the corresponding `pom.xml` files. Therefore, the JVM arguments are:

```--enable-preview --add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED```

## Guidelines

* Writing tests
* Code review
* Other guidelines

### TODO

In some order of importance:
- Fix tests in `PADL Creator C++ (Eclipse)`
- Add tests to `Creator MSE`
- Add tests to `PADL Generator PageRank`
- Clean test outputs
  - Fix/hide any exceptions
- Refactoring the code to make full use of Java 21
- Remove compilation warnings
- Fix JPG export from the menu Export SVG in `...Swing`
- Simplify and update "About" in `...Swing`
- Find an alternative to using the `com.sun.tools.javac` library, which is internal to the JDK.
- Modularise Ptidej to benefit from the Java Platform Module System.
