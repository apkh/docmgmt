package ru.ezdz.docmgmt.export;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import ru.ezdz.docmgmt.model.DocRoot;

public interface DocGenerator {
	void generate(DocRoot doc, OutputStream out) throws IOException;

	void generate(DocRoot doc, File file) throws IOException;
}
