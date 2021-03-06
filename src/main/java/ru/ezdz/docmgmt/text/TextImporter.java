package ru.ezdz.docmgmt.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.ezdz.docmgmt.DocImporter;
import ru.ezdz.docmgmt.DocumentBuilder;

public class TextImporter implements DocImporter {

    final static int BUF_SIZE = 512;
    private static Logger logger = LoggerFactory.getLogger(TextImporter.class);
    Optional<String> content = Optional.empty();
    List<String> contentList = new ArrayList<String>(10);
    private Optional<String> index;
    private String title;

    public void importFrom(InputStream in, DocumentBuilder docBuilder) throws IOException {
		importFrom(new InputStreamReader(in), docBuilder);
	}

	public void importFrom(Reader reader, DocumentBuilder docBuilder) throws IOException {
		TextReader tReader = new TextReader(reader);
		TextIndex tIndex = new TextIndex();
        ParseState state = ParseState.BEGIN;
        title = "";
        index = Optional.empty();
        int skippedLevels;
        Stack<Object> docContext = new Stack<Object>();
		while (!tReader.isEos()) {
            String line = tReader.read();
            if (line == null) {
                break;
            }
            logger.debug(line);
//            System.out.println(">>" +line);
			TextIndex.MatchMode mode = tIndex.match(line);
            skippedLevels = tIndex.getSkippedLevels();
            String trimmedLine = line.trim();
			switch (state) {
                case BEGIN:
                    if (trimmedLine.length() != 0) {
                        title = trimmedLine;
                        content = Optional.empty();
                        contentList.clear();
                        state = ParseState.HEADER;
                    }
                    break;
                case HEADER:
                    if (mode == TextIndex.MatchMode.NEXT_LEVEL) {
                        // first paragraph detected
                        if (content.isPresent()) {
                            contentList.add(content.get());
                            content = Optional.empty();
                        }
                        docContext.push(docBuilder.createDocument(title, contentList));
                        processParagraph(tIndex);
                        state = ParseState.PARAGRAPH;
                    } else {
                        processContent(trimmedLine);
                    }
                    break;

                case PARAGRAPH:
                    if (mode == TextIndex.MatchMode.NONE) {
                        processContent(trimmedLine);
                        break;
                    }
                    if (content.isPresent()) {
                        contentList.add(content.get());
                        /// TODO cleanup content???
                    }
                    Object newPar = docBuilder.createParagraph(docContext.peek(), index.get(), title, contentList);
//                    System.out.println(String.format("p: %s %s %s %s", docContext.peek(), index, title, contentList.toString()));

                    switch (mode) {
                        case NEXT_LEVEL:
                            docContext.push(newPar);
//                            System.out.println("down:" + newPar);
                            break;
                        case UPPER_LEVEL:
                            for (int i = 0; i < skippedLevels; i++) {
                                docContext.pop();
                            }
//                            System.out.println("up:" + newPar + " by " + skippedLevels);
                            break;
                    }
                    processParagraph(tIndex);
                    break;

                case CONTENT:
                    break;
            }
		}
        if (index.isPresent()) {
            docBuilder.createParagraph(docContext.peek(), index.get(), title, contentList);
        }
		
	}

    private void processParagraph(TextIndex tIndex) {
        index = Optional.of(tIndex.getLastIndex());
        title = tIndex.getContentLine();
        content = Optional.empty();
        contentList.clear();
    }

    private void processContent(String trimmedLine) {
        if (trimmedLine.length() == 0) {
            if (content.isPresent()) {
                contentList.add(content.get());
                content = Optional.empty();
            }
        } else {
            content = Optional.of((content.isPresent()
                    ? content.get() + '\n' : "") + trimmedLine);
        }
    }

    public void importFrom(File file, DocumentBuilder docBuilder) throws IOException {
		FileInputStream in = new FileInputStream(file);
		try {
            importFrom(in, docBuilder);
        } finally {
            in.close();
        }
    }

    private enum ParseState {
        BEGIN,
        HEADER,
        PARAGRAPH,
        CONTENT
    }
}
