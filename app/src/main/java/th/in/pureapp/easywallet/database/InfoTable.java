package th.in.pureapp.easywallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import th.in.pureapp.easywallet.model.InfoRecord;

/**
 * Created by pakkapon on 10/12/2560.
 */

public class InfoTable {
    private Context context;
    private DatabaseHelper dbHelper;
    public InfoTable(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }
    public long add(InfoRecord info){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("description",info.description);
        content.put("amount",info.amount);
        long id = db.insert("infomation",null,content);
        db.close();
        return id;
    }
    public void remove(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("infomation","id = ?",new String[]{""+id});
        db.close();
    }
    public List<InfoRecord> fetch(){
        List<InfoRecord> data = new ArrayList<InfoRecord>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id,description,amount from infomation",null);
        while(cursor.moveToNext()){
            data.add(new InfoRecord(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getDouble(cursor.getColumnIndex("amount"))
            ));
        }
        db.close();
        return data;
    }
}
