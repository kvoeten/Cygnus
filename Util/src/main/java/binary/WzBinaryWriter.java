package binary;

import net.packet.OutPacket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WzBinaryWriter extends OutPacket {
    private final String sPath;

    public WzBinaryWriter(String sPath) {
        super(Short.MAX_VALUE);
        this.sPath = sPath;
    }

    public void Write() {
        File pFile = new File(sPath);
        if (pFile.exists()) {
            if(!pFile.delete()) {
                System.out.println("Unable to delete file: " + pFile.getName());
            }
        }
        try (FileOutputStream pStream = new FileOutputStream(new File(sPath))) {
            pStream.write(GetData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
