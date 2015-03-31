package spellingCorrection;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;

public class NGramAnalyzer extends Analyzer {
	private int nlenght;
	public NGramAnalyzer(int nlenght) {
		this.nlenght = nlenght;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		//Reader reader = new Reader();
		Tokenizer tokenizer = new KeywordTokenizer();
		TokenFilter filterLowercase = new LowerCaseFilter( tokenizer);
		TokenFilter filterNgrams = new NGramTokenFilter(filterLowercase, nlenght, nlenght);
		TokenStreamComponents tsc = new TokenStreamComponents(tokenizer, filterNgrams);
		return tsc;
	}
}