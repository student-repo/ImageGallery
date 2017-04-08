package com.example.ubuntu_master.image_gallery;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class ImagesListFragment extends Fragment {

    private HashMap<Integer, ImageInfo> imagesInfo = new HashMap<Integer, ImageInfo>(){{
       put(0, new ImageInfo("Google Plus1", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature1", 30, 0));
        put(1, new ImageInfo("Twitter1", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature2", 30, 1));
        put(2, new ImageInfo("Google Plus2", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature3", 30, 2));
        put(3, new ImageInfo("Twitter2", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature4", 30, 3));
        put(4, new ImageInfo("Google Plus3", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature5", 30, 4));
        put(5, new ImageInfo("Twitter3", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature1", 30, 5));
        put(6, new ImageInfo("Google Plus4", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature2", 30, 6));
        put(7, new ImageInfo("Twitter4", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature3", 30, 7));
        put(8, new ImageInfo("Google Plus5", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature4", 30, 8));
        put(9, new ImageInfo("Twitter5", "ala ma kota ale nie ma psa 11111 22222 333333 44444", "nature5", 30, 9));
    }};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_images_list, container, false);
        CustomList adapter = new
                CustomList(getActivity(), getTitleArray(), imagesInfo);


        ListView list = (ListView)view.findViewById(R.id.listview);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + imagesInfo.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), SingleImageActivity.class);
                i.putExtra("imageTitle", imagesInfo.get(position).getTitle());
                i.putExtra("imageDescription", imagesInfo.get(position).getDescription());
                i.putExtra("imageName", imagesInfo.get(position).getImage());
                i.putExtra("imageProgress", String.valueOf(imagesInfo.get(position).getProgress()));
                i.putExtra("imageId", String.valueOf(imagesInfo.get(position).getId()));
                startActivity(i);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        System.out.println("%%%%%%%%%%% " + getArguments().get("ratingBarValue"));
    }

    private String[] getTitleArray(){
        ArrayList<String> s = new ArrayList<>();
        for(Integer i: imagesInfo.keySet()){
            s.add(imagesInfo.get(i).getTitle());
        }
        return s.toArray(new String[0]);
    }
}


