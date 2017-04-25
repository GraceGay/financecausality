package edu.sjtu.adapt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

public class Process {

	public static void main(String[] args) throws IOException {
		HashSet<String>causekeys=new HashSet<>();
		HashSet<String>effectkeys=new HashSet<>();
		effectkeys.addAll(Arrays.asList("原来" ,"以便" ,"是故" ,"以致" ,"因为" ,"因此" ,"所以" ,"由于" ,"致使" ,"因而" ,"只怪" ,"这说明" ,"表明了","这表明" ,"出于" ,"起源于" ,"来自于" ,"的原因" ,"原因是" ,"取决于" ,"导致" ));
		causekeys.addAll(Arrays.asList("原来"  ,"是故"  ,"因为"  ,"由于"   ,"只怪" ,"出于" ,"起源于" ,"来自于" ,"的原因" ,"原因是" ,"取决于" ));
		effectkeys.removeAll(causekeys);
//		System.out.println(causekeys.size());
//		System.out.println(effectkeys.size());
//		System.out.println(causekeys.stream().reduce((x,y)->x+"*"+y));
//		System.out.println(effectkeys.stream().reduce((x,y)->x+"*"+y));
		System.out.println(args[0]);
		System.out.println(args[1]);
		
		BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]));
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[1]));
		reader.lines().parallel().filter(line->{
		return causekeys.stream().anyMatch(key->line.indexOf(key)>=0)&
						   effectkeys.stream().anyMatch(key->line.indexOf(key)>=0);	
		}).forEach(line->{
			try {
				writer.write(line+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});	
		reader.close();
		writer.close();
		
		
		
		
	}

}
