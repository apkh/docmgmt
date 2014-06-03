package ru.ezdz.docmgmt.text;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Test;
import ru.ezdz.docmgmt.DocumentBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamplesImporterTest extends TestCase {

    private Object doc = new Object();
    private Object junk = new Object();

    @Test
    public void testCyrLetters() throws IOException {
        TextImporter textImporter = new TextImporter();
        InputStream resourceAsStream = getClass().getResourceAsStream("/examples/1f2e72b0.txt");
        DocumentBuilder documentBuilder = EasyMock.createMock(DocumentBuilder.class);
        EasyMock.expect(documentBuilder.createDocument("v1", Collections.EMPTY_LIST)).andReturn(doc);
        EasyMock.expect(documentBuilder.createParagraph(doc, "1", "fgtr", Collections.EMPTY_LIST)).andReturn(junk);
        EasyMock.expect(documentBuilder.createParagraph(doc, "2", "kjlj", Collections.EMPTY_LIST)).andReturn(junk);
        EasyMock.expect(documentBuilder.createParagraph(doc, "3", "ytyu", list("1 kkjg"))).andReturn(junk);

        EasyMock.replay(documentBuilder);

        textImporter.importFrom(resourceAsStream, documentBuilder);
        EasyMock.verify(documentBuilder);

    }

    private <T> List<T> list(T... values) {
        List<T> result = new ArrayList<T>(values.length);
        for (T val : values) {
            result.add(val);
        }
        return result;
    }
}

