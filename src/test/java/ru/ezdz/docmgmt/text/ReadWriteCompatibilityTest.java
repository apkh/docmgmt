package ru.ezdz.docmgmt.text;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.ezdz.docmgmt.DocFormat;
import ru.ezdz.docmgmt.DocGenerator;
import ru.ezdz.docmgmt.DocProcessorBuilder;
import ru.ezdz.docmgmt.DocumentBuilder;
import ru.ezdz.docmgmt.export.mock.MockDocRoot;
import ru.ezdz.docmgmt.model.DocRoot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by pakhoa on 5/27/2014.
 */
public class ReadWriteCompatibilityTest {

    private DocRoot doc;
    private File file;

    @Before
    public void setUp() throws Exception {
        doc = new MockDocRoot();
        file = new File("tmpGenFile1.txt");
    }

    @After
    public void tearDown() throws Exception {
        if (file.exists()) {
            file.delete();
        }
        file = null;
        doc = null;
    }

    static int counter = 0;

    @Test
    public void testWR() throws IOException {
        DocGenerator gen = DocProcessorBuilder.createGenerator(DocFormat.TEXT);
        File file = new File("tmpGenFile.txt");
        gen.generate(doc, file);

        FileReader fr = new FileReader(file);
        TextImporter textImporter = new TextImporter();
        DocumentBuilder mockBuilder = new DocumentBuilder() {
            @Override
            public Object createDocument(String name, List<String> content) {
                return "docTag";
            }

            @Override
            public Object createParagraph(Object paragraph, String number, String title, List<String> content) {
                counter++;
//                System.out.println(number + ":" + title);
                return "p" + counter +"Tag";
            }
        };
        textImporter.importFrom(fr, mockBuilder);
        assertEquals(14, counter);
    }
}
