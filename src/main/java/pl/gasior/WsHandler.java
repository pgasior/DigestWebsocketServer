package pl.gasior;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class WsHandler {

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        //l//ogger.info("User logged " + user.getRemoteAddress().getHostString());
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) throws IOException {
        user.getRemote().sendString(message);
    }
}
