package net.canadensys.dataportal.vascan.taxonomy;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Enumeration of Vascan used taxonomic ranks.
 * It differs from org.gbif.api.vocabulary.Rank by providing comparable ranks but it uses a limited set of ranks.
 * @author cgendreau
 *
 */
public enum TaxonRankEnum implements Comparable<TaxonRankEnum>{
	CLASS("class",null,1),
	SUBCLASS("subclass",null,2),
	SUPERORDER("superorder",null,3),
	ORDER("order",null,4),
	FAMILY("family",null,5),
	SUBFAMILY("subfamily",null,6),
	TRIBE("tribe",null,7),
	SUBTRIBE("subtribe",null,8),
	GENUS("genus",null,9),
	SUBGENUS("subgenus","subg.",10),
	SECTION ("section","sect.",11),
	SUBSECTION("subsection","subsect.",12),
	SERIES("series","ser.",13),
	SPECIES("species",null,14),
	SUBSPECIES("subspecies","subsp.",15),
	VARIETY("variety","var.",16);
	
	//in an enumeration, the static block is called after the values definition
	private static final TreeMap<Integer, TaxonRankEnum> ORDERED_VALUES = new TreeMap<Integer, TaxonRankEnum>();
	static{
		for(TaxonRankEnum curr : TaxonRankEnum.values()){
			if(ORDERED_VALUES.put(curr.getOrder(),curr)!=null){
				throw new IllegalStateException("More than one TaxonRankEnum value for order/level " + curr.getOrder());
			}
    	}
	}
	
	private final String label;
    private final String abbreviation;
    private final int order;
    
    private TaxonRankEnum(String label, String abbreviation, int order) {
        this.label = label;
        this.abbreviation = abbreviation;
        this.order = order;
    }

    /**
     * @return label of this rank (in english)
     */
	public String getLabel() {
		return label;
	}

	/**
	 * @return abbreviation of the rank or null if this rank doesn't have any abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * The order/level of a rank represents its position relative to the other rank.
	 * This is NOT an id.
	 * @return
	 */
	public int getOrder() {
		return order;
	}
	
    /**
     * You should use this function instead of valueOf if you need more flexibility.
     * This function is case insensitive and will return null instead an throwing an exception
     * when a value is not found.
     * @param text
     * @return the matching TaxonRankEnum value or null if not found
     */
    public static TaxonRankEnum fromLabel(String label) {
    	if (label != null) {
    		for (TaxonRankEnum rank : TaxonRankEnum.values()) {
    			if (label.equalsIgnoreCase(rank.toString())) {
    				return rank;
    			}
    		}
    	}
        return null;
    }
    
    /**
     * The function will return the TaxonRankEnum ordered according to taxonomic order/level.
     * You should use this function instead of TaxonRankEnum.values()
     * @return ordered TaxonRankEnum
     */
    public static Collection<TaxonRankEnum> getOrderedRanks(){
    	return ORDERED_VALUES.values();
    }
    
    public static class TaxonRankEnumComparator implements Comparator<TaxonRankEnum>{
		@Override
		public int compare(TaxonRankEnum o1, TaxonRankEnum o2) {
			return ObjectUtils.compare(o1, o2);
		}
    }
}
