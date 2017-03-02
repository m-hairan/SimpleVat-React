package org.simplevat.entity.domain;

import java.io.Serializable;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import java.util.Calendar;
import java.util.TimeZone;
import static java.util.UUID.randomUUID;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Hiren
 */
@Getter
@Setter
public abstract class AbstractDomain extends Object implements Serializable {

    private static final long serialVersionUID = 4773139730329730019L;

    @Nonnull
    private String uuid;

    @Nonnull
    private Calendar createdDate;

    @Nonnull
    private Calendar updatedDate;

    protected AbstractDomain() {
        uuid = format("%s-%s", currentTimeMillis(), randomUUID());
        createdDate = updatedDate = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    }

}
