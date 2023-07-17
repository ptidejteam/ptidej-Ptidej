
package utils;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;

/*
 * class to use the Stanford Parser API in project
 */
public final class CustomParser {

    private static CustomParser cp = null;
    private static LexicalizedParser lp = null;
    private static TokenizerFactory<CoreLabel> tokenizerFactory;
    private static Properties props;
    private static StanfordCoreNLP pipeline;

    public static CustomParser getInstance() {
        if (CustomParser.cp == null) {
            CustomParser.cp = new CustomParser();
        }
        return CustomParser.cp;
    }

    private CustomParser() {

        CustomParser.lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
        CustomParser.tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        CustomParser.props = new Properties();
        CustomParser.props.put("annotators", "tokenize, ssplit, pos, lemma");
        CustomParser.pipeline = new StanfordCoreNLP(CustomParser.props);

    }

    public static Tree getTree(final String sentence) {

        final List<CoreLabel> tokens = CustomParser.tokenizerFactory.getTokenizer(new StringReader(sentence))
                .tokenize();

        return CustomParser.lp.apply(tokens);
    }

    public static Tree getTree(final Vector<String> sentence) {

        final String[] sentenceArray = sentence.toArray(new String[sentence.size()]);

        final List<CoreLabel> tokens = SentenceUtils.toCoreLabelList(sentenceArray);
        return CustomParser.lp.apply(tokens);
    }

    public static boolean hasPOS(final String pos, final String sentence) {

        final Tree tree = CustomParser.getTree(sentence);
        final List<CoreLabel> taggedWords = tree.taggedLabeledYield();
        final Iterator<CoreLabel> taggedWord = taggedWords.iterator();
        CoreLabel next = null;

        while (taggedWord.hasNext()) {
            next = taggedWord.next();
            final String taggedPOS = next.get(PartOfSpeechAnnotation.class).toString();

            if (taggedPOS.equalsIgnoreCase(pos)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPOS(final String pos, final Vector<String> sentence) {

        final Tree tree = CustomParser.getTree(sentence);
        final List<CoreLabel> taggedWords = tree.taggedLabeledYield();
        final Iterator<CoreLabel> taggedWord = taggedWords.iterator();
        CoreLabel next = null;

        while (taggedWord.hasNext()) {
            next = taggedWord.next();
            final String taggedPOS = next.get(PartOfSpeechAnnotation.class).toString();

            if (taggedPOS.equalsIgnoreCase(pos)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPOSInAnyForm(final String pos, final String sentence) {

        final Iterator<CoreLabel> taggedWord = CustomParser.getTree(sentence).taggedLabeledYield().iterator();
        CoreLabel next = null;

        while (taggedWord.hasNext()) {
            next = taggedWord.next();

            if (next.get(PartOfSpeechAnnotation.class).startsWith(pos)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPOSInAnyForm(final String pos, final Vector<String> sentence) {

        final Iterator<CoreLabel> taggedWord = CustomParser.getTree(sentence).taggedLabeledYield().iterator();
        CoreLabel next = null;

        while (taggedWord.hasNext()) {
            next = taggedWord.next();

            if (next.get(PartOfSpeechAnnotation.class).startsWith(pos)) {
                return true;
            }
        }
        return false;
    }

    public static String getAllPOS(final String sentence) {

        final Tree tree = CustomParser.getTree(sentence);
        List<CoreLabel> taggedWords = tree.taggedLabeledYield();

        String result = "";
        for (final CoreLabel taggedWord : taggedWords) {
            result = result + taggedWord.toString() + " ";
        }
        result = result.substring(0, result.length() - 1);
        return result;

    }

    public static String getPOS(final String term, final String sentence) {

        final Tree tree = CustomParser.getTree(sentence);
        final List<CoreLabel> taggedWords = tree.taggedLabeledYield();
        final Iterator<CoreLabel> taggedWord = taggedWords.iterator();
        CoreLabel next = null;

        while (taggedWord.hasNext()) {
            next = taggedWord.next();
            final String word = next.get(TextAnnotation.class);

            if (word.compareTo(term) == 0) {
                return next.get(PartOfSpeechAnnotation.class);
            }
        }
        return null;
    }

    public static String getPOS(final String term, final Vector<String> sentence) {

        final Tree tree = CustomParser.getTree(sentence);
        final List<CoreLabel> taggedWords = tree.taggedLabeledYield();
        final Iterator<CoreLabel> taggedWord = taggedWords.iterator();
        CoreLabel next = null;

        while (taggedWord.hasNext()) {
            next = taggedWord.next();
            final String word = next.get(TextAnnotation.class);

            if (word.compareTo(term) == 0) {
                return next.get(PartOfSpeechAnnotation.class);
            }
        }
        return null;
    }

}
