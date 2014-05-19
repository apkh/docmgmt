package ru.ezdz.docmgmt.export.text;

import static org.junit.Assert.assertEquals;

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
	public void testSameLevel1() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("3.1"));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("3"));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("4"));
	}

	@Test
	public void testSameLevel2() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		ti.indexList.add(5);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("1"));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2"));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.4"));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5"));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.4.1"));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5.2"));
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.6.1"));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("2.6"));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("2.7"));
	}
	
	@Test
	public void testUpperLevel2() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		ti.indexList.add(5);
		assertEquals(TextIndex.MatchMode.UPPER_LEVEL, ti.match("3"));
		assertEquals(TextIndex.MatchMode.SAME_LEVEL, ti.match("4"));
	}
	
	@Test
	public void testUpperLevel3() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		ti.indexList.add(5);
		ti.indexList.add(7);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5"));
		assertEquals(TextIndex.MatchMode.UPPER_LEVEL, ti.match("2.6"));
		ti.indexList.add(7);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5"));
		assertEquals(TextIndex.MatchMode.UPPER_LEVEL, ti.match("3"));
	}
	
	@Test
	public void testBelowLevel3() throws IOException {
		TextIndex ti = new TextIndex();
		ti.indexList.add(2);
		ti.indexList.add(5);
		assertEquals(TextIndex.MatchMode.NONE, ti.match("2.5.2"));
		assertEquals(TextIndex.MatchMode.NEXT_LEVEL, ti.match("2.5.1"));
	}
}
