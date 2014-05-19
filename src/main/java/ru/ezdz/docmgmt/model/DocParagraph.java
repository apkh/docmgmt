package ru.ezdz.docmgmt.model;

import java.util.Iterator;

public interface DocParagraph {
	String getIndex();
	String getTitle();
	Iterator<DocContent> getContentIterator();
	Iterator<DocParagraph> getArticleIterator();
}
