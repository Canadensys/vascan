package net.canadensys.dataportal.vascan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.canadensys.dataportal.vascan.VernacularNameService;
import net.canadensys.dataportal.vascan.dao.VernacularNameDAO;
import net.canadensys.dataportal.vascan.model.VernacularNameModel;

@Service("vernacularNameService")
public class VernacularNameServiceImpl implements VernacularNameService {
	
	@Autowired
	private VernacularNameDAO vernacularNameDAO;

	@Transactional(readOnly=true)
	@Override
	public VernacularNameModel loadVernacularNameModel(Integer vernacularNameId) {
		return vernacularNameDAO.loadVernacularName(vernacularNameId);
	}

}
