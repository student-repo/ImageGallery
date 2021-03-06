package com.example.ubuntu_master.image_gallery;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.test.espresso.core.deps.guava.primitives.Ints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MainActivity extends AppCompatActivity implements ImagesListFragment.updateImagesListFragment, SimpleFragment.updateImageInfoData{


    private HashMap<Integer, ImageInfo> imagesInfo = new HashMap<Integer, ImageInfo>(){{
        put(0, new ImageInfo("Image0", "Image0 description description description", "nature1", 0, 0));
        put(1, new ImageInfo("Image1", "Image1 description description description", "nature2", 0, 1));
        put(2, new ImageInfo("Image2", "Image2 description description description", "nature3", 0, 2));
        put(3, new ImageInfo("Image3", "Image3 description description description", "nature4", 0, 3));
        put(4, new ImageInfo("Image4", "Image4 description description description", "nature5", 0, 4));
        put(5, new ImageInfo("Image5", "Image5 description description description", "nature1", 0, 5));
        put(6, new ImageInfo("Image6", "Image6 description description description", "nature2", 0, 6));
        put(7, new ImageInfo("Image7", "Image7 description description description", "nature3", 0, 7));
        put(8, new ImageInfo("Image8", "Image8 description description description", "nature4", 0, 8));
        put(9, new ImageInfo("Image9", "Image9 description description description", "nature5", 0, 9));
    }};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            restoreInstanceState(savedInstanceState);
        }
        catch(Exception e){}
        getSupportActionBar().setTitle("Image Gallery");  // provide compatibility to all the versions

        this.getIntent().addFlags(FLAG_ACTIVITY_SINGLE_TOP);
        commitFragment();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreInstanceState(savedInstanceState);
     }

    private void restoreInstanceState(Bundle savedInstanceState){
        ArrayList<Integer> arr1 = savedInstanceState.getIntegerArrayList("ids");
        ArrayList<Integer> arr2 = savedInstanceState.getIntegerArrayList("progresses");
        for(Integer j : arr1){
            ImageInfo ii = imagesInfo.get(j);
            ii.setProgress(arr2.get(j));
            imagesInfo.put(j, ii);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntegerArrayList("ids", getIdArrayList());
        outState.putIntegerArrayList("progresses", getProgressArrayList());
    }

    @Override
    public void updateImagesListFragment(int id, int progress) {
        ImageInfo ii = imagesInfo.get(id);
        ii.setProgress(progress);
        imagesInfo.put(id, ii);
        commitFragment();
    }

    @Override
    public void updateImageInfoData(int progress, int id) {
        ImageInfo ii = imagesInfo.get(id);
        ii.setProgress(progress);
        imagesInfo.put(id, ii);

        RatingBar rb = (RatingBar)findViewById(R.id.landscape_rating_bar);
        rb.setProgress(imagesInfo.get(id).getProgress() / 10);
    }

    @Override
    public void handleLandscapeListClick(String s, int progress, int id) {
        TextView tt = (TextView)findViewById(R.id.landscape_id);
        tt.setText(String.valueOf(id));

        TextView title = (TextView)findViewById(R.id.landscape_image_title);
        title.setText(imagesInfo.get(id).getTitle());

        TextView description = (TextView)findViewById(R.id.landscape_description);
        description.setText(imagesInfo.get(id).getDescription());

        ImageView iv = (ImageView)findViewById(R.id.landscape_image);
        int resID = getResources().getIdentifier(imagesInfo.get(id).getImage() + "_big" , "drawable", getPackageName());
        iv.setImageResource(resID);

        RatingBar rb = (RatingBar)findViewById(R.id.landscape_rating_bar);
        rb.setVisibility(View.VISIBLE);
        rb.setProgress(imagesInfo.get(id).getProgress() / 10);
    }

    private void commitFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // get the display mode
        int displaymode = getResources().getConfiguration().orientation;
        if (displaymode == 1) { // it portrait mode
            ImagesListFragment portraitFragment = new ImagesListFragment();

            portraitFragment.setArguments(getBundle());
            fragmentTransaction.replace(android.R.id.content, portraitFragment);
        }
        else {// its landscape
            LandscapeFragment f = new LandscapeFragment();

            f.setArguments(getBundle());
            fragmentTransaction.replace(android.R.id.content, f);
        }
        fragmentTransaction.commit();
    }


    private Bundle getBundle(){
        Bundle b = new Bundle();
        b.putStringArray("titleArray", getTitleArray());
        b.putStringArray("descriptionArray", getDescriptionArray());
        b.putIntArray("progressArray", getProgressArray());
        b.putStringArray("imageArray", getImageArray());
        b.putIntArray("idArray", getIdArray());
        return b;
    }





















    private String[] getTitleArray(){
        ArrayList<String> s = new ArrayList<>();
        for(Integer i: imagesInfo.keySet()){
            s.add(imagesInfo.get(i).getTitle());
        }
        return s.toArray(new String[0]);
    }

    private String[] getDescriptionArray(){
        ArrayList<String> s = new ArrayList<>();
        for(Integer i: imagesInfo.keySet()){
            s.add(imagesInfo.get(i).getDescription());
        }
        return s.toArray(new String[0]);
    }

    private int[] getProgressArray(){
        List<Integer> s = new ArrayList<>();
        for(Integer i: imagesInfo.keySet()){
            s.add(imagesInfo.get(i).getProgress());
        }
        return Ints.toArray(s);
    }

    private String[] getImageArray(){
        ArrayList<String> s = new ArrayList<>();
        for(Integer i: imagesInfo.keySet()){
            s.add(imagesInfo.get(i).getImage());
        }
        return s.toArray(new String[0]);
    }

    private int[] getIdArray(){
        List<Integer> s = new ArrayList<>();
        for(Integer i: imagesInfo.keySet()){
            s.add(imagesInfo.get(i).getId());
        }
        return Ints.toArray(s);
    }

    private ArrayList<Integer> getIdArrayList(){
        ArrayList<Integer> s = new ArrayList<>();
//        int[] arr = imagesInfo.keySet();
        Integer[] arr = imagesInfo.keySet().toArray(new Integer[imagesInfo.keySet().size()]);
        Arrays.sort(arr);

        for(Integer i: arr){
            s.add(imagesInfo.get(i).getId());
        }
        return s;
    }

    private ArrayList<Integer> getProgressArrayList(){
        ArrayList<Integer> s = new ArrayList<>();
//        int[] arr = imagesInfo.keySet();
        Integer[] arr = imagesInfo.keySet().toArray(new Integer[imagesInfo.keySet().size()]);
        Arrays.sort(arr);

        for(Integer i: arr){
            s.add(imagesInfo.get(i).getProgress());
        }
        return s;
    }




















    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void jsonContent(){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONObject oo = (JSONObject)obj.getJSONObject("images2").get("2");
            System.out.println(oo.get("name"));
            HashMap<String, JSONObject> h = new HashMap<>();
            System.out.println(oo.keys().getClass());

//            HashMap<String,Object> result =
//                    new ObjectMapper().readValue(loadJSONFromAsset(), HashMap.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
