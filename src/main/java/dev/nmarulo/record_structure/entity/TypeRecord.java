package dev.nmarulo.record_structure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "type_records")
public class TypeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "line_identifier", nullable = false)
    private String lineIdentifier;
    
    @Column(name = "lengths", nullable = false)
    private String lengths;
    
    @Column(name = "columns", nullable = false)
    private String columns;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "typeRecord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FieldTypeRecord> fieldTypeRecords = new HashSet<>();
    
    @Override
    public final boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null) {return false;}
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer()
                                                                                     .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
                                                                                              .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {return false;}
        TypeRecord that = (TypeRecord) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
    
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
                                                                       .getPersistentClass()
                                                                       .hashCode() : getClass().hashCode();
    }
    
}
