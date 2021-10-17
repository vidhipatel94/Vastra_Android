package com.esolution.vastrafashiondesigner;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrafashiondesigner.adapter.CatalogueAdapter;
import com.esolution.vastrafashiondesigner.databinding.ActivityFashiondesignerProfileBinding;
import java.util.ArrayList;

public class FashionDesignerProfileActivity extends AppCompatActivity {
    ActivityFashiondesignerProfileBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFashiondesignerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.catalogueRecyclerView.setLayoutManager(layoutManager);

        ArrayList<String> items = new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        binding.catalogueRecyclerView.setAdapter(new CatalogueAdapter(items));
    }
}
