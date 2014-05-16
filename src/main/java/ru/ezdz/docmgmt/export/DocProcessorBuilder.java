package ru.ezdz.docmgmt.export;

import ru.ezdz.docmgmt.export.docx.DocXGenerator;
import ru.ezdz.docmgmt.export.text.TextGenerator;
import ru.ezdz.docmgmt.model.DocRoot;

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
}
