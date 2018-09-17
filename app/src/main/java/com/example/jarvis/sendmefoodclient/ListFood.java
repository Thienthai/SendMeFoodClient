package com.example.jarvis.sendmefoodclient;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.MenuHld.FdViewHld;
import com.example.jarvis.sendmefoodclient.Model.Categories;
import com.example.jarvis.sendmefoodclient.Model.MyFood;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class ListFood extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;

    FirebaseDatabase db;
    DatabaseReference db_ref;
    FirebaseStorage storage;
    StorageReference str_ref;

    String CatId = "";
    FirebaseRecyclerAdapter<MyFood,FdViewHld> adapter;

    EditText txtName,txtDescip,txtPrice,txtDiscnt;
    Button selct,upld;

    MyFood myNewFood;

    Uri myUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("Foods");
        storage = FirebaseStorage.getInstance();
        str_ref = storage.getReference();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fab = (FloatingActionButton) findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialgAdd();
            }
        });

        if(getIntent() != null){
            CatId = getIntent().getStringExtra("CatId");
        }
        if(!CatId.isEmpty()){
            fdLoad(CatId);
        }
    }

    private void dialgAdd() {
        AlertDialog.Builder alrt = new AlertDialog.Builder(ListFood.this,R.style.AlertDialog);
        alrt.setTitle("add new food");
        alrt.setMessage("fill information");

        LayoutInflater infl = this.getLayoutInflater();
        View addMenu = infl.inflate(R.layout.food_add,null);

        txtName = addMenu.findViewById(R.id.edt_name);
        txtDescip = addMenu.findViewById(R.id.edt_descrip);
        txtPrice = addMenu.findViewById(R.id.edt_price);
        txtDiscnt = addMenu.findViewById(R.id.edt_discnt);
        selct = addMenu.findViewById(R.id.select_food_add);
        upld = addMenu.findViewById(R.id.upld_food_add);

        selct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelImg();
            }
        });

        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upldImg();
            }
        });

        alrt.setView(addMenu);
        alrt.setIcon(R.drawable.ic_local_grocery_store_black_24dp);

        alrt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(myNewFood != null){
                    db_ref.push().setValue(myNewFood);
                }

            }
        });

        alrt.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alrt.show();
    }

    private void fdLoad(String catId) {
        adapter = new FirebaseRecyclerAdapter<MyFood, FdViewHld>(MyFood.class,R.layout.food_itm,FdViewHld.class,db_ref.orderByChild("menuId").equalTo(catId)) {
            @Override
            protected void populateViewHolder(FdViewHld viewHolder, MyFood model, int position) {
                    viewHolder.fd_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.fd_image);
                viewHolder.setlistenerClck(new ListenerClck() {
                    @Override
                    public void onClick(int Pos, View view, boolean isClck) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void upldImg() {
        if(myUri != null){
            final ProgressDialog dilg = new ProgressDialog(this);
            dilg.setMessage("uploading");
            dilg.show();
            String imgName = UUID.randomUUID().toString();
            final StorageReference imageFldr = str_ref.child("img/" + imgName);
            imageFldr.putFile(myUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dilg.dismiss();
                            Toast.makeText(ListFood.this,"Upload Finish!!",Toast.LENGTH_SHORT).show();
                            imageFldr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    myNewFood = new MyFood(
                                            txtName.getText().toString(),
                                            myUri.toString(),
                                            txtDescip.getText().toString(),
                                            CatId,
                                            txtPrice.getText().toString(),
                                            txtDiscnt.getText().toString(),
                                            Current.usrCurrent.getNumber()
                                    );

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dilg.dismiss();
                    Toast.makeText(ListFood.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    dilg.setMessage("Upload " + progress + "%");
                }
            });
        }
    }

    private void SelImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose Picture"),71);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Current.UPDATE)){
            UpdtFood(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if(item.getTitle().equals(Current.DELETE)){
            DelFood(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void DelFood(String key) {
        db_ref.child(key).removeValue();
    }

    private void UpdtFood(final String key, final MyFood item) {
        AlertDialog.Builder alrt = new AlertDialog.Builder(ListFood.this,R.style.AlertDialog);
        alrt.setTitle("edit my food");
        alrt.setMessage("fill information");

        LayoutInflater infl = this.getLayoutInflater();
        View addMenu = infl.inflate(R.layout.food_add,null);

        txtName = addMenu.findViewById(R.id.edt_name);
        txtDescip = addMenu.findViewById(R.id.edt_descrip);
        txtPrice = addMenu.findViewById(R.id.edt_price);
        txtDiscnt = addMenu.findViewById(R.id.edt_discnt);
        selct = addMenu.findViewById(R.id.select_food_add);
        upld = addMenu.findViewById(R.id.upld_food_add);

        txtName.setText(item.getName());
        txtDiscnt.setText(item.getDiscount());
        txtPrice.setText(item.getPrice());
        txtDescip.setText(item.getDescription());

        selct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelImg();
            }
        });

        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetImage(item);
            }
        });

        alrt.setView(addMenu);
        alrt.setIcon(R.drawable.ic_local_grocery_store_black_24dp);

        alrt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    item.setName(txtName.getText().toString());
                    item.setDescription(txtDescip.getText().toString());
                    item.setDiscount(txtDiscnt.getText().toString());
                    item.setPrice(txtPrice.getText().toString());

                    db_ref.child(key).setValue(item);

            }
        });

        alrt.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alrt.show();
    }

    private void resetImage(final MyFood item) {
        if(myUri != null){
            final ProgressDialog dilg = new ProgressDialog(this);
            dilg.setMessage("uploading");
            dilg.show();
            String imgName = UUID.randomUUID().toString();
            final StorageReference imageFldr = str_ref.child("img/" + imgName);
            imageFldr.putFile(myUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dilg.dismiss();
                            Toast.makeText(ListFood.this,"Upload Finish!!",Toast.LENGTH_SHORT).show();
                            imageFldr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    item.setImage(myUri.toString());

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dilg.dismiss();
                    Toast.makeText(ListFood.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    dilg.setMessage("Upload " + progress + "%");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 71 && resultCode == RESULT_OK && data != null && data.getData() != null){
            myUri = data.getData();
            selct.setText("Image selected");
        }
    }
}
