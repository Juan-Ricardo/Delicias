package com.pe.delicias.plate;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pe.delicias.R;
import com.pe.delicias.apirest.ApiClient;
import com.pe.delicias.apirest.ApiService;
import com.pe.delicias.apirest.response.category.CategoryResponse;
import com.pe.delicias.apirest.response.plate.PlateDataResponse;
import com.pe.delicias.apirest.response.plate.PlateResponse;
import com.pe.delicias.category.adapter.CategoryRecyclerAdapter;
import com.pe.delicias.category.model.Category;
import com.pe.delicias.plate.adapter.PlateRecyclerAdapter;
import com.pe.delicias.plate.model.Plate;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlateFragment extends Fragment {

    //Rainbow Brackets

    @BindView(R.id.material_search_view)
    public MaterialSearchView materialSearchView;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.plate_recycler_view)
    public RecyclerView plateRecyclerView;

    private PlateRecyclerAdapter adapter;
    private List<Plate> plates;

    @BindView(R.id.plate_nested_scroll_view)
    public NestedScrollView plateNestedScrollView;

    //@BindView(R.id.menu_bottom_navigation)
    public BottomNavigationView menuBottomNavigationView;

    private boolean isNavigationHide = false;

    private boolean showPlateByCategory = false;

    public PlateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plate, container, false);
        ButterKnife.bind(this, view);
        finds();
        return view;
    }

    private void finds() {
        setHasOptionsMenu(true);
        menuBottomNavigationView = getActivity().findViewById(R.id.menu_bottom_navigation);
        setupToolbar("Platos", "", false);
        events();
    }

    private void events() {
        materialSearchView.setHint("Buscar...");
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (adapter != null)
                    adapter.getFilter().filter(query);
                return true;
            }
        });
    }

    private void setupToolbar(String title, String subTitle, boolean arrow) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(arrow);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupShowPlate();
    }

    private void setupShowPlate() {
        if (showPlateByCategory)
            showPlateByCategoryRecyclerView();
        else
            setPlateRecyclerView();
    }

    private void showPlateByCategoryRecyclerView() {
        plateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PlateRecyclerAdapter(plates, R.layout.plate_card_view, getActivity());
        plateRecyclerView.setAdapter(adapter);
    }

    private void setPlateRecyclerView() {
        plates = new LinkedList<>();
        plateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PlateRecyclerAdapter(plates, R.layout.plate_card_view, getActivity());
        plateRecyclerView.setAdapter(adapter);
        loadPlates();
    }

    private void loadPlates() {
        Call<PlateResponse> plateResponse = ApiClient.getInstance(getContext())
                .createService(ApiService.class)
                .getPlates();
        plateResponse.enqueue(new Callback<PlateResponse>() {
            @Override
            public void onResponse(Call<PlateResponse> call, Response<PlateResponse> response) {
                if (response.isSuccessful()) {

                    List<PlateDataResponse> rows = response.body().getData();
                    for (PlateDataResponse row : rows) {

                        Plate plate = new Plate();
                        plate.setId(row.get_id());
                        plate.setName(row.getNombre());
                        plate.setDescription(row.getCategoria_id().getNombre() + " " +
                                row.getCategoria_id().getDescripcion());
                        plate.setImage(row.getImagen());
                        plate.setPrice(Double.parseDouble(row.getPrecio()));

                        plates.add(plate);
                    }
                    adapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<PlateResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search_item_menu);
        materialSearchView.setMenuItem(item);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void setPlates(PlateResponse response) {
        plates = new LinkedList<>();
        List<PlateDataResponse> rows = response.getData();
        for (PlateDataResponse row : rows) {

            Plate plate = new Plate();
            plate.setId(row.get_id());
            plate.setName(row.getNombre());
            plate.setDescription(row.getCategoria_id().getNombre() + " " +
                    row.getCategoria_id().getDescripcion());
            plate.setImage(row.getImagen());
            plate.setPrice(Double.parseDouble(row.getPrecio()));

            plates.add(plate);
        }
    }

    public void setShowPlateByCategory(boolean showPlateByCategory) {
        this.showPlateByCategory = showPlateByCategory;
    }
}
