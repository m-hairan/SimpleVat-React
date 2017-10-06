package com.simplevat.web.common.controller;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import java.io.*;
import javax.faces.context.FacesContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Uday
 */
@Controller
@SessionScope
public class StreamedContentSessionController implements Serializable {

    public static final String STREAMED_CONTENT_PROFILE_PIC = "STREAMED_CONTENT_PROFILE_PIC";
    public static final String STREAMED_CONTENT_USER_PIC = "STREAMED_CONTENT_USER_PIC";
    public static final String STREAMED_CONTENT_COMPANY_LOGO = "STREAMED_CONTENT_COMPANY_LOGO";
    public StreamedContent StreamedProfilePic;

    public StreamedContent getStreamedProfilePic() {
        Object objProfilePic = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STREAMED_CONTENT_PROFILE_PIC);
        if (objProfilePic != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream((byte[]) objProfilePic);
            return new DefaultStreamedContent(inputStream);
        }
        return null;
    }
    
    public StreamedContent getStreamedUserPic() {
        Object objProfilePic = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STREAMED_CONTENT_USER_PIC);
        if (objProfilePic != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream((byte[]) objProfilePic);
            return new DefaultStreamedContent(inputStream);
        }
        return null;
    }
    
    public StreamedContent getStreamedComapanyLogo() {
        Object objProfilePic = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STREAMED_CONTENT_COMPANY_LOGO);
        if (objProfilePic != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream((byte[]) objProfilePic);
            return new DefaultStreamedContent(inputStream);
        }
        return null;
    }
}
