package edu.sjtu.adapt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class ChineseSegment {

	public static void main(String[] args) throws IOException {
		System.out.println("Hello,world!");
		String filein = null;
		String fileout = null;
		if (args.length > 0) {
			filein = args[0];
			fileout = args[1];
		}
//		filein="D:\\data\\tmp\\filein2.txt";
//		fileout="D:\\data\\tmp\\out.txt";
		System.out.println("filein:"+filein);
		System.out.println("fileout:"+fileout);
		StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese-tokenize.properties");
		BufferedReader bReader=Files.newBufferedReader(Paths.get(filein));
		BufferedWriter bWriter = Files.newBufferedWriter(Paths.get(fileout));
//		while(bReader.ready()){
		bReader.lines().parallel().map(text->{
			Annotation annotation = new Annotation(text);
			pipeline.annotate(annotation);
			List<CoreLabel> list = annotation.get(CoreAnnotations.TokensAnnotation.class);
			String tokens = list.stream().map(x->x.get(CoreAnnotations.TextAnnotation.class))
			.reduce((x,y)->x+" "+y).orElse("");
			return tokens;
		}).forEach(x->{
			try {
				bWriter.write(x);
			} catch (IOException e) {
				e.printStackTrace();
			}}
		);
		bWriter.close();
	}
}
