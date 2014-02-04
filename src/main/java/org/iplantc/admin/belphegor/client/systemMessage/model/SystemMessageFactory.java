package org.iplantc.admin.belphegor.client.systemMessage.model;

import org.iplantc.de.commons.client.models.sysmsgs.MessageFactory;

import com.google.web.bindery.autobean.shared.AutoBean;

public interface SystemMessageFactory extends MessageFactory {

    AutoBean<SystemMessageList> systemMessageList();

    AutoBean<SystemMessageTypesList> systemMessageTypesList();

    AutoBean<SystemMessage> systemMessage();

}
