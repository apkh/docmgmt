package ru.ezdz.docmgmt.text;

import java.io.StringReader;
import java.util.ArrayList;

/** Smart object responsible for index counting
 * 
 * @author pakhoa
 *
 */
public class TextIndex {

	ArrayList<Integer> indexList = new ArrayList<Integer>();
    private String lastIndex = "";
    private String contentLine = "";
    private int skippedLevels;

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
		while (pos < line.length() && (Character.isWhitespace(line.charAt(pos)) )) {
			pos++;
		}
		for (int i = 0; i < indexList.size(); i ++) {
			String index = indexList.get(i).toString();
			if (matchSubstr(line, index, pos)) {
				//matching sequence
				if (line.length() == pos + index.length()
                        || (line.charAt(pos + index.length()) != '.')) {
                    return MatchMode.NONE;
                }
                // next symbol is always '.'
				if (i == indexList.size() - 1) {
                    int endPos = pos + index.length();
                    if (endPos + 2 <= line.length()
                        && line.substring(endPos, endPos + 2).equals(".1")) {
                        indexList.add(1);
                        contentLine = line.length() > endPos + 4 ? line.substring(endPos + 3) : "";
                        lastIndex = "1";
                        return MatchMode.NEXT_LEVEL;
                    }     else {
                        return MatchMode.NONE;
                    }
				}
				pos += index.length() + 1;
			} else {
				// Not matching
				// Candidate for SAME or UPPER
				Integer expectedIndex = Integer.valueOf(indexList.get(i).intValue() + 1);
				String expectedIndexStr = expectedIndex.toString();
				if (matchSubstr(line, expectedIndexStr, pos)) {
					// it shall be last piece
                    int expectedEnd = pos + expectedIndexStr.length();
                    if (line.length() == expectedEnd
							|| line.charAt(expectedEnd) == ' ') {
                        lastIndex = expectedIndex.toString();
						indexList.set(i, expectedIndex);
                        contentLine = line.length() >= expectedEnd + 1
                                ? line.substring(expectedEnd + 1)
                                : "";
                        if (i < indexList.size() - 1) {
                            skippedLevels = 0;
							while (indexList.size() > i + 1) {
								indexList.remove(i + 1);
                                skippedLevels++;
							}

							return MatchMode.UPPER_LEVEL;
						} else {
							return MatchMode.SAME_LEVEL;
						}
					}
				} else {
					// either last or upper level shall increment
					return MatchMode.NONE;
				}
					
			}
		}
		// We can get here iff we found the same sequence as were observed last time
		if (indexList.size() == 0 && (line.substring(pos).equals("1") || line.startsWith("1 ", pos))  ) {
            indexList.add(1);
            contentLine = (line.length() > pos + 2) ? line.substring(pos + 2) : "";
            lastIndex = "1";
            return MatchMode.NEXT_LEVEL;
        }
        if (line.length() >= pos + 2 && line.substring(pos, pos + 2).equals(".1") &&
			(line.length() == pos + 2 || line.charAt(pos + 2) == ' ')) {
            contentLine = (line.length() > pos + 2) ? line.substring(pos + 2) : "";
            lastIndex = "1";
			return MatchMode.NEXT_LEVEL;
		}
		return MatchMode.NONE;
	}

	static boolean matchSubstr(String line, String index, int pos) {
        int endIndex = pos + index.length();
        return line.length() >= endIndex &&
               line.substring(pos, endIndex).equals(index);
	}

    public String getLastIndex() {
        return lastIndex;
    }

    public String getContentLine() {
        return contentLine;
    }

    public int getSkippedLevels() {
        return skippedLevels;
    }
}
