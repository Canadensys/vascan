package net.canadensys.dataportal.vascan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.canadensys.dataportal.vascan.constant.Rank;
import net.canadensys.dataportal.vascan.impl.PropertyMapHelper;
import net.canadensys.dataportal.vascan.taxonomy.TaxonRankEnum;

import org.junit.Test;

public class PropertyMapHelperTest {
	
	@Test
	public void testGetRankRangeForDisplay(){
		TaxonRankEnum[] rankToDisplay = null;
		for(TaxonRankEnum currEnum: TaxonRankEnum.values()){
			rankToDisplay = PropertyMapHelper.getRankRangeForDisplay(currEnum);
			if(rankToDisplay != null){
				validateRankToDisplay(rankToDisplay);
			}
			else{
				assertEquals(TaxonRankEnum.VARIETY, currEnum);
			}
		}
		
	}
	
	@Test
	public void testGetRankLabelRange(){
		String[] rankToDisplay = null;
		for(TaxonRankEnum currEnum: TaxonRankEnum.values()){
			rankToDisplay = PropertyMapHelper.getRankLabelRange(currEnum);
			if(rankToDisplay != null){
				validateTaxonRankEnumLabels(rankToDisplay);
			}
			else{
				assertEquals(TaxonRankEnum.VARIETY, currEnum);
			}
		}
	}
	
	/**
	 * Ensure no rank is skipped and that taxonomy is preserved.
	 * @param rankToDisplay
	 */
	private void validateRankToDisplay(TaxonRankEnum[] rankToDisplay){
		List<TaxonRankEnum> orderedRanks = new ArrayList<TaxonRankEnum>(TaxonRankEnum.getOrderedRanks());
		
		int startingIdx = orderedRanks.indexOf(rankToDisplay[0]);
		for(int i = 0; i < rankToDisplay.length;i++){
			assertEquals(orderedRanks.get(startingIdx+i), rankToDisplay[i]);
		}
	}

	/**
	 * Ensure returned rank labels can be used against Rank class.
	 * @param rankToDisplay
	 */
	private void validateTaxonRankEnumLabels(String[] rankToDisplay){
		for(int i = 0; i < rankToDisplay.length;i++){
			assertTrue(Rank.getIdFromLabel(rankToDisplay[i]) != Rank.UNDEFINED);
		}
	}
}
