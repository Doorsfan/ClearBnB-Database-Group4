package com.company.application;

import com.company.domain.Message;
import com.company.infrastructure.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
        List<WsContext> clients = new ArrayList<>();

        app.get("/rest/usersInChat", (req, res) -> {
            List<String> usernames = new ArrayList<>();
            for (WsContext ctx : clients) {
                usernames.add(ctx.pathParam("username"));
            }
            res.json(usernames);
        });

        app.ws("/websocket/:username", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Connected");
                clients.add(ctx);
            });

            ws.onMessage(ctx -> {
                Message msg = ctx.message(Message.class); // convert from json
                messageRepository.save(msg);
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
