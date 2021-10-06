package com.company.application;

import com.company.domain.Message;
import com.company.domain.User;
import com.company.infrastructure.MessageRepository;
import com.company.infrastructure.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import express.Express;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import io.javalin.websocket.WsContext;

public class MessageHandler {
    private final Express app;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageHandler(Express app, MessageRepository messageRepository, UserRepository userRepository){
        this.app = app;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        initMessageHandler();
    }


    private void initMessageHandler() {
        List<WsContext> clients = new ArrayList<>();

        app.get("/api/usersInChat", (req, res) -> {
            List<String> usernames = new ArrayList<>();
            for (WsContext ctx : clients) {
                if (!usernames.contains(ctx.pathParam("username"))) {
                    usernames.add(ctx.pathParam("username"));
                }
            }
            res.json(usernames);
        });

        app.get("/api/getAllMessagesForUser/:id", (req, res) -> {
            List<User> user = userRepository.findByUsername(req.params("id"));

            // get both sent and received messages for a user
            List<Message> messages = messageRepository.findAllForUser(user.get(0));

            // order the messages for display
            messages.sort(Comparator.comparing(Message::getMessageId));

            res.json(messages);
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
                for (WsContext client : clients) {
                    if (client.pathParam("username").equals(ctx.pathParam("username"))) {
                        client.send(msg);
                    }
                }
                //clients.forEach(client -> client.send(msg)); // convert to json and send back to ALL connected clients
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
