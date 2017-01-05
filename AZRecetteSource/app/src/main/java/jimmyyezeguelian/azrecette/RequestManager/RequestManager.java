package jimmyyezeguelian.azrecette.RequestManager;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jimmy on 29/12/2016.
 */
public class RequestManager {

    Context context = null;

    public RequestManager(Context context) {
        this.context = context;
    }

    /*
        Proxy Settings
     */

    public boolean setProxy(String ip, String port) {
        ContentResolver contentResolver = this.context.getApplicationContext().getContentResolver();

        if (Settings.System.putString(contentResolver, Settings.System.HTTP_PROXY, ip+":"+port)) {
            return true;
        } else {
            return false;
        }
    }

    public String getRecettesFromMarmiton() {
        StringBuilder content = new StringBuilder();

        try {
            // create a url object
            URL url = new URL("http://www.marmiton.org/recettes/recette-hasard.aspx");

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public String getTitle(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        System.out.println(doc.title().split(":")[0]);
        return doc.title().split(":")[0];
    }

    public List<String> getType(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        String[] split = doc.getElementsByClass("m_content_recette_breadcrumb").text().split(" - ");
        List<String> type = new ArrayList<String>();

        if (split.length > 0) {
            type.add(split[0]);
        }
        if (split.length > 1) {
            type.add(split[1]);
        }
        return type;
    }

    public List<String> getTempsDePreparationCuisson(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        List<String> temps = new ArrayList<String>();
        temps.add(doc.getElementsByClass("preptime").text());
        temps.add(doc.getElementsByClass("cooktime").text());
        return temps;
    }

    public String getNombreDePersonnes(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        String preparationHtml = doc.getElementsByClass("m_content_recette_ingredients m_avec_substitution").tagName("span").text();

        String personnes = preparationHtml.split(":")[0];

        String pers = personnes.substring(personnes.lastIndexOf("(") + 1, personnes.lastIndexOf(")"));
        pers = pers.replaceAll("\\D+","");
        return pers;
    }

    public String getIngredients(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        String ingredientsFinal = "Pas d'information";
        String elements = doc.getElementsByClass("m_content_recette_ingredients m_avec_substitution").html();
        Document ingredients = Jsoup.parse(elements);
        List<String> ingredientsArray = new ArrayList<>((Arrays.asList(ingredients.toString().split(":"))));
        String finalIngredients = "";
        for (int i = 1; i < ingredientsArray.size(); i++) {
            finalIngredients += ingredientsArray.get(i);
        }
        Document ingredientsDoc = Jsoup.parse(finalIngredients);
        ingredientsFinal = ingredientsDoc.text().replace("-", "\n-");
        return ingredientsFinal;
    }

    public String getPreparation(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        String preparationFinal = "Pas d'informations";

        preparationFinal = doc.getElementsByClass("m_content_recette_todo").text().replace("Préparation de la recette :", "");

        if (preparationFinal.startsWith(" ")) {
            preparationFinal = preparationFinal.replaceFirst(" ", "");
        }

        if (preparationFinal.contains(" Boisson conseillée : ")) {
            preparationFinal = preparationFinal.replace(" Boisson conseillée : ", "\nBoisson conseillée : ");
        }

        if (preparationFinal.contains(" Remarques : ")) {
            preparationFinal = preparationFinal.replace(" Remarques : ", "\nRemarques : ");
        }

        return preparationFinal;
    }

    public String getImage(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Elements imageHtml = doc.getElementsByClass("photo m_pinitimage");
        String imageUrl = imageHtml.attr("src");

        return imageUrl;
    }

}
