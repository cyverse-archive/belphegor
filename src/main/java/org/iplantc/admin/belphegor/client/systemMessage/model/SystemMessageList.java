package org.iplantc.admin.belphegor.client.systemMessage.model;

import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;

import java.util.List;

public interface SystemMessageList {

    @PropertyName("system-messages")
    List<SystemMessage> getSystemMessages();

}
