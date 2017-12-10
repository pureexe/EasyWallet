package th.in.pureapp.easywallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pakkapon on 10/12/2560.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "EasyWallet";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE infomation(id INTEGER PRIMARY KEY AUTOINCREMENT,description TEXT,amount REAL)");
        initalData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversin) {
        //Nothing to upgrade
    }
    private  void initalData(SQLiteDatabase db){
        initalData(db,"คุณพ่อให้เงิน",8000.0);
        initalData(db,"จ่ายค่าหอ",-2500.0);
        initalData(db,"ซื้อล็อตเตอรี่ 1 ชุด",-700.0);
        initalData(db,"ถูกล็อตเตอรี่รางวันที่ 1 ",30000000.0);
    }
    private  void initalData(SQLiteDatabase db,String description,double amount){
        ContentValues content = new ContentValues();
        content.put("description",description);
        content.put("amount",amount);
        db.insert("infomation",null,content);
    }
 }