package jimmyyezeguelian.azrecette;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.orm.query.Condition;
import com.orm.query.Select;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jimmyyezeguelian.azrecette.Adapter.RecettesAdapter;
import jimmyyezeguelian.azrecette.DATABASE.Recette;

public class Recherche extends AppCompatActivity {

    private List<Recette> listOfSearchedRecette;
    private RecyclerView recyclerView;
    private RecettesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get ingredients
        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("ingredients"));

        List<String> ingredients = new ArrayList<>((Arrays.asList(intent.getStringExtra("ingredients").split(","))));


        // Remplacement des permiers espaces
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).startsWith(" ")) {
                ingredients.set(i, ingredients.get(i).replaceFirst(" ", ""));
            }
            ingredients.set(i, ingredients.get(i).replace("\n", ""));
        }

        // Query
        List<Recette> recetteList = new ArrayList<>();

        for (int i = 0; i< ingredients.size(); i++) {
            List<Recette> tmpList = Select.from(Recette.class)
                                    .where(Condition.prop("ingredients").like("%"+ingredients.get(i)+"%")).list();
            for (int j = 0; j < tmpList.size(); j++) {
                recetteList.add(tmpList.get(j));
            }
        }

        for (int i = 0; i< recetteList.size(); i++) {
            System.out.println(recetteList.get(i).getTitre());
        }

        this.listOfSearchedRecette = recetteList;


        // Lookup the recyclerview in activity layout
        RecyclerView rtAdapter = (RecyclerView) findViewById(R.id.recyclerViewRecette);
        RecettesAdapter adapter = new RecettesAdapter(this, recetteList);
        rtAdapter.setAdapter(adapter);
        rtAdapter.setLayoutManager(new LinearLayoutManager(this));
    }
}
