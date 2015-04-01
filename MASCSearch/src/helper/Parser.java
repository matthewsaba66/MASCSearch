package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jwat.common.Diagnosis;
import org.jwat.warc.WarcReader;
import org.jwat.warc.WarcReaderFactory;
import org.jwat.warc.WarcRecord;

public class Parser {

	public static void main(String[] args) throws IOException{
		getIndex();
	}

	public static IndexWriter getIndex() throws IOException{

		/* create a standard analyzer */
		StandardAnalyzer analyzer = new StandardAnalyzer( CharArraySet.EMPTY_SET);

		/* create the index in the pathToFolder or in RAM (choose one) */
		//File file = new File("pathToFolder");
		//Path path = Paths.get(System.getProperty("user.dir")+"/index/");
		Path path = Paths.get("/home/matteo/Scrivania/index/");
		Directory index = FSDirectory.open(path);
		//Directory index =new RAMDirectory();

		/* set an index config */
		IndexWriterConfig config = new IndexWriterConfig( analyzer);
		config.setOpenMode(OpenMode.CREATE);
		/* create the writer */
		IndexWriter writer = new IndexWriter(index, config);

		/*get file list of warc's to analyze*/
		List<String> fileList = listFilesForFolder(new File("/home/matteo/CRAWL/Decompressi/cCrawl/"));

		long records = 0;

		for (String warc : fileList){

			InputStream in = new FileInputStream(new File("/home/matteo/CRAWL/Decompressi/cCrawl/" + warc ));


			int errors = 0;

			WarcReader reader = WarcReaderFactory.getReader( in );
			WarcRecord record;
			long j = 0;
			System.out.println("analizzo documento: " + warc);
			while ((record = reader.getNextRecord()) != null /* && j!=300*/) {

				++records;

				if (record.diagnostics.hasErrors()) {
					Document d = printRecord(record);
					writer.addDocument(d);
					j++;
					System.out.println(j + " record ");
					//printRecordErrors(record);
					//errors += record.diagnostics.getErrors().size();
				}
			}
			System.out.println("--------------");
			System.out.println("       Records: " + records);
			//System.out.println("        Errors: " + errors);
			System.out.println(warc);
			reader.close();
			in.close();

		}

		writer.close();

		return writer;

	}


	public static List<String> listFilesForFolder(final File folder) {

		List<String> fileList = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				fileList.add(fileEntry.getName());
			}
		}
		return fileList;
	}

	private static Document printRecord(WarcRecord record) throws IOException {
		// TODO Auto-generated method stub
		Document doc = new Document();
		//if else null
		if (record.header.contentTypeStr != null){
			doc.add(new StringField("type", record.header.contentTypeStr, Field.Store.YES));
		}
		else doc.add(new StringField("type","Type non presente", Field.Store.YES));

		if(record.header.warcTargetUriUri!=null){
			Field url = new TextField("url", record.header.warcTargetUriUri.toString(), Field.Store.YES);
			doc.add(url);
		}
		else doc.add(new StringField("url","Url non presente", Field.Store.YES));
		//doc.add(new TextField("title",getTitle(record), Field.Store.YES));
		String[] prova = getPayload(record);
		Field title = new TextField("title",prova[0], Field.Store.YES);
		title.setBoost(2f);
		doc.add(title);
		doc.add(new TextField("body",prova[1], Field.Store.YES));
		doc.add(new TextField("payload",prova[2], Field.Store.YES));


		return doc;
	}

	private static String[] getPayload(WarcRecord record) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader payin = new BufferedReader(new InputStreamReader(record.getPayload().getInputStream()));
		String line = "";
		String nextLine;
		while((nextLine = payin.readLine()) != null) {
			line = line.concat(nextLine + "\n");
		}

		//System.out.println("LINE: ---"+line);

		org.jsoup.nodes.Document doc = Jsoup.parse(line);
		//line = doc.toString();



		String[] text = new String[3];
		text[0] = "Title: ";
		text[2] = "Payload: ";

		for(Element div : doc.select("title")){
			text[0] = text[0].concat(div.text()+"\n");
			text[2] = text[2].concat(div.text()+"\n");

		}
		text[1] = "Body: ";
		for(Element div : doc.select("body")){
			text[1] = text[1].concat(div.text()+"\n");
			text[2] = text[2].concat(div.text()+"\n");
		}

		return text;
	}

	public static void printRecordErrors(WarcRecord record) {
		System.out.println("---- Errors:");
		if (record.diagnostics.hasErrors()) {
			List<Diagnosis> errorCol = record.diagnostics.getErrors();
			if (errorCol != null && errorCol.size() > 0) {
				Iterator<Diagnosis> iter = errorCol.iterator();
				while (iter.hasNext()) {
					Diagnosis error = iter.next();
					System.out.println( "Type:" + error.type );
					System.out.println( "Entity: "+ error.entity );
					for( String info : error.information ) {
						System.out.println( "Info: "+ info );
					}
				}
			}
		}
	}

}
