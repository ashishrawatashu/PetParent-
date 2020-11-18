package com.cynoteck.petofyparents.parameter.paymentHistoryStaus;

public class PaymentHistoryRequest {
    private PaymentHistoryParms data;

    public PaymentHistoryParms getData ()
    {
        return data;
    }

    public void setData (PaymentHistoryParms data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
