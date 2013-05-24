/*
	Copyright (c) 2010 Canadensys
*/

package net.canadensys.dataportal.vascan.manager;

import net.canadensys.dataportal.vascan.constant.Status;
import net.canadensys.dataportal.vascan.model.TaxonModel;

import org.apache.commons.collections.Predicate;

public class IsSynonymPredicate implements Predicate{

    public boolean evaluate(Object o) {
        boolean is = false;
        TaxonModel taxon = (TaxonModel) o;
        if(taxon.getStatus().getId() == Status.SYNONYM){
            is = true;
        }
        return is;
    }
} 