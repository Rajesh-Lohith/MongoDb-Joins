package com.db.collections.joins;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.db.core.main;

public class InnerJoinN {

	private List<Map> resultSet = new ArrayList<>();

	public List<Map> innerJoinNFields(String mappingField1, String mappingField2, List<Map> document1,
			List<Map> document2) {
//		System.out.println("Document1 \n" + document1);
//		System.out.println("Document2 \n" + document2);
//		System.out.println("mapping Field= "+ mappingField);
		Instant start = Instant.now();
		List<Map> result = document1.parallelStream().map(d -> {
			Optional<Map> joined = document2.parallelStream().filter(fl -> {
				return (fl.get(mappingField1) != null && fl.get(mappingField1).equals(d.get(mappingField2)))
						|| (fl.get(mappingField2) != null && fl.get(mappingField2).equals(d.get(mappingField1)));
			}).findFirst();
			if (joined.isPresent()) {
				Map hashMap = new HashMap<>(d);
				hashMap.putAll(joined.get());
				return hashMap;
			} else {
				return null;
			}
		}).filter(d -> !Objects.isNull(d)).collect(Collectors.toList());
		resultSet.addAll(result);
		Instant finish = Instant.now();
		main.timeElapsed = Duration.between(start, finish).toMillis();
		// System.out.println("result =" + result);
		return resultSet;
	}

	public ArrayList<Map> performJoinN(Map<String, ArrayList<Map<String, String>>> JsonMapList, String mappingField1,
			String mappingField2) {
		ArrayList<Map<String, String>> listOfDocuments = new ArrayList(JsonMapList.values());

		ArrayList<Map> joined = (ArrayList<Map>) listOfDocuments.get(0);
		if (listOfDocuments.size() > 1) {
			for (int i = 1; i < listOfDocuments.size(); i++) {
				ArrayList<Map> nextDocument = (ArrayList<Map>) listOfDocuments.get(i);
				joined = (ArrayList<Map>) innerJoinNFields(mappingField1, mappingField2, nextDocument, joined);
			}
		}

		System.out.println("Inner Join N Performed using " + mappingField1 + " and " + mappingField2);
		return joined;
	}

}
