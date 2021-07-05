package com.cynoteck.petofy.response.paymentStatusResponse;

public class PaymentStausData {
    private String transactionType;

    private String amount;

    private String paymentId;

    private String id;

    private String transactionCode;

    private String razorPayOrderId;

    private String paymentDate;

    private String userId;

    private String transactionId;

    private String paymentStatus;

    public String getTransactionType ()
    {
        return transactionType;
    }

    public void setTransactionType (String transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getPaymentId ()
    {
        return paymentId;
    }

    public void setPaymentId (String paymentId)
    {
        this.paymentId = paymentId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTransactionCode ()
    {
        return transactionCode;
    }

    public void setTransactionCode (String transactionCode)
    {
        this.transactionCode = transactionCode;
    }

    public String getRazorPayOrderId ()
    {
        return razorPayOrderId;
    }

    public void setRazorPayOrderId (String razorPayOrderId)
    {
        this.razorPayOrderId = razorPayOrderId;
    }

    public String getPaymentDate ()
    {
        return paymentDate;
    }

    public void setPaymentDate (String paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getTransactionId ()
    {
        return transactionId;
    }

    public void setTransactionId (String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getPaymentStatus ()
    {
        return paymentStatus;
    }

    public void setPaymentStatus (String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [transactionType = "+transactionType+", amount = "+amount+", paymentId = "+paymentId+", id = "+id+", transactionCode = "+transactionCode+", razorPayOrderId = "+razorPayOrderId+", paymentDate = "+paymentDate+", userId = "+userId+", transactionId = "+transactionId+", paymentStatus = "+paymentStatus+"]";
    }
}
