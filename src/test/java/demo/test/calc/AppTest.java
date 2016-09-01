package demo.test.calc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class AppTest {
	private String operand1;
	private String operand2;
	private String operator;
	private String result;

	public AppTest(String operand1, String operand2, String operator,
			String result) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operator = operator;
		this.result = result;
	}

	@Parameterized.Parameters
	public static Collection<String[]> inputData() throws IOException {
		List<String[]> data = new ArrayList<String[]>();

		String fileName = "./test.csv";
		String pattern = "^[\\d]+,[\\d]+,[\\+\\-\\*\\/]{1},[\\d]+$";
		Pattern p = Pattern.compile(pattern);

		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String line = "";

		while ((line = br.readLine()) != null) {
			System.out.println(line);
			Matcher m = p.matcher(line);
			if (m.matches()) {
				String[] str = line.split(",");
				data.add(str);
			}
			else {
				System.out.println("Invalid line: " + line);
			}
		}
		br.close();
		return data;

	}
	

	@Test
	public void testApp() {
		Integer expectedResult = null;
		Integer factResult = Integer.parseInt(result);
		if (operator.equals("+"))
			expectedResult = Integer.parseInt(operand1)
					+ Integer.parseInt(operand2);
		if (operator.equals("-"))
			expectedResult = Integer.parseInt(operand1)
					- Integer.parseInt(operand2);
		if (operator.equals("*"))
			expectedResult = Integer.parseInt(operand1)
					* Integer.parseInt(operand2);
		if (operator.equals("/")) {
			assertFalse("Cannot divide by zero!", operand2.equals("0"));
			expectedResult = Integer.parseInt(operand1)
					/ Integer.parseInt(operand2);
		}

		assertEquals(expectedResult, factResult);

	}
}
