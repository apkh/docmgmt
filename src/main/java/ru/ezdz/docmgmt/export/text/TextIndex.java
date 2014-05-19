package ru.ezdz.docmgmt.export.text;

import java.util.ArrayList;

public class TextIndex {

	ArrayList<Integer> indexList = new ArrayList<Integer>();
	
	public TextIndex() {
		indexList.add(1);
	}

	public enum MatchMode {
		NONE,
		SAME_LEVEL,
		NEXT_LEVEL,
		UPPER_LEVEL
	}

	public MatchMode match(String line) {
		int pos = 0;
		while (line.charAt(pos) == ' ') {
			pos++;
		}
		return null;
	}

}
