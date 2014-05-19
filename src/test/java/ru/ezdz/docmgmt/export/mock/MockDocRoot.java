package ru.ezdz.docmgmt.export.mock;

import java.util.ArrayList;
import java.util.Iterator;

import ru.ezdz.docmgmt.model.DocParagraph;
import ru.ezdz.docmgmt.model.DocContent;
import ru.ezdz.docmgmt.model.DocRoot;

public class MockDocRoot implements DocRoot {

	public String getIndex() {
		return "0";
	}

	public String getTitle() {
		return "Test Doc";
	}

	public Iterator<DocContent> getContentIterator() {
		ArrayList<DocContent> array = new ArrayList<DocContent>();
		return array.iterator();
	}

	public Iterator<DocParagraph> getArticleIterator() {
		ArrayList<DocParagraph> array = new ArrayList<DocParagraph>();
		array.add(new MockArticle("1"));
		array.add(new MockArticle("2"));
		return array.iterator();
	}

}
