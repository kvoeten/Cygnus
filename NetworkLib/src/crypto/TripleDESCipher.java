package crypto;

import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import net.OutPacket;
import net.Socket;

/**
 *
 * @author PacketBakery
 */
public class TripleDESCipher {

    public byte[] aKey = new byte[24];
    public Key pKey;

    public static byte[] GenKey(String sCharacterID, byte[] aMachineID) {
        byte[] aKey = new byte[24];
        int nLen = sCharacterID.length();
        for (int i = 0; i < nLen; i++) {
            aKey[i] = (byte) sCharacterID.charAt(i);
        }
        for (int i = nLen; i < 16; i++) {
            aKey[i] = aMachineID[i - nLen];
        }
        System.arraycopy(aKey, 0, aKey, 16, 8);
        return aKey;
    }

    public TripleDESCipher(byte[] aKey) {
        System.arraycopy(aKey, 0, this.aKey, 0, aKey.length);
        this.pKey = new SecretKeySpec(aKey, "DESede");
    }

    public byte[] Encrypt(byte[] aData) throws Exception {
        Cipher pCipher = Cipher.getInstance("DESede");
        pCipher.init(Cipher.ENCRYPT_MODE, this.pKey);
        return pCipher.doFinal(aData);
    }

    public byte[] Decrypt(byte[] aData) throws Exception {
        Cipher pCipher = Cipher.getInstance("DESede");
        pCipher.init(Cipher.DECRYPT_MODE, this.pKey);
        return pCipher.doFinal(aData);
    }

    public void Encode(OutPacket oPacket, String sBuffer) {
        try {
            byte[] aBuffer = new byte[Short.MAX_VALUE + 1];//Padding
            byte[] aEncrypt = Encrypt(sBuffer.getBytes());
            System.arraycopy(aEncrypt, 0, aBuffer, 0, aEncrypt.length);
            for (int i = aEncrypt.length; i < aBuffer.length; i++) {
                aBuffer[i] = (byte) Math.random();
            }
            oPacket.EncodeInt(aBuffer.length);
            oPacket.EncodeBuffer(aBuffer);
        } catch (Exception ex) {
            Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, "TripleDES Encryption error:\r\n{0}", ex.getLocalizedMessage());
            oPacket.EncodeInt(0);
        }
    }
}
