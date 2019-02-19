package sg.dex.starfish;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class ADataAsset extends AAsset implements DataAsset {

	public ADataAsset(Agent agent, String meta) {
		super(agent, meta);
	}

	@Override
	public boolean isDataAsset() {
		return true;
	}

	public abstract long getSize();

	@Override
	public byte[] getBytes() {
		InputStream is = getInputStream();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		byte[] buf = new byte[16384];

		int bytesRead;
		try {
			while ((bytesRead = is.read(buf, 0, buf.length)) != -1) {
				buffer.write(buf, 0, bytesRead);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return buffer.toByteArray();
	}
}
