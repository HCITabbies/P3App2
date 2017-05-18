package com.p3app2;

import android.os.AsyncTask;
import android.util.Log;

import com.p3app2.Chat_Window.ChatMessage;
import com.p3app2.Chat_Window.ChatWindowActivity;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.util.Date;

import static android.os.SystemClock.sleep;
import static com.p3app2.Chat_Window.ChatWindowActivity.scroll;

/**
 * Created by prashantanantharaman on 5/14/17.
 */

public class XMPPConnections {

    Chat currentChat;
    private static final String DOMAIN = "prashant";
    private static final String HOST = "prashant.at";
    private static final int PORT = 5222;
    private String userName = "jack";
    private String passWord = "secr3t";
    AbstractXMPPConnection connection;
    ChatManager chatmanager;
    static Chat newChat;
    public static boolean connected = false;
    private boolean isToasted;
    private boolean chat_created;
    private boolean loggedin;


    public XMPPConnections() {
        Log.i("XMPP", "Initializing!");


        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setUsernameAndPassword(userName, passWord);
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setResource("Android");
        configBuilder.setServiceName(DOMAIN);

        configBuilder.setHost(HOST);
        configBuilder.setPort(PORT);
        configBuilder.setDebuggerEnabled(true);

        connection = new XMPPTCPConnection(configBuilder.build());
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    public static void sendMessage(final String message) throws SmackException.NotConnectedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (!connected) {
                        sleep(1000);
                        Log.d("Not connected", "Not connected");
                    }
                    if (connected)
                        newChat.sendMessage(message);
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.run();
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> implements ConnectionListener {


        @Override
        protected Void doInBackground(Void... params) {

            Log.d("Init", "Init");
            try {
                init(userName, passWord);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public void init(String userId, String pwd) throws NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException, IOException {

            try {
                connection.connect();
                connection.login();
                chatmanager = ChatManager.getInstanceFor(connection);
                newChat = chatmanager.createChat("kirti@prashant", new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        ChatMessage msg = new ChatMessage();
                        msg.setId(message.getStanzaId());
                        msg.setIsStudent(false);
                        msg.setMessage(message.getBody());
                        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                        ChatWindowActivity.displayMessage(msg);
                        ChatWindowActivity.scroll();
                        Log.d("Received", message.getBody());
                    }
                });
                connected = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void connected(XMPPConnection connection) {
            Log.d("ABCD", "Connected");
        }

        @Override
        public void authenticated(XMPPConnection connection, boolean resumed) {

        }

        @Override
        public void connectionClosed() {
            connected = false;
            Log.d("XMPP", "Disconnected");

        }

        @Override
        public void connectionClosedOnError(Exception e) {
            connected = false;
            Log.d("XMPP", " Closed");


        }

        @Override
        public void reconnectionSuccessful() {

        }

        @Override
        public void reconnectingIn(int seconds) {

        }

        @Override
        public void reconnectionFailed(Exception e) {

        }
    }

}
