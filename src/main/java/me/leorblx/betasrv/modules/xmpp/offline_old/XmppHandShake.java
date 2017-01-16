package me.leorblx.betasrv.modules.xmpp.offline_old;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class XmppHandShake
{
    private XmppTalk xmppTalk;
    private int pkgCount = 0;

    public XmppHandShake(XmppTalk xmppTalk)
    {
        this.xmppTalk = xmppTalk;
        handShakeBeforeSsl();
        handShakeAfterSsl();
    }

    private void handShakeBeforeSsl()
    {
        String[] packets = new String[2];
        packets[0] = "<stream:stream xmlns='jabber:client' xml:lang='en' xmlns:stream='http://etherx.jabber.org/streams' from='127.0.0.1' id='174159513' version='1.0'><stream:features><starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/></stream:features>";
        packets[1] = "<proceed xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>";
        do {
            try {
                xmppTalk.read().get();
                xmppTalk.write(packets[pkgCount]);
                pkgCount++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } while (pkgCount < packets.length);
        TlsWrapper.wrapXmppTalk(xmppTalk);
        pkgCount = 0;
    }

    private void handShakeAfterSsl()
    {
        int personaId = 0;
        String[] packets = new String[4];
        packets[0] = "<stream:stream xmlns='jabber:client' xml:lang='en' xmlns:stream='http://etherx.jabber.org/streams' from='127.0.0.1' id='5000000000000A' version='1.0'><stream:features/>";
        packets[1] = "";
        packets[2] = "<iq id='EA-Chat-2' type='result' xml:lang='en'/>";
        packets[3] = "";
        do {
            String read;
            
            try {
                read = xmppTalk.read().get(3, TimeUnit.SECONDS);

                if (pkgCount == 1) {
                    int start = read.indexOf("<username>nfsw.");
                    read = read.substring(start);
                    read = read.replaceAll("\\D+", "");
                    personaId = Integer.valueOf(read);
                    packets[1] = "<iq id='EA-Chat-1' type='result' xml:lang='en'><query xmlns='jabber:iq:auth'><username>nfsw." + personaId
                            + "</username><password/><digest/><resource/><clientlock xmlns='http://www.jabber.com/schemas/clientlocking.xsd'/></query></iq>";
                    System.out.println("parse personaId: " + personaId);
                }
                xmppTalk.write(packets[pkgCount]);
                pkgCount++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("Didn't receive anything after 3 seconds, sending next packet");
                xmppTalk.write(packets[pkgCount]);
                pkgCount++;
            }
        } while (pkgCount < 3);
        for (int i = 0; i < 3; i++) {
            xmppTalk.read();
        }
        
        packets[3] = XmppChat.getPresenceResponse(100L, "en", 13);
//        packets[3] = "<presence from='channel.en__1@conference.127.0.0.1' to='nfsw." + personaId
//                + "@127.0.0.1/EA-Chat' type='error'><error code='401' type='auth'><not-authorized xmlns='urn:ietf:params:xml:ns:xmpp-stanzas'/></error><x xmlns='http://jabber.org/protocol/muc'/></presence>";
        xmppTalk.write(packets[3]);
        xmppTalk.setPersonaId(personaId);
        XmppSrv.addXmppClient(personaId, xmppTalk);
    }

}