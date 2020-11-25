package com.cynoteck.petofyparents.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.AdoptionAdopter;
import com.cynoteck.petofyparents.adapter.DonationAdopter;
import com.cynoteck.petofyparents.adapter.DonationImagesAdopter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.response.adoptionResponse.AdoptionData;
import com.cynoteck.petofyparents.response.adoptionResponse.AdoptionResponse;
import com.cynoteck.petofyparents.response.donationResponse.Datum;
import com.cynoteck.petofyparents.response.donationResponse.PetImageList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.DonationClickListener;
import com.cynoteck.petofyparents.utils.ImagesOnClick;
import com.cynoteck.petofyparents.utils.Methods;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Response;

public class PendingAdoptionFragments extends Fragment implements ApiResponse, DonationClickListener, ImagesOnClick {
    View view;
    RecyclerView donation_pending_RV;
    ShimmerFrameLayout shimmer_view_container;
    Methods methods;
    AdoptionAdopter adoptionAdopter;
    DonationImagesAdopter donationImagesAdopter;
    List<AdoptionData> data;
    TextView no_record_fpond;
    Dialog dialog;

    List<PetImageList> imagesData;

    LinearLayout image_LL;
    ImageView fronImage;
    RecyclerView imagesLis;
    TextView petNameGender,updateDetails,petAge,petBreed,petColor,petSize,donarName,donarContact,donrEmail,donarAdress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pending_adoption_fragments, container, false);
        initialize();
        return view;
    }

    public void initialize()
    {
        methods=new Methods(getActivity());
        donation_pending_RV=view.findViewById(R.id.donation_pending_RV);
        shimmer_view_container=view.findViewById(R.id.shimmer_view_container);
        no_record_fpond=view.findViewById(R.id.no_record_fpond);

        if(methods.isInternetOn())
        {
            getData();
        }
        else
        {
            methods.isInternetOn();
        }

    }

    private void getData()
    {
        ApiService<AdoptionResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAdoptionRequestListPending(Config.token), "getAdoptionRequestListPending");

    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "getAdoptionRequestListPending":
                try {
                    AdoptionResponse adoptionResponse = (AdoptionResponse) arg0.body();
                    Log.d("getAdoptionRequestListPending", "" + adoptionResponse.toString());
                    if (adoptionResponse.getData().size() > 0) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        donation_pending_RV.setLayoutManager(linearLayoutManager);
                        adoptionAdopter = new AdoptionAdopter(getContext(), adoptionResponse.getData(), this);
                        donation_pending_RV.setAdapter(adoptionAdopter);
                        adoptionAdopter.notifyDataSetChanged();
                        data = adoptionResponse.getData();
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        no_record_fpond.setVisibility(View.GONE);
                    } else {
                        shimmer_view_container.setVisibility(View.GONE);
                        donation_pending_RV.setVisibility(View.GONE);
                        no_record_fpond.setVisibility(View.VISIBLE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void onItemClickView(int position) {
        imagesData=data.get(position).getPet().getPetImageList();
        String petName=data.get(position).getPet().getPetName();
        String petGendr=data.get(position).getPet().getPetCategory();
        String updateData=data.get(position).getRequestUpdateDate();
        String petAge=data.get(position).getPet().getPetAge();
        String petBreed=data.get(position).getPet().getPetBreed();
        String petColor=data.get(position).getPet().getPetColor();
        String petSize=data.get(position).getPet().getPetSize();
        String donarName=data.get(position).getPet().getDonarName();
        String donarContact=data.get(position).getPet().getPhoneNumber();
        String donarAddress=data.get(position).getPet().getAddress();
        viewDetailsDailog(imagesData,petName,petGendr,updateData,petAge,petBreed,petColor,petSize,donarName,donarContact,donarAddress);

    }

    @Override
    public void onItemClickEdit(int position) {

    }

    private void viewDetailsDailog(List<PetImageList> images, String strpetName, String strpetGendr, String strupdateData, String strpetAge, String strpetBreed, String strpetColor, String strpetSize, String strdonarName, String strdonarContact, String strdonarAddress)
    {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.donate_pet_details_dialog);

        fronImage=dialog.findViewById(R.id.fronImage);
        imagesLis=dialog.findViewById(R.id.images);
        image_LL=dialog.findViewById(R.id.image_LL);

        petNameGender=dialog.findViewById(R.id.petNameGender);
        updateDetails=dialog.findViewById(R.id.updateDetails);
        petAge=dialog.findViewById(R.id.petAge);
        petBreed=dialog.findViewById(R.id.petBreed);
        petColor=dialog.findViewById(R.id.petColor);
        petSize=dialog.findViewById(R.id.petSize);
        donarName=dialog.findViewById(R.id.donarName);
        donarContact=dialog.findViewById(R.id.donarContact);
        donrEmail=dialog.findViewById(R.id.donrEmail);
        donarAdress=dialog.findViewById(R.id.donarAdress);

        Log.d("ImaGeOne",""+images.size());

        if(images.size()>0)
        {
            image_LL.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(images.get(0).getPetImageUrl())
                    .into(fronImage);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            imagesLis.setLayoutManager(linearLayoutManager);
            donationImagesAdopter  = new DonationImagesAdopter(getContext(),images,this);
            imagesLis.setAdapter(donationImagesAdopter);
            donationImagesAdopter.notifyDataSetChanged();
        }
        else
        {
            image_LL.setVisibility(View.GONE);
        }

        petNameGender.setText(strpetName+"( "+strpetGendr+" )");
        updateDetails.setText("Updated on "+strupdateData);
        petAge.setText(strpetAge);
        petBreed.setText(strpetBreed);
        petColor.setText(strpetColor);
        petSize.setText(strpetSize);
        donarName.setText(strdonarName);
        donarContact.setText(strdonarContact);
        donarAdress.setText(strdonarAddress);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    @Override
    public void onImgeClick(int position) {
        Glide.with(getContext())
                .load(imagesData.get(position).getPetImageUrl())
                .into(fronImage);
    }
}