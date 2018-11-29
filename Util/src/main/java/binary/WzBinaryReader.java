package binary;

import net.packet.InPacket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class WzBinaryReader extends InPacket {

    public WzBinaryReader(String sPath) {
        super();

        File pFile = new File(sPath);
        if (pFile.exists()) {
            try (BufferedInputStream pStream = new BufferedInputStream(new FileInputStream(pFile))) {
                byte[] aData = new byte [pStream.available()];
                pStream.read(aData);
                AppendBuffer(aData);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Unable to read file");
        }
        DecodeShort(); //Skip useless opcode, TODO: use for defining data later.
    }
}
