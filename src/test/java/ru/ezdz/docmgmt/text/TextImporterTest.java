package ru.ezdz.docmgmt.text;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Test;
import ru.ezdz.docmgmt.DocumentBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextImporterTest extends TestCase {
    final String text =
            "Test doc\n" +
            "This is test doc\n" +
            "1 Mock title 1\n" +
            "  1.1 Mock title 1.1\n" +
            "    Mock content 1\n" +
            "    1.1.1 Mock title 1.1.1\n" +
            "      Mock content 1.1\n" +
            "    1.1.2 Mock title 1.1.2\n" +
            "      Mock content 1.3\n" +
            "  1.2 Mock title 1.2\n" +
            "    Mock content 1.6\n" +
            "    1.2.1 Mock title 1.2.1\n" +
            "      Mock content 1.2\n" +
            "    1.2.2 Mock title 1.2.2\n" +
            "      Mock content 1.4\n" +
            "2 Mock title 2\n";

    private Object docObj = "docTag";
    private Object p1 = "p1Tag";
    private Object p11 = "p11Tag";
    private Object p12 = "p12Tag";
    private Object junk = "junkTag";

    @Test
    public void testHierTextImport() throws IOException {
        TextImporter textImporter = new TextImporter();
        StringReader sr = new StringReader(text);
        DocumentBuilder mockReader = EasyMock.createStrictMock(DocumentBuilder.class);

        EasyMock.expect(mockReader.createDocument("Test doc", list("This is test doc"))).andReturn(docObj);
        EasyMock.expect(mockReader.createParagraph(docObj, "1", "Mock title 1", Collections.EMPTY_LIST)).andReturn(p1);
        EasyMock.expect(mockReader.createParagraph(p1, "1", "Mock title 1.1", list("Mock content 1"))).andReturn(p11);
        EasyMock.expect(mockReader.createParagraph(p11, "1", "Mock title 1.1.1", list("Mock content 1.1"))).andReturn(junk);
        EasyMock.expect(mockReader.createParagraph(p11, "2", "Mock title 1.1.2", list("Mock content 1.3"))).andReturn(junk);
        EasyMock.expect(mockReader.createParagraph(p1, "2", "Mock title 1.2", list("Mock content 1.6"))).andReturn(p12);
        EasyMock.expect(mockReader.createParagraph(p12, "1", "Mock title 1.2.1", list("Mock content 1.2"))).andReturn(junk);
        EasyMock.expect(mockReader.createParagraph(p12, "2", "Mock title 1.2.2", list("Mock content 1.4"))).andReturn(junk);
        EasyMock.expect(mockReader.createParagraph(docObj, "2", "Mock title 2", Collections.EMPTY_LIST)).andReturn(junk);

        EasyMock.replay(mockReader);
        textImporter.importFrom(sr, mockReader);

        EasyMock.verify(mockReader);
    }

    @Test
    public void testCyrLetters() throws IOException {
        TextImporter textImporter = new TextImporter();
        InputStream resourceAsStream = getClass().getResourceAsStream("/cyr_file.txt");
        DocumentBuilder documentBuilder = EasyMock.createMock(DocumentBuilder.class);
        textImporter.importFrom(resourceAsStream, documentBuilder);

    }

    private <T> List<T> list(T... values) {
        List<T> result = new ArrayList<T>(values.length);
        for (T val: values) {
            result.add(val);
        }
        return result;
    }
}