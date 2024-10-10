package javainuse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    JacksonXmlModule xmlModule = new JacksonXmlModule();
    xmlModule.setDefaultUseWrapper(false);
    ObjectMapper objectMapper = new XmlMapper(xmlModule);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String xml = Files.readString(Path.of("C:\\AI\\datasets\\DP\\Design Pattern List v1.2.xml"), StandardCharsets.ISO_8859_1);
    List<DesignPatterns> patterns = objectMapper.readValue(xml, new TypeReference<>() {});
  }
}
