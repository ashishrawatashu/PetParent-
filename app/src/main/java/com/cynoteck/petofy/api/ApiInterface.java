package com.cynoteck.petofy.api;

import com.cynoteck.petofy.parameter.addParamRequest.AddPetRequset;
import com.cynoteck.petofy.parameter.addPetToVetRegisterUsingQRCodeRequest.AddPetToVetRegisterUsingQRRequest;
import com.cynoteck.petofy.parameter.adoptionListRequest.AdoptionListRequestModel;
import com.cynoteck.petofy.parameter.adoptionRequest.AdoptionRequest;
import com.cynoteck.petofy.parameter.appointmentParams.AppointmentsStatusRequest;
import com.cynoteck.petofy.parameter.appointmentParams.CreateAppointRequest;
import com.cynoteck.petofy.parameter.appointmentParams.UpdateAppointmentRequest;
import com.cynoteck.petofy.parameter.changePassRequest.ChangePassRequest;
import com.cynoteck.petofy.parameter.checkpetInVetRegister.InPetRegisterRequest;
import com.cynoteck.petofy.parameter.donateParamRequest.DonatePetRequest;
import com.cynoteck.petofy.parameter.forgetPassRequest.ForgetPassRequest;
import com.cynoteck.petofy.parameter.getOrderDetailsParms.GetOrderRequest;
import com.cynoteck.petofy.parameter.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofy.parameter.getPetParentAppointmentsParams.GetAppointmentRequest;
import com.cynoteck.petofy.parameter.getServiceProvidersListRequest.GetServiceProviderListRequest;
import com.cynoteck.petofy.parameter.getVetListParams.GetVetListRequest;
import com.cynoteck.petofy.parameter.getpetAgeRequest.GetPetAgeRequestData;
import com.cynoteck.petofy.parameter.immunizationRequest.ImmunizationRequest;
import com.cynoteck.petofy.parameter.loginParameter.Loginparams;
import com.cynoteck.petofy.parameter.newPetEntryParams.NewPetRequest;
import com.cynoteck.petofy.parameter.otpRequest.SendOtpRequest;
import com.cynoteck.petofy.parameter.paymentHistoryStaus.PaymentHistoryRequest;
import com.cynoteck.petofy.parameter.petBreedRequest.BreedParams;
import com.cynoteck.petofy.parameter.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofy.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofy.parameter.petReportsRequest.VisitTypeRequest;
import com.cynoteck.petofy.parameter.punchingPolicyPetDocumentsRequest.PunchingPolicyPetDocumentsRequest;
import com.cynoteck.petofy.parameter.punchingPolicyPetRequest.PunchingPolicyPetRequest;
import com.cynoteck.petofy.parameter.punchingPolicyRequest.PunchingPolicyRequest;
import com.cynoteck.petofy.parameter.registerRequest.Registerparams;
import com.cynoteck.petofy.parameter.registrationWithQrCodeRequest.RegistrationWithQrCodeRequest;
import com.cynoteck.petofy.parameter.saveFeedbackRequest.SaveFeedbackRequest;
import com.cynoteck.petofy.parameter.searchKeywordParams.SearchKeywordRequest;
import com.cynoteck.petofy.parameter.searchPetParentRequest.SearchPetParentRequestData;
import com.cynoteck.petofy.parameter.searchProviderRequest.SearchProviderRequest;
import com.cynoteck.petofy.parameter.serviceProviderDetailRequest.SearchProviderFullDetailRequest;
import com.cynoteck.petofy.parameter.updateDonation.UpdateDonationRequest;
import com.cynoteck.petofy.parameter.updatePArentDeatils.UpdateParentDetailsRequest;
import com.cynoteck.petofy.parameter.updateRequest.updateParamRequest.UpdatePetRequest;
import com.cynoteck.petofy.parameter.uploadVetProfileImageParams.UploadVetProfileImageData;
import com.cynoteck.petofy.response.InPetVeterian.InPetVeterianResponse;
import com.cynoteck.petofy.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofy.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofy.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofy.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofy.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofy.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofy.response.addPet.uniqueIdResponse.UniqueResponse;
import com.cynoteck.petofy.response.adoptionListResponse.AdoptionListResponse;
import com.cynoteck.petofy.response.adoptionResponse.AdoptionResponse;
import com.cynoteck.petofy.response.appointmentResponse.AppointmentDetailsResponse;
import com.cynoteck.petofy.response.appointmentResponse.CreateAppointmentResponse;
import com.cynoteck.petofy.response.appointmentResponse.GetAppointmentResponse;
import com.cynoteck.petofy.response.cityResponse.CityResponseModel;
import com.cynoteck.petofy.response.dateOfBirthResponse.DateOfBirthResponse;
import com.cynoteck.petofy.response.getAdoptionRequestListResponse.GetAdoptionRequestListResponse;
import com.cynoteck.petofy.response.getCityListWithStateResponse.GetCityListWithStateResponse;
import com.cynoteck.petofy.response.getDonationRequestResponse.GetDonationRequestResponse;
import com.cynoteck.petofy.response.getInsuranceMasterResponse.InsuranceMastersResponse;
import com.cynoteck.petofy.response.getPetAgeResponse.GetPetAgeresponseData;
import com.cynoteck.petofy.response.getPetHospitalizationResponse.getHospitalizationDeatilsResponse.GetHospitalizationDeatilsResponse;
import com.cynoteck.petofy.response.getPetNamesResponse.GetPetNamesResponse;
import com.cynoteck.petofy.response.getPetParrentnameReponse.GetPetParentResponseData;
import com.cynoteck.petofy.response.getPetReportsResponse.AddUpdateDeleteClinicVisitResponse;
import com.cynoteck.petofy.response.getProviderFeedbackResponse.GetProviderFeedbackResponse;
import com.cynoteck.petofy.response.getSaveFeedbackResponse.GetSaveFeedbackResponse;
import com.cynoteck.petofy.response.getSearchKeywordResponse.SearchKeywordResponse;
import com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse.SearchProviderFullDetailResponse;
import com.cynoteck.petofy.response.getXRayReports.getXRayReportDetailsResponse.GetXRayReportDeatilsResponse;
import com.cynoteck.petofy.response.getpetbreedsResponse.GetPetBreedsResponse;
import com.cynoteck.petofy.response.loginRegisterResponse.LoginWithEmailRegisterResponse;
import com.cynoteck.petofy.response.petAgeUnitResponse.PetAgeUnitResponseData;
import com.cynoteck.petofy.response.registerParentWithQRResponse.RegisterParentWithQRResponse;
import com.cynoteck.petofy.response.resendOTPResposne.ResendOTPResponse;
import com.cynoteck.petofy.response.stateResponse.StateResponse;
import com.cynoteck.petofy.response.donationResponse.DonationResponseList;
import com.cynoteck.petofy.response.forgetAndChangePassResponse.PasswordResponse;
import com.cynoteck.petofy.response.getAppointmentsStatusResponse.AppointmentStatusResponse;
import com.cynoteck.petofy.response.getImmunizationReport.PetImmunizationRecordResponse;
import com.cynoteck.petofy.response.getLabTestReportResponse.getPetLabWorkListResponse.PetLabWorkResponse;
import com.cynoteck.petofy.response.getOrderResponse.GetOrderResponse;
import com.cynoteck.petofy.response.getPetDetailsResponse.GetPetResponse;
import com.cynoteck.petofy.response.getPetHospitalizationResponse.getHospitalizationListResponse.GetPetHospitalizationResponse;
import com.cynoteck.petofy.response.getPetIdCardResponse.PetIdCardResponse;
import com.cynoteck.petofy.response.getPetParentResponse.GetPetParentResponse;
import com.cynoteck.petofy.response.getPetReportsResponse.GetReportsTypeResponse;
import com.cynoteck.petofy.response.getPetReportsResponse.getClinicVisitDetails.GetClinicVisitsDetailsResponse;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetClinicVisitsListsResponse.GetPetClinicVisitListResponse;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofy.response.getVetListResponse.GetVetListResponse;
import com.cynoteck.petofy.response.getXRayReports.getPetTestAndXRayResponse.GetPetTestAndXRayResponse;
import com.cynoteck.petofy.response.loginResponse.LoginRegisterResponse;
import com.cynoteck.petofy.response.newPetResponse.NewPetRegisterResponse;
import com.cynoteck.petofy.response.otpResponse.OtpResponse;
import com.cynoteck.petofy.response.paymentStatusResponse.PaymentStatusResponse;
import com.cynoteck.petofy.response.twofactAuthResponse.TwoFactAuthResponse;
import com.cynoteck.petofy.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofy.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofy.response.updatepetparentprofile.UpdatePetParentProfile;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {

    //TODO================LOGIN=============================

    @POST("User/Login")
    Call<LoginWithEmailRegisterResponse> loginApi(@Body Loginparams loginparams);

    //TODO=============SEND OTP TO USER======================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("common/SendOtp")
    Call<OtpResponse> senOtp(@Header("Authorization") String auth, @Body SendOtpRequest inPetRegisterRequest);

    //TODO=============Register API============================

    @POST("User/PetParentRegistration")
    Call<LoginRegisterResponse> registerApi(@Body Registerparams registerparams);

    //TODO============Forget Password API=======================

    @POST("user/forgotpassword")
    Call<PasswordResponse> getPasswordResponse(@Body ForgetPassRequest forgetPassRequest);

    //TODO===========Get Pet List API===========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetList")
    Call<GetPetListResponse> getPetList(@Header("Authorization") String auth, @Body PetDataRequest getPetDataRequest);

    //TODO===========Get Pet ID Card============================


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST(" pet/GetPetIdentityCard")
    Call<PetIdCardResponse> getPetIdCard(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest idCardRequest);

    //TODO==========Get Pet Response API========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetDetail")
    Call<GetPetResponse> getPetDetails(@Header("Authorization") String auth,
                                       @Body GetPetListRequest addPetRequset);

    //TODO==========Pet Type API===============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("common/GetPetTypes")
    Call<PetTypeResponse> petTypeApi();

    //TODO=========Get Pet Unique ID===========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pet/GeneratePetUniqueId")
    Call<UniqueResponse> getGeneratePetUniqueId(@Header("Authorization") String auth);

    //TODO========Get Breed API================================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetBreed")
    Call<BreedCatRespose> getGetPetBreedApi(@Body BreedParams breedParams);

    //TODO=========Get Pet Age API=============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetAge")
    Call<PetAgeValueResponse> getGetPetAgeApi(@Body BreedParams breedParams);

    //TODO=========Get Pet Color API===========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetColor")
    Call<PetColorValueResponse> getGetPetColorApi(@Body BreedParams breedParams);

    //TODO========Get Pet Size API=============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetSize")
    Call<PetSizeValueResponse> getGetPetSizeApi(@Body BreedParams breedParams);

    //TODO=======Upload Images API=============================

    @Multipart
    @POST("document/UploadDocument")
    Call<ImageResponse> uploadImages(@Header("Authorization") String auth, @Part MultipartBody.Part file);

    //TODO=========Update data API==============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/UpdatePetDetails")
    Call<AddPetValueResponse> updatePetDetails(@Header("Authorization") String auth,

                                               @Body UpdatePetRequest addPetRequset);

    //TODO=========Add New Pet API=============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/AddPet")
    Call<AddPetValueResponse> addNewPet(@Header("Authorization") String auth,
                                        @Body AddPetRequset addPetRequset);

    //TODO========Get Reprts Type API===========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("report/GetVisitTypes")
    Call<GetReportsTypeResponse> getReportsType(@Header("Authorization") String auth);

    //TODO========Get Hospitalization API========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetHospitalization")
    Call<GetPetHospitalizationResponse> getPetHospitalization(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    //TODO========Get Pet Lab Work API============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetLabWork")
    Call<PetLabWorkResponse> getPetLabWorkResponse(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    @GET
    Call<CityResponse> getCityApi(@Url String url);

    //TODO=======Get Clinic Visit List API=======================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetClinicVisits")
    Call<GetPetClinicVisitListResponse> getPetClinicVisits(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    //TODO======Get Pet Test Xray API============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetTestAndXRay")
    Call<GetPetTestAndXRayResponse> getPetTestAndXRay(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    //TODO=======Get Clinic Visit Details API====================


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetClinicVisit")
    Call<GetClinicVisitsDetailsResponse> getClinicVisitDetails(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);

    //CHECK IF A PET EXIST PET IN VET REGISTER........................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/CheckPetInVetRegister")
    Call<InPetVeterianResponse> checkPetInVetRegister(@Header("Authorization") String auth, @Body InPetRegisterRequest inPetRegisterRequest);

    //TODO=======Update Veterinarian Details=====================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/UpdateVeterinarian")
    Call<PasswordResponse> getPasswordResponse(@Header("Authorization") String auth, @Body ChangePassRequest changePassRequest);

    //TODO========ENABLE.DISABLE TWO FACTOR AUTHENTICATION=======

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/EnableTwoStepAuthentication")
    Call<TwoFactAuthResponse> enableDisableTowFactorAuth(@Header("Authorization") String auth,
                                                         @Body GetPetListRequest addPetRequset);

    //TODO========APPOINTMENTS===================================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/GetPetParentList")
    Call<GetPetParentResponse> getPetParentList(@Header("Authorization") String auth);

    //TODO=======GET  APPOINTMENTS BY APPOINTMENT ID=============

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/GetAppointmentById")
    Call<AppointmentDetailsResponse> getAppointmentsDetails(@Header("Authorization") String auth,
                                                            @Body GetPetListRequest addPetRequset);

    //TODO=======GET APPOINTMENTS================================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/getpetparentappointments")
    Call<GetAppointmentResponse> getAppointment(@Header("Authorization") String auth,@Body GetAppointmentRequest getAppointmentRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/GetVeterinarianList")
    Call<GetVetListResponse> getVetList(@Header("Authorization") String auth, @Body GetVetListRequest getVetListRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("services/GetServiceProvidersListByServiceAndCity")
    Call<GetVetListResponse> getServiceProvidersListByServiceAndCity(@Header("Authorization") String auth, @Body GetServiceProviderListRequest getServiceProviderListRequest);

    //TODO=======Get Veterinarian List============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/UpdateAppointment")
    Call<CreateAppointmentResponse> updateAppointment(@Header("Authorization") String auth, @Body UpdateAppointmentRequest updateAppointmentRequest);

    //TODO======CREATE APPOINTMENTS===============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/CreateAppointment")
    Call<CreateAppointmentResponse> createAppointment(@Header("Authorization") String auth, @Body CreateAppointRequest createAppointRequest);

    //TODO======ADD A PET TO VET REGISTER========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/AddPetToRegister")
    Call<NewPetRegisterResponse> addPetToRegister(@Header("Authorization") String auth, @Body NewPetRequest newPetRequest);

    //TODO========GET ORDER=====================================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("paymentgateway/GetOrder")
    Call<GetOrderResponse> getOrderDetails(@Header("Authorization") String auth, @Body GetOrderRequest getOrderRequest);

    //TODO========Payment History===============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("paymentgateway/PaymentHistory")
    Call<PaymentStatusResponse> getPaymentHistory(@Header("Authorization") String auth, @Body PaymentHistoryRequest paymentHistoryRequest);

    //TODO==========CancelAppointment===========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Appointment/CancelAppointment")
    Call<AppointmentStatusResponse> cancelAppointment(@Header("Authorization") String auth, @Body AppointmentsStatusRequest appointmentsStatusRequest);

    //TODO==========View Pet Vaccination Details=================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/ViewPetVaccination")
    Call<PetImmunizationRecordResponse> viewPetVaccination(@Header("Authorization") String auth, @Body ImmunizationRequest immunizationRequest);

    //TODO==========GET DONATION REQUEST LIST PENDING=====================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("social-service/get-donation-request-list/1")
    Call<DonationResponseList> viewPetVaccinationPending(@Header("Authorization") String auth);

    //TODO==========GET DONATION REQUEST LIST ACCEPTED=====================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("social-service/get-donation-request-list/2")
    Call<DonationResponseList> viewPetVaccinationAccepted(@Header("Authorization") String auth);

    //TODO==========GET DONATION REQUEST LIST REJECTED=====================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("social-service/get-donation-request-list/3")
    Call<DonationResponseList> viewPetVaccinationRejected(@Header("Authorization") String auth);

    //TODO==========GET STATE===============================================

    @GET("common/GetState")
    Call<StateResponse> getState();

    //TODO==========Get City================================================

    @GET("common/GetCity")
    Call<CityResponseModel> getCity();

    //TODO==========SEND PET DONATION REQUEST===============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/donate-a-pet")
    Call<JsonObject> donateAPet(@Header("Authorization") String auth, @Body DonatePetRequest donatePetRequest);

    //TODO==============UPDATE DONATION REQUEST=============================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/update-donation-request")
    Call<JsonObject> updateDonationRequest(@Header("Authorization") String auth, @Body UpdateDonationRequest updateDonationRequest);

    //TODO==============GET ADOPTION REQUEST LIST PENDING ===================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/get-adoption-request-list/1")
    Call<AdoptionResponse> getAdoptionRequestListPending(@Header("Authorization") String auth);

    //TODO==============GET ADOPTION REQUEST LIST ACCEPT===================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/get-adoption-request-list/2")
    Call<AdoptionResponse> getAdoptionRequestListAccept(@Header("Authorization") String auth);

    //TODO==============GET ADOPTION REQUEST LIST REJECT ===================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/get-adoption-request-list/3")
    Call<AdoptionResponse> getAdoptionRequestListReject(@Header("Authorization") String auth);

    //TODO==============GET LIST OF PETS READY FOR ADOPTION==================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/get-adoption-list")
    Call<AdoptionListResponse> getAdoptionList(@Header("Authorization") String auth, @Body AdoptionListRequestModel adoptionListRequestModel);

    //TODO=============SEND ADOPTION REQUEST==================================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/send-adoption-request")
    Call<JsonObject> sendAdoptionRequest(@Header("Authorization") String auth, @Body AdoptionRequest adoptionRequest);

    //GET DATE OF BIRTH........................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pet/GetPetDateOfBirth/{data}")
    Call<DateOfBirthResponse> GetPetDateOfBirth(@Path("data") String data);

    //GET PET AGE STRING......................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetAgeString")
    Call<GetPetAgeresponseData> getPetAgeString(@Body GetPetAgeRequestData getPetAgeRequestData);

    //GET PET AGE UNIT LIST..................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetAgeUnit")
    Call<PetAgeUnitResponseData> getPetAgeUnit();

    //SEARCH PET PARENT.......................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/SearchPetParent")
    Call<GetPetParentResponseData> searchPetParent(@Header("Authorization") String auth, @Body SearchPetParentRequestData getPetAgeRequestData);


    @POST("pet/PetRegistrationUsingQRCode")
    Call<RegisterParentWithQRResponse> petParentRegistrationUsingQRCode(@Header("Authorization") String auth,@Body RegistrationWithQrCodeRequest registrationWithQrCodeRequest);


    @POST("pet/AddPetToVetRegisterUsingQRCode")
    Call<RegisterParentWithQRResponse> addPetToVetRegisterUsingQRCode(@Header("Authorization") String auth,@Body AddPetToVetRegisterUsingQRRequest addPetToVetRegisterUsingQRRequest);

    @POST("user/SendRegistrationOtp")
    Call<JsonObject> sendRegistrationOtp(@Body JsonObject sendRegistrationOtp);


    @POST("user/ValidatepetParentOtp")
    Call<LoginRegisterResponse> validatepetParentOtp(@Body JsonObject sendRegistrationOtp);

    @POST("user/SendRegistrationOtp")
    Call<ResendOTPResponse> resendOTP(@Body JsonObject resendotp);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetTestXRay")
    Call<GetXRayReportDeatilsResponse> getTestXRayDetails(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/DeleteTestXRay")
    Call<AddUpdateDeleteClinicVisitResponse> deleteTestXRay(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetPetHospitalization")
    Call<GetHospitalizationDeatilsResponse> getPetHospitalizationDetails(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/DeleteClinicVisit")
    Call<AddUpdateDeleteClinicVisitResponse> deleteClinicVisit(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/DeletePetHospitalization")
    Call<AddUpdateDeleteClinicVisitResponse> deletePetHospitalization(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("User/UpdatePetParentProfile")
    Call<UpdatePetParentProfile> updatePetParent(@Header("Authorization") String auth, @Body UpdateParentDetailsRequest updateParentDeatilsParams);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/UpdateProfileImage")
    Call<JsonObject> updateProfileImage(@Header("Authorization") String auth, @Body UploadVetProfileImageData uploadVetProfileImageData);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("search/GetCityListWithState")
    Call<GetCityListWithStateResponse> getCityListWithState(@Header("Authorization") String auth);


    @GET
    Call<GetPetBreedsResponse> getPetBreedsList(@Url String url);

    @GET
    Call<GetPetNamesResponse> getNamesList(@Url String url);

    // Adoption Donation API
    @GET
    Call<GetAdoptionRequestListResponse> getAdoptionRequest(@Header("Authorization") String auth, @Url String url);

    @GET
    Call<JsonObject> deleteAdoptionRequest(@Header("Authorization") String auth, @Url String url);

    @GET
    Call<GetDonationRequestResponse> getDonationRequest(@Header("Authorization") String auth, @Url String url);

    @GET
    Call<JsonObject> deleteDonationRequest(@Header("Authorization") String auth, @Url String url);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("social-service/donate-a-pet-by-petId")
    Call<JsonObject> donatePetById(@Header("Authorization") String auth, @Body JsonObject jsonObject);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("search/SearchKeyWords")
    Call<SearchKeywordResponse> searchKeyWords(@Header("Authorization") String auth, @Body SearchKeywordRequest searchKeywordRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("search/GetSearchResults")
    Call<GetVetListResponse> getSearchProviderResults(@Header("Authorization") String auth, @Body SearchProviderRequest searchProviderRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("search/GetServiceProviderDetails")
    Call<SearchProviderFullDetailResponse> getProviderDetail(@Header("Authorization") String auth, @Body SearchProviderFullDetailRequest searchProviderFullDetailRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/provider-feedback")
    Call<GetSaveFeedbackResponse> giveFeedbackToProvider(@Header("Authorization") String auth, @Body SaveFeedbackRequest saveFeedbackRequest);

    @POST
    Call<GetProviderFeedbackResponse> getProviderFeedback(@Header("Authorization") String auth, @Url String url);


//  Insurance API
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("insurance-masters")
    Call<InsuranceMastersResponse> insuranceMasters();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("punching-policy")
    Call<JsonObject> punchingPolicy(@Header("Authorization") String auth,@Body PunchingPolicyRequest punchingPolicyRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("punching-policy-pet")
    Call<JsonObject> punchingPolicyPet(@Header("Authorization") String auth, @Body PunchingPolicyPetRequest punchingPolicyRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("punching-policy-pet-documents")
    Call<JsonObject> punchingPolicyDocumentsUpload(@Header("Authorization") String auth, @Body PunchingPolicyPetDocumentsRequest punchingPolicyPetDocumentsRequest);




}


