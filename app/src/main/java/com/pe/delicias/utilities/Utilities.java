package com.pe.delicias.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Patterns;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Utilities {
    public static boolean emailIsValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static String getRandomImage() {
        List<String> images = new LinkedList<>();
        images.add("https://www.vegetariantimes.com/.image/t_share/MTQ3MDM3MzQ5NjA2MzM2NDA3/zi3000-shutterstock-buddha-bowl.jpg");
        images.add("https://static3.lasprovincias.es/www/multimedia/201903/03/media/cortadas/vegano-k9rD-U70808076745bNI-624x385@Las%20Provincias.jpg");
        images.add("https://www.seriouseats.com/recipes/images/2015/02/20150228-vegan-loaded-queso-dip7.jpg");
        images.add("https://adityarestaurant.com/wp-content/uploads/2018/04/maxresdefault-7.jpg");
        images.add("https://tienda.guvisa.com.mx/wp-content/uploads/2018/10/Turkey-FB.jpg");
        images.add("https://www.hotforfoodblog.com/wp-content/uploads/2019/05/antipastobreadsalad_hotforfood_filtered1-1024x683.jpg");
        Collections.shuffle(images);
        return images.get(0);
    }

    public static Typeface bold(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/friz-quadrata-std-bold.otf");
        return typeface;
    }

    public static Typeface boldItalic(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/friz-quadrata-std-bold-italic.otf");
        return typeface;
    }

    public static Typeface medium(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/friz-quadrata-std-medium.otf");
        return typeface;
    }

    public static Typeface anke(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/anke.otf");
        return typeface;
    }

    public static Typeface sansBold(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/ProductSans-Bold.ttf");
        return typeface;
    }

    public static Typeface sansLight(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/ProductSans-Light.ttf");
        return typeface;
    }

    public static Typeface sansMedium(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/ProductSans-Medium.ttf");
        return typeface;
    }

    public static Typeface sansBlack(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/ProductSans-Black.ttf");
        return typeface;
    }
}
