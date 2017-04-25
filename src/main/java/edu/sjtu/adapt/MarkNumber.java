package edu.sjtu.adapt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;

public class MarkNumber {

	public static void main(String[] args) throws IOException {
		HashSet<String>causekeys=new HashSet<>();
		HashSet<String>effectkeys=new HashSet<>();
		effectkeys.addAll(Arrays.asList("原来" ,"以便" ,"是故" ,"以致" ,"因为" ,"因此" ,"所以" ,"由于" ,"致使" ,"因而" ,"只怪" ,"这说明" ,"表明了","这表明" ,"出于" ,"起源于" ,"来自于" ,"的原因" ,"原因是" ,"取决于" ,"导致" ));
		causekeys.addAll(Arrays.asList("原来"  ,"是故"  ,"因为"  ,"由于"   ,"只怪" ,"出于" ,"起源于" ,"来自于" ,"的原因" ,"原因是" ,"取决于" ));
		effectkeys.removeAll(causekeys);
		
		BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]));
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[1]));
		int num=0;
		while(reader.ready()){
			String line = reader.readLine();
			String cause=null;
			String effect=null;
			System.out.println(line);
			for(String sen:line.split("，")){
				if(causekeys.stream().anyMatch(key->sen.indexOf(key)>=0)){
					cause=sen;
					if (effect!=null) break;
					}
				if(effectkeys.stream().anyMatch(key->sen.indexOf(key)>=0)){
					effect=sen;
					if	(cause!=null) break;
				}
			}
			
			
			if (cause ==null|effect==null) continue;
			
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("con", line);
			jsonObject.put("lineid", ++num);
			jsonObject.put("cause", cause);
			jsonObject.put("effect", effect);
			writer.write(jsonObject.toJSONString()+"\n");
		}
		reader.close();
		writer.close();
		
		

	}

}
