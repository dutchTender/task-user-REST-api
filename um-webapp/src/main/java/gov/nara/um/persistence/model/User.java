package gov.nara.um.persistence.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import gov.nara.common.interfaces.ILongNameableDto;
import gov.nara.common.persistence.model.ILongNameableEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
//@Table(name = "user", schema = "oif_ods")
@Table(name = "user")
public class User implements ILongNameableEntity, ILongNameableDto {
    @Id
    @Column(name = "user_id")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "oif_ods.user_user_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;
    @Column(name = "user_name")
    private String name;
    private String user_type;

    @JoinTable(
            name = "user_business_unit",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "business_unit_id",
                    referencedColumnName = "id"
            )
    )
    @OneToMany
    private Set<BusinessUnit> businessUnits;
    public Set<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public BusinessUnit addBusinessUnit(BusinessUnit businessUnit){ businessUnits.add(businessUnit);return businessUnit; }
    public void removeBusinessUnit(BusinessUnit businessUnit){ businessUnits.remove(businessUnit); }
    public void setBusinessUnits(Set<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }

}
