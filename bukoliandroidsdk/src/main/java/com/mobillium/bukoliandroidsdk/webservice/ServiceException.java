
package com.mobillium.bukoliandroidsdk.webservice;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;
    boolean showServiceMessage = false;
    private ServiceError serviceError;

    public ServiceException() {
        super();
    }

    public ServiceException(String detailMessage, boolean showServiceMessage) {
        super(detailMessage);
        this.showServiceMessage = showServiceMessage;
    }

    public ServiceException(String detailMessage, boolean showServiceMessage, ServiceError error) {
        super(detailMessage);
        this.showServiceMessage = showServiceMessage;
        serviceError = error;
    }

    public boolean isShowServiceMessage() {
        return showServiceMessage;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }

    public void setServiceError(ServiceError serviceError) {
        this.serviceError = serviceError;
    }
}
