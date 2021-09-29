package com.company.application;

import com.company.domain.Message;
import com.company.infrastructure.MessageRepository;
import express.Express;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.javalin.websocket.WsContext;

public class MessageHandler {
    private final Express app;
    private final MessageRepository messageRepository;

    public MessageHandler(Express app, MessageRepository messageRepository){
        this.app = app;
        this.messageRepository = messageRepository;
        initMessageHandler();
    }


    private void initMessageHandler() {
        app.get("/rest/hello", (req, res) -> {
            res.json(Map.of("message", "Hello from express"));
        });

        List<WsContext> clients = new ArrayList<>();

        app.ws("/websocket", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Connected");
                clients.add(ctx);
            });

            ws.onMessage(ctx -> {
                Message msg = ctx.message(Message.class); // convert from json
                System.out.println(ctx);
                clients.forEach(client -> client.send(msg)); // convert to json and send back to ALL connected clients
//        ctx.send(msg); // convert to json and send back (ONLY to the sender)
            });

            ws.onClose(ctx -> {
                System.out.println("Closed");
                clients.remove(ctx);
            });

            ws.onError(ctx -> {
                System.out.println("Errored");
                clients.remove(ctx);
            });
        });
    }
}
