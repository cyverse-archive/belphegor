package org.iplantc.admin.belphegor.client.refGenome;

import org.iplantc.admin.belphegor.client.refGenome.model.ReferenceGenome;
import org.iplantc.de.client.models.IsMaskable;

import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;

public interface RefGenomeView extends IsWidget, IsMaskable {

    public interface Presenter {

        void go(HasOneWidget container);

        void addReferenceGenome(ReferenceGenome referenceGenome);

        void editReferenceGenome(ReferenceGenome referenceGenome);

    }

    void setReferenceGenomes(List<ReferenceGenome> refGenomes);

    void addReferenceGenome(ReferenceGenome referenceGenome);

    void updateReferenceGenome(ReferenceGenome referenceGenome);

    void setPresenter(RefGenomeView.Presenter presenter);

    void editReferenceGenome(ReferenceGenome refGenome);

}
