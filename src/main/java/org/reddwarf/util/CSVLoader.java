/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.reddwarf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Parse input csv file (as resource).  
 *
 * @author Michal Bocek
 */
@Component
public class CSVLoader {
	private static final Logger logger = LoggerFactory.getLogger(CSVLoader.class);
	private static final char DEFAULT_COLUMN_SEPARATOR = ',';
	private static final char DEFAULT_TEXT_SEPARATOR = '\"';
	private char columnSeparator = DEFAULT_COLUMN_SEPARATOR;
	private char textSeparator = DEFAULT_TEXT_SEPARATOR;

	/**
	 * Load data from CSV as resource.
	 * @param csvName
	 * @return
	 */
	public Collection<Collection<String>> load(final String csvName) {
		Collection<Collection<String>> result = new ArrayList<Collection<String>>();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(csvName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String strLine;
		try {
			while ((strLine = br.readLine()) != null) {
				strLine = strLine.trim();
				int textSeparatorCount  = 0;
				String data = "";
				Collection<String> cs = new ArrayList<String>();
				for (char character : strLine.toCharArray()) {
					if (character == textSeparator) {
						textSeparatorCount++;
						if (textSeparatorCount >= 2) {
							cs.add(data.trim());
							data = "";
							textSeparatorCount = 0;
						}
						continue;
					}
					if (character == columnSeparator && textSeparatorCount == 0) {
						if (!data.isEmpty()) cs.add(data.trim());
						data = "";
						continue;
					}
					data += character;
				}
				result.add(cs);
			}
			br.close();
			br = null;
		} catch (IOException e) {
			logger.error("Cannot load default violation properties.");
			throw new RuntimeException("Can not load csv file: " + csvName, e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Can not close imput stream.");
				}
			}
		}
		return result;
	}
}
