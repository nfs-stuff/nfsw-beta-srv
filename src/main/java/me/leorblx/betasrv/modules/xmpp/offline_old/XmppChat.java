package me.leorblx.betasrv.modules.xmpp.offline_old;

import me.leorblx.betasrv.config.Configuration;
import me.leorblx.betasrv.config.ConfigurationManager;
import me.leorblx.betasrv.utils.HtmlUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmppChat
{
    private String chatMessage;
    private Long personaId;

    public XmppChat(Long personaId, String chatMsg)
    {
        this.personaId = personaId;
        this.chatMessage = chatMsg;
    }

    public Long getPersonaId()
    {
        return personaId;
    }

    public static String getPresenceResponse(Long personaId, String channelName, Integer channelNumber)
    {
        String xmppIp = ConfigurationManager.getInstance().getConfiguration().getXmppIp();
        String formatString = "<presence from='channel.%s__%d@conference.%s' to='nfsw.%d@%s/EA-Chat' xml:lang='en'>"
                + "<x xmlns='http://jabber.org/protocol/muc#user'><item affiliation='none' role='none'/></x></presence>";
        return String.format(formatString, channelName, channelNumber, xmppIp, personaId, xmppIp);
    }

    public static String getUserMessage(Long targetPersonaId, Long fromPersonaId, String fromName, String message)
    {
        String xmppIp = ConfigurationManager.getInstance().getConfiguration().getXmppIp();
        return String.format("<message from='nfsw.engine.engine@conference.%s/EA_Chat' id='JN_2578' to='nfsw.%d@%s'>"
                        + "<body>&lt;response status='1' ticket='0'&gt;&lt;ChatBroadcast &gt;&lt;ChatBlob&gt;&lt;FromName&gt;%s&lt;/FromName&gt;"
                        + "&lt;FromPersonaId&gt;%d&lt;/FromPersonaId&gt;&lt;FromUserId&gt;0&lt;/FromUserId&gt;&lt;Message&gt;%s"
                        + "&lt;/Message&gt;&lt;ToId&gt;0&lt;/ToId&gt;&lt;/ChatBlob&gt;&lt;/ChatBroadcast&gt;&lt;/response&gt;</body>"
                        + "<subject>69</subject></message>",
                xmppIp, targetPersonaId, xmppIp, fromName, fromPersonaId, message);
    }

    public static String getJoinChannelMessage(Long personaId, Integer channelNumber)
    {
        String xmppIp = ConfigurationManager.getInstance().getConfiguration().getXmppIp();
        
        return String.format("<message from='nfsw.engine.engine@conference.%s/EA_Chat' id='JN_2578' to='nfsw.%d@%s'>"
                + "<body>%s</body><subject>69</subject></message>",
                xmppIp,
                personaId,
                xmppIp,
                HtmlUtils.symbolsToEntities(String.format("<response status='1' ticket='0'>" +
                        "<ChatJoinChannelMsg><Channel>%d</Channel><Status>0</Status></ChatJoinChannelMsg></response>", channelNumber)));
    }

    public static String getFriendPresence(Long targetPersonaId, String personaName, Long personaId, boolean online)
    {
        String xmppIp = ConfigurationManager.getInstance().getConfiguration().getXmppIp();

        return String.format("<message from='nfsw.engine.engine@conference.%s/EA_Chat' id='JN_2578' to='nfsw.%d@%s'>" +
                        "<body><response status='1' ticket='0'>" +
                        "<PersonaBase>" +
                        "<userId>1000000</userId><personaId>%d</personaId>" +
                        "<name>%s</name><originalName>%s</originalName>" +
                        "<level>1</level><iconIndex>16</iconIndex>" +
                        "<presence>%d</presence><statusMessage />" +
                        "</PersonaBase></response></body><subject>69</subject></message>",
                xmppIp,
                targetPersonaId,
                xmppIp,
                personaId,
                personaName,
                personaName,
                online ? 2 : 0);
//        return String.format("<message from='nfsw.engine.engine@conference.%s/EA_Chat' id='JN_2578' to='nfsw.%d@%s'>" +
//                "<body>&lt;response status='1' ticket='0'&gt;&lt;PersonaBase&gt;" +
//                "&lt;userId&gt;100&lt;/userId&gt;&lt;personaId&gt;%d&lt;/personaId&gt;" +
//                "&lt;name&gt;%s&lt;/name&gt;&lt;originalName&gt;%s&lt;/originalName&gt;" +
//                "&lt;level&gt;1&lt;/level&gt;&lt;iconIndex&gt;16&lt;/iconIndex&gt;" +
//                "&lt;presence&gt;%d&lt;/presence&gt;&lt;statusMessage /&gt;" +
//                "&lt;/PersonaBase&gt;&lt;/response&gt;</body><subject>69</subject></message>", xmppIp, targetPersonaId, xmppIp, personaId, personaName, personaName, online ? 1 : 0);
//        return String.format("<message from='nfsw.engine.engine@conference.%s/EA_Chat' id='JN_2578' to='nfsw.%d@%s'>"
//                        + "<body>&lt;response status='1' ticket='0'&gt;&lt;ChatBroadcast &gt;&lt;ChatBlob&gt;&lt;FromName&gt;%s&lt;/FromName&gt;"
//                        + "&lt;FromPersonaId&gt;%d&lt;/FromPersonaId&gt;&lt;FromUserId&gt;0&lt;/FromUserId&gt;&lt;Message&gt;%s"
//                        + "&lt;/Message&gt;&lt;ToId&gt;0&lt;/ToId&gt;&lt;/ChatBlob&gt;&lt;/ChatBroadcast&gt;&lt;/response&gt;</body>"
//                        + "<subject>69</subject></message>", xmppIp, targetPersonaId, xmppIp);
    }

    public String getEventMessage(Long targetPersonaId)
    {
        Configuration configuration = ConfigurationManager.getInstance().getConfiguration();
        String newMsg = chatMessage;
        newMsg = newMsg.replaceFirst("message to=", "message from=");
        newMsg = newMsg.replaceFirst("@conference.".concat(configuration.getXmppIp()),
                "@conference.".concat(configuration.getXmppIp()).concat("/nfsw." + personaId));
        newMsg = newMsg.replaceFirst("type=", "to='nfsw."
                .concat(String.valueOf(targetPersonaId) + "@" + configuration.getXmppIp()).concat("/EA-Chat' type="));
        return newMsg;
    }

    public String getFreeroamMessage(Long hostPersonaId, Long targetPersonaId, String channelName, Integer channelNumber)
    {
        String xmppIp = ConfigurationManager.getInstance().getConfiguration().getXmppIp();
        String newMessageBlock = String.format("message from='nfsw.%s__%d@conference.%s/nfsw.%d' to='nfsw.%d@%s/EA-Chat'",
                channelName, channelNumber, xmppIp, hostPersonaId, targetPersonaId, xmppIp);
        return chatMessage.replaceFirst("message to='(.*)'", newMessageBlock);
    }

    public static String getSystemMessage(Long targetPersonaId, String announcementText)
    {
        String xmppIp = ConfigurationManager.getInstance().getConfiguration().getXmppIp();
        return String.format("<message from='nfsw.engine.engine@conference.%s/EA_Chat' id='JN_2578' to='nfsw.%d@%s'>"
                        + "<body>&lt;response status='1' ticket='0'&gt;&lt;ChatBroadcast &gt;&lt;ChatBlob&gt;&lt;FromName&gt;System&lt;/FromName&gt;"
                        + "&lt;FromPersonaId&gt;0&lt;/FromPersonaId&gt;&lt;FromUserId&gt;0&lt;/FromUserId&gt;&lt;Message&gt;%s"
                        + "&lt;/Message&gt;&lt;ToId&gt;0&lt;/ToId&gt;&lt;Type&gt;2&lt;/Type&gt;&lt;/ChatBlob&gt;&lt;/ChatBroadcast&gt;&lt;/response&gt;</body>"
                        + "<subject>69</subject></message>",
                xmppIp, targetPersonaId, xmppIp, announcementText);
    }

    public String getWhisperMessage(Long targetPersonaId)
    {
        Pattern regPattern = Pattern.compile(
                "message to='nfsw.(\\d+)@(.*)' type='(\\w+)'><channel>Chat_Whisper</channel>(.*)Uid=&quot;(\\d+)&quot;");
        Matcher match = regPattern.matcher(chatMessage);
        String newMsg = chatMessage;
        if (match.find()) {
            String messageType = match.group(3);
            Long hostUserId = Long.valueOf(match.group(5));
            Long hostPersonaId = 100L;
            String xmppIp = ConfigurationManager.getInstance().getConfiguration().getXmppIp();

            String newMessageBlock = String.format("message from='nfsw.%d@%s/EA-Chat' to='nfsw.%d@%s' type='%s'>",
                    hostPersonaId, xmppIp, targetPersonaId, xmppIp, messageType);
            newMsg = chatMessage.replaceFirst("message to='nfsw.(\\d+)@(.*)' type='(\\w+)'>", newMessageBlock);
        }
        return newMsg;
    }

}