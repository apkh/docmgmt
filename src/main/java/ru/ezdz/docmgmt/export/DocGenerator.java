package ru.ezdz.docmgmt.export;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public interface DocGenerator {
	void generate(OutputStream out) throws IOException;

	void generate(File file) throws IOException;
}
