package ru.ezdz.docmgmt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public interface DocImporter {

	public void importFrom(InputStream in, DocumentBuilder docBuilder) throws IOException;
	public void importFrom(File file, DocumentBuilder docBuilder) throws IOException;

}
