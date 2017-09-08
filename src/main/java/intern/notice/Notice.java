package intern.notice;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notices")
@Data
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    private Boolean unRead;

    public Notice(String userId, String message) {
        this.userId = userId;
        this.message = message;
        this.createdAt = new Date();
        this.unRead = true;
    };
}
