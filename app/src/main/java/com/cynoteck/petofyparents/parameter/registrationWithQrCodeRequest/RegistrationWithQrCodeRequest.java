package com.cynoteck.petofyparents.parameter.registrationWithQrCodeRequest;

public class RegistrationWithQrCodeRequest {

        private RegistrationWithQrCodeParams data;

        public RegistrationWithQrCodeParams getData ()
        {
            return data;
        }

        public void setData (RegistrationWithQrCodeParams data)
        {
            this.data = data;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [data = "+data+"]";
        }

}
