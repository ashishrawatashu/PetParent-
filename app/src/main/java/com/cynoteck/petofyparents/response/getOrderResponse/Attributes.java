package com.cynoteck.petofyparents.response.getOrderResponse;

public class Attributes
{
    private String amount;

    private String amount_paid;

    private String[] notes;

    private String created_at;

    private String amount_due;

    private String currency;

    private String receipt;

    private String id;

    private String entity;

    private String offer_id;

    private String status;

    private String attempts;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getAmount_paid ()
    {
        return amount_paid;
    }

    public void setAmount_paid (String amount_paid)
    {
        this.amount_paid = amount_paid;
    }

    public String[] getNotes ()
    {
        return notes;
    }

    public void setNotes (String[] notes)
    {
        this.notes = notes;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getAmount_due ()
    {
        return amount_due;
    }

    public void setAmount_due (String amount_due)
    {
        this.amount_due = amount_due;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    public String getReceipt ()
    {
        return receipt;
    }

    public void setReceipt (String receipt)
    {
        this.receipt = receipt;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getEntity ()
    {
        return entity;
    }

    public void setEntity (String entity)
    {
        this.entity = entity;
    }

    public String getOffer_id ()
{
    return offer_id;
}

    public void setOffer_id (String offer_id)
    {
        this.offer_id = offer_id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getAttempts ()
    {
        return attempts;
    }

    public void setAttempts (String attempts)
    {
        this.attempts = attempts;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", amount_paid = "+amount_paid+", notes = "+notes+", created_at = "+created_at+", amount_due = "+amount_due+", currency = "+currency+", receipt = "+receipt+", id = "+id+", entity = "+entity+", offer_id = "+offer_id+", status = "+status+", attempts = "+attempts+"]";
    }
}
