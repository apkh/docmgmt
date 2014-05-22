package ru.ezdz.docmgmt.text;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.ezdz.docmgmt.DocFormat;
import ru.ezdz.docmgmt.DocGenerator;
import ru.ezdz.docmgmt.DocProcessorBuilder;
import ru.ezdz.docmgmt.export.mock.MockDocRoot;
import ru.ezdz.docmgmt.model.DocRoot;

public class TextGeneratorTest {

	private DocRoot doc;
	private File file;

	@Before
	public void setUp() throws Exception {
		doc = new MockDocRoot();
		file = new File("tmpGenFile.txt");
	}

	@After
	public void tearDown() throws Exception {
		if (file.exists()) {
			file.delete();
		}
		file = null;
		doc = null;
	}

	@Test
	public void testGenerateOutputStream() throws IOException {
		DocGenerator gen = DocProcessorBuilder.createGenerator(DocFormat.TEXT);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		gen.generate(doc, out);
		String output = out.toString();
		String[] lineArray = output.split("\n");
		System.out.println(output);
		assertEquals(26, lineArray.length);
	}

	@Test
	public void testGenerateFile() throws IOException {
		DocGenerator gen = DocProcessorBuilder.createGenerator(DocFormat.TEXT);
		File file = new File("tmpGenFile.txt");
		gen.generate(doc, file);
		assertTrue(file.canRead());
		assertEquals(604, file.length());
	}

}
