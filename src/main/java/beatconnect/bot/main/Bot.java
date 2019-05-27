package beatconnect.bot.main;

import lt.ekgame.bancho.client.BanchoClient;
import lt.ekgame.bancho.client.BanchoClientBuilder;
import lt.ekgame.bancho.client.EventHandler;
import lt.ekgame.bancho.client.channels.Channel;
import lt.ekgame.bancho.client.channels.User;
import lt.ekgame.bancho.client.events.EventMessage;
import lt.ekgame.bancho.client.events.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Bot {
    private static boolean isIncomingPrivateMessage(Event event, BanchoClient client){
        Channel channel = ((EventMessage) event).getChannel();
        int currentUserID = client.getClientManager().getUserId();
        User currentUser = client.getChannelManager().getUserById(currentUserID);
        return currentUser.getUsername().toLowerCase().equals(channel.getName());
    }

    private static void runCommand(EventMessage event){
        if(event.getContent().startsWith("!")){
            String commandString = event.getContent().substring(1);
            Command command = new Command();
            User sender = event.getSender();
            try {
                Method commandFunc = Command.class.getMethod(commandString);
                Object answer = commandFunc.invoke(command);
            }
            catch (NoSuchMethodException e) {
                sender.sendMessage("Unknown command, please use !infos to check commands");
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                sender.sendMessage("Something went pretty wrong server side, please try later or contact me on BeatConnect discord");
            }
            System.out.println(event.getContent());
        }
    }

    public static void main(String[] args) {
        Settings settings = new Settings();
        BanchoClientBuilder builder = new BanchoClientBuilder(settings.getUsername(), settings.getPassword());
        BanchoClient client = builder.build();
        client.addEventHandler((Event event) -> {
            if (event instanceof EventMessage) {
                if(isIncomingPrivateMessage(event, client)) {
                    runCommand((EventMessage)event);
                }
            }
        });

    }
}
