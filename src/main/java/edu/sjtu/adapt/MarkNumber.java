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

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class MarkNumber {

	public static void main(String[] args) throws IOException {

		token(args);

	}

	private static void token(String[] args) throws IOException {
		BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]));
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[1]));
		StanfordCoreNLP pipeline=new StanfordCoreNLP("StanfordCoreNLP-chinese-tokenize.properties");
		reader.lines().parallel().forEach(line->{
			System.out.println("line"+line);
			JSONObject jsonObject = JSONObject.parseObject(line);
			String cause = jsonObject.getString("cause");
			String effect = jsonObject.getString("effect");
			cause=cause.replaceAll("@", "");
			effect=effect.replaceAll("@", "");
			Annotation annotation=new Annotation(cause);
			pipeline.annotate(annotation);
			cause = annotation.get(CoreAnnotations.TokensAnnotation.class).stream()
			.map(x->x.get(CoreAnnotations.TextAnnotation.class)).reduce((x,y)->x+"@"+y).orElse("");
			
			annotation=new Annotation(effect);
			pipeline.annotate(annotation);
			effect = annotation.get(CoreAnnotations.TokensAnnotation.class).stream()
					.map(x->x.get(CoreAnnotations.TextAnnotation.class)).reduce((x,y)->x+"@"+y).orElse("");
			if (cause.isEmpty()||effect.isEmpty()) return;
			jsonObject.put("causetokens", cause);
			jsonObject.put("effecttokens", effect);
			try {
				writer.write(jsonObject.toJSONString() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		reader.close();
		writer.close();
	}
	private static void mark(String[] args) throws IOException {
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
