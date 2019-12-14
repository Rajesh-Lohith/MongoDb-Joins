package com.db.core;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.db.collection.reader.CollectionHelper;
import com.db.collections.joins.InnerJoinN;
import com.db.collections.joins.InnerJoiner;

public class main {
	
	public static long timeElapsed; 

	public static void main(String[] args) {
		
		  // System.out.println("Enter Folder Path"); // 
//		Scanner scanner = new Scanner(System.in); // 
//		String folderPath = scanner.nextLine();
		  Instant start = Instant.now();
		  CollectionHelper jsonHelper = new CollectionHelper("C:\\\\Users\\\\itsme\\\\Desktop\\\\Truchet Programming\\\\joins\\\\JsonFiles\\\\"); 
		  File[] listOfFiles = jsonHelper.getFilesList(); // 
		  Map<String, ArrayList<Map<String, String>>> JsonMapList = jsonHelper.getJsonMapList(listOfFiles);
		  
		  //Perform Inner Join Operation // 
		  InnerJoiner innerJoiner = new InnerJoiner(); // 
//		  ArrayList<Map> resultSet = innerJoiner.performJoin(JsonMapList, "Tweet Id");
		  
		  
		  
		  //Perform InnerJoin with N Mapping Field // 
		  InnerJoinN innerJoinN = new InnerJoinN(); // 
		  ArrayList<Map> resultSet = innerJoinN.performJoinN(JsonMapList, "_uId","_OwnerUserId"); // //
		  
		  
		  jsonHelper.writeResultSet(resultSet);
			Instant finish = Instant.now();
			main.timeElapsed = Duration.between(start, finish).toMillis();
		  
		  System.out.println("Time Consumed = " + timeElapsed);
		 		
		
			
		
		
		
	}

}
