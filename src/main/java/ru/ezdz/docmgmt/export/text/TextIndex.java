package ru.ezdz.docmgmt.export.text;

import java.util.ArrayList;

/** Smart object responsible for index counting
 * 
 * @author pakhoa
 *
 */
public class TextIndex {

	ArrayList<Integer> indexList = new ArrayList<Integer>();
	
	public TextIndex() {
		//indexList.add(0);
	}

	enum MatchMode {
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
		for (int i = 0; i < indexList.size(); i ++) {
			String index = indexList.get(i).toString();
			if (matchSubstr(line, index, pos)) {
				//matching sequence
				if (line.length() == pos + index.length()
						|| (i == indexList.size() - 1 && line.charAt(pos + index.length()) != ' ') 
						|| (line.charAt(pos + index.length()) != '.')) {
					return MatchMode.NONE;
				}
				pos += index.length() + 1;
			} else {
				// Not matching
				// Candidate for UPPER
				Integer expectedIndex = Integer.valueOf(indexList.get(i).intValue() + 1);
				String expectedIndexStr = expectedIndex.toString();
				if (matchSubstr(line, expectedIndexStr, pos)) {
					// it shall be last piece
					if (line.length() == pos + expectedIndexStr.length()
							|| line.charAt(pos + expectedIndexStr.length()) == ' ') {
						indexList.set(i, expectedIndex);
						if (i < indexList.size() - 1) {
							while (indexList.size() > i + 1) {
								indexList.remove(i + 1);
							}
							return MatchMode.UPPER_LEVEL;
						} else {
							return MatchMode.SAME_LEVEL;
						}
					}
				}
					
			}
		}
		return MatchMode.NONE;
	}

	private boolean matchSubstr(String line, String index, int pos) {
		return line.substring(pos, pos + index.length()).equals(index);
	}

}
