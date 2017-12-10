package th.in.pureapp.easywallet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import th.in.pureapp.easywallet.R;
import th.in.pureapp.easywallet.adapter.AmountAdapter;
import th.in.pureapp.easywallet.database.InfoTable;
import th.in.pureapp.easywallet.model.InfoRecord;

public class MainActivity extends AppCompatActivity {

    public InfoTable infoTable;
    private List<InfoRecord> amountList;
    private AmountAdapter amountAdapter;
    private TextView summaryTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        summaryTv = (TextView) findViewById(R.id.sumary_textview);
        ListView listView = (ListView) findViewById(R.id.info_listview);
        infoTable = new InfoTable(this);
        amountList = infoTable.fetch();
        amountAdapter = new AmountAdapter(this,amountList);
        listView.setAdapter(amountAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long l) {
                InfoRecord item = amountAdapter.get(position);
                NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("ยืนยันการลบรายการ '"+item.description+" "+nf.format(Math.abs(item.amount))+" บาท' บาท?")
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                infoTable.remove(amountAdapter.getIdByIndex(position));
                                amountAdapter.removeByIndex(position);
                                amountAdapter.notifyDataSetChanged();
                            }
                        }).show();
                return false;
            }
        });
        updateSummary();
        Button incomeBtn = (Button) findViewById(R.id.income_button);
        Button expenseBtn = (Button) findViewById(R.id.expense_button);
        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddActivity("income");
            }
        });
        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddActivity("expense");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == this.RESULT_OK){
            InfoRecord infoRecord = new InfoRecord(
                    data.getIntExtra("id",0),
                    data.getStringExtra("description"),
                    data.getDoubleExtra("amount",0.0)
            );
            amountAdapter.add(infoRecord);
            amountAdapter.notifyDataSetChanged();
            updateSummary();
        }
    }
    private void startAddActivity(String type){
        Intent intent = new Intent(MainActivity.this,AddActivity.class);
        intent.putExtra("type",type);
        startActivityForResult(intent,1);
    }
    private void updateSummary(){
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        summaryTv.setText("คงเหลือ "+nf.format(amountAdapter.sumAmount())+" บาท");
    }
}
