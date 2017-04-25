package edu.sjtu.adapt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

public class SelectSentence {

	public static void main(String[] args) throws IOException {
		
		String filein=null;
		String fileout=null;
		if(args.length>0){
			filein=args[0];
			fileout=args[1];
		}
		HashSet<String>keys=new HashSet<>();
		keys.addAll(Arrays.asList("原来" ,"以便" ,"是故" ,"以致" ,"因为" ,"因此" ,"所以" ,"由于" ,"致使" ,"因而" ,"只怪" ,"这说明" ,"表明" ,"出于" ,"起源于" ,"来自于" ,"的原因" ,"原因是" ,"取决于" ,"导致" ));
		BufferedReader reader = Files.newBufferedReader(Paths.get(filein));
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileout));
		reader.lines().parallel()
		.flatMap(x->Arrays.asList(x.split("。|！|!|？|\\?")).stream())
		.filter(x->keys.stream().anyMatch(key->x.indexOf(key)>=0))
		.forEach(x->{
			try {
				writer.write(x+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		reader.close();
		writer.close();
		
		
		

	}

}
