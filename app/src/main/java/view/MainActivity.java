package view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.blackjack.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Commenter blackjack console lorsque inutile
        new BlackJackConsole();

        // On met le clickHandler pour le bouton d'options en haut à droite

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
        return true;
    }
}