package com.cynoteck.petofyparents.parameter.paymentHistoryStaus;

public class PaymentHistoryParms {
    private String amount;

    private String orderId;

    private String appointmentId;

    private String paymentId;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getOrderId ()
    {
        return orderId;
    }

    public void setOrderId (String orderId)
    {
        this.orderId = orderId;
    }

    public String getAppointmentId ()
    {
        return appointmentId;
    }

    public void setAppointmentId (String appointmentId)
    {
        this.appointmentId = appointmentId;
    }

    public String getPaymentId ()
    {
        return paymentId;
    }

    public void setPaymentId (String paymentId)
    {
        this.paymentId = paymentId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", orderId = "+orderId+", appointmentId = "+appointmentId+", paymentId = "+paymentId+"]";
    }
}
