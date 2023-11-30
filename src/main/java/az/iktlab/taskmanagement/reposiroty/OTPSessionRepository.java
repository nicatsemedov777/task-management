package az.iktlab.taskmanagement.reposiroty;

import az.iktlab.taskmanagement.dao.OTPSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPSessionRepository extends JpaRepository<OTPSession,String> {

    @Query("Select o from OTPSession o where o.otpCode = ?1 and o.userId=?2 and o.isUsed=false")
    Optional<OTPSession> findByOtpCodeAndUserIdAndIsUsedFalse(String otpCode, String userId);


}
