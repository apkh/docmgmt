package ru.ezdz.docmgmt.export;

import ru.ezdz.docmgmt.export.docx.DocXGenerator;
import ru.ezdz.docmgmt.export.text.TextGenerator;
import ru.ezdz.docmgmt.model.DocRoot;

public class DocGeneratorBuilder {
	public static DocGenerator create(DocFormat format, DocRoot root) {
		DocGenerator generator = null;
		switch (format) {
		case DOCX:
			generator = new DocXGenerator(root);
			break;
		case PDF:
			break;
		case TEXT:
			generator = new TextGenerator(root);
			break;
		default:
			break;
		
		}
		return generator;
		
	}
}
