package com.wmp.demo.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.google.common.base.CharMatcher;

@Service
public class DemoService {
	//
	public Map<String, String> convert(String url, String type, int unit) throws IOException {
		//
		Document document = Jsoup.connect(url).timeout(1000).get();
		
		String contents = null;
		if("HTML".equals(type)) {
			contents = removeTag(document);
		} else if("TXT".equals(type)) {
			contents = document.toString();
		}
		
		String engContents = extractEng(contents);
		String intContents = extractInt(contents);
		
		engContents = sortEng(engContents);
		intContents = sortInt(intContents);
		
		StringBuilder crossStr = new StringBuilder();
		int maxLength = engContents.length() >= intContents.length() ? engContents.length() : intContents.length();
		for(int i=0; i<maxLength; i++) {
			if(i < engContents.length()) {
				crossStr.append(engContents.charAt(i));
			}
			if(i < intContents.length()) {
				crossStr.append(intContents.charAt(i));
			}
		}
		
		String share = null;
		String remain = null;
		int crossStrLength = crossStr.length();
		if(crossStrLength % unit == 0) {
			share = crossStr.toString();
		} else if(crossStrLength < unit){
			remain = crossStr.toString();
		} else {
			int divIndex = crossStrLength - (crossStrLength % unit);
			share = crossStr.substring(0, divIndex);
			remain = crossStr.substring(divIndex);
		}
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("html", document.toString());
		result.put("share", share);
		result.put("remain", remain);
		
		return result;
	}

	private String sortInt(String intContents) {
		//
		char[] intArr = intContents.toCharArray();
		Arrays.sort(intArr);
		return new String(intArr);
	}

	private String sortEng(String engContents) {
		//
		List<Character> list = new LinkedList<Character>();
		for (char ch : engContents.toCharArray()) {
			list.add(ch);
		}
		
		list.sort(new Comparator<Character>() {
			//
			@Override
			public int compare(Character ch1, Character ch2) {
				//
				if(ch1 == ch2) {
					return 0;
				}
				double ch1Code = Character.isUpperCase(ch1) ? ch1 : Character.toUpperCase(ch1) + 0.5;
				double ch2Code = Character.isUpperCase(ch2) ? ch2 : Character.toUpperCase(ch2) + 0.5;
				
				return ch1Code > ch2Code ? 1 : -1;
 			}
		});
		StringBuilder str = new StringBuilder();
		for (Character ch : list) {
			str.append(ch);
		}
		return str.toString();
	}

	private String extractInt(String contents) {
		//
		return CharMatcher.inRange('0','9').retainFrom(contents);
	}

	private String extractEng(String contents) {
		//
		return CharMatcher.inRange('A','Z').or(CharMatcher.inRange('a','z')).retainFrom(contents);
	}

	private String removeTag(Document document) {
		//
		StringBuilder str = new StringBuilder();
		Elements scriptElements = document.getElementsByTag("script");
		for (Element element : scriptElements) {
			str.append(element.data().toString());
		}
		str.append(document.text());
		return str.toString();
	}
}
