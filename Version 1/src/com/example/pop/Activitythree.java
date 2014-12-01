package com.example.pop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class Activitythree extends Activity {
	public Elements title;
	public Elements img;
	public Bitmap bitmap;
	public ArrayList<String> titleList = new ArrayList<>();
	private ArrayAdapter<String> adapter;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.three);
	    lv = (ListView)findViewById(R.id.lvLinks);
	    new NewThread().execute();
	    new ImgThread().execute();
	    adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.product_name, titleList);
	  }
	public class ImgThread extends AsyncTask<String, Void, String> {
		// Метод выполняющий запрос в фоне, в версиях выше 4 андроида, запросы в главном потоке выполнять
        // нельзя, поэтому все что вам нужно выполнять - выносите в отдельный тред

        @Override
        protected void onPostExecute(String result) {
        	ImageView logoimg = (ImageView) findViewById(R.id.imageView1);
			logoimg.setImageBitmap(bitmap);
        }

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// класс который захватывает страницу
            Document doc;
            try {
                    // определяем откуда будем воровать данные
                    doc = Jsoup.connect("http://1001bilet.com.ua/estrada.html").get();
                    // задаем с какого места, я выбрал заголовке статей
                    img = doc.select("a[class=photo] img[src]");
                    String imgSrc = "http://1001bilet.com.ua/"+img.attr("src");
    				// Download image from URL
    				InputStream input = new java.net.URL(imgSrc).openStream();
    				bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                    e.printStackTrace();
            }
            // ничего не возвращаем потому что я так захотел)
			return null;
		}
	}
	public class NewThread extends AsyncTask<String, Void, String> {

        // Метод выполняющий запрос в фоне, в версиях выше 4 андроида, запросы в главном потоке выполнять
        // нельзя, поэтому все что вам нужно выполнять - выносите в отдельный тред
        @Override
        protected String doInBackground(String... arg) {

                // класс который захватывает страницу
                Document doc;
                try {
                        // определяем откуда будем воровать данные
                        doc = Jsoup.connect("http://1001bilet.com.ua/estrada.html").get();
                        // задаем с какого места, я выбрал заголовке статей
                        title = doc.select("div[class=info]");
                        // чистим наш аррей лист для того что бы заполнить
                        titleList.clear();
                        // и в цикле захватываем все данные какие есть на странице
                        for (Element titles : title) {
                                // записываем в аррей лист
                                titleList.add(titles.text());
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                // ничего не возвращаем потому что я так захотел)
                return null;
        }

        @Override
        protected void onPostExecute(String result) {

                // после запроса обновляем листвью
                lv.setAdapter(adapter);
        }
}
}
