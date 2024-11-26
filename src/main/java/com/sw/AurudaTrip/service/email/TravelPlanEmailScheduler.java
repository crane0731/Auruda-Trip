package com.sw.AurudaTrip.service.email;

import com.sw.AurudaTrip.dto.email.TravelPlanEmailDto;
import com.sw.AurudaTrip.repository.TravelPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TravelPlanEmailScheduler {

    private final TravelPlanRepository travelPlanRepository;
    private final EmailService emailService;


    // 매일 자정 실행 (startDate 전날 메일을 보내는 작업)
    @Scheduled(cron = "0 0 20 * * *") // 매일 오후 8시 실행
    public void sendEmailsForUpcomingTravelPlans() {

        System.out.println("자ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ 스케줄러 시작합니다....................................");
        LocalDate tomorrow = LocalDate.now().plusDays(1);//현재 날짜의 다음날
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = tomorrow.format(formatter); // "20241119" 형식

        List<TravelPlanEmailDto> emailDetails = travelPlanRepository.findEmailDetailsByStartDate(formattedDate);

        for (TravelPlanEmailDto detail : emailDetails) {
            String recipient = detail.getEmail();
            String subject = "여행 일정 알림: " + detail.getTitle();
            String content = String.format(
                    "안녕하세요,\n\n" +
                            "내일(%s)부터 '%s' 지역에서 시작되는 여행 계획 '%s'에 대한 알림입니다.\n\n" +
                            "여행 기간: %s ~ %s\n\n" +
                            "즐거운 여행이 되시길 바랍니다!\n\n" +
                            "감사합니다.",
                    detail.getStartDate(),    // 내일 날짜
                    detail.getRegion(),      // 여행 지역
                    detail.getTitle(),       // 여행 제목
                    detail.getStartDate(),   // 시작 날짜
                    detail.getEndDate()      // 끝 날짜
            );

            emailService.sendEmail(recipient, subject, content);
        }
        }

}
