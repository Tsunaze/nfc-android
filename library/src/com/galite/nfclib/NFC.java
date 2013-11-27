package com.galite.nfclib;

import android.content.Context;
import android.nfc.NfcAdapter;


public class NFC {

	private NfcAdapter adapter;
	private Context context;
	public NFC(Context context) {
		this.context = context;
		adapter = NfcAdapter.getDefaultAdapter(context);
	}
	
	public boolean isEnabled(){
		if(adapter.isEnabled()){
			return true;
		}else{
			return false;
		}
	}
	public boolean isAvailable(){
		if(adapter != null){
			return true;
		}else{
			return false;
		}
	}
}
