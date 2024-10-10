package ptidej.dp.detector;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import net.sourceforge.plantuml.SourceStringReader;
import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.visitor.IWalker;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.Generator;
import ptidej.solver.java.domain.Manager;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

public class DPExtractor {

  public static void main(String[] args) {
    Arguments arguments = getArguments(args);

    String jarFilePath = arguments.jarFilePath;
    String projectName = arguments.name;
    Path artifactsOutput = Path.of(arguments.output, projectName);
    List<String> motifs = arguments.motifs;

    DPExtractor extractor = new DPExtractor();
    extractor.extract(projectName, jarFilePath, motifs, artifactsOutput);
  }

  public void extract(String projectName, String jarFilePath, List<String> motifs, Path artifactsOutput) {
    IIdiomLevelModel model = getiIdiomLevelModel(jarFilePath);
    analyzeCodeLevelModel(model, motifs, projectName, artifactsOutput);
//    generateUMLDiagramImage(model, Path.of("UML.png"));
  }

  private IIdiomLevelModel getiIdiomLevelModel(String jarFilePath) {
    IIdiomLevelModel result = ModelGenerator.generateModelFromJAR(jarFilePath);
    System.out.printf("Number of top-level entities: %d%n", result.getNumberOfTopLevelEntities());

    return result;
  }

  private void analyzeCodeLevelModel(IAbstractLevelModel model, List<String> motifs,
                                     String projectName, Path artifactsOutputDirectory) {
    try {

      for (String motifName : motifs) {
        analyzeMotif(model, projectName, artifactsOutputDirectory, motifName);
      }

    } catch (Exception e) {
      e.printStackTrace(ProxyConsole.getInstance().errorOutput());
    }
  }

  private void analyzeMotif(IAbstractLevelModel model, String projectName, Path artifactsOutputDirectory,
                            String motifName) throws Exception {

    System.out.printf("Identifying occurrences of the %s design motif...%n", motifName);

    IWalker generator = new Generator();
    Problem problem = getProblem(model, generator, motifName);
    writeInitFiles(problem, projectName, motifName, artifactsOutputDirectory);

    //Occurrence[] occurrences = getOccurrences(path, problem);
  }

  private void writeInitFiles(Problem problem, String projectName, String motifName, Path artifactsOutputDirectory) {
    String iniFileName = String.format("%s-%s.ini", projectName, motifName);
    Path path = artifactsOutputDirectory.resolve(iniFileName);
    Writer writer = ProxyDisk.getInstance().fileTempOutput(path.toString());
    problem.setWriter(new PrintWriter(writer));
    problem.automaticSolve(true);
  }

  private Occurrence[] getOccurrences(Path path) throws IOException {
    Properties properties = new Properties();
    properties.load(new ReaderInputStream(ProxyDisk.getInstance().fileTempInput(path.toString())));

    return OccurrenceBuilder.getInstance().getCanonicalOccurrences(properties);
  }

  private void generateUMLDiagramImage(IAbstractLevelModel model, Path imagePath)  {
    String umlCode = generatePlantUML(model);
    SourceStringReader reader = new SourceStringReader(umlCode);

    try {
      OutputStream imageOutputStream = new FileOutputStream(imagePath.toFile());
      String desc = reader.outputImage(imageOutputStream).getDescription();

      if ("Error".equals(desc)) {
        throw new RuntimeException("Unable to process UMLCode");
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (PrintWriter printWriter = ProxyConsole.getInstance().normalOutput()) {
      printWriter.println("PlantUML Image generated successfully.");
    }
  }

  private String generatePlantUML(IAbstractLevelModel model) {
    PlantUMLGenerator plantUMLGenerator = new PlantUMLGenerator();
    model.generate(plantUMLGenerator);

    return plantUMLGenerator.getCode();
  }

  private Problem getProblem(IAbstractLevelModel anAbstractLevelModel, IWalker aWalker, String motifName)
    throws Exception {

    String motifClassName = String.format("ptidej.solver.java.problem.%sMotif", motifName);
    Class<?> problemClass = Class.forName(motifClassName);
    Method getProblemMethod = problemClass.getMethod("getProblem", List.class);
    List<?> entities = Manager.build(anAbstractLevelModel, aWalker);

    return (Problem) getProblemMethod.invoke(null, entities);
  }

  private static Arguments getArguments(String[] args) {
    Arguments result = new Arguments();
    JCommander.newBuilder()
      .addObject(result)
      .build()
      .parse(args);

    return result;
  }

  private static class Arguments {
    @Parameter(names = {"-n", "--name"}, description = "Project name.", required = true)
    private String name;

    @Parameter(names = {"-j", "--jar"}, description = "Project jar file path.", required = true)
    private String jarFilePath;

    @Parameter(names = {"-m", "--motifs"}, description = "Comma separated design patter motif list.", required = true)
    private List<String> motifs;

    @Parameter(names = {"-o", "--out"}, description = "Artifacts output path.")
    private String output = ".";
  }
}
