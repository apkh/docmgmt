package ru.ezdz.docmgmt.export.text;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.ezdz.docmgmt.export.DocFormat;
import ru.ezdz.docmgmt.export.DocGenerator;
import ru.ezdz.docmgmt.export.DocProcessorBuilder;
import ru.ezdz.docmgmt.export.mock.MockDocRoot;
import ru.ezdz.docmgmt.model.DocRoot;

public class TextReaderTest {

	private static final String testText = 
			"ABc\n" +
			"Def\n";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStringReader() throws IOException {
		StringReader sr = new StringReader(testText);
		TextReader tr = new TextReader(sr);
		String str;
		int counter = 0;
		String[] result = new String[5];
		do {
			str = tr.read();
			result[counter] = str;
			counter++;
		}
		while (str != null && str.length() > 0 && counter < 5);
		assertEquals(3, counter);
		
	}

	@Test
	public void testStringReaderLong() throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 1000; i++) {
			sb.append('A');
		}
		StringReader sr = new StringReader(sb.toString());
		final TextReader tr = new TextReader(sr);
		assertEquals(1000, tr.read().length());
		assertEquals(0, tr.read().length());
	}

}
