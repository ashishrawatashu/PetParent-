package com.cynoteck.petofyparents.parameter.registerRequest;

public class Registerparams {
        private RegisterRequest data;

        public RegisterRequest getData()
        {
            return data;
        }

        public void setData(RegisterRequest data)
        {
            this.data = data;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [data = "+ data +"]";
        }
    }

