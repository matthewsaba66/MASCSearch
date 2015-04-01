package actions;

import java.io.FileWriter;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class HighLighterUtil {
	private static final String text =
			"In this section we'll show you how to make the simplest " +
					"programmatic query, searching for a single term, and then " +
					"we'll see how to use QueryParser to accept textual queries. " +
					"In the sections that follow, we�ll take this simple example " +
					"further by detailing all the query types built into Lucene. " +
					"We begin with the simplest search of all: searching for all " +
					"documents that contain a single term.";
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: HighlightIt <filename-out>");
			System.exit(-1);
		}
		String filename =
				args[0];
		String searchText = "term";
		QueryParser parser = new QueryParser("f",
				new StandardAnalyzer(CharArraySet.EMPTY_SET));
		Query query = parser.parse(searchText);
		SimpleHTMLFormatter formatter =
				new SimpleHTMLFormatter("<span class=\"highlight\">",
						"</span>");
		TokenStream tokens = new StandardAnalyzer()
		.tokenStream("f", new StringReader(text));
		QueryScorer scorer = new QueryScorer(query, "f");
		Highlighter highlighter
		= new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(
				new SimpleSpanFragmenter(scorer));
		String result =
				highlighter.getBestFragments(tokens, text, 3, "...");
		FileWriter writer = new FileWriter(filename);
		writer.write("<html>");
		writer.write("<style>\n" +
				".highlight {\n" +
				" background: yellow;\n" +
				"}\n" +
				"</style>");
		writer.write("<body>");
		writer.write(result);
		writer.write("</body></html>");
		writer.close();
	}


	public void testHits() throws Exception {
		Path path = Paths.get("C:/Users/orangepc/git/MASCSearch/MASCSearch/index/");
		Directory index = FSDirectory.open(path);
		IndexReader reader1 = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader1);
		TermQuery query = new TermQuery(new Term("title", "action"));
		TopDocs hits = searcher.search(query, 10);
		QueryScorer scorer = new QueryScorer(query, "title");
		Highlighter highlighter = new Highlighter(scorer);
		highlighter.setTextFragmenter(
				new SimpleSpanFragmenter(scorer));
		Analyzer analyzer = new SimpleAnalyzer();
		for (ScoreDoc sd : hits.scoreDocs) {
			Document doc = searcher.doc(sd.doc);
			String title = doc.get("title");
			TokenStream stream =
					TokenSources.getAnyTokenStream(searcher.getIndexReader(),
							sd.doc,
							"title",
							doc,
							analyzer);
			String fragment =
					highlighter.getBestFragment(stream, title);
			System.out.println(fragment);
		}
	}
}