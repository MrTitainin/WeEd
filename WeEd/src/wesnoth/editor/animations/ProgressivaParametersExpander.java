package wesnoth.editor.animations;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgressivaParametersExpander {

	public String[] expand(String input) {
		String[] progressiveValues = getProgressiveValues(input);
		String[] outputParts = getOutputParts(input);
		String[][] expandedValues = expandValues(progressiveValues);

		int count = getCount(expandedValues);
		int valueLength = outputParts.length + progressiveValues.length;

		String[] result = new String[count];

		for (int i = 0; i < count; i++) {
			StringBuilder stringBuilder = new StringBuilder("");
			for (int j = 0; j < valueLength; j++)
				if(j%2==0)
					stringBuilder.append(outputParts[j/2]);
				else
					stringBuilder.append(expandedValues[i][j/2]);
			result[i] = stringBuilder.toString();
		}

		return new String[] { "" };
	}

	private int getCount(String[][] expandedValues) {
		int min = Integer.MAX_VALUE;
		for (String[] valueList : expandedValues)
			if(valueList.length < min)
				min = valueList.length;
		return min;
	}

	private String[] getOutputParts(String input) {
		String[] result = input.split("]");

		for (int i = 0; i < result.length; i++)
			result[i] = result[i].split("[")[0];

		return result;
	}

	private String[] getProgressiveValues(String input) {
		List<String> tempList = new LinkedList<>();
		Matcher m = Pattern.compile("\\[([^\\]]+)\\)").matcher(input);
		while (m.find()) {
			String found = m.group(1);
			System.out.println(found + " matched in " + input);
			tempList.add(found);
		}
		return tempList.toArray(new String[0]);
	}

	private String[][] expandValues(String[] packedValues) {
		String[][] result = new String[packedValues.length][0];
		for(int i = 0; i < packedValues.length; i++)
			result[i] = interpretProgressiveValue(packedValues[i]);
		return result;
	}

	private String[] interpretProgressiveValue(String value){
		return value.split(",");
	}
}
