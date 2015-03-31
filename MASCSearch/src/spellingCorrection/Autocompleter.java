package spellingCorrection;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;




public final class Autocompleter {

	public static void main(String[] args) throws IOException{
		/* create a standard analyzer */
		StandardAnalyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
		IndexWriterConfig config = new IndexWriterConfig( analyzer);
		
		
		//directory creation
        Path dirPath = Paths.get(System.getProperty("user.dir")+"/index/");
        
        Directory directory = FSDirectory.open(dirPath);
        //Path dictPath = Paths.get("/home/matteo/Scrivania/dict.txt");


		//spell checker instantiation 
		final SpellChecker sp = new SpellChecker(directory);

		Path dictPath = Paths.get("/home/matteo/Scrivania/dict.txt");
		//index the dictionary
PlainTextDictionary dictionary = new PlainTextDictionary(dictPath);
        
        sp.indexDictionary(dictionary, config, true);
       

		//your 'wrong' search
		String search = "wordl";


		//number of suggestions
		final int suggestionNumber = 5;


		//get the suggested words
		
		String[] suggestions = sp.suggestSimilar(search, suggestionNumber);


		//show the results.
		System.out.println("Your Term:" + search);


		for (String word : suggestions) {
			System.out.println("Did you mean:" + word);
		}


		//creating another misspelled search
		//search = "bava";

		suggestions = sp.suggestSimilar(search, suggestionNumber);


		System.out.println("Your Term:" + search);
		for (String word : suggestions) {
			System.out.println("Did you mean:" + word);
		} 
	}
}