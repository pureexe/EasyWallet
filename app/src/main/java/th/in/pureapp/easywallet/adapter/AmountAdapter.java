package th.in.pureapp.easywallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import th.in.pureapp.easywallet.R;
import th.in.pureapp.easywallet.model.InfoRecord;

/**
 * Created by pakkapon on 10/12/2560.
 */

public class AmountAdapter extends ArrayAdapter {
    List<InfoRecord> myTable;
    private int viewId;
    private Context context;
    public AmountAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.amount_item, objects);
        viewId = R.layout.amount_item;
        myTable = objects;
        this.context = context;
    }

    public void add(InfoRecord object) {
        //super.add(object);
        myTable.add(object);
    }
    public int getIdByIndex(int id){
        return myTable.get(id).id;
    }
    public void removeByIndex(int id){
        myTable.remove(id);
    }
    public void remove(int id) {
        InfoRecord rem;
        for(int i=0;i<myTable.size();i++){
            if(myTable.get(i).id == id){
                rem = myTable.get(i);
            }
        }
    }

    @Override
    public int getCount() {
        return myTable.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        InfoRecord item = myTable.get(position);
        View view = View.inflate(context,viewId,null);
        TextView descriptionTv = view.findViewById(R.id.description_item_textview);
        TextView amountTv = view.findViewById(R.id.amount_item_textview);
        ImageView iconIv = view.findViewById(R.id.icon_item_imageview);
        if(item.amount < 0){
            iconIv.setImageResource(R.drawable.ic_expense);
        }
        descriptionTv.setText(item.description);
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        amountTv.setText(nf.format(Math.abs(item.amount)));
        return view;
    }

    public double sumAmount(){
        double sum = 0;
        for(int i=0;i<myTable.size();i++){
            sum += myTable.get(i).amount;
        }
        return sum;
    }
    public InfoRecord get(int id){
        return myTable.get(id);
    }
}
