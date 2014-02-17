package org.iplantc.admin.belphegor.client.toolRequest.model;

import org.iplantc.de.client.models.toolRequest.ToolRequest;

import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;

import java.util.List;

public interface ToolRequestList {

    @PropertyName("tool_requests")
    List<ToolRequest> getToolRequests();

}
