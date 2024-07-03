package practice.hhpluslectures.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhpluslectures.service.account.AccountRepository;
import practice.hhpluslectures.service.account.domain.Account;
import practice.hhpluslectures.service.lectures.LecturesRepository;
import practice.hhpluslectures.service.lectures.LecturesService;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccount;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

@MockBean(JpaMetamodelMappingContext.class)
public class LecturesServiceTest {

  @Mock
  LecturesRepository lecturesRepository;

  @Mock
  AccountRepository accountRepository;

  @InjectMocks
  LecturesService lecturesService;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void 특강목록조회_성공케이스() {
    //given
    long id = 1L;
    String title = "강의";
    int currentCapacity = 15;
    long lecturesId = 1L;

    long addOne = 1L;
    long addTwo = 2L;

    List<LecturesSchedule> givenLecturesScheduleList = List.of(
        LecturesSchedule
            .builder()
            .id(id)
            .title(title)
            .currentCapacity(currentCapacity)
            .lecturesId(lecturesId)
            .build()
        , LecturesSchedule
            .builder()
            .id(id + addOne)
            .title(title + addOne)
            .currentCapacity((int) (currentCapacity + addOne))
            .lecturesId(lecturesId + addOne)
            .build()
        , LecturesSchedule
            .builder()
            .id(id + addTwo)
            .title(title + addTwo)
            .currentCapacity((int) (currentCapacity + addTwo))
            .lecturesId(lecturesId + addTwo)
            .build()
    );

    //when
    when(lecturesRepository.getLecturesList()).thenReturn(givenLecturesScheduleList);
    List<LecturesSchedule> whenLecturesList = lecturesService.getLecturesList();

    //then
    for (int i = 0; i < givenLecturesScheduleList.size(); i++) {
      assertEquals(whenLecturesList.get(i).id(), givenLecturesScheduleList.get(i).id());
      assertEquals(whenLecturesList.get(i).title(), givenLecturesScheduleList.get(i).title());
      assertEquals(whenLecturesList.get(i).currentCapacity(), givenLecturesScheduleList.get(i).currentCapacity());
      assertEquals(whenLecturesList.get(i).lecturesId(), givenLecturesScheduleList.get(i).lecturesId());
    }
    assertEquals(whenLecturesList.size(), givenLecturesScheduleList.size());
  }

  @Test
  public void 특강신청_성공케이스() {
    //given
    long accountId = 1L;
    boolean isSuccess = true;
    long lecturesScheduleId = 1L;

    Account givenAccount = Account
        .builder()
        .id(accountId)
        .name("이름")
        .build();

    LecturesSchedule givenLecturesSchedule = LecturesSchedule
        .builder()
        .id(lecturesScheduleId)
        .openAt(LocalDateTime.now())
        .title("타이틀")
        .currentCapacity(15)
        .lecturesId(1L)
        .build();

    LecturesScheduleAccount givenLecturesScheduleAccount = LecturesScheduleAccount
        .builder()
        .id(1L)
        .accountId(accountId)
        .lecturesScheduleId(lecturesScheduleId)
        .build();

    LecturesScheduleAccountHistory lecturesScheduleAccountHistory = LecturesScheduleAccountHistory
        .builder()
        .isSuccess(isSuccess)
        .lecturesScheduleId(lecturesScheduleId)
        .accountId(accountId)
        .build();


    //when
    when(accountRepository.getAccount(accountId)).thenReturn(givenAccount);
    when(lecturesRepository.getLectureSchedulerByLectureSchedulerId(lecturesScheduleId)).thenReturn(givenLecturesSchedule);
    when(lecturesRepository.registerLecture(accountId, lecturesScheduleId)).thenReturn(givenLecturesScheduleAccount);
    when(lecturesRepository.existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId)).thenReturn(false);
    when(lecturesRepository.saveLecturesScheduleAccountHistory(lecturesScheduleAccountHistory)).thenReturn(lecturesScheduleAccountHistory);

    //then
    Boolean thenBoolean = lecturesService.registerLecture(accountId, lecturesScheduleId);
    assertEquals(thenBoolean,isSuccess);

