package com.simplevat.converter;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Project;
import com.simplevat.service.ProjectService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hiren
 * @since 18 May, 2017 5:38:35 PM
 */
@Service
public class ProjectConverter implements Converter {

    @Autowired
    private ProjectService projectService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            ProjectCriteria pc = new ProjectCriteria();
            pc.setProjectId(Integer.parseInt(string));
            Project project = new Project();
            try {
                project = projectService.getProjectsByCriteria(pc)
                        .stream()
                        .findFirst()
                        .orElse(new Project());
            } catch (Exception ex) {
                Logger.getLogger(ProjectConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
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
