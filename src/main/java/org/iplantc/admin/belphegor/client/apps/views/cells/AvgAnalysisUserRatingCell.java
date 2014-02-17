package org.iplantc.admin.belphegor.client.apps.views.cells;

import org.iplantc.de.client.models.apps.AppFeedback;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class AvgAnalysisUserRatingCell extends AbstractCell<AppFeedback> {

    public AvgAnalysisUserRatingCell() {

    }

    @Override
    public void render(Cell.Context context, AppFeedback value, SafeHtmlBuilder sb) {
        if (value == null) {
            return;
        }

        // NumberFormat df = new NumberFormat("#.###");
        sb.append(SafeHtmlUtils.fromString(NumberFormat.getFormat("0.00").format(
                value.getAverageRating())));
        // sb.append(SafeHtmlUtils.fromString(Double.toString(value.getAverageRating())));
    }
}
