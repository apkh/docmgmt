package ru.ezdz.docmgmt.export;

public interface DocumentBuilder {
	Object createDocument(String name, String content);
	Object createParagraph(Object paragraph, String number, String title, String content);
}
