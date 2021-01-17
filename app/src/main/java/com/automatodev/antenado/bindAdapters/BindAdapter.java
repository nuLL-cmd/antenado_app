package com.automatodev.antenado.bindAdapters;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

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

    @BindingAdapter("android:urls")
    public static void setImages(CarouselView carouselView, List<String> images){
        try{
            carouselView.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    imageView.setAlpha(0f);
                    Picasso.get().load(images.get(position)).into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.animate().setDuration(50).alpha(1f).start();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
            });
            carouselView.setPageCount(images.size());

        }catch (Exception e){

        }
    }
}
