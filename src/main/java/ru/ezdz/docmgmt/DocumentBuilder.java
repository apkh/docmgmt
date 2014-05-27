package ru.ezdz.docmgmt;

import java.util.List;

public interface DocumentBuilder {
	Object createDocument(String name, List<String> content);
	Object createParagraph(Object paragraph, String number, String title, List<String> content);
}
