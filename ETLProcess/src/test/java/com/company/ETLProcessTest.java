package com.company;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class ETLProcessTest {

	String inputFilePath = "resources/InputFile.csv";
	String outputFilePath = "resources/OutputFile.json";
	ETLProcess etlProcess;
	Map<String, Float> map;

	@Before
	public void executeBeforeEach() {
		etlProcess = new ETLProcess();
		map = new TreeMap<String, Float>();
		map.put("Java", (float) 1.8);
		map.put("Maven", (float) 3.3);
		map.put("JUnit", (float) 4.0);
	}

	@Test
	public void inputFileExistAndNotEmptyTest() {
		File tmpDir = new File(inputFilePath);
		assertTrue(tmpDir.exists() && tmpDir.length() != 0);
	}

	@Test
	public void getAvrgPerformance() {
		assertNotNull(etlProcess.getAvrgPerformance(inputFilePath));
	}

	@Test
	public void topPerformerListTest() {
		etlProcess.topPerformerList(map);
		File tmpDir = new File(outputFilePath);
		assertTrue(tmpDir.exists() && tmpDir.length() != 0);
	}

	@Test
	public void testFileNotFoundException() throws FileNotFoundException {
		File tmpDir = new File("resources/OutputFiwwwle.json");
	}
}
