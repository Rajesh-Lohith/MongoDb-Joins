package com.db.collections.joins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface joins {

	
	public List<Map> join(ArrayList<Map<String, String>> listofMap, String joinField);
	
	
}