    verify(accountRepository).getAccount(accountId);
    verify(lecturesRepository).getLectureSchedulerByLectureSchedulerId(lecturesScheduleId);
    verify(lecturesRepository).registerLecture(accountId, lecturesScheduleId);
    verify(lecturesRepository).existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId);
    verify(lecturesRepository).saveLecturesScheduleAccountHistory(lecturesScheduleAccountHistory);
  }

  @Test
  public void 특강신청_정원초과_실패케이스() {
    //given
    long accountId = 1L;
    boolean isSuccess = false;
    long lecturesScheduleId = 1L;

    Account givenAccount = Account
        .builder()
        .id(accountId)
        .name("이름")
        .build();

    LecturesSchedule givenLecturesSchedule = LecturesSchedule
        .builder()
        .id(lecturesScheduleId)
        .openAt(LocalDateTime.now())
        .title("타이틀")
        .currentCapacity(30)
        .lecturesId(1L)
        .build();

    LecturesScheduleAccountHistory lecturesScheduleAccountHistory = LecturesScheduleAccountHistory
        .builder()
        .isSuccess(isSuccess)
        .lecturesScheduleId(lecturesScheduleId)
        .accountId(accountId)
        .build();

    //when
    when(accountRepository.getAccount(accountId)).thenReturn(givenAccount);
    when(lecturesRepository.getLectureSchedulerByLectureSchedulerId(lecturesScheduleId)).thenReturn(givenLecturesSchedule);
    when(lecturesRepository.existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId)).thenReturn(false);
    when(lecturesRepository.saveLecturesScheduleAccountHistory(lecturesScheduleAccountHistory)).thenReturn(lecturesScheduleAccountHistory);

    //then
    Boolean thenBoolean = lecturesService.registerLecture(accountId, lecturesScheduleId);
    assertEquals(thenBoolean,isSuccess);

    verify(accountRepository).getAccount(accountId);
    verify(lecturesRepository).getLectureSchedulerByLectureSchedulerId(lecturesScheduleId);
    verify(lecturesRepository).existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId);
    verify(lecturesRepository).saveLecturesScheduleAccountHistory(lecturesScheduleAccountHistory);
  }

  @Test
  public void 특강신청_중복신청_실패케이스() {
    //given
    long accountId = 1L;
    long lecturesScheduleId = 1L;

    Account givenAccount = Account
        .builder()
        .id(accountId)
        .name("이름")
        .build();

    LecturesSchedule givenLecturesSchedule = LecturesSchedule
        .builder()
        .id(lecturesScheduleId)
        .openAt(LocalDateTime.now())
        .title("타이틀")
        .currentCapacity(30)
        .lecturesId(1L)
        .build();

    LecturesScheduleAccount givenLecturesScheduleAccount = LecturesScheduleAccount
        .builder()
        .id(1L)
        .accountId(accountId)
        .lecturesScheduleId(lecturesScheduleId)
        .build();

    //when
    when(accountRepository.getAccount(accountId)).thenReturn(givenAccount);
    when(lecturesRepository.getLectureSchedulerByLectureSchedulerId(lecturesScheduleId)).thenReturn(givenLecturesSchedule);
    when(lecturesRepository.registerLecture(accountId, lecturesScheduleId)).thenReturn(givenLecturesScheduleAccount);
    when(lecturesRepository.existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId)).thenReturn(true);


    String errorMessage = null;
    Exception exception = null;

    //when
    try {
      lecturesService.registerLecture(accountId, lecturesScheduleId);
    } catch (IllegalArgumentException e) {
      exception = e;
      errorMessage = e.getMessage();
    }

    assertThat(exception).isNotNull();
    assertThat(errorMessage).isNotNull();
    assertEquals(errorMessage, "이미 신청한 특강입니다.");

    //then
    verify(accountRepository).getAccount(accountId);
    verify(lecturesRepository).getLectureSchedulerByLectureSchedulerId(lecturesScheduleId);
    verify(lecturesRepository).existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId);
  }

  @Test
  public void 특강신청합격여부목록조회_성공케이스() {
    //given
    long accountId = 1L;
    boolean isSuccess = true;
    long LecturesScheduleAccountHistoryId = 1L;
    long lecturesScheduleId = 1L;

    List<LecturesScheduleAccountHistory> givenLecturesScheduleAccountHistoryList = List.of(
        LecturesScheduleAccountHistory
            .builder()
            .id(LecturesScheduleAccountHistoryId)
            .isSuccess(isSuccess)
            .lecturesScheduleId(lecturesScheduleId)
            .accountId(accountId)
            .build(),
        LecturesScheduleAccountHistory
            .builder()
            .id(LecturesScheduleAccountHistoryId +1)
            .isSuccess(!isSuccess)
            .lecturesScheduleId(lecturesScheduleId +1)
            .accountId(accountId)
            .build(),
        LecturesScheduleAccountHistory
            .builder()
            .id(LecturesScheduleAccountHistoryId +2)
            .isSuccess(isSuccess)
            .lecturesScheduleId(lecturesScheduleId +2)
            .accountId(accountId)
            .build()
    );

    //when
    when(lecturesRepository.getLectureSchedulerAccountHistoryList(accountId)).thenReturn(givenLecturesScheduleAccountHistoryList);
    List<LecturesScheduleAccountHistory> whenLectureSchedulerAccountHistoryList = lecturesService.getLectureSchedulerAccountHistoryList(accountId);

    //then
    for (int i = 0; i < givenLecturesScheduleAccountHistoryList.size(); i++) {
      assertEquals(whenLectureSchedulerAccountHistoryList.get(i).id(), givenLecturesScheduleAccountHistoryList.get(i).id());
      assertEquals(whenLectureSchedulerAccountHistoryList.get(i).isSuccess(), givenLecturesScheduleAccountHistoryList.get(i).isSuccess());
      assertEquals(whenLectureSchedulerAccountHistoryList.get(i).lecturesScheduleId(), givenLecturesScheduleAccountHistoryList.get(i).lecturesScheduleId());
      assertEquals(whenLectureSchedulerAccountHistoryList.get(i).accountId(), givenLecturesScheduleAccountHistoryList.get(i).accountId());
    }
    assertEquals(givenLecturesScheduleAccountHistoryList.size(), whenLectureSchedulerAccountHistoryList.size());
  }
}
