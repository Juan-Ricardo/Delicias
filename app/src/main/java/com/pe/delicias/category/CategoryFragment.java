package com.pe.delicias.category;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pe.delicias.R;
import com.pe.delicias.apirest.ApiClient;
import com.pe.delicias.apirest.ApiService;
import com.pe.delicias.apirest.response.category.CategoryData;
import com.pe.delicias.apirest.response.category.CategoryResponse;
import com.pe.delicias.category.adapter.CategoryRecyclerAdapter;
import com.pe.delicias.category.model.Category;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView menuRecyclerView;
    private CategoryRecyclerAdapter adapter;
    private List<Category> categories;

    public CategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        finds(view);
        return view;
    }

    private void finds(View view) {
        setupToolbar(view, "Menús", "", false);
        this.menuRecyclerView = view.findViewById(R.id.menu_recycler_view);
        this.categories=new LinkedList<>();
    }

    private void setupToolbar(View view, String title, String subTitle, boolean arrow) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuRecyclerView();
    }

    private void setMenuRecyclerView() {
        loadCategories();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        menuRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CategoryRecyclerAdapter(categories, R.layout.menu_card_view, getActivity());
        menuRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadCategories() {
        //categories = Category.gets();
        Call<CategoryResponse> categoryResponse = ApiClient.getInstance(getContext())
                .createService(ApiService.class)
                .getCategories();
        categoryResponse.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                List<CategoryData> row = response.body().getData();
                if (response.isSuccessful()) {
                    for (int i = 0; i < row.size(); i++) {
                        Category category =new Category();
                        category.setId(row.get(i).get_id());
                        category.setTitle(row.get(i).getNombre());
                        category.setImage("https://querecetas.net/wp-content/uploads/2019/03/Causa-rellena-de-Pollo.jpg");
                        category.setDescription(row.get(i).getDescripcion());
                        categories.add(category);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.v("menu: ", "False");
                    //Motrar un toast.
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.v("menu: ", "onFailure: "+t.getMessage());
            }
        });
    }
}
