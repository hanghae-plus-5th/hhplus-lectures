package practice.hhpluslectures.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import practice.hhpluslectures.controller.lectures.LecturesController;
import practice.hhpluslectures.service.lectures.LecturesService;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

@WebMvcTest(LecturesController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class LecturesControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  LecturesService lecturesService;


  @Test
  public void 특강신청_성공케이스() throws Exception {
    //given
    long accountId = 1L;
    boolean isSuccess = true;
    long lecturesScheduleId = 1L;

    //when
    when(lecturesService.registerLecture(accountId, lecturesScheduleId)).thenReturn(true);

    //then
    mockMvc.perform(post("/lectures/schedule/{lectures-schedule-id}/account/{account-id}", lecturesScheduleId, accountId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists());

    verify(lecturesService).registerLecture(accountId, lecturesScheduleId);
  }

  @Test
  public void 특강목록조회_성공케이스() throws Exception {
    //given

    //when
    List<LecturesSchedule> lecturesScheduleList = List.of(
        LecturesSchedule
            .builder()
            .id(1L)
            .title("강의1")
            .currentCapacity(15)
            .lecturesId(1L)
            .build()
        , LecturesSchedule
            .builder()
            .id(2L)
            .title("강의2")
            .currentCapacity(15)
            .lecturesId(2L)
            .build()
        , LecturesSchedule
            .builder()
            .id(3L)
            .title("강의3")
            .currentCapacity(15)
            .lecturesId(3L)
            .build()
    );

    when(lecturesService.getLecturesList()).thenReturn(lecturesScheduleList);

    //then
    mockMvc.perform(get("/lectures/schedule"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].title").exists())
        .andExpect(jsonPath("$[0].currentCapacity").exists())
        .andExpect(jsonPath("$[0].lecturesId").exists());

    verify(lecturesService).getLecturesList();
  }

  @Test
  public void 특강신청합격여부목록조회_성공케이스() throws Exception {
    //given
    long accountId = 1L;
    boolean isSuccess = true;
    long LecturesScheduleAccountHistoryId = 1L;
    long lecturesScheduleId = 1L;

    //when
    List<LecturesScheduleAccountHistory> LecturesScheduleAccountHistoryList = List.of(
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
            .isSuccess(isSuccess)
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

    when(lecturesService.getLectureSchedulerAccountHistoryList(accountId)).thenReturn(LecturesScheduleAccountHistoryList);
    //then
    mockMvc.perform(get("/lectures/schedule/history/account/{account-id}", accountId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].isSuccess").exists())
        .andExpect(jsonPath("$[0].lecturesScheduleId").exists())
        .andExpect(jsonPath("$[0].accountId").exists());

    verify(lecturesService).getLectureSchedulerAccountHistoryList(accountId);
  }
}
