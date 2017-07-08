package hotelsmembership.com.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import hotelsmembership.com.Applications.Initializer;
import hotelsmembership.com.Fragments.CardFragment;
import hotelsmembership.com.Fragments.RoomReservation;
import hotelsmembership.com.Fragments.VouchersFragment;
import hotelsmembership.com.Model.Membership;
import hotelsmembership.com.Model.Vouchers.Voucher;
import hotelsmembership.com.R;

public class MembershipActivity extends AppCompatActivity implements VouchersFragment.Listener, CardFragment.OnFragmentInteractionListener,
        RoomReservation.OnFragmentInteractionListener{

    @BindView(R.id.frame)
    FrameLayout frameLayout;
    CardFragment cardFragment;
    Membership membership;
    String cardNumber;
    List<Voucher> vouchers;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_card:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, cardFragment);
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_vouchers:

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, VouchersFragment.newInstance(((Initializer) getApplication()).getCardContext().getVouchers(),((Initializer) getApplication()).getCardContext().getCardNumber(),((Initializer) getApplication()).getCardContext().getMembership()));
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_offers:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, VouchersFragment.newInstance(((Initializer) getApplication()).getCardContext().getVouchers(),((Initializer) getApplication()).getCardContext().getCardNumber(),((Initializer) getApplication()).getCardContext().getMembership()));
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        membership = ((Initializer) getApplication()).getCardContext().getMembership();
        cardNumber = membership.getCardNumber();
        vouchers = ((Initializer) getApplication()).getCardContext().getVouchers();
        setTitle(membership.getHotel().getHotelName());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cardFragment = CardFragment.newInstance("","");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, cardFragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.commit();
    }

    @Override
    public void onVoucherClicked(Voucher voucher, String cardNumber, Membership membership) {

    }
    public void callForTableBooking(View view) {
        String[] tokens = membership.getHotel().getPhoneNumbers().getTableResevation().replace("|",",").split(",");
        if (tokens.length > 1) {
            chooseNumberToCall(tokens);
        }
        else {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",membership.getHotel().getPhoneNumbers().getTableResevation() , null)));
        }
    }
    void chooseNumberToCall(final String[] numbers){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Call to Book");
        builder.setItems(numbers, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",numbers[item] , null)));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void mailForBooking(View view) {
        FragmentManager fm = getSupportFragmentManager();
        RoomReservation dialogFragment = RoomReservation.newInstance("","");
        dialogFragment.setCancelable(true);
        dialogFragment.show(fm, "room");
//            Intent intent = new Intent(Intent.ACTION_SENDTO);
//            intent.setData(Uri.parse("mailto:" + TextUtils.join(", ",membership.getHotel().getEmailIds()))); // only email apps should handle
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Booking Request");
//        Intent chooser = Intent.createChooser(intent, "Send Email Via");
//
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(chooser);
//            }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
