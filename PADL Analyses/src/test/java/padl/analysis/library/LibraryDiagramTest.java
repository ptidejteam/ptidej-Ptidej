package padl.analysis.library;

import java.nio.file.Files;
import java.nio.file.Path;
import junit.framework.TestCase;
import padl.analysis.plantUMLGenerator.PlantUMLGenerator;

public class LibraryDiagramTest extends TestCase {
    public void testGeneratesLibraryDiagram() throws Exception {
        var model = LibraryModelTest.readLibrary();
        String uml = model.generate(new PlantUMLGenerator());
        assertTrue(uml.trim().startsWith("@startuml"));
        assertTrue(uml.contains("class Book {"));
        assertTrue(uml.contains("class EBook {"));
        assertTrue(uml.contains("Book --^ EBook"));
        assertTrue(uml.trim().endsWith("@enduml"));
        Files.writeString(Path.of("target", "library.puml"), uml);
    }
}
