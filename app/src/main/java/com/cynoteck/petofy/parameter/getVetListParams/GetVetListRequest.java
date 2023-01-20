package com.cynoteck.petofy.parameter.getVetListParams;

public class GetVetListRequest {

        private GetVetListParams data;

        public GetVetListParams getData ()
        {
            return data;
        }

        public void setData (GetVetListParams data)
        {
            this.data = data;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [data = "+data+"]";
        }

}
