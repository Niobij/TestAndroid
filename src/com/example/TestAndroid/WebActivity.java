package com.example.TestAndroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ZOG on 12/22/2014.
 */
public class WebActivity extends Activity implements View.OnClickListener {

	private EditText etLink;
	private WebView webView;
	private Button btnGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);

		etLink = (EditText) findViewById(R.id.etLink);
		webView = (WebView) findViewById(R.id.webView);
		btnGo = (Button) findViewById(R.id.btnGo);
		btnGo.setOnClickListener(this);

//		webView.setWebViewClient(new MyWebViewClient());
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient());
		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setWebContentsDebuggingEnabled(true);
		webView.loadUrl("google.com");
	}

	@Override
	public void onClick(View view) {
		if (view == btnGo) {
			String link = etLink.getText().toString();
			webView.loadUrl(link);
		}
	}

	class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			return super.shouldOverrideUrlLoading(view, url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}

}
