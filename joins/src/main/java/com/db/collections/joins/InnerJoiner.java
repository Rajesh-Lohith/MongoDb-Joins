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

public class InnerJoiner {
	private List<Map> resultSet = new ArrayList<>();

	public InnerJoiner innerJoin(Map<String, Map> table1, Map<String, Map> table2, String mappingField) {
		List<Map> result = table1.entrySet().parallelStream().map(d -> {
			Map joined = table2.get(String.valueOf(d.getValue().get(mappingField)));
			if (joined != null && !joined.isEmpty()) {
				Map hashMap = new HashMap<>(d.getValue());
				hashMap.putAll(joined);
				return hashMap;
			} else {
				return null;
			}
		}).filter(d -> !Objects.isNull(d)).collect(Collectors.toList());

		resultSet.addAll(result);
		return this;
	}

	public ArrayList<Map> performJoin(Map<String, ArrayList<Map<String, String>>> JsonMapList, String mappingField) {
		ArrayList<Map<String, String>> listOfDocuments = new ArrayList(JsonMapList.values());

		ArrayList<Map> joined = (ArrayList<Map>) listOfDocuments.get(0);
		if (listOfDocuments.size() > 1) {
			for (int i = 1; i < listOfDocuments.size(); i++) {
				ArrayList<Map> nextDocument = (ArrayList<Map>) listOfDocuments.get(i);
				joined = (ArrayList<Map>) innerJoin(mappingField, nextDocument, joined);
			}
		}

		System.out.println("Inner Join Performed using field = " + mappingField);
		return joined;
	}

	public List<Map> innerJoin(String mappingField, List<Map> document1, List<Map> document2) {
//		System.out.println("Document1 \n" + document1);
//		System.out.println("Document2 \n" + document2);
//		System.out.println("mapping Field= "+ mappingField);
//		Instant start = Instant.now();
		List<Map> result = document1.parallelStream().map(d -> {
			Optional<Map> joined = document2.parallelStream()
					.filter(fl -> fl.get(mappingField).equals(d.get(mappingField))).findFirst();
			if (joined.isPresent()) {
				Map hashMap = new HashMap<>(d);
				hashMap.putAll(joined.get());
				return hashMap;
			} else {
				return null;
			}
		}).filter(d -> !Objects.isNull(d)).collect(Collectors.toList());
//		Instant finish = Instant.now();
//		main.timeElapsed = Duration.between(start, finish).toMillis();
		resultSet.addAll(result);
		//System.out.println("result =" + result);
		return resultSet;
	}

	public InnerJoiner innerJoin(List<Map> document1, List<Map> document2, String mappingField) {
		List<Map> result = document1.parallelStream().map(d -> {
			Optional<Map> joined = document2.parallelStream().filter(fl -> fl.get(mappingField) == d.get(mappingField))
					.findFirst();
			if (joined.isPresent()) {
				Map hashMap = new HashMap<>(d);
				hashMap.putAll(joined.get());
				return hashMap;
			} else {
				return null;
			}
		}).filter(d -> !Objects.isNull(d)).collect(Collectors.toList());

		resultSet.addAll(result);
		return this;
	}

	public InnerJoiner innerJoin(List<Map> table2, String mappingField) {
		return innerJoin(this.resultSet, table2, mappingField);
	}

	public List<Map> getResult() {
		return resultSet;
	}
}
