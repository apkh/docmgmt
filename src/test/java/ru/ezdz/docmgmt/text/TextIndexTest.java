package ru.ezdz.docmgmt.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextIndexTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

    @Test
    public void testEmptyString() throws IOException {
        TextIndex ti = new TextIndex();
        ti.indexList.add(2);
        assertEquals(TextIndex.MatchMode.NONE, ti.match(""));
    }

    @Test
        public void testSameLevel1() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("3.1" + suffix()));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("3" + suffix()));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("4" + suffix()));
	}

    private String suffix() {
        return "";
    }

    @Test
	public void testSameLevel2() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		ti.indexList.add(5);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("1" + suffix()));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2" + suffix()));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.4" + suffix()));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5" + suffix()));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.4.1" + suffix()));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5.2" + suffix()));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.6.1" + suffix()));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("2.6" + suffix()));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("2.7" + suffix()));
	}
	
	@Test
	public void testUpperLevel2() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		ti.indexList.add(5);
		assertEquals(TextIndex.MatchMode.UPPER_LEVEL, ti.match("3" + suffix()));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("4" + suffix()));
	}
	
	@Test
	public void testUpperLevel3() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		ti.indexList.add(5);
		ti.indexList.add(7);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5" + suffix()));
		assertEquals(TextIndex.MatchMode.UPPER_LEVEL, ti.match("2.6" + suffix()));
		ti.indexList.add(7);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5" + suffix()));
		assertEquals(TextIndex.MatchMode.UPPER_LEVEL, ti.match("3" + suffix()));
	}

    @Test
    public void testBelowLevel3() throws IOException {
        TextIndex ti = new TextIndex();
        ti.indexList.add(2);
        ti.indexList.add(5);
        assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5.2" + suffix()));
        assertEquals(TextIndex.MatchMode.NEXT_LEVEL, ti.match("2.5.1" + suffix()));
        assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("2.5.2" + suffix()));
        assertEquals(TextIndex.MatchMode.NEXT_LEVEL, ti.match("2.5.2.1" + suffix()));
    }

    @Test
    public void testComplexDoc() throws IOException {
        TextIndex ti = new TextIndex();
//        assertEquals(TextIndex.MatchMode.NONE, ti.match("0" + suffix()));
        assertEquals(TextIndex.MatchMode.NEXT_LEVEL, ti.match("1" + suffix()));
        assertEquals(TextIndex.MatchMode.NONE, ti.match("1.2" + suffix()));
        assertEquals(TextIndex.MatchMode.NEXT_LEVEL, ti.match("1.1" + suffix()));
        assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("1.2" + suffix()));
        assertEquals(TextIndex.MatchMode.UPPER_LEVEL, ti.match("2" + suffix()));
        assertEquals(TextIndex.MatchMode.NEXT_LEVEL, ti.match("2.1" + suffix()));
        assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("2.2" + suffix()));
        assertEquals(TextIndex.MatchMode.NEXT_LEVEL, ti.match("2.2.1" + suffix()));
    }

    @Test
    public void testMatchSubs() {
        assertTrue(TextIndex.matchSubstr("2.5.2", "2", 4));
    }
}
