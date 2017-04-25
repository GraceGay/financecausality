package edu.sjtu.adapt;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;
public interface CauseEffectPatternInterface {
@SuppressWarnings("serial")
	LinkedHashMap<Pattern, Integer> pattern=new LinkedHashMap<Pattern,Integer>(){
		{
//			put("([\\s\\S]*?)(只怪|由于)([\\s\\S]*?)[，]([\\s\\S]*)", 123);
//			put("([\\s\\S]+)(这说明|表明|暗示)([\\s\\S]*)", 231);
//			Arrays.asList("原来" ,"以便" ,"是故" ,"以致" ,"因为" ,"因此" ,"所以" ,"由于" ,"致使" ,
//					"因而" ,"只怪" ,"这说明" ,"表明了","这表明" ,"出于" ,"起源于" ,,"的原因" ,"原因是" ,"取决于" ,"导致" )
			put(Pattern.compile("([\\s\\S]*?)(因为|由于|出于|源于)([\\s\\S]*?)(，?所以|导致[\\s]?了?|因此|故)([\\s\\S]*)"), 135);
			put(Pattern.compile("([\\s\\S]+)(所以|导致[\\s]?了?|因此|致使)([\\s\\S]*)"), 213);
			put(Pattern.compile("([\\s\\S]+)(是[\\s]?因为|是[\\s]?由于|原因是)([\\s\\S]*)"), 331);
//			put(Pattern.compile("([\\s\\S]*?)(因为|由于|出于|源于)([\\s\\S]*?)，([\\s\\S]*)"), 434);
			
//			put("([\\s\\S]*?)(，是因为|，是由于|，起源于|，来自于|，在于|，取决于)([\\s\\S]*)$", 431);
//			put("([\\s\\S]*)(，所以|，因此|导致)([\\s\\S]*)", 513);
//			put("(早知道)([\\s\\S]*?)就([\\s\\S]*)", 623);
//			put("^([\\s\\S]*)(的原因是|原来是)([\\s\\S]*)", 731);
//			put("之所以([\\s\\S]*)(是因为|原来是)([\\s\\S]*)", 831);
		}
	};
}
