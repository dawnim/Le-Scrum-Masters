package services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.location.places.Place;

import com.le_scrum_masters.notpokemongo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albin on 2016-10-18.
 */

public class NPGPlaceBasedListHelper {
    public static Context context;

    public static List<Bitmap> getImagesForPlaceType(int placetype){
        List<Bitmap> tmp;
        switch (placetype){
            case Place.TYPE_CAFE:
                tmp = getImagesForCafe();
                break;
            case Place.TYPE_CAMPGROUND:
                tmp = getImagesForCampground();
                break;
            case Place.TYPE_DEPARTMENT_STORE:
                tmp = getImagesForDepartmentStore();
                break;
            case Place.TYPE_FOOD:
                tmp = getImagesForFood();
                break;
            case Place.TYPE_HOSPITAL:
                tmp = getImagesForHospital();
                break;
            case Place.TYPE_LIBRARY:
                tmp = getImagesForLibrary();
                break;

            default:
                tmp = getImagesForCafe();
                break;

        }

        return tmp;
    }

    public static List<Integer> getMp3FilenamesForPlaceType(int placetype){
        List<Integer> tmp;
        switch (placetype){
            case Place.TYPE_CAFE:
                tmp = getMp3FileIntegerForCafe();
                break;
            case Place.TYPE_CAMPGROUND:
                tmp = getMp3FileIntegerForCampground();
                break;
            case Place.TYPE_DEPARTMENT_STORE:
                tmp = getMp3FileIntegerForDepartmentStore();
                break;
            case Place.TYPE_FOOD:
                tmp = getMp3FileIntegerForFood();
                break;
            case Place.TYPE_HOSPITAL:
                tmp = getMp3FileIntegerForHospital();
                break;
            case Place.TYPE_LIBRARY:
                tmp = getMp3FileIntegerForLibrary();
                break;

            default:
                tmp = getMp3FileIntegerForCafe();
                break;

        }

        return tmp;
    }

    public static Integer getVideoFileIntegerForPlaceType(int placetype){
        Integer tmp;
        switch (placetype){
            case Place.TYPE_CAFE:
                tmp = getVideoFileIntegerForCafe();
                break;
            case Place.TYPE_LIBRARY:
                tmp = getVideoFileIntegerForLibrary();
                break;
            default:
                tmp = getVideoFileIntegerForCafe();
                break;

        }

        return tmp;
    }

    /*
    Existing placetypes:
        cafe - 15
        Campground - 16
        Department store - 29
        Food - 38
        Hospital - 50
        Library - 55
     */

    //Images

    private static List<Bitmap> getImagesForCafe(){
        List<Bitmap> tmp = new ArrayList<>();

        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_brod));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_kaffe));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_kaka));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_kanelbulle));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_te));

        return tmp;
    }

    private static List<Bitmap> getImagesForCampground(){
        return null;
    }

    private static List<Bitmap> getImagesForDepartmentStore(){
        return null;
    }

    private static List<Bitmap> getImagesForFood(){
        List<Bitmap> tmp = new ArrayList<>();


        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_brod));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_kaffe));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.bibliotek_lampa));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.restaurang));

        return tmp;
    }

    private static List<Bitmap> getImagesForHospital(){
        return null;
    }

    private static List<Bitmap> getImagesForLibrary(){
        List<Bitmap> tmp = new ArrayList<>();

        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.bibliotek_bok));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.bibliotek_dator));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.bibliotek_glasogon));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.bibliotek_lampa));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.bibliotek_penna));

        return tmp;
    }

    //Mp3files

    private static List<Integer> getMp3FileIntegerForCafe(){
        List<Integer> tmp = new ArrayList<>();

        tmp.add(R.raw.brod);
        tmp.add(R.raw.kaffe);
        tmp.add(R.raw.kaka);
        tmp.add(R.raw.bulle);
        tmp.add(R.raw.te);

        return tmp;
    }

    private static List<Integer> getMp3FileIntegerForCampground(){
        List<Integer> tmp = new ArrayList<>();

        tmp.add(R.raw.busshallplats);
        tmp.add(R.raw.gras);
        tmp.add(R.raw.hund);
        tmp.add(R.raw.lampa);
        tmp.add(R.raw.trad);

        return tmp;
    }

    private static List<Integer> getMp3FileIntegerForDepartmentStore(){
        List<Integer> tmp = new ArrayList<>();

        tmp.add(R.raw.brod);
        tmp.add(R.raw.kaffe);
        tmp.add(R.raw.bulle);
        tmp.add(R.raw.kaka);
        tmp.add(R.raw.te);

        return tmp;
    }

    private static List<Integer> getMp3FileIntegerForFood(){
        List<Integer> tmp = new ArrayList<>();

        tmp.add(R.raw.brod);
        tmp.add(R.raw.kaffe);
        tmp.add(R.raw.lampa);
        tmp.add(R.raw.restaurang);

        return tmp;
    }

    private static List<Integer> getMp3FileIntegerForHospital(){
        List<Integer> tmp = new ArrayList<>();

        tmp.add(R.raw.busshallplats);
        tmp.add(R.raw.penna);
        tmp.add(R.raw.fontan);

        return tmp;
    }

    private static List<Integer> getMp3FileIntegerForLibrary(){
        List<Integer> tmp = new ArrayList<>();

        tmp.add(R.raw.bok);
        tmp.add(R.raw.dator);
        tmp.add(R.raw.glasogon);
        tmp.add(R.raw.lampa);
        tmp.add(R.raw.penna);

        return tmp;
    }

    private static Integer getVideoFileIntegerForLibrary(){
        return R.raw.bibliotekfilm;
    }

    private static Integer getVideoFileIntegerForCafe(){
        return R.raw.cafefilm;

    }

}
