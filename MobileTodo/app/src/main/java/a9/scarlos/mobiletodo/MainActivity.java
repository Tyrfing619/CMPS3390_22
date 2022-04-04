package a9.scarlos.mobiletodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnItemListener {

    private TabLayout tabLists;
    private EditText txtTodoItem;
    private RecyclerView listItems;
    private String selectedTab;
    private ArrayList<Item> items = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLists = findViewById(R.id.tabLists);
        txtTodoItem = findViewById(R.id.txtTodoItem);
        listItems = findViewById(R.id.listItems);
        selectedTab = getResources().getString(R.string.home);

        listItems.setAdapter(new RecyclerAdapter(this, items, this));
        listItems.setLayoutManager(new LinearLayoutManager(this));

        tabLists.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = Objects.requireNonNull(tab.getText()).toString();
                Log.i("TAB_CHANGED", selectedTab);
                getItems();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        txtTodoItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Item tmp = new Item(txtTodoItem.getText().toString());
                Log.i("REFERENCE", tmp.getDesc());
                addItem(tmp);
                txtTodoItem.setText(null);
                return false;
            }
        });
        getItems();
    }

    public void onAddClick(View v) {
        txtTodoItem.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    //-----------------------------Firebase Functions-----------------------------//
    private void addItem(Item item) {
        try {
            String path = URLEncoder.encode(item.getDesc(), String.valueOf(StandardCharsets.UTF_8));
            DatabaseReference ref = database.getReference(selectedTab + "/" + path);
            ref.setValue(item);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void getItems() {
        DatabaseReference ref = database.getReference(selectedTab);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while(iterator.hasNext())  {
                    HashMap val = (HashMap) iterator.next().getValue();
                    Item tmp = new Item(val.get("desc").toString());
                    items.add(tmp);
                }
                listItems.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Log.i("ITEM_CLICKED", items.get(position).getDesc());
        try {
            String path = URLEncoder.encode(items.get(position).getDesc(), String.valueOf(StandardCharsets.UTF_8));
            DatabaseReference ref = database.getReference(selectedTab + "/" + path);
            ref.removeValue();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}

//Reminder, rules in Firebase DB are now set to "true" for permissions, so it is completely public.  Set to false when the app has an authenticator