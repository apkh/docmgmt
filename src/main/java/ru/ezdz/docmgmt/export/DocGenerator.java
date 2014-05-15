package ru.ezdz.docmgmt.export;

import java.io.IOException;
import java.io.OutputStream;

public interface DocGenerator {
	void generate(OutputStream out) throws IOException;
}
