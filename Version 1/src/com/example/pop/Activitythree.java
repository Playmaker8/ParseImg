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
		// ����� ����������� ������ � ����, � ������� ���� 4 ��������, ������� � ������� ������ ���������
        // ������, ������� ��� ��� ��� ����� ��������� - �������� � ��������� ����

        @Override
        protected void onPostExecute(String result) {
        	ImageView logoimg = (ImageView) findViewById(R.id.imageView1);
			logoimg.setImageBitmap(bitmap);
        }

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// ����� ������� ����������� ��������
            Document doc;
            try {
                    // ���������� ������ ����� �������� ������
                    doc = Jsoup.connect("http://1001bilet.com.ua/estrada.html").get();
                    // ������ � ������ �����, � ������ ��������� ������
                    img = doc.select("a[class=photo] img[src]");
                    String imgSrc = "http://1001bilet.com.ua/"+img.attr("src");
    				// Download image from URL
    				InputStream input = new java.net.URL(imgSrc).openStream();
    				bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                    e.printStackTrace();
            }
            // ������ �� ���������� ������ ��� � ��� �������)
			return null;
		}
	}
	public class NewThread extends AsyncTask<String, Void, String> {

        // ����� ����������� ������ � ����, � ������� ���� 4 ��������, ������� � ������� ������ ���������
        // ������, ������� ��� ��� ��� ����� ��������� - �������� � ��������� ����
        @Override
        protected String doInBackground(String... arg) {

                // ����� ������� ����������� ��������
                Document doc;
                try {
                        // ���������� ������ ����� �������� ������
                        doc = Jsoup.connect("http://1001bilet.com.ua/estrada.html").get();
                        // ������ � ������ �����, � ������ ��������� ������
                        title = doc.select("div[class=info]");
                        // ������ ��� ����� ���� ��� ���� ��� �� ���������
                        titleList.clear();
                        // � � ����� ����������� ��� ������ ����� ���� �� ��������
                        for (Element titles : title) {
                                // ���������� � ����� ����
                                titleList.add(titles.text());
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                // ������ �� ���������� ������ ��� � ��� �������)
                return null;
        }

        @Override
        protected void onPostExecute(String result) {

                // ����� ������� ��������� �������
                lv.setAdapter(adapter);
        }
}
}
