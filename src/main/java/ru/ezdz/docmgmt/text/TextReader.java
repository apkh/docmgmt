package ru.ezdz.docmgmt.text;

import java.io.IOException;
import java.io.Reader;

class TextReader {

	private final Reader reader;
	char[] cbuf = new char[TextImporter.BUF_SIZE];
	int head = 0;
	int tail = 0;
	StringBuilder sb = new StringBuilder();
    private boolean eos = false;

    public TextReader(Reader reader) {
		this.reader = reader;
	}

    public boolean ready() throws IOException {
        return reader.ready();
    }

    public boolean isEos() {
        return eos;
    }

	public String read() throws IOException {
		if (!reader.ready() && head > tail) {
			return null;
		}
		sb = new StringBuilder(); // or clear is possible
		do {
			if (head >= tail) {
				tail = reader.read(cbuf);
				head = 0;
				if (tail <= 0) {
                    eos = true;
					return sb.toString();
				}
			}
			int crpos = getCRPos();
			if (crpos >= 0) {
				sb.append(cbuf, head, crpos - head);
				head = crpos + 1;
				return sb.toString();
			}
			sb.append(cbuf, head, tail - head);
			head = tail;
		} while (true);
	}

	private int getCRPos() {
		for (int i = head; i < tail; i++) {
			if (cbuf[i] == '\n') {
				return i;
			}
		}
		return -1;
	}

}