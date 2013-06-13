package net.canadensys.dataportal.vascan.constant;

/**
 * Rank constants
 * @author canadensys
 *
 */
public class Rank {
	
	public static final int ROOT = 0;
	public static final int CLASS = 1;
	public static final int SUBCLASS = 2;
	public static final int	SUPERORDER = 3;
	public static final int	ORDER = 4;
	public static final int	FAMILY = 5;
	public static final int	SUBFAMILY = 6;
	public static final int	TRIBE = 7;
	public static final int	SUBTRIBE = 8;
	public static final int	GENUS = 9;
	public static final int	SUBGENUS = 10;
	public static final int	SECTION = 11;
	public static final int	SUBSECTION = 12;
	public static final int	SERIES = 13;
	public static final int	SPECIES = 14;
	public static final int	SUBSPECIES = 15;
	public static final int	VARIETY = 16;
	public static final int	UNDEFINED = 17;
	
	public static final String ROOT_LABEL = "root";
	public static final String CLASS_LABEL = "class";
	public static final String SUBCLASS_LABEL = "subclass";
	public static final String SUPERORDER_LABEL = "superorder";
	public static final String ORDER_LABEL = "order";
	public static final String FAMILY_LABEL = "family";
	public static final String SUBFAMILY_LABEL = "subfamily";
	public static final String TRIBE_LABEL = "tribe";
	public static final String SUBTRIBE_LABEL = "subtribe";
	public static final String GENUS_LABEL = "genus";
	public static final String SUBGENUS_LABEL = "subgenus";
	public static final String SECTION_LABEL = "section";
	public static final String SUBSECTION_LABEL = "subsection";
	public static final String SERIES_LABEL = "series";
	public static final String SPECIES_LABEL = "species";
	public static final String SUBSPECIES_LABEL = "subspecies";
	public static final String VARIETY_LABEL = "variety";
	public static final String UNDEFINED_LABEL = "undefined";
	
	public static int getIdFromLabel(String label){
		if(CLASS_LABEL.equalsIgnoreCase(label)){
			return CLASS;
		}
		else if(SUBCLASS_LABEL.equalsIgnoreCase(label)){
			return SUBCLASS;
		}
		else if(SUPERORDER_LABEL.equalsIgnoreCase(label)){
			return SUPERORDER;
		}
		else if(ORDER_LABEL.equalsIgnoreCase(label)){
			return ORDER;
		}
		else if(FAMILY_LABEL.equalsIgnoreCase(label)){
			return FAMILY;
		}
		else if(SUBFAMILY_LABEL.equalsIgnoreCase(label)){
			return SUBFAMILY;
		}
		else if(TRIBE_LABEL.equalsIgnoreCase(label)){
			return TRIBE;
		}
		else if(SUBTRIBE_LABEL.equalsIgnoreCase(label)){
			return SUBTRIBE;
		}
		else if(GENUS_LABEL.equalsIgnoreCase(label)){
			return GENUS;
		}
		else if(SUBGENUS_LABEL.equalsIgnoreCase(label)){
			return SUBGENUS;
		}
		else if(SECTION_LABEL.equalsIgnoreCase(label)){
			return SECTION;
		}
		else if(SUBSECTION_LABEL.equalsIgnoreCase(label)){
			return SUBSECTION;
		}
		else if(SERIES_LABEL.equalsIgnoreCase(label)){
			return SERIES;
		}
		else if(SPECIES_LABEL.equalsIgnoreCase(label)){
			return SPECIES;
		}
		else if(SUBSPECIES_LABEL.equalsIgnoreCase(label)){
			return SUBSPECIES;
		}
		else if(VARIETY_LABEL.equalsIgnoreCase(label)){
			return VARIETY;
		}
		return UNDEFINED;
	}
}
