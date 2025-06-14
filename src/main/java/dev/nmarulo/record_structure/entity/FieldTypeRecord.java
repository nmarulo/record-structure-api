package dev.nmarulo.record_structure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "field_type_records")
public class FieldTypeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "column_name", nullable = false)
    private String columnName;
    
    @Column(name = "order_number", nullable = false)
    private Integer order;
    
    @Column(name = "length", nullable = false)
    private Integer length;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private FieldType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private FieldFormat format;
    
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_record_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_field_type_record_type_record"))
    private TypeRecord typeRecord;
    
    @Override
    public final boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null) {return false;}
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer()
                                                                                     .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
                                                                                              .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {return false;}
        FieldTypeRecord that = (FieldTypeRecord) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }
    
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
                                                                       .getPersistentClass()
                                                                       .hashCode() : getClass().hashCode();
    }
    
    public enum FieldType {
        STRING,
        INTEGER,
        DECIMAL,
        DATE
    }
    
    @Getter
    public enum FieldFormat {
        DDMMYYYY("ddMMyyyy"),
        HHMMSS("HHmmss"),
        FIXED_POINT_NUMBERS("0.00"),
        DEFAULT("");
        
        private final String format;
        
        FieldFormat(String format) {
            this.format = format;
        }
        
    }
    
}
