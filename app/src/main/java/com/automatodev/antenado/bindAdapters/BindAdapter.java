package com.automatodev.antenado.bindAdapters;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindAdapter {


    @BindingAdapter("android:url")
    public static void setImage(ImageView image, String url){
        try{
            image.setAlpha(0f);
            Picasso.get().load(url).into(image, new Callback() {
                @Override
                public void onSuccess() {
                    image.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("logx","Erro setImage: "+e.getMessage());

        }
    }
}
