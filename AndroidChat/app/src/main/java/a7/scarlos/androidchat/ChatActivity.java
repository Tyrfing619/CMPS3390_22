package a7.scarlos.androidchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat$InspectionCompanion;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements CanWriteMessage {
    private String userName;
    private Socket socket;
    private Client client;
    private ArrayList<String> messages, members;
    private RecyclerView messagesList, membersList;
    private EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        this.userName = intent.getStringExtra("userName");
        startClient(userName);

        txtMessage = findViewById(R.id.txtMessage);

        messagesList = findViewById(R.id.messagesList);
        messages = new ArrayList<>();
        messagesList.setAdapter(new RecyclerAdapter(this, messages));
        messagesList.setLayoutManager(new LinearLayoutManager(this));

        membersList = findViewById(R.id.membersList);
        members = new ArrayList<>();
        membersList.setAdapter(new RecyclerAdapter(this, members));
        membersList.setLayoutManager(new LinearLayoutManager(this));

        txtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((i & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_DONE) {
                    onSendClicked(textView);

                    return true;
                }
                return false;
            }
        });

        messagesList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                messagesList.scrollToPosition(messages.size()-1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    private void startClient(String userName) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("odin.cs.csub.edu", 3390);
                    client = new Client(socket, userName);
                    client.listenForMessages(ChatActivity.this);
                    client.sendMessage(userName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void onSendClicked(View v) {
        String messageToSend = txtMessage.getText().toString();
        client.sendMessage(userName + ": " + messageToSend);
        txtMessage.setText(null);
        hideKeyboard(this);
    }

    public static void hideKeyboard(ChatActivity chatActivity) {
        InputMethodManager imm = (InputMethodManager) chatActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = chatActivity.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void writeMessage(String message) {
        switch(message.charAt(0)) {
            case '+':
                members.add(message.substring(1));
                break;
            case '-':
                members.remove(message.substring(1));
                break;
            default:
                messages.add(message);
        }

        ChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messagesList.getAdapter().notifyDataSetChanged();
                membersList.getAdapter().notifyDataSetChanged();
                messagesList.scrollToPosition(messages.size() - 1);
            }
        });
    }


    public static Intent createIntent(Context context, String userName) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("userName", userName);
        return intent;
    }
}