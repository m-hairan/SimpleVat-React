package com.simplevat.converter;

import com.simplevat.entity.Project;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author hiren
 * @since 18 May, 2017 5:38:35 PM
 */
@FacesConverter("projectConverter")
public class ProjectConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Project project = new Project();
            project.setProjectId(Integer.parseInt(string));
            return project;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Project) {
            Project project = (Project) o;
            return Integer.toString(project.getProjectId());
        }
        return o.toString();
    }

}
