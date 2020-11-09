package view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blackjack.R;

import controller.BlackJack;
import controller.EmptyDeckException;
import model.Card;

public class MainActivity extends AppCompatActivity {

    private Button drawButton;
    private Button bankLastTurnButton;
    private Button resetButton;
    private Button betButton;

    private LinearLayout playerLayout;
    private LinearLayout playerStatusLayout;
    private LinearLayout bankLayout;
    private LinearLayout bankStatusLayout;

    private SeekBar betBar;

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

        drawButton = findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener(){public void onClick(View view){ onDraw(); }});
        bankLastTurnButton = findViewById(R.id.endButton);
        bankLastTurnButton.setOnClickListener(new View.OnClickListener(){public void onClick(View view){ onBankLastTurn(); }});
        resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener(){public void onClick(View view){ onReset(); }});
        betButton = findViewById(R.id.betButton);
        betButton.setOnClickListener(new View.OnClickListener(){public void onClick(View view){ onBetLastTurn(); }});

        betBar = findViewById(R.id.betBar);
        betBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                              @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { updateBetAmmount(); }
                                              @Override public void onStartTrackingTouch(SeekBar seekBar) { }
                                              @Override public void onStopTrackingTouch(SeekBar seekBar) { }
                                          });

        playerLayout = findViewById(R.id.playerHand);
        bankLayout = findViewById(R.id.bankHand);
        playerStatusLayout = findViewById(R.id.playerStatusLayout);
        bankStatusLayout = findViewById(R.id.bankStatusLayout);

        updateBetAmmount();
        updateAmmount();
        updatePlayerPanel();
        updateBankPanel();
    }

    private void onDraw(){
        try {
            blackJack.playerDrawAnotherCard();
        } catch (EmptyDeckException e) {
            EmptyDeckError();
        }
        updatePlayerPanel();
    }

    private void onBankLastTurn(){
        try {
            blackJack.bankLastTurn();
        } catch (EmptyDeckException e) {
            EmptyDeckError();
        }
        updateBankPanel();
    }

    private void onBetLastTurn(){
        try {
            blackJack.bankLastTurn();
            blackJack.betResult(betBar.getProgress());
        } catch (EmptyDeckException e) {
            EmptyDeckError();
        }
        updateAmmount();
        updateBankPanel();
    }

    private void onReset(){
        try {
            blackJack.reset();
            drawButton.setEnabled(true);
            bankLastTurnButton.setEnabled(true);
            betButton.setEnabled(true);
        } catch (EmptyDeckException e) {
            EmptyDeckError();
        }
        updateBetAmmount();
        updateAmmount();
        updatePlayerPanel();
        updateBankPanel();
    }

    private void EmptyDeckError(){
        new AlertDialog.Builder(this)
                .setTitle("EmptyDeckError")
                .setMessage("Le deck est vide")
                .setPositiveButton(R.string.RefillDeck, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        blackJack.setNbDeck(blackJack.getNbDeck());
                        onReset();
                    }
                })
                .show();
        drawButton.setEnabled(false);
        bankLastTurnButton.setEnabled(false);
        betButton.setEnabled(false);
    }

    private void addToPanel(LinearLayout  lay, String token){
        ImageView imv = new ImageView(this);
        int resID = getResources().getIdentifier("card_"+ token.toLowerCase(), "drawable", getPackageName());
        Bitmap tempBMP = BitmapFactory.decodeResource(getResources(), resID);
        imv.setPadding(10,10,10,10);
        imv.setImageBitmap(tempBMP);
        lay.addView(imv);
    }

    private void updatePlayerPanel(){
        playerLayout.removeAllViews();
        playerStatusLayout.removeAllViews();
        TextView playerScore = findViewById(R.id.playerScore);
        playerScore.setText(getResources().getText(R.string.playerScore) + " "+ blackJack.getPlayerBest());
        for(Card c : blackJack.getPlayerCardList()){
            addToPanel(playerLayout ,c.getColorName()+"_"+c.getValueSymbole());
        }
        if(blackJack.getPlayerBest()==21){ addToPanel(playerStatusLayout,"blackjack");}
        if(blackJack.isGameFinished()){ gameFinished();}
    }

    private void updateBankPanel(){
        bankLayout.removeAllViews();
        bankStatusLayout.removeAllViews();
        TextView playerScore = findViewById(R.id.bankScore);
        playerScore.setText(getResources().getText(R.string.bankScore) + " "+ blackJack.getBankBest());
        for(Card c : blackJack.getBankCardList()){
            addToPanel(bankLayout ,c.getColorName()+"_"+c.getValueSymbole());
        }
        if(blackJack.getBankBest()==21){addToPanel(bankStatusLayout,"blackjack");}
        if(blackJack.isGameFinished()){ gameFinished();}
    }

    private void updateAmmount(){
        TextView playerTotalAmmount = findViewById(R.id.totalAmmountTextView);
        playerTotalAmmount.setText(getResources().getText(R.string.PlayerAmmount) + " "+ blackJack.getSomme() + "$");
    }

    private void updateBetAmmount(){
        TextView betAmmount = findViewById(R.id.betAmmountText);
        int betAmmountRound = betBar.getProgress(); //FORCER UN PAS DE 10 SUR LA BARRE DES PARIS
        if(betAmmountRound>0) {
            betAmmountRound /= 10;
            betAmmountRound *= 10;
        }
        betAmmountRound=Integer.min(blackJack.getSomme(),betAmmountRound); //NE PEUT PAS DEPENSER PLUS QU'IL N'A D'ARGENT
        betBar.setProgress(betAmmountRound);
        betAmmount.setText(getResources().getText(R.string.PlayerBet) + " " + betBar.getProgress() + "$");
    }

    private void gameFinished(){
        if(!blackJack.isBankWinner() && blackJack.isPlayerWinner()){
            addToPanel(playerStatusLayout,"winner");
            addToPanel(bankStatusLayout,"looser");
        }
        else if(blackJack.isBankWinner() && !blackJack.isPlayerWinner()){
            addToPanel(playerStatusLayout,"looser");
            addToPanel(bankStatusLayout,"winner");
        }
        else{
            addToPanel(playerStatusLayout,"draw");
            addToPanel(bankStatusLayout,"draw");
        }
        drawButton.setEnabled(false);
        bankLastTurnButton.setEnabled(false);
        betButton.setEnabled(false);
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
                configDialog();
                return true;
            default:
                return false;
        }
    }

    public void configDialog(){
        final Context context = getApplicationContext();
        AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle(R.string.DialogBoxTitle);

        LayoutInflater inflater = getLayoutInflater();
        final View configView = inflater.inflate(R.layout.dialog_layout, null);
        final RadioGroup rg = configView.findViewById(R.id.radioBgGroup);

        final EditText nbDeck = configView.findViewById(R.id.montanTextview);
        nbDeck.setText(""+blackJack.getNbDeck());

        final EditText nbMontant = configView.findViewById(R.id.montantMiseEdit);
        nbMontant.setText(""+blackJack.getSomme());

        diag.setView(configView);
        final ConstraintLayout mainLayout = findViewById(R.id.mainLayout);

        diag.setPositiveButton(R.string.ValidateChoiceDialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String toastText="Background: ";
                if(rg.getCheckedRadioButtonId() == R.id.greenBgButton){
                    mainLayout.setBackgroundColor(Color.parseColor("#00574A"));
                    toastText+= getResources().getString(R.string.GreenBackgroundchoice);
                }
                if(rg.getCheckedRadioButtonId() == R.id.blackBgButton){
                    mainLayout.setBackgroundColor(Color.parseColor("#3A3939"));
                    toastText+= getResources().getString(R.string.DarkBackgroundchoice);
                }
                try{
                    int valueNbDeck = Integer.parseInt(nbDeck.getText().toString());
                    blackJack.setNbDeck(valueNbDeck);
                    toastText+= " nbDeck: "+valueNbDeck;
                }catch (Exception e){
                    blackJack.setNbDeck(blackJack.getNbDeck());
                    toastText+= "nbDeck inchangé";
                }
                try{
                    int valueSomme = Integer.parseInt(nbMontant.getText().toString());
                    blackJack.setSomme(valueSomme);
                }catch (Exception e){
                    blackJack.setSomme(blackJack.getSomme());
                }
                Toast.makeText(context,toastText,Toast.LENGTH_LONG).show();
                betBar.setProgress(0);
                onReset();
            }
        });
        diag.setNegativeButton(R.string.ResetChoiceDialog,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,getResources().getString(R.string.ResetChoiceDialog),Toast.LENGTH_LONG).show();
            }
        });

        diag.show();
    }
}