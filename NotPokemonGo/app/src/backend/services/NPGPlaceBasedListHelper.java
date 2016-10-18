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
                tmp = null;
                break;

        }

        return tmp;
    }

    public static List<String> getMp3FilenamesForPlaceType(int placetype){
        List<String> tmp;
        switch (placetype){
            case Place.TYPE_CAFE:
                tmp = getMp3FilenamesForCafe();
                break;
            case Place.TYPE_CAMPGROUND:
                tmp = getMp3FilenamesForCampground();
                break;
            case Place.TYPE_DEPARTMENT_STORE:
                tmp = getMp3FilenamesForDepartmentStore();
                break;
            case Place.TYPE_FOOD:
                tmp = getMp3FilenamesForFood();
                break;
            case Place.TYPE_HOSPITAL:
                tmp = getMp3FilenamesForHospital();
                break;
            case Place.TYPE_LIBRARY:
                tmp = getMp3FilenamesForLibrary();
                break;

            default:
                tmp = null;
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
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_kaka));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_kanelbulle));
        tmp.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cafe_te));

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

    private static List<String> getMp3FilenamesForCafe(){
        return null;
    }

    private static List<String> getMp3FilenamesForCampground(){
        return null;
    }

    private static List<String> getMp3FilenamesForDepartmentStore(){
        return null;
    }

    private static List<String> getMp3FilenamesForFood(){
        return null;
    }

    private static List<String> getMp3FilenamesForHospital(){
        return null;
    }

    private static List<String> getMp3FilenamesForLibrary(){
        return null;
    }

}
