package ru.ezdz.docmgmt.model;

import java.util.Iterator;

public interface DocParagraph {
	String getIndex();
	String getTitle();
	Iterator<? extends DocContent> getContentIterator();
	Iterator<? extends DocParagraph> getArticleIterator();
}
