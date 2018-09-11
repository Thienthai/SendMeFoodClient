package com.example.jarvis.sendmefoodclient;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.MenuHld.ViewHld;
import com.example.jarvis.sendmefoodclient.Model.Categories;
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

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView user_fullname,my_number;

    FirebaseDatabase db;
    DatabaseReference db_ref;
    FirebaseRecyclerAdapter<Categories,ViewHld> adapter;
    FirebaseStorage store;
    StorageReference store_ref;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    TextView txtName;
    Button upld,selct;

    Categories newCat;

    Uri myUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Manager");
        setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("Categories");
        store = FirebaseStorage.getInstance();
        store_ref = store.getReference();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialShw();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View top_menu = navigationView.getHeaderView(0);
        user_fullname = (TextView)top_menu.findViewById(R.id.user_fullname);
        user_fullname.setText(Current.usrCurrent.getName());
        my_number = (TextView)top_menu.findViewById(R.id.user_email);
        my_number.setText(Current.usrCurrent.getNumber());

        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        mnLoad();
    }

    private void dialShw() {
        AlertDialog.Builder alrt = new AlertDialog.Builder(Home.this,R.style.AlertDialog);
        alrt.setTitle("add new categories");
        alrt.setMessage("fill information");

        LayoutInflater infl = this.getLayoutInflater();
        View addMenu = infl.inflate(R.layout.menu_add,null);

        txtName = addMenu.findViewById(R.id.edt_name);
        selct = addMenu.findViewById(R.id.select_add);
        upld = addMenu.findViewById(R.id.upld_add);

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
                if(newCat != null){
                    db_ref.push().setValue(newCat);
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

    private void upldImg() {
        if(myUri != null){
            final ProgressDialog dilg = new ProgressDialog(this);
            dilg.setMessage("uploading");
            dilg.show();
            String imgName = UUID.randomUUID().toString();
            final StorageReference imageFldr = store_ref.child("img/" + imgName);
            imageFldr.putFile(myUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dilg.dismiss();
                    Toast.makeText(Home.this,"Upload Finish!!",Toast.LENGTH_SHORT).show();
                    imageFldr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newCat = new Categories(txtName.getText().toString(),myUri.toString());

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dilg.dismiss();
                    Toast.makeText(Home.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
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

    private void SelImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose Picture"),71);
    }

    private void mnLoad() {

        adapter = new FirebaseRecyclerAdapter<Categories,ViewHld>(Categories.class,R.layout.list_item,ViewHld.class,db_ref){
            @Override
            protected void populateViewHolder(ViewHld viewHolder, Categories model, final int position) {
                viewHolder.name_menu.setText(model.getTitle());
                Picasso.with(getBaseContext()).load(model.getImg())
                        .into(viewHolder.img);
                viewHolder.setlistenerClck(new ListenerClck() {
                    @Override
                    public void onClick(int Pos, View view, boolean isClck) {
                        Intent intent = new Intent(Home.this,ListFood.class);
                        intent.putExtra("CatId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_orders) {
            Intent intent = new Intent(Home.this,OrderBoard.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Current.UPDATE)){
            uPdtDialg(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if(item.getTitle().equals(Current.DELETE)){
            delCat(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void delCat(String key) {
        db_ref.child(key).removeValue();
        final ProgressDialog dilg = new ProgressDialog(this);
        dilg.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dilg.dismiss();
            }
        },3000);
        Toast.makeText(this,"Deleate Successful",Toast.LENGTH_SHORT).show();
    }

    private void uPdtDialg(final String key, final Categories item) {
        AlertDialog.Builder alrt = new AlertDialog.Builder(Home.this,R.style.AlertDialog);
        alrt.setTitle("update new categories");
        alrt.setMessage("fill information");

        LayoutInflater infl = this.getLayoutInflater();
        View addMenu = infl.inflate(R.layout.menu_add,null);

        txtName = addMenu.findViewById(R.id.edt_name);
        selct = addMenu.findViewById(R.id.select_add);
        upld = addMenu.findViewById(R.id.upld_add);

        txtName.setText(item.getTitle());

        selct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelImg();
            }
        });

        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chngImg(item);
            }
        });

        alrt.setView(addMenu);
        alrt.setIcon(R.drawable.ic_local_grocery_store_black_24dp);

        alrt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item.setTitle(txtName.getText().toString());
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

    private void chngImg(final Categories item) {
        if(myUri != null){
            final ProgressDialog dilg = new ProgressDialog(this);
            dilg.setMessage("uploading");
            dilg.show();
            String imgName = UUID.randomUUID().toString();
            final StorageReference imageFldr = store_ref.child("img/" + imgName);
            imageFldr.putFile(myUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dilg.dismiss();
                            Toast.makeText(Home.this,"Upload Finish!!",Toast.LENGTH_SHORT).show();
                            imageFldr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    item.setImg(myUri.toString());

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dilg.dismiss();
                    Toast.makeText(Home.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
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
}
