package com.fiap.blackoutservice.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService(name = "PowerOutageService", targetNamespace = "http://soap.blackoutservice.fiap.com/")
@SOAPBinding(style = Style.DOCUMENT)
public interface PowerOutageSOAPService {

    @WebMethod(operationName = "getPowerOutageStatus")
    @WebResult(name = "outageStatus")
    String getPowerOutageStatus(@WebParam(name = "locationCode") String locationCode);
    
    @WebMethod(operationName = "reportPowerOutage")
    @WebResult(name = "reportStatus")
    boolean reportPowerOutage(
            @WebParam(name = "locationCode") String locationCode,
            @WebParam(name = "description") String description,
            @WebParam(name = "affectedCustomers") int affectedCustomers);
    
    @WebMethod(operationName = "getEstimatedResolutionTime")
    @WebResult(name = "estimatedTime")
    String getEstimatedResolutionTime(@WebParam(name = "outageId") String outageId);
}
