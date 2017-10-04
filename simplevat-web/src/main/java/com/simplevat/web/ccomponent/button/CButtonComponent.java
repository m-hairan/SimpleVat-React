package com.simplevat.web.ccomponent.button;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import com.simplevat.web.filter.Breadcrumb;

@FacesComponent("com.simplevat.web.ccomposite.cButtonComponent")
public class CButtonComponent extends UIInput implements NamingContainer {
	
	private String title;
	private String outcome;
	private String icon;
	
	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		String type = getAttributeValue("type", "home");
		String outcome = getAttributeValue("outcome", null);
		if (type.equalsIgnoreCase("home")) {
			setTitle("Home");
			setOutcome("/pages/secure/home");
			setIcon("ui-icon-home");
		} else if (type.equalsIgnoreCase("back")) {
			setTitle("Back");
			Breadcrumb breadcrumb = (Breadcrumb) context.getExternalContext().getSessionMap().get("breadcrumb");
			if(breadcrumb != null){
				setOutcome(breadcrumb.getPreviousUri());
			}
		} else if (type.equalsIgnoreCase("cancel")) {
			setTitle("Cancel");
			if(outcome == null){
                            Breadcrumb breadcrumb = (Breadcrumb) context.getExternalContext().getSessionMap().get("breadcrumb");
                            if(breadcrumb != null){
                                setOutcome(breadcrumb.getPreviousUri());
                            }
			}else{
                            setOutcome(outcome);
			}
			setIcon("ui-icon-cancel");
		}
		super.encodeBegin(context);
	}

	/**
	 * Return specified attribute value or otherwise the specified default if
	 * it's null.
	 */
	@SuppressWarnings("unchecked")
	private <T> T getAttributeValue(String key, T defaultValue) {
		T value = (T) getAttributes().get(key);
		return (value != null) ? value : defaultValue;
	}

	public String getTitle() {
		return (String) getStateHelper().get("title");
	}

	public void setTitle(String title) {
		getStateHelper().put("title", title);
	}

	public String getOutcome() {
		return (String) getStateHelper().get("outcome");
	}

	public void setOutcome(String outcome) {
		getStateHelper().put("outcome", outcome);
	}
	
	public String getIcon() {
		return (String) getStateHelper().get("icon");
	}

	public void setIcon(String icon) {
		getStateHelper().put("icon", icon);
	}

}
