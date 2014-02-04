package org.iplantc.admin.belphegor.client.refGenome.model;

import java.util.Date;

import org.iplantc.core.uicommons.client.models.HasId;
import org.iplantc.de.diskResource.client.services.errors.HasPath;

import com.google.gwt.user.client.ui.HasName;
import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;

public interface ReferenceGenome extends HasId, HasName, HasPath {

    String NAME = "name";
    String PATH = "path";
    String DELETED = "deleted";
    String UUID = "uuid";

    @PropertyName("last_modified")
    Date getLastModifiedDate();

    @PropertyName("last_modified")
    void setLastModifiedDate(Date lastModifiedDate);

    @PropertyName("last_modified_by")
    String getLastModifiedBy();

    @PropertyName("last_modified_by")
    void setLastModifiedBy(String userName);

    @PropertyName("created_on")
    Date getCreatedDate();

    @PropertyName("created_on")
    void setCreatedDate(Date createdOn);

    @PropertyName("created_by")
    String getCreatedBy();

    @PropertyName("created_by")
    void setCreatedBy(String userName);

    boolean isDeleted();

    void setDeleted(boolean deleted);

    String getUuid();

    void setPath(String path);

}
