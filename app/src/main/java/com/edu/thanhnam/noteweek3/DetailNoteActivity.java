package com.edu.thanhnam.noteweek3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.edu.thanhnam.noteweek3.model.Note;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailNoteActivity extends AppCompatActivity {

  private EditText edtTitle, edtContent, edtTime;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_note);

    edtTitle = (EditText) this.findViewById(R.id.edt_title);
    edtTime = (EditText) this.findViewById(R.id.edt_time);

    edtContent = (EditText) this.findViewById(R.id.edt_content);
    edtTime.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePikerFragment date = new DatePikerFragment();
        date.show(getFragmentManager(), "birthday");
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_detail, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    String strTitle = edtTitle.getText().toString().trim();
    String strTime = edtTime.getText().toString().trim();
    String strContent = edtContent.getText().toString().trim();
    if (TextUtils.isEmpty(strTitle)) {
      edtTitle.setError("input title please");
    } else if (TextUtils.isEmpty(strTime)) {
      edtTime.setError("input time please");
    } else {
      Note note = new Note(strTitle, strTime, strContent);
      DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notes");
      String key = databaseReference.push().getKey();
      databaseReference.child(key).setValue(note);
      Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
      setResult(20);
      finish();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

  }
}
