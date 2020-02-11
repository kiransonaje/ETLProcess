package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Author: Kiran Sonaje
 * Date: 02/11/2020 10am
 * Purpose: This class is implementation of ETL process to find top 3 performers
 * Input: resources/InputFile.csv
 * Output: resources/OutputFile.json
 */
public class ETLProcess {
	public static void main(String[] args) {
		String fileToExtract = args[0]; 
		Map<String, Float> map = getAvrgPerformance(fileToExtract);
		topPerformerList(map);
	}
	
	//Print output with top performer's list
	public static void topPerformerList(Map<String, Float> unsortedMap) {
		// Sort map by value
		Map<String, Float> sortedMap = sortByValues(unsortedMap);
		ObjectMapper objectMapper = new ObjectMapper();
		String path = "resources/OutputFile.json";

		try {
			String json = objectMapper.writeValueAsString(sortedMap);
			Files.write(Paths.get(path), json.getBytes());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Sort input map by value
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k1).compareTo(map.get(k2));
				if (compare == 0)
					return 1;
				else
					return compare;
			}
		};

		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	//Load input file and calculates average
	public static Map<String, Float> getAvrgPerformance(String csvFile) {
		
		Map<String, Float> map = new TreeMap<String, Float>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		//Number of records
		int cnt = 0;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] lapRecord = line.split(cvsSplitBy);
				System.out.println("Driver Name= " + lapRecord[0] + " , Lap time=" + lapRecord[1]);

				if (map.containsKey(lapRecord[0])) {
					map.replace(lapRecord[0], map.get(lapRecord[0]) + Float.valueOf(lapRecord[1]));
				} else {
					map.put(lapRecord[0], Float.valueOf(lapRecord[1]));
				}
				cnt++;
			}
			//Number of laps per driver
			int laps = cnt / map.size();
			for (Map.Entry<String, Float> entry : map.entrySet()) {
				entry.setValue(entry.getValue() / laps);				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

}