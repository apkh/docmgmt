package ru.ezdz.docmgmt.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import ru.ezdz.docmgmt.DocGenerator;
import ru.ezdz.docmgmt.model.DocParagraph;
import ru.ezdz.docmgmt.model.DocContent;
import ru.ezdz.docmgmt.model.DocRoot;

public class TextGenerator implements DocGenerator {

    ArrayList<Integer> hierIndex = new ArrayList<Integer>(10);

	public TextGenerator() {
	}

	public void generate(DocRoot docRoot, OutputStream out) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        hierIndex.clear();
        try {
            writeSingleArticle(writer, new char[0], docRoot);
            hierIndex.add(1);
            generate(writer, docRoot, 0);
            writer.flush();
        } finally {
            writer.close();
        }
	}
	
	void generate(Writer writer, DocParagraph article, int level) throws IOException {
		char[] prefix = new char[level * 2];
		Arrays.fill(prefix, ' ');
		
		Iterator<? extends DocParagraph> articleIterator = article.getArticleIterator();
		while (articleIterator.hasNext()) {
			DocParagraph currentArticle = articleIterator.next();
			writer.write(prefix);
			writer.write(calculateIndex());
			writer.write(" ");
            writeSingleArticle(writer, prefix, currentArticle);
            hierIndex.add(level + 1, 1);
			generate(writer, currentArticle, level + 1);
            hierIndex.remove(level + 1);
            hierIndex.set(level, hierIndex.get(level) + 1);
			writer.flush();
		}
	}

    private String calculateIndex() {
        StringBuilder sb = new StringBuilder(3 * hierIndex.size());
        for (int index = 0; index < hierIndex.size(); index++) {
            if (index != 0) {
                sb.append('.');
            }
            sb.append(hierIndex.get(index));
        }
        return sb.toString();
    }

    private void writeSingleArticle(Writer writer, char[] prefix, DocParagraph currentArticle) throws IOException {
        writer.write(currentArticle.getTitle());
        writer.write('\n');
        Iterator<? extends DocContent> contentIterator = currentArticle.getContentIterator();
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
		try {
            generate(docRoot, fileOutputStream);
        } finally {
            fileOutputStream.close();
        }
	}

}
