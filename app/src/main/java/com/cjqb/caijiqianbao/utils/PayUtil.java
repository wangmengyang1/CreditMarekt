package com.cjqb.caijiqianbao.utils;

import android.app.Activity;
import android.util.Log;

import com.cjqb.caijiqianbao.R;
import com.razorpay.Checkout;

import org.json.JSONObject;

public class PayUtil {
//    public void startPayment() {
//        /**
//         * Instantiate Checkout
//         */
//        Checkout checkout = new Checkout();
//        checkout.setKeyID("");
//        /**
//         * Set your logo here
//         */
//        checkout.setImage(R.drawable.icon_login_ff);
//
//        /**
//         * Reference to current activity
//         */
//        final Activity activity = this;
//
//        /**
//         * Pass your payment options to the Razorpay Checkout as a JSONObject
//         */
//        try {
//            JSONObject options = new JSONObject();
//
//            options.put("name", "Merchant Name");
//            options.put("description", "Reference No. #123456");
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
//            options.put("theme.color", "#3399cc");
//            options.put("currency", "INR");
//            options.put("amount", "50000");//pass amount in currency subunits
//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","9988776655");
//            checkout.open(activity, options);
//        } catch(Exception e) {
//            Log.e("show", "Error in starting Razorpay Checkout", e);
//        }
//    }
}
