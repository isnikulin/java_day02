import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Program {

	private static final String SIGNATURES = "/Users/aarcelia/IdeaProjects/java_piscine/java_day02/src/ex00/signatures.txt";
	private static final String RESULT = "result.txt";

	public static void main(String[] args) throws IOException {

		SignatureParser signatureParser = new SignatureParser(new File(SIGNATURES));

		Map<short[], String> parsedSignatures = signatureParser.parseKeyValues();

		List<String> result = new FileSignatureParser().getResultingTypes(parsedSignatures);

		saveResult(result);
	}

	public static void saveResult(List<String> result) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(RESULT);

		for (String line : result) {
			outputStream.write((line + '\n').getBytes(StandardCharsets.UTF_8));
		}
	}
}
