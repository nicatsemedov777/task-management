package az.iktlab.taskmanagement.dao;

import az.iktlab.taskmanagement.dao.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "otp-sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OTPSession {

    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_used")
    @Builder.Default
    private Boolean isUsed = false;

    @CreationTimestamp
    private Date createDate;
}
