package de.derandroidpro.notizenapptutorial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Edit_Notes_Activity extends AppCompatActivity {

    String notetext;
    File notefile;

    EditText editText2;

    boolean isDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__notes);

        editText2 = (EditText) findViewById(R.id.editText2);

        if(getIntent().hasExtra("EXTRA_NOTE_NEXT") && getIntent().hasExtra("EXTRA_NOTE_FILE")){

            notetext = getIntent().getStringExtra("EXTRA_NOTE_NEXT");
            notefile = (File) getIntent().getExtras().get("EXTRA_NOTE_FILE");

            editText2.setText(notetext);

        }


    }

    @Override
    protected void onPause() {

        if(!isDeleted) {
            try {
                OutputStream outputStream = new FileOutputStream(notefile);
                outputStream.write(editText2.getText().toString().getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit__notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_note) {
            deleteNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Edit_Notes_Activity.this);
        dialog.setTitle("Notiz löschen?");
        dialog.setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notefile.delete();
                Toast.makeText(getApplicationContext(), "Notiz wurde gelöscht!", Toast.LENGTH_SHORT).show();
                isDeleted = true;
                finish();
            }
        });

        dialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nichts
            }
        });

        dialog.show();

    }
}
