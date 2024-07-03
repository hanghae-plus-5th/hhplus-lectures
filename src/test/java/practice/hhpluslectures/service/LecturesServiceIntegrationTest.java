package practice.hhpluslectures.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import practice.hhpluslectures.service.account.AccountRepository;
import practice.hhpluslectures.service.account.domain.Account;
import practice.hhpluslectures.service.lectures.LecturesRepository;
import practice.hhpluslectures.service.lectures.LecturesService;
import practice.hhpluslectures.service.lectures.domain.Lectures;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LecturesServiceIntegrationTest {

  @Autowired
  private LecturesService lecturesService;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private LecturesRepository lecturesRepository;


  @Test
  public void 특강신청_동시성_통합테스트_성공테스트() {
    //given
    Account account = accountRepository.save("유저1");
    Account account2 = accountRepository.save("유저2");
    Account account3 = accountRepository.save("유저3");
    Lectures lectures = lecturesRepository.saveLectures("https://www.youtube.com/watch?v=bu0C9np-ZE8");
    LecturesSchedule lecturesSchedule = lecturesRepository.saveLecturesSchedule(LecturesSchedule
        .builder()
        .openAt(LocalDateTime.now())
        .title("스프링 특강")
        .currentCapacity(25)
        .lecturesId(lectures.id())
        .build()
    );

    //when
    CompletableFuture.allOf(
        CompletableFuture.runAsync(() -> {
          lecturesService.registerLecture(account.id(), lecturesSchedule.id());
        }),
        CompletableFuture.runAsync(() -> {
          lecturesService.registerLecture(account2.id(), lecturesSchedule.id());
        }),
        CompletableFuture.runAsync(() -> {
          lecturesService.registerLecture(account3.id(), lecturesSchedule.id());
        })
    ).join();

    //then
    LecturesSchedule thenLecturesSchedule = lecturesRepository.getLectureSchedulerByLectureSchedulerId(lecturesSchedule.id());
    assertEquals(thenLecturesSchedule.lecturesId(), lectures.id());
    assertEquals(thenLecturesSchedule.id(), lecturesSchedule.id());
    assertEquals(thenLecturesSchedule.title(), lecturesSchedule.title());
    assertEquals(thenLecturesSchedule.openAt(), lecturesSchedule.openAt());
    assertEquals(thenLecturesSchedule.currentCapacity(), 25 + 1 + 1 + 1);
  }

  @Test
  public void 특강신청_동시성_통합테스트_정원초과로인한_1개_업데이트안되는_테스트() {
    //given
    Account account = accountRepository.save("유저4");
    Account account2 = accountRepository.save("유저5");
    Account account3 = accountRepository.save("유저6");
    Lectures lectures = lecturesRepository.saveLectures("https://www.youtube.com/watch?v=bu0C9np-ZE8");
    LecturesSchedule lecturesSchedule = lecturesRepository.saveLecturesSchedule(LecturesSchedule
        .builder()
        .openAt(LocalDateTime.now())
        .title("스프링 특강")
        .currentCapacity(28)
        .lecturesId(lectures.id())
        .build()
    );

    //when
    CompletableFuture.allOf(
        CompletableFuture.runAsync(() -> {
          lecturesService.registerLecture(account.id(), lecturesSchedule.id());
        }),
        CompletableFuture.runAsync(() -> {
          lecturesService.registerLecture(account2.id(), lecturesSchedule.id());
        }),
        CompletableFuture.runAsync(() -> {
          lecturesService.registerLecture(account3.id(), lecturesSchedule.id());
        })
    ).join();

    //then
    LecturesSchedule thenLecturesSchedule = lecturesRepository.getLectureSchedulerByLectureSchedulerId(lecturesSchedule.id());
    assertEquals(thenLecturesSchedule.lecturesId(), lectures.id());
    assertEquals(thenLecturesSchedule.id(), lecturesSchedule.id());
    assertEquals(thenLecturesSchedule.title(), lecturesSchedule.title());
    assertEquals(thenLecturesSchedule.openAt(), lecturesSchedule.openAt());
    assertEquals(thenLecturesSchedule.currentCapacity(), 30); //동시성 처리 해놔서 31이 아닌 30

    List<LecturesScheduleAccountHistory> lectureSchedulerAccountHistoryList = lecturesRepository.getLectureSchedulerAccountHistoryList(account.id());
    LecturesScheduleAccountHistory lecturesScheduleAccountHistory = lectureSchedulerAccountHistoryList.stream().findAny().orElse(null);
    assertEquals(lecturesScheduleAccountHistory.accountId(), account.id());
  }
}
