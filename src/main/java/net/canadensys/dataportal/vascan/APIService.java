package net.canadensys.dataportal.vascan;

import java.util.List;

import net.canadensys.dataportal.vascan.model.VascanAPIResponse;

public interface APIService {
	public List<VascanAPIResponse> search(String query);
}
