
package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;

final public class CustomDictionary {

    private static CustomDictionary cd;
    private static Dictionary d;
    private String CONFIG_FILE_PATH = "platform:/plugin/polymtl.pmd/bin/utils/files/file_properties.xml";

    private CustomDictionary() throws FileNotFoundException {
        try {
            final URL url = new URL(CONFIG_FILE_PATH);
            JWNL.initialize(url.openConnection().getInputStream());
        } catch (final JWNLException | IOException e) {
            e.printStackTrace();
        }

        CustomDictionary.d = Dictionary.getInstance();
    }

    public static CustomDictionary getInstance() {
        if (CustomDictionary.cd == null) {
            try {
                CustomDictionary.cd = new CustomDictionary();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return CustomDictionary.cd;
    }

    public static Dictionary getD() {
        return d;
    }

    public static boolean areSynonyms(final IndexWord source, final IndexWord target) throws JWNLException {

        for (int i = 1; i <= source.getSenseCount(); i++) {

            for (int j = 1; j <= target.getSenseCount(); j++) {

                final RelationshipList list = RelationshipFinder.getInstance().findRelationships(source.getSense(i),
                        target.getSense(j), PointerType.SIMILAR_TO);

                if (list.size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean areAntonyms(final IndexWord source, final IndexWord target) throws JWNLException {

        for (int i = 1; i <= source.getSenseCount(); i++) {

            for (int j = 1; j <= target.getSenseCount(); j++) {

                final RelationshipList list = RelationshipFinder.getInstance().findRelationships(source.getSense(i),
                        target.getSense(j), PointerType.ANTONYM);

                if (list.size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean haveAntonyms(final IndexWordSet source, final IndexWordSet target) throws JWNLException {

        final Iterator<?> sourcePos = source.getValidPOSSet().iterator();
        final Set<?> targetPos = target.getValidPOSSet();
        IndexWord sourceIndexWord, targetIndexWord;
        POS currentPos = null;

        while (sourcePos.hasNext()) {

            currentPos = (POS) sourcePos.next();

            if (targetPos.contains(currentPos)) {

                sourceIndexWord = CustomDictionary.getD().lookupIndexWord(currentPos, source.getLemma());
                targetIndexWord = CustomDictionary.getD().lookupIndexWord(currentPos, target.getLemma());

                if (CustomDictionary.areAntonyms(sourceIndexWord, targetIndexWord)
                        && !CustomDictionary.areSynonyms(sourceIndexWord, targetIndexWord)) {
                    return true;
                }
            }
        }

        return false;
    }

    // public static boolean areAntonyms(final String sourceWord, final String
    // targetWord){
    //
    //
    // }
    //

    public static IndexWordSet stringToIndexWordSet(final String lemma) throws JWNLException {

        return CustomDictionary.getD().lookupAllIndexWords(lemma);

    }

    public static POS taggerToDictionaryPos(final String pos) {

        if (pos.equalsIgnoreCase("NN") || pos.equalsIgnoreCase("NNP") || pos.equalsIgnoreCase("NNPS")
                || pos.equalsIgnoreCase("NNS")) {
            return POS.NOUN;
        } else if (pos.equalsIgnoreCase("VB") || pos.equalsIgnoreCase("VBD") || pos.equalsIgnoreCase("VBG")
                || pos.equalsIgnoreCase("VBN") || pos.equalsIgnoreCase("VBP") || pos.equalsIgnoreCase("VBZ")) {
            return POS.VERB;
        } else if (pos.equalsIgnoreCase("JJ") || pos.equalsIgnoreCase("JJR") || pos.equalsIgnoreCase("JJS")) {
            return POS.ADJECTIVE;
        } else if (pos.equalsIgnoreCase("RB") || pos.equalsIgnoreCase("RBR") || pos.equalsIgnoreCase("RBS")) {
            return POS.ADVERB;
        } else {
            return null;
        }
    }

    public static boolean hasIndexWordForPos(final String lemma, final POS pos) throws JWNLException {

        IndexWordSet indexWordSet = null;
        CustomDictionary.getInstance();
        indexWordSet = CustomDictionary.getD().lookupAllIndexWords(lemma);

        return indexWordSet.getIndexWord(pos) != null;
    }

}
