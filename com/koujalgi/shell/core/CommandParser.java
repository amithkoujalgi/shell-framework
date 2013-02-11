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

	/**
	 * Parse command and push params to param-list
	 */
	private void init() {
		// load the string without basecommand
		if (!command.contains(" ")) {
			this.baseCommand = command;
			return;
		}
		HashMap<Integer, String> paramPositionMap = new HashMap<>();
		this.baseCommand = command.substring(0, command.indexOf(" ")).trim();
		String tmp = command.substring(command.indexOf(" "), command.length())
				.trim();
		Pattern quotedParamPattern = Pattern.compile(" *\"[^\"]+\"");
		Matcher matcher = quotedParamPattern.matcher(tmp);
		// fetch all params within double quotes
		while (matcher.find()) {
			String param = matcher.group();
			if (!param.replaceAll("\"", "").trim().equals("")) {
				param = param.replaceAll("\"", "").trim();
				paramPositionMap.put(command.indexOf(param), param);
			}
			tmp = tmp.replace(param, "").replaceAll("\\s+", " ");
		}
		// fetch all params separated by spaces
		String[] params = tmp.split(" ");
		for (String param : params) {
			if (!param.replaceAll("\"", "").trim().equals("")) {
				param = param.replaceAll("\"", "").trim();
				paramPositionMap.put(command.indexOf(param), param);
			}
			tmp = tmp.replace(param, "").replaceAll("\\s+", " ");
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
