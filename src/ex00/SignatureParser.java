import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignatureParser implements SignatureReader {

	private final Scanner scanner;

	public SignatureParser(File signatures) throws FileNotFoundException {
		this.scanner = new Scanner(signatures);
	}

	private String readLine() {
		if (scanner.hasNextLine()) {
			return (scanner.nextLine());
		}
		return ("");
	}

	private String getValue(String line) {
		return (line.substring(0, line.indexOf(COMMA)));
	}

	private String getKey(String line) {
		return (line.substring(line.indexOf(COMMA) + 2));
	}

	private short[] convertKeyToBytes(String key) {
		int count = countBytes(key);
		short[] ret = new short[count];

		Scanner keyParser = new Scanner(key).useRadix(16);

		for (int i = 0; i < count; i++) {
			ret[i] = keyParser.nextShort();
		}
		return (ret);
	}

	private int countBytes(String key) {
		int count = 0;

		Scanner keyParser = new Scanner(key).useRadix(16);

		while (keyParser.hasNextShort()) {
			keyParser.nextShort();
			count++;
		}
		keyParser.close();
		return (count);
	}

	public Map<short[], String> parseKeyValues() {
		Map<short[], String> ret = new HashMap<>();
		String line = this.readLine();

		while (!line.isEmpty()) {
			short[] key = convertKeyToBytes(getKey(line));
			String value = getValue(line);
			if (key.length > 0 && !value.isEmpty()) {
				ret.put(key, value);
			}
			line = this.readLine();
		}
		return (ret);
	}
}
