package com.cynoteck.petofyparents.response.addPet.addPetResponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;
public class AddPetValueResponse {
  private Header header;

  private AddPetModel data;

  private Response response;

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public AddPetModel getData() {
    return data;
  }

  public void setData(AddPetModel data) {
    this.data = data;
  }

  public Response getResponse() {
    return response;
  }

  public void setResponse(Response response) {
    this.response = response;
  }

  @Override
  public String toString()
  {
    return "ClassPojo [data = "+ data +", response = "+ response +", header = "+header+"]";
  }
}