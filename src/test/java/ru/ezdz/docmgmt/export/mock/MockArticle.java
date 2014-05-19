package ru.ezdz.docmgmt.export.mock;

import java.util.ArrayList;
import java.util.Iterator;

import ru.ezdz.docmgmt.model.DocParagraph;
import ru.ezdz.docmgmt.model.DocContent;
import ru.ezdz.docmgmt.model.ParagraphStyle;

public class MockArticle implements DocParagraph {

	private final String level;

	public MockArticle(String level) {
		this.level = level;
	}

	public String getIndex() {
		return level;
	}

	public String getTitle() {
		return "Mock title " + level;
	}

	public Iterator<DocContent> getContentIterator() {
		ArrayList<DocContent> result = new ArrayList<DocContent>();
		result.add(new DocContent() {
			
			public ParagraphStyle getStyle() {
				return ParagraphStyle.NORMAL;
			}
			
			public String getData() {
				return "Mock content " + level;
			}
		});
		return result.iterator();
	}

	public Iterator<DocParagraph> getArticleIterator() {
		ArrayList<DocParagraph> result = new ArrayList<DocParagraph>();
		if (level.length() < 4) {
			result.add(new MockArticle(level + ".1"));
			result.add(new MockArticle(level + ".2"));
		}
		return result.iterator();
	}

}
