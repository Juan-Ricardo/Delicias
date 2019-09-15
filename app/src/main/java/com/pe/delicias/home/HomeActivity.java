package com.pe.delicias.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pe.delicias.R;
import com.pe.delicias.chef.ChefFragment;
import com.pe.delicias.category.CategoryFragment;
import com.pe.delicias.plate.PlateFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView menuBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuBottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        menuBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.home_item:
                                goHome();
                                return true;

                            case R.id.menu_item:
                                goCategory();
                                return true;

                            case R.id.order_item:
                                goOrder();
                                return true;

                            case R.id.chef_item:
                                goChef();
                                return true;
                        }
                        return false;
                    }
                });
        menuBottomNavigationView.setSelectedItemId(R.id.menu_item);
        addFragment(new CategoryFragment(), "CategoryFragment");
    }

    private void goHome() {
        addFragment(new PlateFragment(), "PlateFragment");
    }

    private void goCategory() {
        addFragment(new CategoryFragment(), "CategoryFragment");
    }

    private void goOrder() {

    }

    private void goChef() {
        addFragment(new ChefFragment(), "ChefFragment");
    }

    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_layout, fragment, tag)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_item_menu) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
