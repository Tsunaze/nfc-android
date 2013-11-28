package com.galite.nfclib;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.util.Log;

public class NFC {
	private String TAG = "NFClib";
	private NfcAdapter adapter;
	private Context context;

	public NFC(Context context) {
		this.context = context;
		adapter = NfcAdapter.getDefaultAdapter(context);
	}

	public boolean isEnabled() {
		if (adapter.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAvailable() {
		if (adapter != null) {
			return true;
		} else {
			return false;
		}
	}

	public String read(Tag tag) {
		Ndef ndef = Ndef.get(tag);
		if (ndef == null) {
			// NDEF is not supported by this Tag.
			return null;
		}
		NdefMessage ndefMessage = ndef.getCachedNdefMessage();
		NdefRecord[] records = ndefMessage.getRecords();
		for (NdefRecord ndefRecord : records) {
			if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN
					&& Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
				try {
					return readText(ndefRecord);
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, "Unsupported Encoding", e);
				}
			}
		}
		return null;
	}

	private String readText(NdefRecord record)
			throws UnsupportedEncodingException {
		byte[] payload = record.getPayload();
		// Get the Text Encoding
		String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
		// Get the Language Code
		int languageCodeLength = payload[0] & 0063;
		// String languageCode = new String(payload, 1, languageCodeLength,
		// "US-ASCII");
		// e.g. "en"
		// Get the Text
		return new String(payload, languageCodeLength + 1, payload.length
				- languageCodeLength - 1, textEncoding);
	}

	/*
	 * 
	 * GETTERS & SETTERS
	 */
	public NfcAdapter getAdapter() {
		return adapter;
	}

}
