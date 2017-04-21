package com.edu.thanhnam.noteweek3.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.edu.thanhnam.noteweek3.MainActivity;
import com.edu.thanhnam.noteweek3.R;
import com.edu.thanhnam.noteweek3.model.Note;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhNam on 4/12/2017.
 */

public class AdapterNote extends BaseAdapter {
  private Context context;
  private List<Note> notes;

  public AdapterNote(MainActivity context, ArrayList<Note> notes) {
    this.context = context;
    this.notes = notes;
  }

  @Override
  public int getCount() {
    return notes.size();
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
    notifyDataSetChanged();
  }

  public List<Note> getNotes() {
    return notes;
  }

  @Override
  public Note getItem(int position) {
    return notes.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
    View row = convertView;
    if (row == null) {
      row = inflater.inflate(R.layout.item, parent, false);
      ViewHolder holder = new ViewHolder();
      holder.txtTitle = (TextView) row.findViewById(R.id.txt_title1);
      holder.txtTime = (TextView) row.findViewById(R.id.txt_time1);
      holder.txtContent = (TextView) row.findViewById(R.id.txt_content1);
      holder.chkCheck = (CheckBox) row.findViewById(R.id.chk_check);
      row.setTag(holder);
    }

    final Note note = notes.get(position);
    final ViewHolder holder = (ViewHolder) row.getTag();
    holder.chkCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        note.setSelected(isChecked);
        boolean visible = false;
        for (Note note : notes) {
          if (note.isSelected()) {
            if (context instanceof MainActivity) {
              visible = true;
              break;
            }
          }
        }
        if (context instanceof MainActivity) {
          ((MainActivity) context).toggleUndo(visible);
        }

      }
    });

    for (int i = 0; i < notes.size(); i++) {
      if (notes.get(i).isSelected()) {
        holder.chkCheck.setVisibility(View.VISIBLE);
      }else{
      }
    }
    holder.txtTitle.setText(note.getTitle());
    holder.txtTime.setText(note.getTime());
    holder.txtContent.setText(note.getContent());
    return row;
  }

  public static class ViewHolder {
    TextView txtTitle;
    TextView txtTime;
    TextView txtContent;
    CheckBox chkCheck;
  }

}
