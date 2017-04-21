package com.edu.thanhnam.noteweek3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.edu.thanhnam.noteweek3.model.Note;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class InforNoteActivity extends AppCompatActivity {
  private TextView txtContent, txtTitle, txtTime;
  private EditText edtTitle, edtTime, edtContent;
  private Note note;
  private Button btnOk;
  private DatePikerFragment datePikerFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_infor_note);

    txtTitle = (TextView) this.findViewById(R.id.txt_title);
    txtTime = (TextView) this.findViewById(R.id.txt_time);
    txtContent = (TextView) this.findViewById(R.id.txt_content);
    edtTitle = (EditText) this.findViewById(R.id.edt_title);
    edtTime = (EditText) this.findViewById(R.id.edt_time);
    edtContent = (EditText) this.findViewById(R.id.edt_content);
    btnOk = (Button) this.findViewById(R.id.btn_ok);
    note = (Note) getIntent().getSerializableExtra("note");

    txtTitle.setText(note.getTitle());
    txtTime.setText(note.getTime());
    txtContent.setText(note.getContent());
    datePikerFragment = new DatePikerFragment();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_infor, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.mnu_edit:
        txtTime.setVisibility(View.INVISIBLE);
        edtTime.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.INVISIBLE);
        edtTitle.setVisibility(View.VISIBLE);
        txtContent.setVisibility(View.INVISIBLE);
        edtContent.setVisibility(View.VISIBLE);
        btnOk.setVisibility(View.VISIBLE);
        edtTitle.setText(txtTitle.getText().toString());
        edtTime.setText(txtTime.getText().toString());
        edtContent.setText(txtContent.getText().toString());
        edtTime.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            DatePikerFragment date = new DatePikerFragment();
            date.show(getFragmentManager(), "birthday");
          }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            String strTitle = edtTitle.getText().toString().trim();
            String strTime = edtTime.getText().toString().trim();
            String strContent = edtContent.getText().toString().trim();
            if (TextUtils.isEmpty(strTitle)) {
              edtTitle.setError("input title please");
            } else if (TextUtils.isEmpty(strTime)) {
              edtTime.setError("input time please");
            } else {
              String id = note.getId();
              note = new Note(strTitle, strTime, strContent);
              DatabaseReference database = FirebaseDatabase.getInstance().getReference("notes");
              database.child(id).child("title").setValue(strTitle);
              database.child(id).child("time").setValue(strTime);
              database.child(id).child("content").setValue(strContent);
              setResult(10);
              finish();
             /* if (ok != 0) {
                Toast.makeText(InforNoteActivity.this, "update success", Toast.LENGTH_LONG).show();

              } else {
                Toast.makeText(InforNoteActivity.this, "update fail", Toast.LENGTH_LONG).show();
              }*/
            }

          }
        });

        break;
      case R.id.mnu_delete:
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notes");
        databaseReference.child(note.getId()).removeValue();
        Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show();
        setResult(30);
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
