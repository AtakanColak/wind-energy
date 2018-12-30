package Network;

import sun.security.pkcs11.Secmod.Module;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
public class NetState {
	public static String remoteInetAddr = "59.231.36.59";//IP address
	/**
	 * get IP check successful or not
	 * @param remoteInetAddr
	 * @return
	 */
	public static boolean isReachable(String remoteInetAddr) {
		boolean reachable = false;
		try {
			InetAddress address = InetAddress.getByName(remoteInetAddr);
			reachable = address.isReachable(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reachable;
	}
}
