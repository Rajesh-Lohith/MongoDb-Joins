package com.db.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.db.collection.reader.CollectionHelper;
import com.db.collections.joins.InnerJoiner;

public class main2 {


	public static void main(String[] args) {

//		System.out.println("Enter Folder Path");
//		Scanner scanner = new Scanner(System.in);
//		String folderPath = scanner.nextLine();
		
		
		CollectionHelper jsonHelper = new CollectionHelper("C:\\Users\\itsme\\Desktop\\Truchet Programming\\joins\\JsonFiles\\delete");
		File[] listOfFiles = jsonHelper.getFilesList();
		Map<String, ArrayList<Map<String, String>>> JsonMapList = jsonHelper.getJsonMapList(listOfFiles);
		InnerJoiner innerJoiner = new InnerJoiner();
		ArrayList<Map> resultSet = innerJoiner.performJoin(JsonMapList, "Tweet Id");
		jsonHelper.writeResultSet(resultSet);
		
		
		
	}


}
