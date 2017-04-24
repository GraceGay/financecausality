package edu.sjtu.adapt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
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
		System.out.println("filein:"+filein);
		System.out.println("fileout:"+fileout);
		StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese-tokenize.properties");
		 String text = IOUtils.stringFromFile(filein,StandardCharsets.UTF_8.name());
		Annotation annotation = new Annotation(text);
		pipeline.annotate(annotation);
		List<CoreMap> sens = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		BufferedWriter bWriter = Files.newBufferedWriter(Paths.get(fileout));
		for (CoreMap sen : sens) {
			String tokens = sen.get(CoreAnnotations.TokensAnnotation.class).stream()
					.map(x -> x.get(CoreAnnotations.TextAnnotation.class)).reduce((x, y) -> x + " " + y).orElse("");
			System.out.println(tokens);
			bWriter.write(tokens);
		}
		bWriter.close();
	}
}
