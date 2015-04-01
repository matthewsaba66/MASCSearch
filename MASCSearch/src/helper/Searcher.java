package helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import spellingCorrection.SimpleSuggestionSpeller;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
    private Searcher didYouMeanParser;

	
	public static SearchResult mkQuery(String query) throws Exception{
        long startTime = System.currentTimeMillis();

		/* create a standard analyzer */
		StandardAnalyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);

		/* create the index in the pathToFolder or in RAM (choose one) */
		//File file1 = new File("pathToFolder");
		
		Path path = Paths.get("C:/Users/orangepc/git/MASCSearch/MASCSearch/index/");
		Directory index = FSDirectory.open(path);
		//Directory index =new RAMDirectory();
		
		/* set the maximum number of results */
		int maxHits = 30;

		/* open a directory reader and create searcher and topdocs */
		IndexReader reader1 = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader1);
		TopScoreDocCollector collector =
				TopScoreDocCollector.create(maxHits);

		/* create the query parser */
		QueryParser qp = new QueryParser("payload", analyzer);


		/* query string */
		Query q = qp.parse(query);

		
		/* search into the index */
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
		
		//return hits;
		long endTime = System.currentTimeMillis();
		//System.out.println("Found " + hits.length + " hits.");
		
		ArrayList<Document> docList = new ArrayList<Document>();
		
		for(int i=0;i<hits.length;++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			docList.add(d);
			
			//System.out.println("url: " + d.get("url")+ "\n" + d.get("title")+ d.get("body"));
			//System.out.println("-------------------------------------------\n\n\n\n");
		}
		String suggestedQuery = spellingCorrection.SimpleSuggestionSpeller.spellingCorrection(query);
		
		SearchResult searchResult = new SearchResult
				(docList, hits.length, endTime-startTime, query, suggestedQuery);
		return searchResult;
	}

}
