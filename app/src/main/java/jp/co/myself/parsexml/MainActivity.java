package jp.co.myself.parsexml;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String NONOICHI_XML = "refuge_nonoichi.xml";

    private static final String SABAE_XML = "refuge_sabae.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt01 = (TextView) findViewById(R.id.txt01);
        //txt01.setText(parseNonoichi());
        txt01.setText(parseSabae());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * 野々市市のオープンデータをパースします。
     * @return パース後のデータ文字列。
     */
    private String parseNonoichi() {
        StringBuilder strBuild = new StringBuilder();

        AssetManager assetManager = getResources().getAssets();

        try {
            InputStream is = assetManager.open(NONOICHI_XML);
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStreamReader);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if ("marker".equals(tag)) {
                            strBuild.append(parser.getAttributeValue(null, "title"));
                            strBuild.append(",");
                            strBuild.append(parser.getAttributeValue(null, "lat"));
                            strBuild.append(",");
                            strBuild.append(parser.getAttributeValue(null, "lng"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if ("marker".equals(endTag)) {
                            strBuild.append("\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return strBuild.toString();
    }

    /**
     * 鯖江市のオープンデータをパースします。
     * @return パース後のデータ文字列。
     */
    private String parseSabae() {
        StringBuilder strBuild = new StringBuilder();

        String type = "";
        String name = "";
        String lat = "";
        String lng = "";

        AssetManager assetManager = getResources().getAssets();

        try {
            InputStream is = assetManager.open(SABAE_XML);
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStreamReader);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if ("type".equals(tag)) {
                            type = parser.nextText();
                        } else if ("name".equals(tag)) {
                            name = parser.nextText();
                        } else if ("latitude".equals(tag)) {
                            lat = parser.nextText();
                        } else if ("longitude".equals(tag)) {
                            lng = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if ("refuge".equals(endTag)) {
                            strBuild.append(name);
                            strBuild.append(" ");
                            strBuild.append(type);
                            strBuild.append(" ");
                            strBuild.append(lat);
                            strBuild.append(" ");
                            strBuild.append(lng);
                            strBuild.append("\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return strBuild.toString();
    }
}
