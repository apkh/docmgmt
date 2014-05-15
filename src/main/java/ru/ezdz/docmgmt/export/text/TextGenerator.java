package ru.ezdz.docmgmt.export.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import ru.ezdz.docmgmt.export.DocGenerator;
import ru.ezdz.docmgmt.model.DocArticle;
import ru.ezdz.docmgmt.model.DocContent;
import ru.ezdz.docmgmt.model.DocRoot;

public class TextGenerator implements DocGenerator {

	private final DocRoot docRoot;

	public TextGenerator(DocRoot root) {
		this.docRoot = root;
	}

	public void generate(OutputStream out) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		generate(writer, docRoot, 0);
		writer.flush();
	}
	
	void generate(Writer writer, DocArticle article, int level) throws IOException {
		char[] prefix = new char[level * 2];
		for (int i = level * 2 - 1; i > 0; i--) {
			prefix[i] = ' ';
		}
		
		Iterator<DocArticle> articleIterator = article.getArticleIterator();
		while (articleIterator.hasNext()) {
			DocArticle currentArticle = articleIterator.next();
			writer.write(prefix);
			writer.write(currentArticle.getIndex());
			writer.write(". ");
			writer.write(currentArticle.getTitle());
			writer.write('\n');
			Iterator<DocContent> contentIterator = article.getContentIterator();
			while (contentIterator.hasNext()) {
				DocContent currentContent = contentIterator.next();
				writer.write(prefix);
				writer.write("  ");
				writer.write(currentContent.getData());
				writer.write('\n');
			}
			generate(writer, currentArticle, level + 1);
			writer.flush();
		}
	}

	public void generate(File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		generate(fileOutputStream);
		fileOutputStream.close();
		
	}

}
