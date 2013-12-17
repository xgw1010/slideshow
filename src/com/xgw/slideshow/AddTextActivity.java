package com.xgw.slideshow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddTextActivity extends Activity implements OnClickListener {

	private ImageText imgTxt;
	EditText et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_text);
		Button btn = (Button) this.findViewById(R.id.add_text_confirm);
		btn.setOnClickListener(this);
		
		imgTxt = (ImageText) this.getIntent().getSerializableExtra(ImageText.key);
		et = (EditText)this.findViewById(R.id.pic_text);
		et.setText(imgTxt.imageText);
		et.selectAll();
		
	}

	@Override
	public void onClick(View v) {
		
		PicTextDataBaseHelper dbHelper = new PicTextDataBaseHelper(this);
		imgTxt.imageText = et.getText().toString();
	    dbHelper.insertOrUpdateImage(imgTxt);
	    
	    Intent data=new Intent();  
	    data.putExtra("image_text", imgTxt.imageText);
	    this.setResult(100, data);
	    this.finish();
	}

}
