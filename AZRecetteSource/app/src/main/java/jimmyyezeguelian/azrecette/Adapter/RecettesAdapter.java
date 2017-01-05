package jimmyyezeguelian.azrecette.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import jimmyyezeguelian.azrecette.DATABASE.Recette;
import jimmyyezeguelian.azrecette.R;

/**
 * Created by Jimmy on 02/01/2017.
 */
public class RecettesAdapter extends RecyclerView.Adapter<RecettesAdapter.ViewHolder> {

    private Context context;
    private List<Recette> recetteList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, preptime, cooktime, difficulte;
        public ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            preptime = (TextView) view.findViewById(R.id.preptime);
            cooktime = (TextView) view.findViewById(R.id.cooktime);
            difficulte = (TextView) view.findViewById(R.id.difficulte);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public Context getContext() {
        return context;
    }

    public RecettesAdapter(Context mContext, List<Recette> recetteList) {
        this.context = mContext;
        this.recetteList = recetteList;
    }

    @Override
    public  RecettesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recette_card, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecettesAdapter.ViewHolder holder, int position) {
        Recette recette = recetteList.get(position);
        CropSquareTransformation transformation = new CropSquareTransformation();
        System.out.println(recetteList.size() + " pos: " + position);
        holder.title.setText(recette.getTitre() == null ? "Pas de titre" : recette.getTitre());
        holder.preptime.setText(recette.getTempsDePreparation() + " min.");
        holder.cooktime.setText(recette.getTempsDeCuisson() + " min.");
        if (!recette.getImage().isEmpty()) {
            Picasso.with(context).load(recette.getImage()).transform(transformation).into(holder.thumbnail);
            Glide.with(context).load(recette.getImage()).into(holder.thumbnail);
        } else {
            Picasso.with(context).load(R.mipmap.ic_logo).transform(transformation).into(holder.thumbnail);
            Glide.with(context).load(R.mipmap.ic_logo).into(holder.thumbnail);
        }
    }


    @Override
    public int getItemCount() {
        return recetteList.size();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }


    public class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }
}
