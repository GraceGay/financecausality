package edu.sjtu.adapt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;

import com.alibaba.fastjson.JSONObject;

public class MarkNumber {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]));
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[1]));
		int num = 0;
		while (reader.ready()) {
			String line = reader.readLine();
			String cause = null;
			String effect = null;
			for (java.util.regex.Pattern p : CauseEffectPatternInterface.pattern.keySet()) {
				Matcher matcher = p.matcher(line);
				if (matcher.find()) {
					int val = CauseEffectPatternInterface.pattern.get(p);
					cause = matcher.group(val % 100 / 10);
					effect = matcher.group(val %100 % 10);
					break;
				}
			}
			if(cause==null||effect==null||cause.isEmpty()||effect.isEmpty()) continue;
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("con", line);
			jsonObject.put("lineid", ++num);
			jsonObject.put("cause", cause);
			jsonObject.put("effect", effect);
			writer.write(jsonObject.toJSONString() + "\n");
		}
		reader.close();
		writer.close();

	}

}
