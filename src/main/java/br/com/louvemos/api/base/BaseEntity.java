package br.com.louvemos.api.base;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@MappedSuperclass
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public abstract class BaseEntity implements Serializable {

    @Column(name = "created", nullable = false, columnDefinition = "timestamp with time zone")
    private ZonedDateTime created;

    @Column(name = "updated", nullable = false, columnDefinition = "timestamp with time zone")
    private ZonedDateTime updated;

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    /**
     * Sets updated to now. If created is null, set to now.
     */
    public void setUpTimestamps() {
        if (this.getCreated() == null) {
            this.setCreated(ZonedDateTime.now());
        }
        this.setUpdated(ZonedDateTime.now());
    }
}
