package coop.intergal.ui.security.data.entity;



import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	@Id
	@GeneratedValue
//	@GridColumn(order = 1)
	private Long id;

	@Version
	private int version;

	public Long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, version);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AbstractEntity that = (AbstractEntity) o;
		return version == that.version &&
				Objects.equals(id, that.id);
	}
}
