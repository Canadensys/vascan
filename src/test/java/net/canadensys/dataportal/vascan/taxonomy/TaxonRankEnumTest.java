package net.canadensys.dataportal.vascan.taxonomy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TaxonRankEnumTest {
	
	@Test
	public void testComparator(){
		List<TaxonRankEnum> rankList = new ArrayList<TaxonRankEnum>();
		rankList.add(TaxonRankEnum.VARIETY);
		rankList.add(TaxonRankEnum.GENUS);
		rankList.add(TaxonRankEnum.CLASS);
		
		Collections.sort(rankList, new TaxonRankEnum.TaxonRankEnumComparator());
		
		assertEquals(TaxonRankEnum.CLASS,rankList.get(0));
		assertEquals(TaxonRankEnum.GENUS,rankList.get(1));
		assertEquals(TaxonRankEnum.VARIETY,rankList.get(2));
	}

}
