package webdziekanat.converters;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Jakub on 02.02.2015.
 */
@FacesConverter("PickListConverter")
public class PickListConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        PickList  p = (PickList) component;
        DualListModel dl = (DualListModel) p.getValue();
        return dl.getSource().get(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        PickList  p = (PickList) component;
        DualListModel dl = (DualListModel) p.getValue();
        return  String.valueOf(dl.getSource().indexOf(value));
    }
}
