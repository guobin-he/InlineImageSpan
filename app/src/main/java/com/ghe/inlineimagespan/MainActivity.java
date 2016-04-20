package com.ghe.inlineimagespan;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mTv;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String source = "1234567890123456789012345678901234567890123411111111567890123456789012345678901234567890";
        mTv = (EditText) findViewById(R.id.tv);
        mTv.setText(source);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_insert_image) {
            InlineImageSpan inlineImg = new InlineImageSpan(this, DynamicDrawableSpan.ALIGN_BOTTOM);
            Editable text = mTv.getText();
            int start = Math.max(Selection.getSelectionStart(text), 0);
            text.insert(start,Constants.IMAGE_TAG);
            text.setSpan(inlineImg, start, start+Constants.IMAGE_TAG.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTv.setText(text);
            mMenu.removeItem(R.id.action_insert_image);
            getMenuInflater().inflate(R.menu.menu_remove, mMenu);
            return true;
        }
        if (id == R.id.action_remove_image) {
            Editable text = mTv.getText();
            InlineImageSpan[] spans = text.getSpans(0, text.length(), InlineImageSpan.class);
            for(int i=0; i<spans.length; i++) {
                int spanStart = text.getSpanStart(spans[i]);
                text.removeSpan(spans[i]);
                if(spanStart>=0) {
                    text.delete(spanStart,spanStart+Constants.IMAGE_TAG.length());
                }
            }
            mTv.setText(text);
            mMenu.removeItem(R.id.action_remove_image);
            getMenuInflater().inflate(R.menu.menu_insert, mMenu);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
