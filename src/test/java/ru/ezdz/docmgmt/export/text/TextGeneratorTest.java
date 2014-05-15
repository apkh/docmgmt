package ru.ezdz.docmgmt.export.text;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.ezdz.docmgmt.export.DocFormat;
import ru.ezdz.docmgmt.export.DocGenerator;
import ru.ezdz.docmgmt.export.DocGeneratorBuilder;
import ru.ezdz.docmgmt.export.mock.MockDocRoot;
import ru.ezdz.docmgmt.model.DocRoot;

public class TextGeneratorTest {

	private DocRoot doc;

	@Before
	public void setUp() throws Exception {
		doc = new MockDocRoot();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateOutputStream() throws IOException {
		DocGenerator gen = DocGeneratorBuilder.create(DocFormat.TEXT, doc);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		gen.generate(out);
		String output = out.toString();
		String[] lineArray = output.split("\n");
		System.out.println(output);
		assertEquals(58, lineArray.length);
	}

}
