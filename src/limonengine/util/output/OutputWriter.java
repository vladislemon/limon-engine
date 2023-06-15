package limonengine.util.output;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: slimon
 * Date: 27.12.13
 * Time: 20:40
 */
public class OutputWriter {

	private OutputStream outputStream;

	public OutputWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void write(byte[] data) {
		try {
			if(outputStream != null) {
				outputStream.write(data);
                outputStream.flush();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			outputStream.close();
            outputStream = null;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
