package com.example.sprint3dc.BD;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprint3dc.Adaptadores.ProductAdapter;
import com.example.sprint3dc.Entidades.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private FirebaseFirestore db;

    public DBFirebase(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void insertData(Product product){
        // Create a new user with a first and last name
        Map<String, Object> prod = new HashMap<>();
        prod.put("id", product.getId());
        prod.put("name", product.getName());
        prod.put("description", product.getDescription());
        prod.put("price", product.getPrice());
        prod.put("image", product.getImage());

        // Add a new document with a generated ID
        db.collection("products").add(prod);
    }

    public void getData(ProductAdapter adapter, ArrayList<Product> list){
        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = new Product(
                                        document.getData().get("id").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("description").toString(),
                                        Integer.parseInt(document.getData().get("price").toString()),
                                        document.getData().get("image").toString()
                                );
                                list.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("Error document", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void updateData(Product product){
        db.collection("products").whereEqualTo("id", product.getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document =
                                    task.getResult().getDocuments().get(0);

                            document.getReference().update(
                                    "name", product.getName(),
                                    "description", product.getDescription(),
                                    "price", product.getPrice(),
                                    "image", product.getImage()
                            );
                        }
                    }
                });
    }

    public void deleteData(String id){
        db.collection("products").whereEqualTo("id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                document.getReference().delete();
                            }
                        }
                    }
                });
    }

}
