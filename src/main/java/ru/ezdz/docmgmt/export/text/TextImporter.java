package ru.ezdz.docmgmt.export.text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;

import ru.ezdz.docmgmt.export.DocImporter;
import ru.ezdz.docmgmt.export.DocumentBuilder;

public class TextImporter implements DocImporter {

	final static int BUF_SIZE = 512;

	public void importFrom(InputStream in, DocumentBuilder docBuilder) throws IOException {
		importFrom(new InputStreamReader(in), docBuilder);
	}

	private void importFrom(Reader reader, DocumentBuilder docBuilder) throws IOException {
		TextReader tReader = new TextReader(reader);
		TextIndex tIndex = new TextIndex();
		final String line = tReader.read();
		while (line != null) {
			TextIndex.MatchMode mode = tIndex.match(line);
			switch (mode) {
				
			}
		}
		
	}

	public void importFrom(File file, DocumentBuilder docBuilder) throws IOException {
		FileInputStream in = new FileInputStream(file);
		importFrom(in, docBuilder);
	}

}
