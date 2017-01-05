package jimmyyezeguelian.azrecette;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import jimmyyezeguelian.azrecette.DATABASE.Recette;
import jimmyyezeguelian.azrecette.RequestManager.RequestManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void downloadRecettes(View view) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    RequestManager requestManager = new RequestManager(getApplicationContext());
                    for (int i = 0; i < 10; i++) {
                        String htmlContent = requestManager.getRecettesFromMarmiton();
                        Recette recette = new Recette();

                        recette.setTitre(requestManager.getTitle(htmlContent));
                        recette.setType(requestManager.getType(htmlContent).toString());
                        recette.setTempsDePreparation(requestManager.getTempsDePreparationCuisson(htmlContent).get(0));
                        recette.setTempsDeCuisson(requestManager.getTempsDePreparationCuisson(htmlContent).get(1));
                        recette.setNombreDePersonnes(requestManager.getNombreDePersonnes(htmlContent));
                        recette.setIngredients(requestManager.getIngredients(htmlContent));
                        recette.setPreparation(requestManager.getPreparation(htmlContent));
                        recette.setImage(requestManager.getImage(htmlContent));

                        recette.save();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void searchRecette(View view) {
        EditText ingredientsSearchView = (EditText)findViewById(R.id.ingredientsSearch);
        String ingreditnesString = "";
        try {
            ingreditnesString = ingredientsSearchView.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start new activity
        Intent intent = new Intent(this, Recherche.class);
        intent.putExtra("ingredients", ingreditnesString);
        startActivity(intent);
    }
}
