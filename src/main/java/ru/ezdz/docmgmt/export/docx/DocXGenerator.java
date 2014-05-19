package ru.ezdz.docmgmt.export.docx;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import ru.ezdz.docmgmt.export.DocGenerator;
import ru.ezdz.docmgmt.model.DocParagraph;
import ru.ezdz.docmgmt.model.DocRoot;

public class DocXGenerator implements DocGenerator {


	public DocXGenerator() {
	}

	public void generate(DocRoot doc, OutputStream out) {
		generate(out, doc, 0);
	}
	
	public void generate(OutputStream out, DocParagraph article, int level) {
		Iterator<DocParagraph> articleIterator = article.getArticleIterator();
		while (articleIterator.hasNext()) {
			
		}
	}

	public void generate(DocRoot doc, File file) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
