package spellingCorrection;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.hunspell.Dictionary;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SimpleSuggestionSpeller {
    
    public static void main(String[] args) throws Exception {
    	/* create a standard analyzer */
		NGramAnalyzer analyzer = new NGramAnalyzer(2);
		IndexWriterConfig config = new IndexWriterConfig( analyzer);
    	
        //File dir = new File("c:/spellchecker/");
        Path dirPath = Paths.get(System.getProperty("user.dir")+"/index/");
        
        Directory directory = FSDirectory.open(dirPath);
        Path dictPath = Paths.get("/home/matteo/Scrivania/dict.txt");

        PlainTextDictionary dictionary = new PlainTextDictionary(dictPath);
        
        SpellChecker spellChecker = new SpellChecker(directory);
        spellChecker.indexDictionary(dictionary, config, true);
       
        
        String wordForSuggestions = "world";
        
        int suggestionsNumber = 5;

        String[] suggestions = spellChecker.
            suggestSimilar(wordForSuggestions, suggestionsNumber);

        if (!spellChecker.exist(wordForSuggestions) && suggestions!=null && suggestions.length>0) {
            for (String word : suggestions) {
                System.out.println("Did you mean:" + word);
            }
        }
        else {
            System.out.println("No suggestions found for word:"+wordForSuggestions);
        }
        spellChecker.close();
            
    }

}