package com.pe.delicias.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pe.delicias.R;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Utilities {

    public static String ID_CUSTOMER="id_customer";
    public static String TOKEN_CUSTOMER="token_customer";
    public static String NAMES_CUSTOMER="names_customer";
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            CharSequence name = "Delicias Desayuno";
            //String description = getString(R.string.channel_description);
            String description = "Pan, Frutas, Ensalada, etc";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void createNotification(Context context){
        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.chef_notification);
        builder.setContentTitle("Cheeff v1.0.0");
        builder.setContentText("Nuevo orden de pedido");
        builder.setColor(Color.BLUE);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        //builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat
                .from(context);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

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
