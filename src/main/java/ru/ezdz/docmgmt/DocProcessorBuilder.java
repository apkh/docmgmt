package ru.ezdz.docmgmt;

import ru.ezdz.docmgmt.docx.DocXGenerator;
import ru.ezdz.docmgmt.text.TextGenerator;
import ru.ezdz.docmgmt.text.TextImporter;

public class DocProcessorBuilder {
	public static DocGenerator createGenerator(DocFormat format) {
		DocGenerator generator = null;
		switch (format) {
		case DOCX:
			generator = new DocXGenerator();
			break;
		case PDF:
			break;
		case TEXT:
			generator = new TextGenerator();
			break;
		default:
			break;
		
		}
		return generator;
		
	}

	public static TextImporter createImporter(DocFormat format) {
		TextImporter generator = null;
		switch (format) {
		case DOCX:
			break;
		case PDF:
			break;
		case TEXT:
			generator = new TextImporter();
			break;
		default:
			break;
		
		}
		return generator;
		
	}

}
