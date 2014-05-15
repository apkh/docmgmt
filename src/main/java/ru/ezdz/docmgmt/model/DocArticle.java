package ru.ezdz.docmgmt.model;

import java.util.Iterator;

public interface DocArticle {
	String getIndex();
	String getTitle();
	Iterator<DocContent> getContentIterator();
	Iterator<DocArticle> getArticleIterator();
}
