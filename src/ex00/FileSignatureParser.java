import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileSignatureParser implements SignatureReader {

	private final String PROCESSED = "PROCESSED";
	private final String UNDEFINED = "UNDEFINED";

	public List<String> getResultingTypes(Map<short[], String> map) throws IOException {
		Scanner scanner = new Scanner(System.in);
		List<String> ret = new ArrayList<>();
		String input = scanner.nextLine();

		while (!input.equals(TERMINATOR)) {
			boolean wasProcessed = false;
			for (short[] key : map.keySet()) {
				FileInputStream fileScanner = new FileInputStream(input);

				short[] fileContent = getBytes(key.length, fileScanner);
				fileScanner.close();
				if (compareBytes(key, fileContent)) {
					ret.add(map.get(key));
					wasProcessed = true;
					break;
				}

			}
			System.out.println(wasProcessed ? PROCESSED : UNDEFINED);
			input = scanner.nextLine();
		}
		return (ret);
	}

	private short[] getBytes(int length, FileInputStream fileScanner) throws IOException {
		short[] bytes = new short[length];

		for (int i = 0; i < length; i++) {
			bytes[i] = (short) fileScanner.read();
		}
		return (bytes);
	}

	private boolean compareBytes(short[] key, short[] fileContent) {
		for (int i = 0; i < key.length; i++) {
			if (key[i] != fileContent[i]) {
				return (false);
			}
		}
		return (true);
	}
}
