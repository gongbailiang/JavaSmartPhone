package src.mamasrecipe;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.TextView;

import app.App;
import model.po.ImagePO;
import model.po.RecipePO;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
public class MyDishActivity extends ActionBarActivity {
    public String myDishCategoryID = new String();
    public String myDishUserID = new String();
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private TextView dishName1;
    private TextView dishName2;
    private TextView dishName3;
    private TextView dishName4;

    private long dishID1;
    private long dishID2;
    private long dishID3;
    private long dishID4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dish);
        View v = findViewById(R.id.mydish);
        v.getBackground().setAlpha(100);
        getReferece();
        if(myDishCategoryID.isEmpty()){
            imageSetterByUserID();
        }
        else {
            imageSetterByCategoryID();
        }
        clickListener();

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what == 0x1){
                imageView1.setImageBitmap(bitmap1);
            }
            if (msg.what == 0x2){
                imageView2.setImageBitmap(bitmap2);
            }
            if (msg.what == 0x3){
                imageView3.setImageBitmap(bitmap3);
            }
            if (msg.what == 0x4){
                imageView4.setImageBitmap(bitmap4);
            }
            else{
                // Write Exception here !
            }
        }

    };





















    private void getReferece(){
        Intent intent = getIntent();
        myDishUserID = intent.getStringExtra("userID");
        myDishCategoryID = intent.getStringExtra("categoryID");

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        dishName1 = (TextView) findViewById(R.id.DishName1);
        dishName2 = (TextView) findViewById(R.id.DishName2);
        dishName3 = (TextView) findViewById(R.id.DishName3);
        dishName4 = (TextView) findViewById(R.id.DishName4);

    }
    private void clickListener(){
        imageView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent submit = new Intent(MyDishActivity.this, ShowDishActivity.class);
                submit.putExtra("dishID", String.valueOf(dishID1)); // Add dishID later here !
                startActivity(submit);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent submit = new Intent(MyDishActivity.this, ShowDishActivity.class);
                submit.putExtra("dishID", String.valueOf(dishID2)); // Add dishID later here !
                startActivity(submit);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent submit = new Intent(MyDishActivity.this, ShowDishActivity.class);
                submit.putExtra("dishID", String.valueOf(dishID3)); // Add dishID later here !
                startActivity(submit);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent submit = new Intent(MyDishActivity.this, ShowDishActivity.class);
                submit.putExtra("dishID", String.valueOf(dishID4)); // Add dishID later here !
                startActivity(submit);
            }
        });
    }
    private void imageSetterByUserID(){
        App.getRestClient().getRecipeService().getRecipesByUserID(myDishUserID, new Callback<List<RecipePO>>() {
            @Override
            public void success(final List<RecipePO> recipePOs, Response response) {
                int index = recipePOs.size();
                dishName1.setText(recipePOs.get(index-4).getDishName());
                dishName2.setText(recipePOs.get(index-3).getDishName());
                dishName3.setText(recipePOs.get(index-2).getDishName());
                dishName4.setText(recipePOs.get(index-1).getDishName());
                System.out.println("--------*********" + recipePOs.get(index-4).getDishName());
                System.out.println("--------*********" + recipePOs.get(index-3).getDishName());
                System.out.println("--------*********" + recipePOs.get(index-2).getDishName());
                System.out.println("--------*********" + recipePOs.get(index-1).getDishName());
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index - 4).getDishID()), new Callback<ImagePO>() {
                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID1 = imagePO.getDishID();
                        imageThread thread = new imageThread("http://" + imagePO.getImageURI(), 1);
                        thread.start();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index-3).getDishID()), new Callback<ImagePO>() {
                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID2 = imagePO.getDishID();

                        imageThread thread = new imageThread("http://" + imagePO.getImageURI(), 2);
                        thread.start();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index-2).getDishID()), new Callback<ImagePO>() {
                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID3 = imagePO.getDishID();

                        imageThread thread = new imageThread("http://" + imagePO.getImageURI(), 3);
                        thread.start();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index-1).getDishID()), new Callback<ImagePO>() {
                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID4 = imagePO.getDishID();

                        imageThread thread = new imageThread("http://" + imagePO.getImageURI(), 4);
                        thread.start();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    private void imageSetterByCategoryID(){
        App.getRestClient().getRecipeService().getRecipesByCategoryID(myDishCategoryID, new Callback<List<RecipePO>>() {

            @Override
            public void success(List<RecipePO> recipePOs, Response response) {
                int index = recipePOs.size();
                dishName1.setText(recipePOs.get(index-4).getDishName());
                dishName2.setText(recipePOs.get(index-3).getDishName());
                dishName3.setText(recipePOs.get(index-2).getDishName());
                dishName4.setText(recipePOs.get(index-1).getDishName());
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index-4).getDishID()), new Callback<ImagePO>() {

                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID1 = imagePO.getDishID();
                        imageThread thread = new  imageThread("http://" + imagePO.getImageURI(), 1);
                        thread.start();
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index-3).getDishID()), new Callback<ImagePO>() {
                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID2 = imagePO.getDishID();
                        imageThread thread = new  imageThread("http://" + imagePO.getImageURI(), 2);
                        thread.start();
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index-2).getDishID()), new Callback<ImagePO>() {
                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID3 = imagePO.getDishID();
                        imageThread thread = new  imageThread("http://" + imagePO.getImageURI(), 3);
                        thread.start();
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                App.getRestClient().getPhotoService().getImageByDishID(String.valueOf(recipePOs.get(index-1).getDishID()), new Callback<ImagePO>() {
                    @Override
                    public void success(ImagePO imagePO, Response response) {
                        dishID4 = imagePO.getDishID();
                        imageThread thread = new  imageThread("http://" + imagePO.getImageURI(), 4);
                        thread.start();
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    private class imageThread extends Thread{
        private String imageUrl;
        private int index;

        private imageThread(String imageUrl, int index){
            this.imageUrl = imageUrl;
            this.index = index;
        }
        @Override
        public void run() {
            try {
                URL url = new URL(imageUrl);
                InputStream is = url.openStream();
                switch (index){
                    case 1:
                        bitmap1 = BitmapFactory.decodeStream(is);
                        break;
                    case 2:
                        bitmap2 = BitmapFactory.decodeStream(is);
                        break;
                    case 3:
                        bitmap3 = BitmapFactory.decodeStream(is);
                        break;
                    case 4:
                        bitmap4 = BitmapFactory.decodeStream(is);
                        break;
                    default:
                        break;
                }

                handler.sendEmptyMessage(index);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what == 0x1){
                imageView1.setImageBitmap(bitmap1);
            }
            if (msg.what == 0x2){
                imageView2.setImageBitmap(bitmap2);
            }
            if (msg.what == 0x3){
                imageView3.setImageBitmap(bitmap3);
            }
            if (msg.what == 0x4){
                imageView4.setImageBitmap(bitmap4);
            }
            else{
                // Write Exception here !
            }
        }

    };





















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_dish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
