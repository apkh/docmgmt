package ru.ezdz.docmgmt.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import ru.ezdz.docmgmt.DocGenerator;
import ru.ezdz.docmgmt.model.DocParagraph;
import ru.ezdz.docmgmt.model.DocContent;
import ru.ezdz.docmgmt.model.DocRoot;

public class TextGenerator implements DocGenerator {


	public TextGenerator() {
	}

	public void generate(DocRoot docRoot, OutputStream out) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writeSingleArticle(writer, new char[0], docRoot);
		generate(writer, docRoot, 0);
		writer.flush();
	}
	
	void generate(Writer writer, DocParagraph article, int level) throws IOException {
		char[] prefix = new char[level * 2];
		for (int i = level * 2 - 1; i > 0; i--) {
			prefix[i] = ' ';
		}
		
		Iterator<DocParagraph> articleIterator = article.getArticleIterator();
		while (articleIterator.hasNext()) {
			DocParagraph currentArticle = articleIterator.next();
			writer.write(prefix);
			writer.write(currentArticle.getIndex());
			writer.write(" ");
            writeSingleArticle(writer, prefix, currentArticle);
			generate(writer, currentArticle, level + 1);
			writer.flush();
		}
	}

    private void writeSingleArticle(Writer writer, char[] prefix, DocParagraph currentArticle) throws IOException {
        writer.write(currentArticle.getTitle());
        writer.write('\n');
        Iterator<DocContent> contentIterator = currentArticle.getContentIterator();
        while (contentIterator.hasNext()) {
            DocContent currentContent = contentIterator.next();
            writer.write(prefix);
            writer.write("  ");
            writer.write(currentContent.getData());
            writer.write('\n');
        }
    }

    public void generate(DocRoot docRoot, File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		generate(docRoot, fileOutputStream);
		fileOutputStream.close();
		
	}

}
