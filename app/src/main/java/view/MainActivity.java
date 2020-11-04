package view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.blackjack.R;

import controller.BlackJack;
import controller.EmptyDeckException;

public class MainActivity extends AppCompatActivity {

    private BlackJack blackJack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // new BlackJackConsole();
        try {
            blackJack = new BlackJack();
        } catch (EmptyDeckException ex){
            // On passe silencieusement l'exception car impossible de l'obtenir au début
        }
    }

    private void addToPanel(LinearLayout  lay, String token){
        ImageView imv = new ImageView(this);
        int resID = getResources().getIdentifier("card_"+ token.toLowerCase(), "drawable", getPackageName());
        Bitmap tempBMP = BitmapFactory.decodeResource(getResources(), resID);
        imv.setPadding(10,10,10,10);
        imv.setImageBitmap(tempBMP);
        lay.addView(imv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blackjack, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_en_lang:
                return true;
            case R.id.menu_item_fr_lang:
                return true;
            case R.id.config_menu_Item:
                AlertDialog.Builder diag = new AlertDialog.Builder(this);
                diag.setTitle(R.string.DialogBoxTitle);
                diag.setPositiveButton(R.string.ValidateChoiceDialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                diag.setNegativeButton(R.string.ResetChoiceDialog,null);

                diag.setView(R.layout.dialog_layout);
                diag.show();
                return true;



            default:
                return false;
        }
    }
}