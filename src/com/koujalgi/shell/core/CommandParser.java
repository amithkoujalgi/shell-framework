package com.koujalgi.shell.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
	private String commandString;
	private String baseCommand;
	private ArrayList<String> paramList = new ArrayList<String>();

	public CommandParser(String command) {
		this.commandString = cleanText(command);
		init();
	}

	public String getCommandString() {
		return commandString;
	}

	public boolean hasBalancedQuotes() {
		return getNumQuotes() % 2 == 0;
	}

	private int getNumQuotes() {
		if (!this.commandString.contains("\"")) {
			return 0;
		}
		char ch = '"';
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < this.commandString.length(); i++) {
			char c = this.commandString.charAt(i);
			Integer val = map.get(new Character(c));
			if (val != null) {
				map.put(c, new Integer(val + 1));
			} else {
				map.put(c, 1);
			}
		}
		return map.get(ch);
	}

	/**
	 * Parse command and push params to param-list
	 */
	private void init() {
		// load the string without basecommand
		if (!commandString.contains(" ")) {
			this.baseCommand = commandString;
			return;
		}
		HashMap<Integer, String> paramPositionMap = new HashMap<Integer, String>();
		this.baseCommand = commandString.substring(0, commandString.indexOf(" ")).trim();
		String tmp = commandString.substring(commandString.indexOf(" "), commandString.length())
				.trim();
		Pattern quotedParamPattern = Pattern
				.compile("\\s*(\"[^\"]+\"|[^\\s\"]+)");
		Matcher matcher = quotedParamPattern.matcher(tmp);
		// fetch all params within double quotes
		while (matcher.find()) {
			String param = matcher.group();
			if (!param.replaceAll("\"", "").trim().equals("")) {
				param = param.replaceAll("\"", "").trim();
				// prevMatch += tmp.indexOf(param);
				paramPositionMap.put(matcher.start(), param);
			}
			tmp = tmp.replaceFirst(param, "").replaceAll("\\s+", " ");
		}
		paramList = sortMapByIntegerKey(paramPositionMap);
	}

	private ArrayList<String> sortMapByIntegerKey(HashMap<Integer, String> map) {
		Map<Integer, String> treeMap = new TreeMap<Integer, String>(map);
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<Integer, String> e : treeMap.entrySet()) {
			list.add((String) e.getValue());
		}
		return list;
	}

	private String cleanText(String text) {
		return text.replaceAll("\\s+", " ").replaceAll("\\t+", " ").trim();
	}

	public int getParamCount() {
		return paramList.size();
	}

	/**
	 * Get the command context. The first word of the command <br/>
	 * <b>Ex:</b> 'move' src target ('move' is the command)
	 * 
	 * @return The command issued in the shell.
	 */
	public String getBaseCommand() {
		return baseCommand;
	}

	public ArrayList<String> getParams() {
		return paramList;
	}

	public boolean hasParams() {
		return paramList.size() > 0;
	}
}
