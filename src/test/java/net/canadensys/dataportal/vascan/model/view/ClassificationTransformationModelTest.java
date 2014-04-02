package net.canadensys.dataportal.vascan.model.view;

import static org.junit.Assert.*;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import net.canadensys.dataportal.vascan.model.TaxonLookupModel;
import net.canadensys.dataportal.vascan.model.view.ClassificationOrderingViewModel;

import org.junit.Test;

public class ClassificationTransformationModelTest {
	
	@Test
	public void testClassificationTransformationModel(){
		TaxonLookupModel root = new TaxonLookupModel();
		root.setTaxonId(73);
		root.setCalname("Equisetopsida");
		root.setRank("class");
		
		TaxonLookupModel equisetidae = new TaxonLookupModel();
		equisetidae.setTaxonId(26);
		equisetidae.setCalname("Equisetidae");
		equisetidae.setRank("subclass");
		equisetidae.setParentID(73);
		
		TaxonLookupModel lycopodiidae = new TaxonLookupModel();
		lycopodiidae.setTaxonId(35);
		lycopodiidae.setCalname("Lycopodiidae");
		lycopodiidae.setRank("subclass");
		lycopodiidae.setParentID(73);
		
		TaxonLookupModel lycopodiales = new TaxonLookupModel();
		lycopodiales.setTaxonId(40);
		lycopodiales.setCalname("Lycopodiales");
		lycopodiales.setRank("order");
		lycopodiales.setParentID(35);
		
		//Mocktaxon is an 'order' attached to class
		TaxonLookupModel mockTaxon = new TaxonLookupModel();
		mockTaxon.setTaxonId(1);
		mockTaxon.setCalname("Mocktaxon");
		mockTaxon.setRank("order");
		mockTaxon.setParentID(73);
		
		ClassificationOrderingViewModel  classificationTransformationModel = new  ClassificationOrderingViewModel(root);
		
		classificationTransformationModel.attach(lycopodiidae);
		classificationTransformationModel.attach(equisetidae);
		classificationTransformationModel.attach(lycopodiales);
		classificationTransformationModel.attach(mockTaxon);
		
		List<TaxonLookupModel> sortedList = classificationTransformationModel.toOrderedList();
		
		assertEquals(73, sortedList.get(0).getTaxonId().intValue());
		assertEquals(1, sortedList.get(1).getTaxonId().intValue());
		assertEquals(26, sortedList.get(2).getTaxonId().intValue());
		assertEquals(35, sortedList.get(3).getTaxonId().intValue());
		assertEquals(40, sortedList.get(4).getTaxonId().intValue());
	}

}
