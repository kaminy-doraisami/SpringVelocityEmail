package com.learnSpring.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * This class generates the body of an email
 *
 */
public class EmailContentGenerator {

	/*
	 * This method creates the body of an email as per the given Velocity
	 * template. It uses the data in the input file to populate necessary
	 * content in the template
	 */
	public String generateEmailContent() {

		Map<String, String> subjectScoreMap = null;
		List<Map<String, String>> listOfSubjectScoreMap = new ArrayList<>();
		Scanner scanner = null;

		try {
			scanner = new Scanner(new File("src/main/resources/markList.txt"));
			while (scanner.hasNextLine()) {
				String[] subjectScorePair = scanner.nextLine().split(",");
				subjectScoreMap = new HashMap<>();
				subjectScoreMap.put("subject", subjectScorePair[0]);
				subjectScoreMap.put("score", subjectScorePair[1]);
				listOfSubjectScoreMap.add(subjectScoreMap);
			}
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFound Exception thrown while reading input file");
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		VelocityEngine ve = new VelocityEngine();
		ve.init();

		/*
		 * markList is the name used here, to communicate data between Java code and
		 * the Velocity template
		 */
		VelocityContext context = new VelocityContext();
		context.put("markList", listOfSubjectScoreMap);

		StringWriter writer = new StringWriter();
		Velocity.mergeTemplate("src/main/resources/templates/examResultTemplate.vm", "UTF-8", context, writer);

		return writer.toString();
	}
}
