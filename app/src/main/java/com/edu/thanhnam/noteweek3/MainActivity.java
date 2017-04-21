package com.edu.thanhnam.noteweek3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.thanhnam.noteweek3.adapter.AdapterNote;

import com.edu.thanhnam.noteweek3.model.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {

  private ListView lvNote;
  private TextView txtNote;
  private AdapterNote adapterNote;
  private MenuItem miUndo, mnuDelete;
  DatabaseReference database = FirebaseDatabase.getInstance().getReference("notes");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    lvNote = (ListView) this.findViewById(R.id.lv_note);
    txtNote = (TextView) this.findViewById(R.id.txt_noty);

    lvNote.setOnItemClickListener(this);
    lvNote.setLongClickable(true);
    lvNote.setOnItemLongClickListener(this);
    adapterNote = new AdapterNote(MainActivity.this, new ArrayList<Note>());
    lvNote.setAdapter(adapterNote);

    database.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        List<Note> notes = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
          Note note = child.getValue(Note.class);
          note.setId(child.getKey());
          notes.add(note);
        }
        adapterNote.setNotes(notes);
        if (notes.size() > 0) {
          txtNote.setVisibility(View.GONE);
        } else {
          txtNote.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menuadd, menu);

    mnuDelete = menu.findItem(R.id.mnu_delete).setVisible(false);
    miUndo = menu.findItem(R.id.mnu_undo).setVisible(false);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.mnu_delete:
        for (int i = 0; i < adapterNote.getCount(); i++) {
          if (adapterNote.getItem(i).isSelected()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notes");
            databaseReference.child(adapterNote.getItem(i).getId()).removeValue();
          }

        }
        break;
      case R.id.mnu_undo:
        for (int i = 0; i < adapterNote.getCount(); i++) {
          if (adapterNote.getItem(i).isSelected()) {
            adapterNote.getItem(i).setSelected(false);
            adapterNote.notifyDataSetChanged();
            lvNote.setAdapter(adapterNote);
          }

        }
        break;
      case R.id.mnu_add:
        Intent intent = new Intent(MainActivity.this, DetailNoteActivity.class);
        startActivityForResult(intent, 200);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 200 && resultCode == 20 && data != null) {
      adapterNote.notifyDataSetChanged();
      lvNote.setAdapter(adapterNote);

    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Note note = adapterNote.getItem(position);
    Intent intent = new Intent(MainActivity.this, InforNoteActivity.class);
    intent.putExtra("note", note);
    startActivityForResult(intent, 100);
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    adapterNote.getNotes().get(position).setSelected(true);
    adapterNote.notifyDataSetChanged();
    return false;
  }

  public void toggleUndo(boolean visibility) {
    miUndo.setVisible(visibility);
    mnuDelete.setVisible(visibility);
  }
}
