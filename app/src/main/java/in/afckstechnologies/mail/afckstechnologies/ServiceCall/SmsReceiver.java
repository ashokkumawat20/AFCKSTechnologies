package in.afckstechnologies.mail.afckstechnologies.ServiceCall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import in.afckstechnologies.mail.afckstechnologies.Utils.Config;


/**
 * Created by Ashok on 5/17/2017.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String senderAddress = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.
            Log.e(TAG, "Received SMS: " + smsMessage + ", Sender: " + senderAddress);


            String companyFlag = senderAddress.substring(3);

            // if the SMS is not from our gateway, ignore the message
            if (!companyFlag.toLowerCase().contains(Config.SMS_ORIGIN.toLowerCase())) {
                return;
            }


            String messageBody = smsMessage.getMessageBody();

            String text=messageBody.substring(0,11);

            //Pass on the text to our listener.
            if(text.equals("AFCKST Code")) {
                mListener.messageReceived(messageBody);
            }
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
