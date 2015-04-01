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

	public static String spellingCorrection(String query) throws Exception {
		/* create a standard analyzer */
		NGramAnalyzer analyzer = new NGramAnalyzer(2);
		IndexWriterConfig config = new IndexWriterConfig( analyzer);

		//File dir = new File("c:/spellchecker/");
		Path dirPath = Paths.get("C:/Users/orangepc/git/MASCSearch/MASCSearch/index/");

		Directory directory = FSDirectory.open(dirPath);
		Path dictPath = Paths.get("C:/Users/orangepc/git/MASCSearch/MASCSearch/dict.txt");

		PlainTextDictionary dictionary = new PlainTextDictionary(dictPath);

		SpellChecker spellChecker = new SpellChecker(directory);
		spellChecker.indexDictionary(dictionary, config, true);


		String wordForSuggestions = query;

		int suggestionsNumber = 1;

		String[] suggestions = spellChecker.
				suggestSimilar(wordForSuggestions, suggestionsNumber);
		String suggestion =null;
		if (!spellChecker.exist(wordForSuggestions) && suggestions!=null && suggestions.length>0) {
			for (String word : suggestions) {
				suggestion = word;
			}
		}
		spellChecker.close();
		return suggestion; 
	}
}