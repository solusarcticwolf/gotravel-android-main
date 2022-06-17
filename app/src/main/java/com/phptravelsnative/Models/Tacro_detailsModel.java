package com.phptravelsnative.Models;
import java.util.ArrayList;

public class Tacro_detailsModel {
    Response ResponseObject;
    Error ErrorObject;




    public Response getResponse() {
        return ResponseObject;
    }

    public Error getError() {
        return ErrorObject;
    }

    // Setter Methods

    public void setResponse(Response responseObject) {
        this.ResponseObject = responseObject;
    }

    public void setError(Error errorObject) {
        this.ErrorObject = errorObject;
    }



}
class Error {
    private boolean status;
    private String msg;


    // Getter Methods

    public boolean getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
 class Response {
    ArrayList< Object > Segments = new ArrayList < Object > ();
    ArrayList < Object > Passengers = new ArrayList < Object > ();
    ArrayList < Object > SpecialServices = new ArrayList < Object > ();
    FareInfo FareInfoObject;
    ArrayList < Object > OptionalSpecialServices = new ArrayList < Object > ();
    ArrayList < Object > SeatMaps = new ArrayList < Object > ();
    ResponseInfo ResponseInfoObject;
    private String Extensions = null;


    // Getter Methods

    public FareInfo getFareInfo() {
        return FareInfoObject;
    }

    public ResponseInfo getResponseInfo() {
        return ResponseInfoObject;
    }

    public String getExtensions() {
        return Extensions;
    }

    // Setter Methods

    public void setFareInfo(FareInfo FareInfoObject) {
        this.FareInfoObject = FareInfoObject;
    }

    public void setResponseInfo(ResponseInfo ResponseInfoObject) {
        this.ResponseInfoObject = ResponseInfoObject;
    }

    public void setExtensions(String Extensions) {
        this.Extensions = Extensions;
    }
}
 class ResponseInfo {
    private String EchoToken = null;
    private String Error = null;
    private String Warnings = null;
    private float ProcessingMs;
    private String Extensions = null;


    // Getter Methods

    public String getEchoToken() {
        return EchoToken;
    }

    public String getError() {
        return Error;
    }

    public String getWarnings() {
        return Warnings;
    }

    public float getProcessingMs() {
        return ProcessingMs;
    }

    public String getExtensions() {
        return Extensions;
    }

    // Setter Methods

    public void setEchoToken(String EchoToken) {
        this.EchoToken = EchoToken;
    }

    public void setError(String Error) {
        this.Error = Error;
    }

    public void setWarnings(String Warnings) {
        this.Warnings = Warnings;
    }

    public void setProcessingMs(float ProcessingMs) {
        this.ProcessingMs = ProcessingMs;
    }

    public void setExtensions(String Extensions) {
        this.Extensions = Extensions;
    }
}
 class FareInfo {
    ArrayList < Object > EMDTicketFareOptions = new ArrayList < Object > ();
    ArrayList < Object > EMDTicketFares = new ArrayList < Object > ();
    SaleCurrencyAmountTotal SaleCurrencyAmountTotalObject;
    ArrayList < Object > Itineraries = new ArrayList < Object > ();
    ArrayList < Object > FareRules = new ArrayList < Object > ();
    private String SaleCurrencyCode;
    ArrayList < Object > ETTicketFares = new ArrayList < Object > ();
    private String Extensions = null;


    // Getter Methods

    public SaleCurrencyAmountTotal getSaleCurrencyAmountTotal() {
        return SaleCurrencyAmountTotalObject;
    }

    public String getSaleCurrencyCode() {
        return SaleCurrencyCode;
    }

    public String getExtensions() {
        return Extensions;
    }

    // Setter Methods

    public void setSaleCurrencyAmountTotal(SaleCurrencyAmountTotal SaleCurrencyAmountTotalObject) {
        this.SaleCurrencyAmountTotalObject = SaleCurrencyAmountTotalObject;
    }

    public void setSaleCurrencyCode(String SaleCurrencyCode) {
        this.SaleCurrencyCode = SaleCurrencyCode;
    }

    public void setExtensions(String Extensions) {
        this.Extensions = Extensions;
    }
}
 class SaleCurrencyAmountTotal {
    private float DiscountAmount;
    private float BaseAmount;
    private float TaxAmount;
    private float TotalAmount;
    private float MilesAmount;
    private String Extensions = null;


    // Getter Methods

    public float getDiscountAmount() {
        return DiscountAmount;
    }

    public float getBaseAmount() {
        return BaseAmount;
    }

    public float getTaxAmount() {
        return TaxAmount;
    }

    public float getTotalAmount() {
        return TotalAmount;
    }

    public float getMilesAmount() {
        return MilesAmount;
    }

    public String getExtensions() {
        return Extensions;
    }

    // Setter Methods

    public void setDiscountAmount(float DiscountAmount) {
        this.DiscountAmount = DiscountAmount;
    }

    public void setBaseAmount(float BaseAmount) {
        this.BaseAmount = BaseAmount;
    }

    public void setTaxAmount(float TaxAmount) {
        this.TaxAmount = TaxAmount;
    }

    public void setTotalAmount(float TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public void setMilesAmount(float MilesAmount) {
        this.MilesAmount = MilesAmount;
    }

    public void setExtensions(String Extensions) {
        this.Extensions = Extensions;
    }
}