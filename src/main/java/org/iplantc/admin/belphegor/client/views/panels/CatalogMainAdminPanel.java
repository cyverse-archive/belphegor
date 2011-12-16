package org.iplantc.admin.belphegor.client.views.panels;

import org.iplantc.admin.belphegor.client.I18N;
import org.iplantc.admin.belphegor.client.images.Resources;
import org.iplantc.admin.belphegor.client.services.AppTemplateAdminServiceFacade;
import org.iplantc.core.client.widgets.Hyperlink;
import org.iplantc.core.jsonutil.JsonUtil;
import org.iplantc.core.uiapplications.client.models.Analysis;
import org.iplantc.core.uiapplications.client.views.panels.BaseCatalogMainPanel;
import org.iplantc.core.uicommons.client.ErrorHandler;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.dnd.GridDragSource;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * A panel that displays apps in a grid and lets the user delete or modify them. TODO: disable delete
 * button when nothing selected
 */
public class CatalogMainAdminPanel extends BaseCatalogMainPanel {
    private final AppTemplateAdminServiceFacade service;
    private Button deleteButton;

    /**
     * Creates a new CatalogMainAdminPanel.
     * 
     * @param templateService
     */
    public CatalogMainAdminPanel() {
        service = new AppTemplateAdminServiceFacade();
        initToolBar();

        new CatalogMainAdminPanelDragSource(analysisGrid);
    }

    private void initToolBar() {
        deleteButton = buildDeleteButton();
        addToToolBar(deleteButton);
    }

    private Button buildDeleteButton() {
        deleteButton = new Button(I18N.DISPLAY.delete());

        deleteButton.disable();
        deleteButton.setId("idDelete"); //$NON-NLS-1$
        deleteButton.setIcon(AbstractImagePrototype.create(Resources.ICONS.delete()));
        deleteButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                deleteSelectedApp();
            }
        });

        addGridSelectionChangeListener(new Listener<BaseEvent>() {
            @Override
            public void handleEvent(BaseEvent be) {
                deleteButton.setEnabled(getSelectedApp() != null);
            }
        });

        return deleteButton;
    }

    private void deleteSelectedApp() {
        Listener<MessageBoxEvent> callback = new Listener<MessageBoxEvent>() {
            @Override
            public void handleEvent(MessageBoxEvent ce) {
                Button btn = ce.getButtonClicked();

                // did the user click yes?
                if (btn.getItemId().equals(Dialog.YES)) {
                    confirmDeleteSelectedApp();
                }
            }
        };

        String appName = getSelectedApp().getName();
        MessageBox.confirm(I18N.DISPLAY.confirmDeleteAppTitle(), I18N.DISPLAY.confirmDeleteApp(appName),
                callback);
    }

    private void confirmDeleteSelectedApp() {
        String appId = getSelectedApp().getId();
        service.deleteApplication(appId, new AsyncCallback<String>() {

            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onFailure(Throwable caught) {
                ErrorHandler.post(I18N.DISPLAY.cantDeleteApp(), caught);
            }
        });
    }

    /**
     * Overridden to render app names as hyperlinks to edit the app, and display average user rating
     */
    @Override
    protected ColumnModel buildColumnModel() {
        ColumnModel model = super.buildColumnModel();
        ColumnConfig cc = model.getColumnById(Analysis.RATING);
        cc.setHeader("Average User Rating");
        cc.setAlignment(HorizontalAlignment.CENTER);
        cc.setRenderer(new VotingCellRenderer());
        model.getColumnById(Analysis.NAME).setRenderer(new AppNameCellRenderer());
        return model;
    }

    /**
     * Displays app names as hyperlinks; clicking a link edit the app.
     */
    public class AppNameCellRenderer implements GridCellRenderer<Analysis> {

        @Override
        public Object render(final Analysis model, String property, ColumnData config, int rowIndex,
                int colIndex, ListStore<Analysis> store, Grid<Analysis> grid) {
            Hyperlink link = new Hyperlink(model.getName(), "analysis_name"); //$NON-NLS-1$
            link.addListener(Events.OnClick, new AppNameClickHandler(model));
            link.setWidth(model.getName().length());
            return link;
        }
    }

    /**
     * 
     * Show average user rating
     * 
     * @author sriram
     * 
     */
    private class VotingCellRenderer implements GridCellRenderer<Analysis> {
        @Override
        public Object render(final Analysis model, String property, ColumnData config, int rowIndex,
                int colIndex, ListStore<Analysis> store, Grid<Analysis> grid) {
            return NumberFormat.getFormat("0.00").format(model.getFeedback().getAverage_score());
        }

    }

    private class EditCompleteCallback implements AsyncCallback<String> {

        Dialog dialog;

        public EditCompleteCallback(Dialog d) {
            dialog = d;
        }

        @Override
        public void onFailure(Throwable caught) {
            dialog.hide();
            if (caught != null) {
                ErrorHandler.post(caught);
            }
        }

        @Override
        public void onSuccess(String result) {
            dialog.hide();
            updateApp(result);
        }

    }

    private void updateApp(String result) {
        JSONObject obj = JSONParser.parseStrict(result).isObject();
        if (obj != null) {
            JSONObject json_app = obj.get("application").isObject();
            Analysis a = analysisGrid.getStore().findModel(Analysis.ID,
                    JsonUtil.getString(json_app, Analysis.ID));
            if (a != null) {
                a.setName(JsonUtil.getString(json_app, Analysis.NAME));
                a.setIntegratorEmail(JsonUtil.getString(json_app, Analysis.INTEGRATOR_EMAIL));
                a.setIntegratorName(JsonUtil.getString(json_app, Analysis.INTEGRATOR_NAME));
                a.setWikiUrl(JsonUtil.getString(json_app, Analysis.WIKI_URL));
                a.setDescription(JsonUtil.getString(json_app, Analysis.DESCRIPTION));
                analysisGrid.getStore().update(a);
            }
        }

    }

    private final class AppNameClickHandler implements Listener<BaseEvent> {
        private final Analysis model;

        private AppNameClickHandler(Analysis model) {
            this.model = model;
        }

        @Override
        public void handleEvent(BaseEvent be) {
            Dialog d = new Dialog();
            EditAppDetailsPanel editPanel = new EditAppDetailsPanel(model, new EditCompleteCallback(d));
            d.setHeading(model.getName());
            d.getButtonBar().removeAll();
            d.setSize(595, 380);
            d.add(editPanel);
            d.show();
        }
    }

    /**
     * GridDragSource for re-categorizing Apps.
     * 
     * @author psarando
     * 
     */
    private class CatalogMainAdminPanelDragSource extends GridDragSource {
        public CatalogMainAdminPanelDragSource(Grid<Analysis> grid) {
            super(grid);
        }

        @Override
        public void onDragStart(DNDEvent event) {
            // Check if a valid row is selected.
            Element dragStartElement = (Element)event.getDragEvent().getStartElement();
            Element targetRow = analysisGrid.getView().findRow(dragStartElement).cast();
            if (targetRow == null) {
                event.setCancelled(true);
                return;
            }

            // Set the drag source in the event
            Analysis source = analysisGrid.getSelectionModel().getSelectedItem();

            if (source != null) {
                event.setData(source);
                event.getStatus().update(source.getName());
            } else {
                event.setCancelled(true);
                event.getStatus().setStatus(false);
            }
        }

        @Override
        public void onDragDrop(DNDEvent e) {
            // do nothing intentionally
        }
    }
}
