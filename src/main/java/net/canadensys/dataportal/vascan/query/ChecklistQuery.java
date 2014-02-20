package net.canadensys.dataportal.vascan.query;

public class ChecklistQuery {
	
	
    private String[] province = null;

    private String combination = null;
    
    private String habit;

    private int taxonId = -1;
    
    private String[] distributionStatus;
    private String[] rank;
    
    private boolean hybrids;
    
    private String sort;

	public String[] getProvince() {
		return province;
	}

	public void setProvince(String[] province) {
		this.province = province;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
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
