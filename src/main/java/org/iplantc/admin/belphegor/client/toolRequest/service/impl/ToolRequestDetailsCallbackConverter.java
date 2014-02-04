package org.iplantc.admin.belphegor.client.toolRequest.service.impl;

import org.iplantc.admin.belphegor.client.toolRequest.model.ToolRequestAdminAutoBeanFactory;
import org.iplantc.de.apps.client.models.toolrequest.ToolRequestDetails;
import org.iplantc.de.commons.client.services.AsyncCallbackConverter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class ToolRequestDetailsCallbackConverter extends AsyncCallbackConverter<String, ToolRequestDetails> {

    private final ToolRequestAdminAutoBeanFactory factory;

    public ToolRequestDetailsCallbackConverter(AsyncCallback<ToolRequestDetails> callback, ToolRequestAdminAutoBeanFactory factory) {
        super(callback);
        this.factory = factory;
    }

    @Override
    protected ToolRequestDetails convertFrom(String object) {
        final AutoBean<ToolRequestDetails> decode = AutoBeanCodex.decode(factory, ToolRequestDetails.class, object);
        return decode.as();
    }

}
