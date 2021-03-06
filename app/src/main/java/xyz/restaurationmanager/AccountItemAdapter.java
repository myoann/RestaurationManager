package xyz.restaurationmanager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Florian on 08/11/2015.
 */
public class AccountItemAdapter extends BaseAdapter {

    private Context context;
    public List<Account> acounts;
    public int positionA;
    private int image_cercle_contact;
    private int image_cercle_contact1;

    public AccountItemAdapter(Context context, List<Account> acounts) {
        this.context = context;
        this.acounts = acounts;
    }

    @Override
    public int getCount() {
        return acounts.size();
    }

    @Override
    public Object getItem(int arg0) {
        return acounts.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View v = convertView;
        this.positionA = position;
        AccountViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.content_list_accounts, null);
            viewHolder = new AccountViewHolder();
            viewHolder.nom_prenom= (TextView)v.findViewById(R.id.title_name_firstname);
           //viewHolder.date_creation= (TextView)v.findViewById(R.id.txt_date_inscription);
            viewHolder.bSuppAccount = (Button) v.findViewById(R.id.bSuppAccount);
            viewHolder.textConnection = (TextView) v.findViewById(R.id.connectionText);
            viewHolder.buzzer = (ImageView) v.findViewById(R.id.imageView4);
            viewHolder.bSuppAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Suppression de l'utilisateur");
                    String id =acounts.get(AccountItemAdapter.this.positionA).getId();
                    System.out.println("id ==> " + id);
                    DeleteAccountTask task = new DeleteAccountTask();
                    task.execute(id);
                    acounts.remove(AccountItemAdapter.this.positionA);
                    AccountItemAdapter.this.notifyDataSetChanged();


                }
            });

            v.setTag(viewHolder);
        }
        else{
            viewHolder = (AccountViewHolder) v.getTag();
        }
        Account account = acounts.get(position);
        viewHolder.nom_prenom.setText(account.getNom()+ " "+account.getPrenom());
        //affichage de la connexion de l'utilisateur
        String msgco = "Non-connecté";
        if(account.getConnected().equals("true")){
            msgco = "Connecté";
        }else{
            //changement de la couleur du buzzer en fonction de la connexion
            viewHolder.buzzer.setImageResource(R.drawable.image_cercle_contact_rouge);
        }

        viewHolder.textConnection.setText(msgco);
//        viewHolder.date_creation.setText(account.getCreatedAt());
        return v;
    }

    class AccountViewHolder{
        TextView nom_prenom;
        TextView date_creation;
        TextView textConnection;
        Button bSuppAccount;
        ImageView buzzer;
    }
}
