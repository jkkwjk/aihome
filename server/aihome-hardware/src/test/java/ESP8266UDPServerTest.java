import com.jkk.aihome.core.ESP8266UDPServer;
import com.jkk.aihome.core.UDPHandleProcess;
import com.jkk.aihome.model.AppUser;
import com.jkk.aihome.model.HandleResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ESP8266UDPServerTest {
	String appId = "test";
	UDPHandleProcess sendSame;
	ESP8266UDPServer server;
	@Before
	public void setting() {
		server = new ESP8266UDPServer(2333, ',');

		sendSame = (data, appUser) -> {
			server.send(data, appUser);

			return new HandleResult(true, data);
		};

		server.register(appId, Collections.singletonList(sendSame));
	}

	@Test
	public void main() {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String s = sc.next();
			if (s.equals("o")) {
				List<AppUser> appUsers = server.getOnlineList(appId);
				appUsers.forEach(t -> System.out.println(t.getAddress()+":"+t.getPort()));
			}else if (s.equals("s")) {
				AppUser appUser = server.getOnlineList(appId).get(0);
				server.send("1", appUser);
			}else if (s.equals("sall")) {
				server.send("0", server.getOnlineList(appId));
			}else if (s.equals("sc")) {
				server.sendAndCheckOnline(appId,"1", server.getOnlineList(appId),3000);
			}
		}
	}
}
