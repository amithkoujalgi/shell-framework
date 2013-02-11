package com.koujalgi.shell.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
	private String command;
	private String baseCommand;
	private ArrayList<String> paramList = new ArrayList<String>();

	public CommandParser(String command) {
		this.command = cleanText(command);
		init();
	}

	public boolean hasBalancedQuotes() {
		return getQuotesCount() % 2 == 0;
	}

	private int getQuotesCount() {
		if (!this.command.contains("\"")) {
			return 0;
		}
		char ch = '"';
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < this.command.length(); i++) {
			char c = this.command.charAt(i);
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
		if (!command.contains(" ")) {
			this.baseCommand = command;
			return;
		}
		HashMap<Integer, String> paramPositionMap = new HashMap<Integer, String>();
		this.baseCommand = command.substring(0, command.indexOf(" ")).trim();
		String tmp = command.substring(command.indexOf(" "), command.length())
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
