package th.in.pureapp.easywallet.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import th.in.pureapp.easywallet.R;
import th.in.pureapp.easywallet.database.InfoTable;
import th.in.pureapp.easywallet.model.InfoRecord;

public class AddActivity extends AppCompatActivity {

    private InfoTable infoTable;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ImageView iconImg = (ImageView) findViewById(R.id.icontype_imageview);
        infoTable = new InfoTable(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        ActionBar actionBar = getSupportActionBar();
        if(type.equals("income")){
            iconImg.setImageResource(R.drawable.ic_income);
            actionBar.setTitle("บันทึกรายรับ");
        }else{
            iconImg.setImageResource(R.drawable.ic_expense);
            actionBar.setTitle("บันทึกรายจ่าย");
        }
        final EditText descptionInput = (EditText) findViewById(R.id.desciption_edittext);
        final EditText amountInput = (EditText) findViewById(R.id.amount_edittext);
        Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = descptionInput.getText().toString();
                double amount = Double.parseDouble(amountInput.getText().toString());
                if(type.equals("expense")){
                    amount *= -1;
                }
                InfoRecord record = new InfoRecord(description,amount);
                long id = infoTable.add(record);
                if(id != -1){
                    Intent intent = new Intent();
                    intent.putExtra("id",(int)id);
                    intent.putExtra("description",description);
                    intent.putExtra("amount",amount);
                    setResult(AddActivity.this.RESULT_OK,intent);
                }else{
                    setResult(AddActivity.this.RESULT_CANCELED);
                }
                finish();
            }
        });
    }
}
