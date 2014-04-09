package net.canadensys.dataportal.vascan.query;

import net.canadensys.dataportal.vascan.dao.query.RegionQueryPart;

/**
 * Checklist query container object.
 * @author cgendreau
 *
 */
public class ChecklistQuery {
	
    private RegionQueryPart regionQueryPart;

	private String habit;

    private int taxonId = -1;
    
    private String[] distributionStatus;
    private String[] rank;
    
    private boolean hybrids;
    
    private String sort;

	public void setRegionQueryPart(RegionQueryPart regionQueryPart) {
		this.regionQueryPart = regionQueryPart;
	}
    public RegionQueryPart getRegionQueryPart() {
		return regionQueryPart;
	}
    
	public String getHabit() {
		return habit;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}

	public int getTaxonId() {
		return taxonId;
	}

	public void setTaxonId(int taxonId) {
		this.taxonId = taxonId;
	}

	public String[] getDistributionStatus() {
		return distributionStatus;
	}
	public void setDistributionStatus(String[] distributionStatus) {
		this.distributionStatus = distributionStatus;
	}

	public String[] getRank() {
		return rank;
	}
	public void setRank(String[] rank) {
		this.rank = rank;
	}

	public boolean isHybrids() {
		return hybrids;
	}

	public void setHybrids(boolean hybrids) {
		this.hybrids = hybrids;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
