package simpletodo.sundarm.yahoo.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class SimpleTodo extends Activity {

    ArrayList<String> itemsList;
    ArrayAdapter<String> toDoAdapter;
    ListView lvItems;
    EditText etNewItem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_todo);
        etNewItem1 = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsList = new ArrayList<String>();
        readItems();
        toDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsList);
        lvItems.setAdapter(toDoAdapter);
        setupListViewListener();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v){
        String textEntered = etNewItem1.getText().toString();
        toDoAdapter.add(textEntered);
        etNewItem1.setText("");
        writeItems();


    }

    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long l) {
                itemsList.remove(pos);
                toDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void readItems()
    {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            itemsList = (ArrayList<String>)FileUtils.readLines(file);
        }
        catch (IOException e)
        {
            itemsList = new ArrayList<String>();
        }
    }

    private void writeItems()
    {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, itemsList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

