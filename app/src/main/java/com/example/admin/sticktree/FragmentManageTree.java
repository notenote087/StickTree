package com.example.admin.sticktree;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentManageTree extends Fragment {

    View rootView ;

    Button add,edit,search,delete;

    public FragmentManageTree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_manage_tree, container, false);

        add = (Button) rootView.findViewById(R.id.add);
        edit = (Button) rootView.findViewById(R.id.edit);
        search = (Button) rootView.findViewById(R.id.search);
        delete = (Button) rootView.findViewById(R.id.delete);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),online_add.class);
                startActivity(intent);
            }
        });
        try{
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),online_edit_find.class);
                startActivity(intent);

            }
        });

        }catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          /*      Intent intent = new Intent(getActivity(),online_search.class);
                startActivity(intent);*/
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),online_delete.class);
                startActivity(intent);
            }
        });


        return rootView;
    }



}
