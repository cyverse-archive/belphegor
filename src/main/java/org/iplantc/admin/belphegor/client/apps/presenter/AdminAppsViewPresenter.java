package org.iplantc.admin.belphegor.client.apps.presenter;

import org.iplantc.de.apps.client.models.autobeans.App;
import org.iplantc.de.apps.client.models.autobeans.AppGroup;
import org.iplantc.de.apps.client.views.AppsView.Presenter;

public interface AdminAppsViewPresenter extends Presenter {

    boolean canMoveAppGroup(AppGroup parentGroup, AppGroup childGroup);

    boolean canMoveApp(final AppGroup parentGroup, final App app);

    void moveAppGroup(final AppGroup parentGroup, final AppGroup childGroup);

    void moveApp(final AppGroup parentGroup, final App app);
}
