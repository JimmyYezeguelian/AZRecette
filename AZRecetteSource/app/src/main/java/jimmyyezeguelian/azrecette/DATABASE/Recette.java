package jimmyyezeguelian.azrecette.DATABASE;

import com.orm.SugarRecord;

/**
 * Created by Jimmy on 29/12/2016.
 */
public class Recette extends SugarRecord {

    public String titre;
    public String type;
    public String nombreDePersonnes;
    public String tempsDePreparation;
    public String tempsDeCuisson;
    public String ingredients;
    public String preparation;
    public String image;

    public Recette() {}

    public Recette(String titre, String type, String nombreDePersonnes, String tempsDePreparation, String tempsDeCuisson, String ingredients, String preparation, String image) {
        this.titre = titre;
        this.type = type;
        this.nombreDePersonnes = nombreDePersonnes;
        this.tempsDePreparation = tempsDePreparation;
        this.tempsDeCuisson = tempsDeCuisson;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNombreDePersonnes() {
        return nombreDePersonnes;
    }

    public void setNombreDePersonnes(String nombreDePersonnes) {
        this.nombreDePersonnes = nombreDePersonnes;
    }

    public String getTempsDePreparation() {
        return tempsDePreparation;
    }

    public void setTempsDePreparation(String tempsDePreparation) {
        this.tempsDePreparation = tempsDePreparation;
    }

    public String getTempsDeCuisson() {
        return tempsDeCuisson;
    }

    public void setTempsDeCuisson(String tempsDeCuisson) {
        this.tempsDeCuisson = tempsDeCuisson;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
