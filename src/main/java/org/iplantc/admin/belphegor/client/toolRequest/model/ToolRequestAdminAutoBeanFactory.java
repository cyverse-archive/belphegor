package org.iplantc.admin.belphegor.client.toolRequest.model;

import org.iplantc.de.client.models.toolRequest.ToolRequestAutoBeanFactory;

import com.google.web.bindery.autobean.shared.AutoBean;

public interface ToolRequestAdminAutoBeanFactory extends ToolRequestAutoBeanFactory {

    AutoBean<ToolRequestList> toolRequestList();

}
