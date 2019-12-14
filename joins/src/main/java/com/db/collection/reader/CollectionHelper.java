package com.db.collection.reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.db.collections.document.parser.DocumentParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CollectionHelper {

	DocumentParser documentParser = DocumentParser.getInstance();
	public static String FOLDER_PATH = "";
	public static String RESULT_FOLDER = "C:\\Users\\itsme\\Desktop\\Truchet Programming\\joins\\JsonFiles\\ResultSet\\";
	public Map<String, ArrayList<Map<String, String>>> JsonMapList = new LinkedHashMap<String, ArrayList<Map<String, String>>>();
	// C:\\Users\\itsme\\Desktop\\Truchet Programming\\joins\\JsonFiles\\

	public CollectionHelper(String folder_path) {
		FOLDER_PATH = folder_path;
	}

	public static String readData(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(FOLDER_PATH + fileName)));
	}

	public File[] getFilesList() {
		File folder = new File(FOLDER_PATH);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}

	public Map<String, ArrayList<Map<String, String>>> getJsonMapList(File[] listOfFiles) {

		for (File file : listOfFiles) {
			if (file.isFile()) {
				try {
					System.out.println("Reading " + file.getName() + " file");
					String jsonData = readData(file.getName());
					storeMapList(file.getName() , documentParser.parse(jsonData));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return JsonMapList;
	}
	
	public void writeResultSet(ArrayList<Map> resultSet)
	{
		ObjectMapper mapper = new ObjectMapper();
        try
        {
            String json = mapper.writeValueAsString(resultSet);
            FileWriter fileWriter = new FileWriter(RESULT_FOLDER + "Result_Set");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(json);
            printWriter.close();
            System.out.println("Transferred Result set to a file ");
             
        }catch (IOException e) {
            e.printStackTrace();
        } 
		
	}

	public void storeMapList(String fileName ,ArrayList<Map<String, String>> MapList) {
		JsonMapList.put(fileName, MapList);
	}
}

