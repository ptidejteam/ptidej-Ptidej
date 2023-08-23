# README

In the Ptidej Team (Pattern Trace Identification, Detection, and Enhancement in Java), we aim at developing theories, methods, and tools, to evaluate and to improve the quality of object-oriented programs by promoting the use of idioms, design patterns, and architectural patterns. We want to formalise patterns, to identify occurrences of patterns, and to improve the identified occurrences. We also want to evaluate experimentally the impact of patterns on the quality of object-oriented programs. We develop various tools, most notably the Ptidej tool suite and Taupe, to evaluate and to enhance the quality of object-oriented programs, promoting the use of patterns, either at the language-, design-, or architectural-levels.

Since October 10th, 2014, the source code of the Ptidej Tool Suite is open and released under the GNU Public License v2.

Since December 10th, 2004, the runnable versions of the Ptidej Tool Suite are available at http://www.ptidej.net/downloads/tools/ptidejtoolsuite.

## What is this repository for?

* Ptidej 
* http://wiki.ptidej.net/

## How do I get set up?

To build the whole project, use : 
```bash
mvn clean
mvn validate
mvn install -DskipTests=true
```

- The `mvn validate` command is used to install 3rd party JARs like `cfparse`.
- The `mvn install` command is used to compile, package et install all modules.
  - The `-DskipTests=true` option lets you ignore the results of unit tests that are not yet valid.

After executing these commands, you get in particular an executable :
```bash
java -jar "DeMIMA UI Viewer Standalone Swing/target/demima-ui-viewer-swing-1.0.0-jar-with-dependencies.jar"
```

This jar launches a Swing GUI that lets you interact with the Ptidej tool.

## Troubleshooting

Currently, the project is not functional because it uses the `cfparse` library, which is only compatible with java version 1.4.

### To be done

- Migrate the use of the `cfparse` library to the [`bcel` library](https://mvnrepository.com/artifact/org.apache.bcel/bcel)
- Fix unit tests and adapt old tests with Maven (see _Java Parser_ or _JCT Tests_)
- Find an alternative to using the `com.sun.tools.javac` library, which is internal to the JDK.

## Contribution guidelines

* Writing tests
* Code review
* Other guidelines

## Who do I talk to?

- Repo owner or admin: **Yann-Gaël Guéhéneuc** at *yann-gael.gueheneuc@concordia.ca*
- Other community or team contact: **Wiki** of the Ptidej Team at *http://wiki.ptidej.net*